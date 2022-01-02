package net.thedudemc.dudeutils.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlacklistGui implements InventoryHolder, Listener {

    private final Inventory inv;

    public BlacklistGui(String title, int size) {
        this.inv = Bukkit.createInventory(this, size, title);
    }

    @Nonnull
    @Override
    public Inventory getInventory() {
        return this.inv;
    }

    // You can call this whenever you want to put the items in
    public void initializeItems(List<Material> items) {
        items.forEach(material -> inv.addItem(createItem(material, "", "")));
    }

    private ItemStack createItem(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;
        if (!name.isEmpty()) {
            meta.setDisplayName(name);
        }
        ArrayList<String> metaLore = new ArrayList<>();

        Collections.addAll(metaLore, lore);

        meta.setLore(metaLore);
        item.setItemMeta(meta);

        return item;
    }

}
