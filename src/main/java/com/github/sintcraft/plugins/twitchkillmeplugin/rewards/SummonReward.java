package com.github.sintcraft.plugins.twitchkillmeplugin.rewards;
import org.bukkit.Location;
import org.bukkit.entity.*;

public class SummonReward {
  public void run(Player player, BasicReward basicReward) {
    double radius = basicReward.getSettings().getDouble("summon-radius");
    Location loc = player.getLocation().add(BasicReward.randomizerCord(radius), 0, BasicReward.randomizerCord(radius));
    LivingEntity entity = (LivingEntity) player.getWorld().spawnEntity(
            loc,
            EntityType.valueOf(basicReward.getSettings().getString("type")),
            basicReward.getSettings().getBoolean("equip-default")
    );
    entity.setCustomName(basicReward.getSettings().getString("name"));
    entity.setMaxHealth(basicReward.getSettings().getDouble("health"));
    entity.resetMaxHealth();
  }
}
