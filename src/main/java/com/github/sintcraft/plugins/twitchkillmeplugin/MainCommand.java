package com.github.sintcraft.plugins.twitchkillmeplugin;

import com.github.sintcraft.plugins.twitchkillmeplugin.commands.ReloadRewards;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.executors.ExecutorType;

public class MainCommand {
  private TwitchKillMePlugin plugin;

  public MainCommand(TwitchKillMePlugin plugin) {
    this.plugin = plugin;
    command.register();
  }

  private CommandAPICommand command = new CommandAPICommand("twitch")
          .withAliases("twitchkillme", "tkm")
          .withPermission("twitchkillme.commands.twitchkillme")
          .withSubcommand(ReloadRewards.reload)
          .executes((sender, args)->{}, ExecutorType.ALL);
}
