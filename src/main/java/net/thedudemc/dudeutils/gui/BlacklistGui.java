package net.thedudemc.dudeutils.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class BlacklistGui implements InventoryHolder, Listener {

    private final Inventory inv;

    public static HashMap<Player, BlacklistGui> openedInventories = new HashMap<>();

    public BlacklistGui(String title, int size) {
        inv = Bukkit.createInventory(this, size, title);
        initializeItems();
    }

    @Nonnull
    @Override
    public Inventory getInventory() {
        return inv;
    }

    // You can call this whenever you want to put the items in
    public void initializeItems() {
    }

    // You can call this whenever you want to put the items in
    public void initializeItems(Material[] items) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null)
                inv.setItem(i, createGuiItem(items[i], "", ""));
        }
    }

    private ItemStack createGuiItem(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();

        if (!name.isEmpty()) {
            meta.setDisplayName(name);
        }
        ArrayList<String> metaLore = new ArrayList<>();

        Collections.addAll(metaLore, lore);

        if (meta != null) {
            meta.setLore(metaLore);
            item.setItemMeta(meta);
        }
        return item;
    }

    // You can open the inventory with this
    public void openInventory(Player p) {
        openedInventories.put(p, this);
        p.openInventory(inv);
    }

    private boolean isEmpty(ItemStack stack) {
        return stack == null || stack.getType() == Material.AIR;
    }

    public static void addOpened(Player p, BlacklistGui gui) {
        openedInventories.put(p, gui);
    }

    public static void removeOpened(Player p) {
        openedInventories.remove(p);
    }

}
