package com.github.sintcraft.plugins.twitchkillmeplugin.rewards;

import com.github.sintcraft.plugins.twitchkillmeplugin.TwitchKillMePlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class GiveReward {
  public static void run(Player player, BasicReward basicReward, String channelName, String username, String type) {
      ItemStack item = new ItemStack(Material.valueOf(basicReward.getSettings().getString("material")));
      ItemMeta meta = item.getItemMeta();

      // Name config
      if (basicReward.getSettings().getString("name") != "") {
        meta.setDisplayName(BasicReward.format(
                basicReward.getSettings().getString("name"),
                channelName,
                username,
                type
        ));
      }

      // Lore config
      if (basicReward.getSettings().getString("lore") != "") {
        List<String> lore = basicReward.getSettings().getStringList("lore").stream().map(line -> {
          return BasicReward.format(line, channelName, username, type);
        }).toList();
        meta.setLore(lore);
      }

      // Meta config
      item.setItemMeta(meta);

      // Amount config
      item.setAmount(basicReward.getSettings().getInt("amount"));

      // Give to player
      int slot = basicReward.getSettings().getInt("slot");
      Bukkit.getScheduler().runTask(TwitchKillMePlugin.getInstance(), () -> {
        if (slot == -1) {
          player.getInventory().addItem(item);
        } else {
          ItemStack replace = player.getInventory().getItem(slot);
          player.getInventory().setItem(slot, item);
          player.getInventory().addItem(replace);
        }
      });
  }
}
