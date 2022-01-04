package net.thedudemc.dudeutils.features.shulkerdropstwo;

import net.thedudemc.dudeutils.features.Feature;
import net.thedudemc.dudeutils.features.FeatureListener;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class ShulkerDropsTwoListener extends FeatureListener {
    public ShulkerDropsTwoListener(Feature feature) {
        super(feature);
    }

    @EventHandler
    public void onShulkerKilled(EntityDeathEvent event) {
        if (!feature.isEnabled()) return;

        if (event.getEntityType() != EntityType.SHULKER) return;
        Player player = event.getEntity().getKiller();
        if (player == null) return;

        event.getDrops().clear();
        event.getDrops().add(new ItemStack(Material.SHULKER_SHELL, 2));
    }
}
