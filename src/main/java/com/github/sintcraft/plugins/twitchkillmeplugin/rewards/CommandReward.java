package com.github.sintcraft.plugins.twitchkillmeplugin.rewards;

import com.github.sintcraft.plugins.twitchkillmeplugin.TwitchKillMePlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.List;

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
    List<String> cmds = settings.getStringList("commands");
    for(int i = 0; i < cmds.size(); i++) {
      int finalI = i;
      Bukkit.getScheduler().runTaskLater(TwitchKillMePlugin.getInstance(), () -> {
        if(settings.getString("sender").equalsIgnoreCase("player")) {
          for(String name : TwitchKillMePlugin.getInstance().getConfig().getStringList("players")) {
            Player p = Bukkit.getPlayer(name);
            if(p == null) continue;
            p.performCommand(cmds.get(finalI));
          }
        } else if(settings.getString("sender").equalsIgnoreCase("console")) {
          Bukkit.getServer().dispatchCommand(
                  Bukkit.getServer().getConsoleSender(),
                  cmds.get(finalI)
          );
        }
      }, i * settings.getInt("delay"));
    }
  }
}
