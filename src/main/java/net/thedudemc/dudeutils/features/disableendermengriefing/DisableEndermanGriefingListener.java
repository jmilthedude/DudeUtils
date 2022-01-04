package net.thedudemc.dudeutils.features.disableendermengriefing;

import net.thedudemc.dudeutils.features.Feature;
import net.thedudemc.dudeutils.features.FeatureListener;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class DisableEndermanGriefingListener extends FeatureListener {

    public DisableEndermanGriefingListener(Feature feature) {
        super(feature);
    }


    @EventHandler
    public void onEndermanGrief(EntityChangeBlockEvent event) {
        if (!feature.isEnabled()) return;

        if (event.getEntityType() != EntityType.ENDERMAN) return;
        event.setCancelled(true);
    }
}
