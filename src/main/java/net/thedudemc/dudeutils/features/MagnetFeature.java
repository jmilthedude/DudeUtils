package net.thedudemc.dudeutils.features;

import net.thedudemc.dudeutils.DudeUtils;
import net.thedudemc.dudeutils.init.PluginConfigs;
import net.thedudemc.dudeutils.init.PluginData;
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

import java.util.Collection;
import java.util.function.Predicate;

public class MagnetFeature extends Feature {

    private static BukkitTask task;

    public MagnetFeature() {
        this.isEnabled = PluginConfigs.FEATURES.ENABLED.get(this.getName());
    }

    @Override
    public String getName() {
        return "magnet";
    }

    @Override
    public void doEnable() {
        if (!isEnabled()) return;

        task = Bukkit.getScheduler().runTaskTimer(DudeUtils.INSTANCE, () -> {
            Collection<? extends Player> players = Bukkit.getOnlinePlayers();
            for (Player p : players) {
                if (isWearingItem(p) || PluginData.MAGNET_DATA.magnetizedPlayers.contains(p.getName())) {
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
            }
        }, 60L, 1L);
    }

    @Override
    public void doDisable() {
        if (task != null) {
            Bukkit.getScheduler().cancelTask(task.getTaskId());
            task.cancel();
            task = null;
        }
    }

    private boolean isWearingItem(Player player) {
        ItemStack item = player.getInventory().getHelmet();

        if (item != null) {
            Material mat = item.getType();
            return mat == Material.DRAGON_HEAD;
        }
        return false;
    }

    static Predicate<Entity> filter = e -> e.getType() == EntityType.DROPPED_ITEM;
}
