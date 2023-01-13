package com.github.sintcraft.plugins.twitchkillmeplugin.events;

import com.github.sintcraft.plugins.twitchkillmeplugin.TwitchKillMePlugin;
import com.github.sintcraft.plugins.twitchkillmeplugin.rewards.BasicReward;
import com.github.twitch4j.pubsub.domain.ChannelBitsData;
import com.github.twitch4j.pubsub.events.ChannelBitsEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ChannelBits {
  private TwitchKillMePlugin plugin;
  public ChannelBits(TwitchKillMePlugin plugin) {
    this.plugin = plugin;
  }

  public void onChannelBitsEvent(ChannelBitsEvent e) {
    ChannelBitsData bits = e.getData();
    int amount = bits.getTotalBitsUsed();
    String channelID = bits.getChannelId();
    String channelName = bits.getChannelName();
    String userName = bits.getUserName();

    for(BasicReward reward : RewardManager.getRewards()) {
      if(!reward.bitsActivate(amount)) continue;
      plugin.getPlayers().forEach(p -> {
        Player player = Bukkit.getPlayer(p);
        if(player == null) return;
        reward.run(player, channelName, userName, channelID, amount, "bits");
      });
    }
  }
}
