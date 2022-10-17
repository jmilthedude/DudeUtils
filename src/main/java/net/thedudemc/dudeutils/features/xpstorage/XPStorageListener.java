package net.thedudemc.dudeutils.features.xpstorage;

import net.thedudemc.dudeutils.data.XPStorageData;
import net.thedudemc.dudeutils.features.Feature;
import net.thedudemc.dudeutils.features.FeatureListener;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class XPStorageListener extends FeatureListener {
    public XPStorageListener(Feature feature) {
        super(feature);
    }

    @EventHandler
    public void onInteractWithEnchantingTable(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;

        Block block = event.getClickedBlock();
        if (block == null || block.getType() != Material.ENCHANTING_TABLE) return;

        Player player = event.getPlayer();
        Material itemMaterial = event.getMaterial();

        if (handleXPStorage(player, itemMaterial)) {
            event.setCancelled(true);
        }
    }

    private boolean handleXPStorage(Player player, Material itemMaterial) {
        if (itemMaterial.isAir() && player.isSneaking()) {
            ((XPStorageData) feature.getSaveData()).query(player);
            return true;
        } else if (itemMaterial == Material.EXPERIENCE_BOTTLE) {
            if (player.isSneaking()) {
                ((XPStorageData) feature.getSaveData()).collect(player);
            } else {
                ((XPStorageData) feature.getSaveData()).store(player);
            }
            return true;
        }
        return false;
    }
}
