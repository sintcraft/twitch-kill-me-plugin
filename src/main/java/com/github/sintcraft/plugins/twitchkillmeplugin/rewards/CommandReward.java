package com.github.sintcraft.plugins.twitchkillmeplugin.rewards;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class CommandReward implements Runnable {
  private Player player;
  private BasicReward basicReward;
  private String channelName;
  private String username;
  private String type;
  private ConfigurationSection settings;

  public CommandReward(Player player, BasicReward basicReward, String channelName, String username, String type) {
    this.player = player;
    this.basicReward = basicReward;
    this.channelName = channelName;
    this.username = username;
    this.type = type;
    this.settings = basicReward.getSettings();
  }

  @Override
  public void run() {

  }
}
