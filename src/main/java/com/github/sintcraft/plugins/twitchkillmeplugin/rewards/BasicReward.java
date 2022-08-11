package com.github.sintcraft.plugins.twitchkillmeplugin.rewards;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class BasicReward {
  private ConfigurationSection config;
  private ConfigurationSection settings;
  private int id;

  public BasicReward(ConfigurationSection config, int id) {
    this.config = config;
    this.settings = config.getConfigurationSection("settings");
    this.id = id;
  }

  public void run(Player player) {
    // Login for run this reward
  }

  public void sendNotify(Player player) {
    final ConfigurationSection notify = config.getConfigurationSection("notify");

    // Sound
    if(!notify.getString("sound.sound").equalsIgnoreCase("")) {
      player.playSound(
              player.getLocation(),
              notify.getString("sound.sound"),
              (float) notify.getDouble("sound.volume"),
              (float) notify.getDouble("sound.pitch")
      );
    }

    // Title
    if(!notify.getString("title").equalsIgnoreCase("")) {
      player.sendTitle(
              notify.getString("title"),
              notify.getString("subtitle"),
              notify.getInt("title-fadein"),
              notify.getInt("title-fadein"),
              notify.getInt("title-fadeout")
      );
    }

    // Actionbar
    if(!notify.getString("actionbar").equalsIgnoreCase("")) {
      player.sendActionBar(notify.getString("actionbar"));
    }
  }

  public ConfigurationSection getConfig() {
    return config;
  }

  public int getId() {
    return id;
  }

  public ConfigurationSection getSettings() {
    return settings;
  }

  public static double randomizerCord(double radius) {
    double cord = Math.random() * radius;
    if(Math.random() <= 0.5) {
      return -cord;
    } else return cord;
  }
  public static int randomizerCord(int radius) {
    int cord = Math.round((float) Math.random() * radius);
    if(Math.random() <= 0.5) {
      return -cord;
    } else return cord;
  }

  public boolean bitsActivate(int amount) {
    if(config.getString("price.bits").equalsIgnoreCase("none")) return false;
    return config.getInt("price.bits") == amount;
  }

  public boolean pointsActivate(int amount) {
    if(config.getString("price.points").equalsIgnoreCase("none")) return false;
    return config.getInt("price.points") == amount;
  }
}
