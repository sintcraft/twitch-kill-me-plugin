package com.github.sintcraft.plugins.twitchkillmeplugin.rewards;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.material.Stairs;

public class PlaceReward implements Runnable {
  private Player player;
  private BasicReward basicReward;
  private String channelName;
  private String username;
  private String type;
  private ConfigurationSection settings;

  public PlaceReward(Player player, BasicReward basicReward, String channelName, String username, String type) {
    this.player = player;
    this.basicReward = basicReward;
    this.channelName = channelName;
    this.username = username;
    this.type = type;
    this.settings = basicReward.getSettings();
  }

  @Override
  public void run() {
    Location loc;
    if(settings.getString("position").equalsIgnoreCase("player")) {
      loc = player.getLocation();
    } else {
      loc = new Location(player.getLocation().getWorld(), 0, 0,0);
    }
    World world = loc.getWorld();

    for(String key : settings.getConfigurationSection("blocks").getKeys(false)) {
      ConfigurationSection conf = settings.getConfigurationSection("blocks."+key);
      int x = conf.getIntegerList("cords").get(0);
      int y = conf.getIntegerList("cords").get(1);
      int z = conf.getIntegerList("cords").get(2);
      Block block = world.getBlockAt(loc.getBlockX() + x, loc.getBlockY() + y, loc.getBlockZ() + z);
      block.setType(Material.valueOf(conf.getString("material")));
    }
  }
}
