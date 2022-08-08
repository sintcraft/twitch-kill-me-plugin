package com.github.sintcraft.plugins.twitchkillmeplugin;

import org.bukkit.plugin.java.JavaPlugin;

public final class TwitchKillMePlugin extends JavaPlugin {
  boolean enable = false;
  private static TwitchService twitchService;

  @Override
  public void onEnable() {
    // Plugin startup logic
    saveDefaultConfig();
    twitchService = new TwitchService(this);
  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
  }

  public static TwitchService getTwitchService() {
    return twitchService;
  }
}
