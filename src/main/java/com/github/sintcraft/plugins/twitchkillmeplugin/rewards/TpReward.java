package com.github.sintcraft.plugins.twitchkillmeplugin.rewards;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class TpReward implements Runnable {
  private Player player;
  private BasicReward basicReward;
  private String channelName;
  private String username;
  private String type;
  private ConfigurationSection settings;

  public TpReward(Player player, BasicReward basicReward, String channelName, String username, String type) {
    this.player = player;
    this.basicReward = basicReward;
    this.channelName = channelName;
    this.username = username;
    this.type = type;
    this.settings = basicReward.getSettings();
  }

  @Override
  public void run() {
    Location loc = player.getLocation();
    if(settings.getString("mode").equalsIgnoreCase("random")) {
      loc.set(
              BasicReward.randomizerCord(settings.getDouble("x")) + loc.getX(),
              BasicReward.randomizerCord(settings.getDouble("y")) + loc.getY(),
              BasicReward.randomizerCord(settings.getDouble("z")) + loc.getZ()
      );
    } else if (settings.getString("mode").equalsIgnoreCase("equals")) {
      loc.set(
              settings.getDouble("x") + loc.getX(),
              settings.getDouble("y") + loc.getY(),
              settings.getDouble("z") + loc.getZ()
      );
    } else if (settings.getString("mode").equalsIgnoreCase("random-center")) {
      loc.set(
              BasicReward.randomizerCord(settings.getDouble("x")),
              BasicReward.randomizerCord(settings.getDouble("y")),
              BasicReward.randomizerCord(settings.getDouble("z"))
      );
    }

    if(settings.getBoolean("top-world")) {
      loc.set(
              loc.getX(),
              loc.getWorld().getHighestBlockYAt(loc) + 1.1,
              loc.getZ()
      );
    }

    player.teleport(loc);
  }
}
