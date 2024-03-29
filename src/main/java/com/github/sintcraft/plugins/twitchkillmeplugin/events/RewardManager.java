package com.github.sintcraft.plugins.twitchkillmeplugin.events;

import com.github.sintcraft.plugins.twitchkillmeplugin.TwitchKillMePlugin;
import com.github.sintcraft.plugins.twitchkillmeplugin.TwitchService;
import com.github.sintcraft.plugins.twitchkillmeplugin.rewards.BasicReward;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.pubsub.domain.HypeTrainParticipations;
import com.github.twitch4j.pubsub.domain.HypeTrainStart;
import com.github.twitch4j.pubsub.events.ChannelBitsEvent;
import com.github.twitch4j.pubsub.events.ChannelSubscribeEvent;
import com.github.twitch4j.pubsub.events.HypeTrainStartEvent;
import com.github.twitch4j.pubsub.events.RewardRedeemedEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class RewardManager {
  private List<String> channelIds;
  private TwitchClient twitchClient;
  private TwitchKillMePlugin plugin;
  private static List<BasicReward> rewards;

  public void registerListener(TwitchClient client, TwitchKillMePlugin plugin, TwitchService twitchService){
    this.twitchClient = client;
    this.plugin = plugin;
    this.channelIds = plugin.getConfig().getStringList("twitch.channels");
    for(String channelId : this.channelIds){
      twitchClient.getPubSub().listenForChannelPointsRedemptionEvents(twitchService.getCredential(), channelId);
      twitchClient.getPubSub().listenForBitsBadgeEvents(twitchService.getCredential(), channelId);
      twitchClient.getPubSub().listenForSubscriptionEvents(twitchService.getCredential(), channelId);
      twitchClient.getPubSub().listenForChannelSubGiftsEvents(twitchService.getCredential(), channelId);
      twitchClient.getPubSub().listenForHypeTrainEvents(twitchService.getCredential(), channelId);
    }
    this.registerEvents();
  }
  private void registerEvents(){
    this.twitchClient.getEventManager().onEvent(RewardRedeemedEvent.class, new RewardRedeemed(plugin)::onRewardRedeemedEvent);
    this.twitchClient.getEventManager().onEvent(ChannelBitsEvent.class, new ChannelBits(plugin)::onChannelBitsEvent);
    this.twitchClient.getEventManager().onEvent(ChannelSubscribeEvent.class, new ChannelSubscription(plugin)::onSub);
    this.twitchClient.getEventManager().onEvent(HypeTrainStartEvent.class, this::onHypeTrainStart);
  }

  public static List<BasicReward> getRewards() {
    return rewards;
  }

  public static void registerRewards(ConfigurationSection config) {
    rewards = new ArrayList<>();
    config.getKeys(false).forEach(key -> {
      int id = Integer.valueOf(key);
      ConfigurationSection rewardConfig = config.getConfigurationSection(key);
      rewards.add(new BasicReward(rewardConfig, id));
    });
    TwitchKillMePlugin.getInstance().getLogger().info(ChatColor.translateAlternateColorCodes('&',
            String.format("&aRegister &e%s &arewards!", rewards.size())
    ));
  }

  private void onHypeTrainStart(HypeTrainStartEvent e) {
    HypeTrainStart train = e.getData();

    String channelID = train.getChannelId();
    HypeTrainParticipations hypeTrainParticipations = train.getParticipations();
    int level = train.getProgress().getLevel().getValue();

    for(BasicReward reward : rewards) {
      if(!reward.hypeTrainActivate(level)) continue;
      plugin.getPlayers().forEach(p -> {
        Player player = Bukkit.getPlayer(p);
        if(player == null) return;
        reward.run(player, "none", "chat", channelID, level, "train");
      });
    }
  }
}