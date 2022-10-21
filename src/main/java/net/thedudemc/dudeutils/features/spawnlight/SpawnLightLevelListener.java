package net.thedudemc.dudeutils.features.spawnlight;

import net.thedudemc.dudeutils.features.Feature;
import net.thedudemc.dudeutils.features.FeatureListener;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.List;

public class SpawnLightLevelListener extends FeatureListener {
    public SpawnLightLevelListener(Feature feature) {
        super(feature);
    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent event) {
        if (!this.feature.isEnabled()) return;
        if (event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.NATURAL) return;
        if (!event.getEntity().getWorld().getEnvironment().equals(World.Environment.NETHER)) return;
        if (!getAffected().contains(event.getEntity().getType())) return;

        Location location = event.getLocation();
        int light = location.getBlock().getLightLevel();
        if (light > 0) {
            event.setCancelled(true);
        }
    }

    private List<EntityType> getAffected() {
        return List.of(EntityType.SKELETON, EntityType.WITHER_SKELETON, EntityType.ENDERMAN, EntityType.PIGLIN);
    }
}
