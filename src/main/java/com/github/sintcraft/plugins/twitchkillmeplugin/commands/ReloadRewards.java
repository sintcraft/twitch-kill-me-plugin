package com.github.sintcraft.plugins.twitchkillmeplugin.commands;

import com.github.sintcraft.plugins.twitchkillmeplugin.TwitchKillMePlugin;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.executors.ExecutorType;
import org.bukkit.ChatColor;

public class ReloadRewards {
  public static CommandAPICommand reload = new CommandAPICommand("reload")
          .withPermission("twitchkillme.commands.reload")
          .executes((sender, args) -> {
            TwitchKillMePlugin.getInstance().reload();
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aConfig and rewards reloaded!"));
          }, ExecutorType.ALL);
}
