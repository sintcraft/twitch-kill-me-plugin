package com.github.sintcraft.plugins.twitchkillmeplugin.rewards;

import com.github.sintcraft.plugins.twitchkillmeplugin.TwitchKillMePlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DropReward {
  public static void run(Player player, BasicReward basicReward, String channelName, String username, String type) {
      if(player.getInventory().isEmpty()) return;
      ConfigurationSection settings = basicReward.getSettings();
      ItemStack item = null;
      int slot = 0;

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
      } else {
        slot = (int) Math.floor(Math.random() * player.getInventory().getContents().length);
        item = player.getInventory().getContents()[slot];
      }

      if(item == null) return;

      if(settings.getString("mode").equalsIgnoreCase("delete")) {
        item.setType(Material.AIR);
      } else {
        if(item.getAmount() > settings.getInt("amount") && settings.getInt("amount") != -1) {
          ItemStack drop = item.clone();
          drop.setAmount(settings.getInt("amount"));
          if(item.getType() != Material.AIR)
            if(item.getType() != Material.AIR) {
              Item dropped = player.getWorld().dropItemNaturally(player.getLocation(), drop);
              dropped.setPickupDelay(60);
            }
          item.setAmount(item.getAmount() - settings.getInt("amount"));
        } else {
          ItemStack drop = item.clone();
          if(item.getType() != Material.AIR) {
            Item dropped = player.getWorld().dropItemNaturally(player.getLocation(), drop);
            dropped.setPickupDelay(60);
          }
          item.setType(Material.AIR);
        }
      }

      if(settings.getString("slot").equalsIgnoreCase("main-hand")) {
        player.getInventory().setItemInMainHand(item);
      } else if (settings.getString("slot").equalsIgnoreCase("second-hand")) {
        player.getInventory().setItemInOffHand(item);
      } else if (settings.getString("slot").equalsIgnoreCase("helmet")) {
        player.getInventory().setHelmet(item);
      } else if (settings.getString("slot").equalsIgnoreCase("chestplate")) {
        player.getInventory().setChestplate(item);
      } else if (settings.getString("slot").equalsIgnoreCase("leggings")) {
        player.getInventory().setLeggings(item);
      } else if (settings.getString("slot").equalsIgnoreCase("boots")) {
        player.getInventory().setBoots(item);
      } else {
        player.getInventory().setItem(slot, item);
      }
  }
}
