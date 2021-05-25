package net.thedudemc.dudeutils.features.magnet;

import net.thedudemc.dudeutils.DudeUtils;
import net.thedudemc.dudeutils.init.PluginConfigs;
import net.thedudemc.dudeutils.util.VectorHelper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class MagnetHelper {

    public static List<UUID> magnetizedPlayers = new ArrayList<UUID>();

    private static BukkitTask task;

    public static void addPlayer(UUID uuid) {
        if (!magnetizedPlayers.contains(uuid)) {
            magnetizedPlayers.add(uuid);
        }
    }

    public static void removePlayer(UUID uuid) {
        if (magnetizedPlayers.contains(uuid)) {
            magnetizedPlayers.remove(uuid);
        }
    }

    public static boolean isWearingItem(Player player) {
        ItemStack item = player.getInventory().getHelmet();

        if (item != null) {
            Material mat = item.getType();
            return mat == Material.DRAGON_HEAD;
        }
        return false;
    }

    static Predicate<Entity> filter = e -> e.getType() == EntityType.DROPPED_ITEM;

    public static void run() {
        task = Bukkit.getScheduler().runTaskTimer(DudeUtils.INSTANCE, () -> {
            Collection<? extends Player> players = Bukkit.getOnlinePlayers();
            for (Player p : players) {
                if (!MagnetHelper.isWearingItem(p))
                    continue;

                float speed = (float) PluginConfigs.MAGNET.SPEED;
                int range = PluginConfigs.MAGNET.RANGE;

                World world = p.getWorld();
                Vector target = VectorHelper.getVectorFromPos(p.getLocation());
                Collection<Entity> entities = world.getNearbyEntities(p.getLocation(), range, range, range, filter);
                for (Entity e : entities) {
                    Vector current = VectorHelper.getVectorFromPos(e.getLocation());
                    Vector movement = VectorHelper.getMovementVelocity(current, target, speed);
                    e.setVelocity(VectorHelper.add(e.getVelocity(), movement));
                }
            }
        }, 60L, 1L);

    }

    public static void cancelMagnet() {
        if (task != null)
            Bukkit.getScheduler().cancelTask(task.getTaskId());

    }

}
