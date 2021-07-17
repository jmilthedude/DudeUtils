package net.thedudemc.dudeutils.event;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class EndermanEvents implements Listener {

    @EventHandler
    public void onEndermanGrief(EntityChangeBlockEvent event) {
        if (event.getEntityType() != EntityType.ENDERMAN) return;

        event.setCancelled(true);
    }
}
