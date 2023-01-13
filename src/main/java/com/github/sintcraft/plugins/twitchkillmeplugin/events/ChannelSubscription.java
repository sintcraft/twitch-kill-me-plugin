package com.github.sintcraft.plugins.twitchkillmeplugin.events;

import com.github.sintcraft.plugins.twitchkillmeplugin.TwitchKillMePlugin;
import com.github.sintcraft.plugins.twitchkillmeplugin.rewards.BasicReward;
import com.github.twitch4j.pubsub.domain.SubscriptionData;
import com.github.twitch4j.pubsub.events.ChannelSubscribeEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ChannelSubscription {
  private TwitchKillMePlugin plugin;
  public ChannelSubscription(TwitchKillMePlugin plugin) {
    this.plugin = plugin;
  }

  public void onSub(ChannelSubscribeEvent e) {
    SubscriptionData sub = e.getData();

    boolean isGift = sub.getIsGift();
    int amount = sub.getCumulativeMonths();
    String channelID = sub.getChannelId();
    String channelName = sub.getChannelName();
    String userName = sub.getUserName();

    for(BasicReward reward : RewardManager.getRewards()) {
      if(!reward.subActive()) continue;
      plugin.getPlayers().forEach(p -> {
        Player player = Bukkit.getPlayer(p);
        if(player == null) return;
        reward.run(player, channelName, userName, channelID, 1, "sub");
      });
    }
  }
}
