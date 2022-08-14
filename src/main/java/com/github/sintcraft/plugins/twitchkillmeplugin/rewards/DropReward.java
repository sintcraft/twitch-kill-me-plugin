package com.github.sintcraft.plugins.twitchkillmeplugin.rewards;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DropReward {
  public static void run(Player player, BasicReward basicReward, String channelName, String username, String type) {
    if(player.getInventory().isEmpty()) return;
    ConfigurationSection settings = basicReward.getSettings();
    ItemStack item = null;

    if(settings.getString("slot").equalsIgnoreCase("main-hand")) {
      item = player.getInventory().getItemInMainHand();
    } else if (settings.getString("slot").equalsIgnoreCase("second-hand")) {
      item = player.getInventory().getItemInOffHand();
    } else if (settings.getString("slot").equalsIgnoreCase("helmet")) {
      item = player.getInventory().getHelmet();
    } else if (settings.getString("slot").equalsIgnoreCase("chestplate")) {
      item = player.getInventory().getChestplate();
    } else if (settings.getString("slot").equalsIgnoreCase("leggings")) {
      item = player.getInventory().getLeggings();
    } else if (settings.getString("slot").equalsIgnoreCase("boots")) {
      item = player.getInventory().getBoots();
    } else if (settings.getString("slot").equalsIgnoreCase("random")) {
      item = player.getInventory().getContents()[(int) Math.floor(Math.random() * player.getInventory().getContents().length)];
    }

    if(item == null) return;
  }
}
