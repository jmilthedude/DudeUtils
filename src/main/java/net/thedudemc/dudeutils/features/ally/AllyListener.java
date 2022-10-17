package net.thedudemc.dudeutils.features.ally;

import net.thedudemc.dudeutils.data.AllySaveData;
import net.thedudemc.dudeutils.features.Feature;
import net.thedudemc.dudeutils.features.FeatureListener;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityTargetEvent;

import java.util.UUID;

public class AllyListener extends FeatureListener {

    public AllyListener(Feature feature) {
        super(feature);
    }

    @EventHandler
    public void onTarget(EntityTargetEvent event) {
        if (!feature.isEnabled()) return;

        if (!(event.getEntity() instanceof Wolf wolf)) {
            return;
        }
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
        AllySaveData data = feature.getSaveData();
        return data.getAllies(owner).contains(target);
    }

}
