package net.thedudemc.dudeutils.features.magnet;

import net.thedudemc.dudeutils.features.Feature;
import net.thedudemc.dudeutils.features.FeatureListener;
import net.thedudemc.dudeutils.init.PluginConfigs;
import net.thedudemc.dudeutils.init.PluginData;
import net.thedudemc.dudeutils.util.Tickable;
import net.thedudemc.dudeutils.util.VectorHelper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.function.Predicate;

public class MagnetFeature extends Feature implements Tickable {

    static Predicate<Entity> filter = e -> e.getType() == EntityType.DROPPED_ITEM;

    @Override
    public String getName() {
        return "magnet";
    }

    @Override
    public FeatureListener getListener() {
        return null;
    }

    @Override
    public void tick() {
        if (this.isEnabled()) magnetize();
    }

    private void magnetize() {
        Bukkit.getOnlinePlayers()
                .stream()
                .filter(this::shouldMagnetize)
                .forEach(p -> {
                    float speed = (float) PluginConfigs.MAGNET.SPEED;
                    int range = PluginConfigs.MAGNET.RANGE;

                    World world = p.getWorld();
                    Vector target = VectorHelper.getVectorFromPos(p.getLocation());
                    Collection<Entity> entities = world.getNearbyEntities(p.getLocation(), range, range, range, filter);
                    entities.forEach(e -> moveEntity(speed, target, e));
                });
    }

    private boolean shouldMagnetize(Player player) {
        return isWearingItem(player) || PluginData.MAGNET_DATA.magnetizedPlayers.contains(player.getName());
    }

    private boolean isWearingItem(Player player) {
        ItemStack item = player.getInventory().getHelmet();

        if (item != null) {
            Material mat = item.getType();
            return mat == Material.DRAGON_HEAD;
        }
        return false;
    }

    private void moveEntity(float speed, Vector target, Entity e) {
        Vector current = VectorHelper.getVectorFromPos(e.getLocation());
        Vector movement = VectorHelper.getMovementVelocity(current, target, speed);
        e.setVelocity(VectorHelper.add(e.getVelocity(), movement));
    }

}
