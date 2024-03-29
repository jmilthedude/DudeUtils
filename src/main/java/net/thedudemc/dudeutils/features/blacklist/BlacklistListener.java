package net.thedudemc.dudeutils.features.blacklist;

import net.thedudemc.dudeutils.data.BlacklistSaveData;
import net.thedudemc.dudeutils.features.Feature;
import net.thedudemc.dudeutils.features.FeatureListener;
import net.thedudemc.dudeutils.features.alternator.AlternatorHelper;
import net.thedudemc.dudeutils.gui.BlacklistGui;
import net.thedudemc.dudeutils.util.ItemStackUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class BlacklistListener extends FeatureListener {

    public BlacklistListener(Feature feature) {
        super(feature);
    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent event) {
        if (!feature.isEnabled()) return;

        if (!(event.getEntity() instanceof Player player)) return;
        ItemStack stack = event.getItem().getItemStack();
        if (isBlacklistedForPlayer(player.getUniqueId(), stack)) {
            event.setCancelled(true);
        }

    }

    private boolean isBlacklistedForPlayer(UUID playerId, ItemStack stack) {
        BlacklistSaveData data = feature.getSaveData();
        return data.getItems(playerId).stream().anyMatch(material -> material.equals(stack.getType()));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryOpen(PlayerInteractEvent event) {
        if (!feature.isEnabled()) return;

        if (event.getHand() != EquipmentSlot.HAND || !event.getPlayer().isSneaking()) {
            return;
        }
        if (event.getItem() == null || event.getItem().getType() != Material.STICK || event.getAction() != Action.RIGHT_CLICK_AIR) {
            return;
        }

        if (AlternatorHelper.isAlternatorItem(event.getItem())) return;

        event.setCancelled(true);

        Player player = event.getPlayer();
        BlacklistGui gui = create(player);
        open(player, gui);
    }

    private void open(Player player, BlacklistGui gui) {
        BlacklistFeature.openedInventoryMap.put(player.getUniqueId(), gui);
        player.openInventory(gui.getInventory());
    }

    private void close(Player player) {
        BlacklistFeature.openedInventoryMap.remove(player.getUniqueId());
        player.closeInventory();
    }

    private BlacklistGui create(Player player) {
        BlacklistSaveData data = feature.getSaveData();
        BlacklistGui gui = new BlacklistGui(player.getName() + "'s Blacklist", 9);

        List<Material> items = data.getItems(player.getUniqueId());
        gui.initializeItems(items);
        return gui;
    }

    @EventHandler
    public void onLeftClick(InventoryClickEvent event) {
        if (!feature.isEnabled()) return;

        Player player = (Player) event.getWhoClicked();
        BlacklistGui gui = BlacklistFeature.openedInventoryMap.get(player.getUniqueId());

        if (gui == null) {
            return;
        }
        if (event.getClickedInventory() != gui.getInventory()) {
            return;
        }

        ClickType click = event.getClick();
        if (click != ClickType.LEFT) {
            event.setCancelled(true);
            return;
        }

        int slot = event.getSlot();
        ItemStack cursor = event.getCursor();
        if (cursor == null) return;
        if (cursor.getAmount() >= 1) {
            event.setCancelled(true);
        }

        if (cursor.getType() != Material.AIR && gui.getInventory().contains(cursor.getType())) {
            event.setCancelled(true);
            return;
        }
        event.setCancelled(true);
        gui.getInventory().setItem(slot, ItemStackUtils.createItem(cursor.getType(), "", "Blacklisted Item"));
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        if (!feature.isEnabled()) return;

        Player player = (Player) event.getWhoClicked();
        BlacklistGui gui = BlacklistFeature.openedInventoryMap.get(player.getUniqueId());

        event.setCancelled(gui != null);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!feature.isEnabled()) return;

        if (!(event.getPlayer() instanceof Player player)) return;

        BlacklistGui gui = BlacklistFeature.openedInventoryMap.get(player.getUniqueId());

        if (gui == null || event.getInventory() != gui.getInventory()) return;

        BlacklistSaveData data = feature.getSaveData();

        ItemStack[] contents = gui.getInventory().getContents();
        List<Material> materials = Arrays.stream(contents)
                .filter(Objects::nonNull)
                .map(ItemStack::getType)
                .collect(Collectors.toList());

        data.updateItems(player.getUniqueId(), materials);
        close(player);
    }

}
