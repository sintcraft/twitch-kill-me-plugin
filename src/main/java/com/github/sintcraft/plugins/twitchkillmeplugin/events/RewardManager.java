package com.github.sintcraft.plugins.twitchkillmeplugin.events;

import com.github.sintcraft.plugins.twitchkillmeplugin.TwitchKillMePlugin;
import com.github.sintcraft.plugins.twitchkillmeplugin.TwitchService;
import com.github.sintcraft.plugins.twitchkillmeplugin.rewards.BasicReward;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.helix.domain.Subscription;
import com.github.twitch4j.helix.domain.SubscriptionEvent;
import com.github.twitch4j.pubsub.domain.ChannelBitsData;
import com.github.twitch4j.pubsub.domain.ChannelPointsRedemption;
import com.github.twitch4j.pubsub.domain.HypeTrainParticipations;
import com.github.twitch4j.pubsub.domain.HypeTrainStart;
import com.github.twitch4j.pubsub.events.ChannelBitsEvent;
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
    this.twitchClient.getEventManager().onEvent(RewardRedeemedEvent.class, this::onRewardRedeemedEvent);
    this.twitchClient.getEventManager().onEvent(ChannelBitsEvent.class, this::onChannelBitsEvent);
    this.twitchClient.getEventManager().onEvent(SubscriptionEvent.class, this::onSub);
    this.twitchClient.getEventManager().onEvent(HypeTrainStartEvent.class, this::onHypeTrainStart);
  }

  private void onRewardRedeemedEvent(RewardRedeemedEvent e){
    ChannelPointsRedemption redemption = e.getRedemption();
    int amount = (int) redemption.getReward().getCost();
    String userName = redemption.getUser().getDisplayName();
    String channelName = redemption.getUser().getDisplayName();
    String channelID = redemption.getReward().getChannelId();

    for(BasicReward reward : rewards) {
      if(!reward.pointsActivate(amount)) continue;
      plugin.getPlayers().forEach(p -> {
        Player player = Bukkit.getPlayer(p);
        if(player == null) return;
        reward.run(player, channelName, userName, channelID, amount, "points");
      });
    }

    System.out.println(String.format("%s, %s, %s, %s", amount, userName, channelName, channelID));
  }

  private void onChannelBitsEvent(ChannelBitsEvent e) {
    ChannelBitsData bits = e.getData();
    int amount = bits.getTotalBitsUsed();
    String channelID = bits.getChannelId();
    String channelName = bits.getChannelName();
    String userName = bits.getUserName();

    for(BasicReward reward : rewards) {
      if(!reward.bitsActivate(amount)) continue;
      plugin.getPlayers().forEach(p -> {
        Player player = Bukkit.getPlayer(p);
        if(player == null) return;
        reward.run(player, channelName, userName, channelID, amount, "bits");
      });
    }

    System.out.println(String.format("%s, %s, %s, %s", amount, userName, channelName, channelID));
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

  private void onSub(SubscriptionEvent e) {
    Subscription sub = e.getEventData();

    boolean amount = sub.getIsGift();
    String channelID = sub.getBroadcasterId();
    String channelName = sub.getBroadcasterName();
    String userName = sub.getUserName();
  }

  private void onHypeTrainStart(HypeTrainStartEvent e) {
    HypeTrainStart train = e.getData();

    String channelID = train.getChannelId();
    HypeTrainParticipations hypeTrainParticipations = train.getParticipations();
    int level = train.getProgress().getLevel().getValue();
  }
}