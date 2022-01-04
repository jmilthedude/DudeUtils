package net.thedudemc.dudeutils.features.portalutility;

import net.thedudemc.dudeutils.features.Feature;
import net.thedudemc.dudeutils.features.FeatureListener;
import net.thedudemc.dudeutils.util.StringUtils;
import net.thedudemc.dudeutils.util.Tickable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public class PortalUtilityFeature extends Feature implements Tickable {

    private static final HashMap<UUID, Location> portals = new HashMap<>();
    private static final HashMap<UUID, Long> timers = new HashMap<>();

    public static HashMap<UUID, Location> getPortals() {
        return portals;
    }

    public static HashMap<UUID, Long> getTimers() {
        return timers;
    }

    @Override
    public String getName() {
        return "portal_utility";
    }

    @Override
    public FeatureListener getListener() {
        return new PortalUtilityListener(this);
    }

    @Override
    public void tick() {
        if (this.isEnabled()) calculatePortal();
    }

    @Override
    public int frequency() {
        return 20;
    }

    private void calculatePortal() {
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        players.forEach(player -> {
            UUID id = player.getUniqueId();
            if (portals.containsKey(player.getUniqueId())) {

                Location location = portals.get(id);
                if (isChunkLoaded(location)) {
                    spawnParticles(id, location);
                }

                if (decrementTimer(id) <= 0) {
                    removeParticles(player);
                }
            }
        });
    }

    private void spawnParticles(UUID p, Location location) {
        World world = location.getWorld();
        if (world == null) return;
        double x = location.getBlockX() + 0.5;
        double z = location.getBlockZ() + 0.5;
        for (int i = 1; i < 256; i++) {
            world.spawnParticle(Particle.DRAGON_BREATH, x, i, z, 1, 0D, 0D, 0D, 0.005);
        }
    }

    public static void activateParticles(Player player, Location oppositeLocation) {
        World world = oppositeLocation.getWorld();
        if (world == null) return;

        PortalUtilityFeature.getPortals().put(player.getUniqueId(), oppositeLocation);
        PortalUtilityFeature.getTimers().put(player.getUniqueId(), 300L);
        player.sendMessage(StringUtils.getDimensionName(oppositeLocation.getWorld().getEnvironment()) + " Portal Location: " + StringUtils.getCoordinateString(oppositeLocation));
    }

    public static void removeParticles(Player player) {
        Location location = PortalUtilityFeature.getPortals().remove(player.getUniqueId());
        PortalUtilityFeature.getTimers().remove(player.getUniqueId());
        player.sendMessage("Particles Despawned at: " + StringUtils.getCoordinateString(location));
    }

    private boolean isChunkLoaded(Location location) {
        World world = location.getWorld();
        if (world == null) return false;
        return world.getChunkAt(location).isLoaded();
    }

    private long decrementTimer(UUID p) {
        long timeRemaining = timers.get(p) - 1;
        timers.put(p, timeRemaining);
        return timeRemaining;
    }
}
