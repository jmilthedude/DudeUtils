package net.thedudemc.dudeutils.event;

import net.thedudemc.dudeutils.gui.BlacklistGui;
import net.thedudemc.dudeutils.init.PluginData;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class BlacklistEvents implements Listener {

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent event) {
        if (event.getEntity().getType() != EntityType.PLAYER) {
            return;
        }
        Player player = (Player) event.getEntity();

        if (!PluginData.BLACKLIST_DATA.hasItems(player.getName())) {
            return;
        }
        Material[] items = PluginData.BLACKLIST_DATA.getItems(player.getName());
        for (Material material : items) {
            if (material == event.getItem().getItemStack().getType()) {
                event.setCancelled(true);
            }
        }

    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryOpen(PlayerInteractEvent event) {
        if (event.isCancelled()) return;
        if (event.getHand() != EquipmentSlot.HAND || !event.getPlayer().isSneaking()) {
            return;
        }
        if (event.getItem() == null || event.getItem().getType() != Material.STICK || event.getAction() != Action.RIGHT_CLICK_AIR) {
            return;
        }
        event.setCancelled(true);

        Player player = event.getPlayer();
        BlacklistGui gui = new BlacklistGui(player.getName() + " Blacklist", 9);
        if (PluginData.BLACKLIST_DATA.hasItems(player.getName())) {
            Material[] items = PluginData.BLACKLIST_DATA.getItems(player.getName());
            gui.initializeItems(items);
        } else {
            gui.initializeItems();
        }
        BlacklistGui.addOpened(player, gui);
        player.openInventory(gui.getInventory());

    }

    @EventHandler
    public void onLeftClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        BlacklistGui gui = BlacklistGui.openedInventories.get(player);
        if (gui == null)
            return;
        ClickType click = event.getClick();
        if (click != ClickType.LEFT) {
            event.setCancelled(true);
            return;
        }

        if (event.getClickedInventory() != gui.getInventory())
            return;

        int slot = event.getSlot();

        ItemStack cursor = event.getCursor();
        if (cursor.getAmount() > 1)
            event.setCancelled(true);

        if (cursor.getType() != Material.AIR && gui.getInventory().contains(cursor.getType())) {
            event.setCancelled(true);
            return;
        }

        ItemStack toSet = cursor.clone();
        toSet.setAmount(1);
        gui.getInventory().setItem(slot, toSet);
        event.setCancelled(true);
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        Player player = (Player) event.getWhoClicked();
        BlacklistGui gui = BlacklistGui.openedInventories.get(player);
        if (gui == null)
            return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {

        BlacklistGui gui = BlacklistGui.openedInventories.get(event.getPlayer());
        if (gui == null)
            return;
        if (event.getInventory() != gui.getInventory()) {
            return;
        }

        BlacklistGui.removeOpened((Player) event.getPlayer());

        ItemStack[] contents = gui.getInventory().getContents();
        Material[] materials = new Material[9];
        for (int i = 0; i < 9; i++) {
            if (contents[i] == null)
                materials[i] = Material.AIR;
            else
                materials[i] = contents[i].getType();
        }

        PluginData.BLACKLIST_DATA.addItems(event.getPlayer().getName(), materials);

    }

    private boolean isEmpty(ItemStack stack) {
        if (stack == null || stack.getType() == Material.AIR) {
            return true;
        }
        return false;
    }

}
