package net.thedudemc.dudeutils.features;

import net.thedudemc.dudeutils.data.AllySaveData;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityTargetEvent;

import java.util.UUID;

public class AllyFeature extends Feature {

    @Override
    public String getName() {
        return "ally";
    }

    @Override
    public void doEnable() {
    }

    @Override
    public void doDisable() {
    }

    @EventHandler
    public void onTarget(EntityTargetEvent event) {
        if (!isEnabled()) return;

        if (!(event.getEntity() instanceof Wolf)) {
            return;
        }
        Wolf wolf = (Wolf) event.getEntity();
        Entity target = event.getTarget();
        AnimalTamer tamer = wolf.getOwner();
        if (target == null || tamer == null) {
            return;
        }

        if (isAlly(tamer.getUniqueId(), target.getUniqueId())) {
            event.setTarget(null);
        }
    }

    private boolean isAlly(UUID owner, UUID target) {
        AllySaveData data = this.getSaveData();
        return data.getAllies(owner).contains(target);
    }
}
