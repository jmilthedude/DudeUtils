package net.thedudemc.dudeutils.features;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class ShulkerDropsTwoFeature extends Feature {

    @Override
    public String getName() {
        return "shulker_drops_two";
    }

    @Override
    public void doEnable() {

    }

    @Override
    public void doDisable() {

    }

    @EventHandler
    public void onShulkerKilled(EntityDeathEvent event) {
        if (!this.isEnabled()) return;

        if (event.getEntityType() != EntityType.SHULKER) return;
        Player player = event.getEntity().getKiller();
        if (player == null) return;

        event.getDrops().clear();
        event.getDrops().add(new ItemStack(Material.SHULKER_SHELL, 2));
    }

}
