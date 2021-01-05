package net.thedudemc.dudeutils.event;

import net.thedudemc.dudeutils.init.PluginConfigs;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

public class PlayerEvents implements Listener {

    @EventHandler
    public void onTarget(EntityTargetEvent event) {
        if (event.getTarget() instanceof Player) {
            if (isHostile(event.getEntity())) {
                Player player = (Player) event.getTarget();
                if (PluginConfigs.SPECIAL.IGNORED_BY_HOSTILES.contains(player.getName())) {
                    event.setCancelled(true);
                }
            }
        }
    }

    private boolean isHostile(Entity entity) {
        if (entity instanceof Monster) {
            return true;
        } else if (entity.getType() == EntityType.SLIME) {
            return true;
        } else if (entity.getType() == EntityType.PHANTOM) {
            return true;
        }
        return false;
    }

}
