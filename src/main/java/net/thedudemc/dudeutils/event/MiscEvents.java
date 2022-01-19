package net.thedudemc.dudeutils.event;

import net.thedudemc.dudeutils.config.GhastLogConfig;
import net.thedudemc.dudeutils.data.GhastLogData;
import net.thedudemc.dudeutils.init.PluginConfigs;
import net.thedudemc.dudeutils.init.PluginData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Ghast;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
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

    @EventHandler
    public void onGhastSpawn(EntitySpawnEvent event) {
        if (!(event.getEntity() instanceof Ghast)) {
            return;
        }

        Location spawnLocation = event.getLocation();
        if (!shouldLog(spawnLocation)) return;

        Block block = spawnLocation.getBlock();

        GhastLogData.GhastSpawnLocation ghastSpawnLocation = new GhastLogData.GhastSpawnLocation(spawnLocation.getBlockX(), spawnLocation.getBlockY(), spawnLocation.getBlockZ(), block.getRelative(BlockFace.DOWN).getType().toString());
        PluginData.GHAST_LOG.add(ghastSpawnLocation);
    }

    private boolean shouldLog(Location location) {
        GhastLogConfig config = PluginConfigs.GHAST_LOG;
        if (config.isServerWide()) return true;

        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        if(x < config.getMinX() || x > config.getMaxX()) return false;
        if(y < config.getMinY() || y > config.getMaxY()) return false;
        return z >= config.getMinZ() && z <= config.getMaxZ();
    }
}
