package net.thedudemc.dudeutils.event;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class ShulkerEvents implements Listener {

    @EventHandler
    public void onShulkerKilled(EntityDeathEvent event) {
        if (event.getEntityType() != EntityType.SHULKER) return;
        Player player = event.getEntity().getKiller();
        if (player == null) return;

        event.getDrops().clear();
        event.getDrops().add(new ItemStack(Material.SHULKER_SHELL, 2));
    }
}
