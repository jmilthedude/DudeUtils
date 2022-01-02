package net.thedudemc.dudeutils.gui;

import net.thedudemc.dudeutils.util.ItemStackUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import javax.annotation.Nonnull;
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
        items.forEach(material -> inv.addItem(ItemStackUtils.createItem(material, "", "Blacklisted Item")));
    }

}
