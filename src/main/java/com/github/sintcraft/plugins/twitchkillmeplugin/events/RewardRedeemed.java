package com.github.sintcraft.plugins.twitchkillmeplugin.events;

import com.github.sintcraft.plugins.twitchkillmeplugin.TwitchKillMePlugin;
import com.github.sintcraft.plugins.twitchkillmeplugin.rewards.BasicReward;
import com.github.twitch4j.pubsub.domain.ChannelPointsRedemption;
import com.github.twitch4j.pubsub.events.RewardRedeemedEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class RewardRedeemed {
  private TwitchKillMePlugin plugin;
  public RewardRedeemed(TwitchKillMePlugin plugin) {
    this.plugin = plugin;
  }
  public void onRewardRedeemedEvent(RewardRedeemedEvent e){
    ChannelPointsRedemption redemption = e.getRedemption();
    int amount = (int) redemption.getReward().getCost();
    String userName = redemption.getUser().getDisplayName();
    String channelName = redemption.getUser().getDisplayName();
    String channelID = redemption.getReward().getChannelId();

    for(BasicReward reward : RewardManager.getRewards()) {
      if(!reward.pointsActivate(amount)) continue;
      plugin.getPlayers().forEach(p -> {
        Player player = Bukkit.getPlayer(p);
        if(player == null) return;
        reward.run(player, channelName, userName, channelID, amount, "points");
      });
    }
  }
}
