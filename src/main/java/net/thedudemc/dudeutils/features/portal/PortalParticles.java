package net.thedudemc.dudeutils.features.portal;

import net.thedudemc.dudeutils.DudeUtils;
import net.thedudemc.dudeutils.event.PortalEvents;
import net.thedudemc.dudeutils.util.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collection;

public class PortalParticles {

    private static BukkitTask task;

    public static void runSpawner() {
        task = Bukkit.getScheduler().runTaskTimer(DudeUtils.INSTANCE, new Runnable() {
            @Override
            public void run() {
                Collection<? extends Player> players = Bukkit.getOnlinePlayers();
                for (Player p : players) {
                    if (!PortalHelper.portals.containsKey(p))
                        continue;

                    if (PortalEvents.timers.get(p) <= 0) {
                        Location location = PortalHelper.portals.remove(p);
                        PortalEvents.timers.remove(p);
                        p.sendMessage("Particles Despawned at: " + StringUtils.getCoordinateString(location));
                        continue;
                    }

                    Location location = PortalHelper.portals.get(p);
                    if (!location.getWorld().isChunkLoaded(location.getBlockX() / 16, location.getBlockZ() / 16)) {
                        continue;
                    }

                    World world = p.getWorld();
                    double x = location.getBlockX() + 0.5;
                    double z = location.getBlockZ() + 0.5;
                    for (int i = 1; i < 128; i++) {
                        world.spawnParticle(Particle.DRAGON_BREATH, x, i, z, 1, 0D, 0D, 0D, 0.005);
                    }
                    long timeRemaining = PortalEvents.timers.get(p) - 1;
                    PortalEvents.timers.put(p, timeRemaining);
                }

            }
        }, 60L, 20L);
    }

    public static void cancelParticleSpawner() {
        if (task != null)
            Bukkit.getScheduler().cancelTask(task.getTaskId());
    }
}
