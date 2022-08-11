package com.github.sintcraft.plugins.twitchkillmeplugin.events;

import com.github.sintcraft.plugins.twitchkillmeplugin.TwitchKillMePlugin;
import com.github.sintcraft.plugins.twitchkillmeplugin.TwitchService;
import com.github.sintcraft.plugins.twitchkillmeplugin.rewards.BasicReward;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.pubsub.domain.ChannelBitsData;
import com.github.twitch4j.pubsub.domain.ChannelPointsRedemption;
import com.github.twitch4j.pubsub.events.ChannelBitsEvent;
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
    }
    this.registerEvents();
  }
  private void registerEvents(){
    this.twitchClient.getEventManager().onEvent(RewardRedeemedEvent.class, this::onRewardRedeemedEvent);
    this.twitchClient.getEventManager().onEvent(ChannelBitsEvent.class, this::onChannelBitsEvent);
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
        reward.run(player);
      });
      break;
    };

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
        reward.run(player);
      });
      break;
    };

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
}