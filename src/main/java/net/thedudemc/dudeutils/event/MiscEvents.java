package net.thedudemc.dudeutils.event;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

public class MiscEvents implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onUseOldPortal(PlayerPortalEvent event) {
        World world = event.getFrom().getWorld();
        if (world != null && world.getName().contains("world_old")) {
            World spawnWorld = Bukkit.getWorld("world");
            if (spawnWorld != null) {
                event.setCancelled(true);
                event.getPlayer().teleport(spawnWorld.getSpawnLocation());
            }
        }
    }
}
