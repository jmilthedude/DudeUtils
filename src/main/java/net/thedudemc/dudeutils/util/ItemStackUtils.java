package net.thedudemc.dudeutils.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;

public class ItemStackUtils {

    public static ItemStack createItem(Material material, String name, String... lore) {
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
