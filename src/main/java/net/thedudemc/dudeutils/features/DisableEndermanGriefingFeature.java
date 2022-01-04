package net.thedudemc.dudeutils.features;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class DisableEndermanGriefingFeature extends Feature {
    @Override
    public String getName() {
        return "disable_enderman_griefing";
    }

    @Override
    public void onEnabled() {

    }

    @Override
    public void onDisabled() {

    }

    @EventHandler
    public void onEndermanGrief(EntityChangeBlockEvent event) {
        if (!this.isEnabled()) return;

        if (event.getEntityType() != EntityType.ENDERMAN) return;
        event.setCancelled(true);
    }
}
