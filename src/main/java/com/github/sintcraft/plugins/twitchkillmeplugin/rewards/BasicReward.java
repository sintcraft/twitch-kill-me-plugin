package com.github.sintcraft.plugins.twitchkillmeplugin.rewards;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
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

  public void run(Player player, String channelName, String username, String channelID, int amount, String type) {
    if(config.getConfigurationSection("notify") != null) sendNotify(player, channelName, username, type);
    // Login for run this reward
    if(config.getString("preset").equalsIgnoreCase("summon")) {
      SummonReward.run(player, this, channelName, username, type);
    } else if (config.getString("preset").equalsIgnoreCase("give")) {
      GiveReward.run(player, this, channelName, username, type);
    } else if (config.getString("preset").equalsIgnoreCase("give")) {

    } else if (config.getString("preset").equalsIgnoreCase("drop")) {

    } else if (config.getString("preset").equalsIgnoreCase("tp")) {

    }
  }

  public void sendNotify(Player player, String channelName, String username, String type) {
    final ConfigurationSection notify = config.getConfigurationSection("notify");

    // Sound
    if(!notify.getString("sound.sound").equalsIgnoreCase("")) {
      player.playSound(
              player.getLocation(),
              Sound.valueOf(notify.getString("sound.sound")),
              (float) notify.getDouble("sound.volume"),
              (float) notify.getDouble("sound.pitch")
      );
    }

    // Title
    if(!notify.getString("title").equalsIgnoreCase("")) {
      player.sendTitle(
              format(notify.getString("title"), channelName, username, type),
              format(notify.getString("subtitle"), channelName, username, type),
              notify.getInt("title-fadein"),
              notify.getInt("title-fadein"),
              notify.getInt("title-fadeout")
      );
    }

    // Actionbar
    if(!notify.getString("actionbar").equalsIgnoreCase("")) {
      player.sendActionBar(format(notify.getString("actionbar"), channelName, username, type));
    }

    // Chat
    if(!notify.getString("minecraft-msg").equalsIgnoreCase("")) {
      player.sendMessage(format(notify.getString("minecraft-msg"), channelName, username, type));
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
  public static String format(String txt, String channelName, String username, String type) {
    return ChatColor.translateAlternateColorCodes('&',
            txt.replaceAll("%username%", username)
                    .replaceAll("%streamer%", channelName)
                    .replaceAll("%type%", type)
    );
  }
}
