package com.github.sintcraft.plugins.twitchkillmeplugin;

import com.github.sintcraft.plugins.twitchkillmeplugin.events.RewardManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class TwitchKillMePlugin extends JavaPlugin {
  private boolean enable = false;
  private List<String> players;
  private static TwitchService twitchService;
  private static TwitchKillMePlugin instance;

  @Override
  public void onEnable() {
    // Plugin startup logic
    instance = this;
    reload();
    twitchService = new TwitchService(this);
  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
  }

  public static TwitchService getTwitchService() {
    return twitchService;
  }

  public static TwitchKillMePlugin getInstance() {
    return instance;
  }

  public void reload() {
    saveDefaultConfig();
    reloadConfig();
    players = getConfig().getStringList("players");
    RewardManager.registerRewards(getConfig().getConfigurationSection("rewards"));
  }

  public List<String> getPlayers() {
    return players;
  }
}
