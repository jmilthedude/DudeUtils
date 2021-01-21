package net.thedudemc.dudeutils.features.alternator;

import net.thedudemc.dudeutils.DudeUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.List;

public class AlternatorHelper {

    private static final NamespacedKey ALTERNATOR_MODE = new NamespacedKey(DudeUtils.getInstance(), "alternator_mode");

    public static ItemStack getAlternator() {
        ItemStack stack = new ItemStack(Material.STICK);
        Mode mode = Mode.PLACE;
        ItemMeta meta = stack.getItemMeta();

        meta.getPersistentDataContainer().set(ALTERNATOR_MODE, PersistentDataType.INTEGER, mode.ordinal());
        meta.setLore(getAlternatorLore(mode));
        meta.setDisplayName(ChatColor.AQUA + "Alternator " + ChatColor.RESET + "(" + ChatColor.GREEN + mode.getName() + ChatColor.RESET + ")");
        stack.setItemMeta(meta);

        return stack;
    }

    private static List<String> getAlternatorLore(Mode mode) {
        List<String> lore = Arrays.asList(
                "Randomly changes or places",
                "blocks using blocks in",
                "your hotbar!",
                " ",
                ChatColor.AQUA + "Mode" + ChatColor.RESET + ": " + ChatColor.GREEN + mode.getName()
        );
        return lore;
    }

    public static ItemStack changeAlternatorMode(ItemStack alternator) {
        ItemMeta meta = alternator.getItemMeta();
        if (meta != null) {
            if (!meta.getPersistentDataContainer().has(ALTERNATOR_MODE, PersistentDataType.INTEGER)) return alternator;

            int value = meta.getPersistentDataContainer().get(ALTERNATOR_MODE, PersistentDataType.INTEGER);
            Mode mode = getNextMode(Mode.values()[value]);
            meta.getPersistentDataContainer().set(ALTERNATOR_MODE, PersistentDataType.INTEGER, mode.ordinal());
            meta.setLore(getAlternatorLore(mode));
            meta.setDisplayName(ChatColor.AQUA + "Alternator " + ChatColor.RESET + "(" + ChatColor.GREEN + mode.getName() + ChatColor.RESET + ")");

            alternator.setItemMeta(meta);
        }
        return alternator;
    }

    public static Mode getCurrentMode(ItemStack alternator) {
        ItemMeta meta = alternator.getItemMeta();
        if (meta != null) {
            if (meta.getPersistentDataContainer().has(ALTERNATOR_MODE, PersistentDataType.INTEGER)) {
                int value = meta.getPersistentDataContainer().get(ALTERNATOR_MODE, PersistentDataType.INTEGER);
                return Mode.values()[value];
            }
        }
        return null;
    }

    public static boolean isAlternatorItem(ItemStack stack) {
        if (stack.hasItemMeta()) {
            ItemMeta meta = stack.getItemMeta();
            return meta.getPersistentDataContainer().has(ALTERNATOR_MODE, PersistentDataType.INTEGER);
        }
        return false;
    }

    private static Mode getNextMode(Mode mode) {
        int current = mode.ordinal();
        current++;
        if (current > 1) return Mode.values()[0];
        return Mode.values()[current];
    }

    public enum Mode {
        CHANGE("Change"),
        PLACE("Place");

        private final String name;

        Mode(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
