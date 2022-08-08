package com.github.sintcraft.plugins.twitchkillmeplugin.rewards;

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

  public void run() {

  }

  public void sendNotify(Player player) {
    final ConfigurationSection notify = config.getConfigurationSection("notify");

    // Sound
    player.playSound(
            player.getLocation(),
            notify.getString("sound.sound"),
            (float) notify.getDouble("sound.volume"),
            (float) notify.getDouble("sound.pitch")
    );

    // Title
    player.sendTitle(
            notify.getString("title"),
            notify.getString("subtitle"),
            notify.getInt("title-fadein"),
            notify.getInt("title-fadein"),
            notify.getInt("title-fadeout")
    );

    // Actionbar
    player.sendActionBar(notify.getString("actionbar"));
  }
}
