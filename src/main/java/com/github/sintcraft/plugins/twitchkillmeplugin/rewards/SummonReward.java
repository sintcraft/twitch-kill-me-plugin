package com.github.sintcraft.plugins.twitchkillmeplugin.rewards;
import com.github.sintcraft.plugins.twitchkillmeplugin.TwitchKillMePlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.*;

public class SummonReward {
  public static void run(Player player, BasicReward basicReward, String channelName, String username, String type) {
    for(int i = 0; i < basicReward.getSettings().getInt("amount"); i++) {
      double radius = basicReward.getSettings().getDouble("summon-radius");
      Location loc = player.getLocation().add(BasicReward.randomizerCord(radius), 0, BasicReward.randomizerCord(radius));
      if(basicReward.getSettings().getBoolean("top-world")) {
        Block block = player.getWorld().getHighestBlockAt(Math.toIntExact(Math.round(loc.getX())), Math.toIntExact(Math.round(loc.getZ())));
        loc.set(
                loc.getX(),
                block.getY() + 1.2,
                loc.getZ()
        );
      }
      Bukkit.getScheduler().runTask(TwitchKillMePlugin.getInstance(), () -> {
        LivingEntity entity = (LivingEntity) player.getWorld().spawnEntity(
                loc,
                EntityType.valueOf(basicReward.getSettings().getString("type")),
                basicReward.getSettings().getBoolean("equip-default")
        );
        entity.setCustomName(BasicReward.format(basicReward.getSettings().getString("name"), channelName, username, type));
        entity.setMaxHealth(basicReward.getSettings().getDouble("health"));
        entity.resetMaxHealth();
      });
    }
  }
}
