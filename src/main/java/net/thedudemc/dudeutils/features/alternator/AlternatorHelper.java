package net.thedudemc.dudeutils.features.alternator;

import net.thedudemc.dudeutils.DudeUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class AlternatorHelper {

    private static final NamespacedKey ALTERNATOR_MODE = DudeUtils.getKey("alternator_mode");

    public static ItemStack getAlternator() {
        ItemStack alternator = new ItemStack(Material.STICK);
        Mode mode = Mode.PLACE;
        ItemMeta meta = alternator.getItemMeta();
        if (meta == null) return new ItemStack(Material.AIR);

        setAlternaterData(meta, mode, alternator);

        return alternator;
    }

    private static List<String> getAlternatorLore(Mode mode) {
        return Arrays.asList(
                "Randomly changes or places",
                "blocks using blocks in",
                "your hotbar!",
                " ",
                ChatColor.AQUA + "Mode" + ChatColor.RESET + ": " + ChatColor.GREEN + mode.getName()
        );
    }

    public static ItemStack changeAlternatorMode(ItemStack alternator) {
        ItemMeta meta = alternator.getItemMeta();
        if (meta == null) return alternator;
        if (!meta.getPersistentDataContainer().has(ALTERNATOR_MODE, PersistentDataType.INTEGER)) return alternator;

        int value = getModeValue(meta).orElse(0);
        setAlternaterData(meta, Mode.values()[value].getNext(), alternator);

        return alternator;
    }

    private static void setAlternaterData(ItemMeta meta, Mode next, ItemStack alternator) {
        meta.getPersistentDataContainer().set(ALTERNATOR_MODE, PersistentDataType.INTEGER, next.ordinal());
        meta.setLore(getAlternatorLore(next));
        meta.setDisplayName(ChatColor.AQUA + "Alternator " + ChatColor.RESET + "(" + ChatColor.GREEN + next.getName() + ChatColor.RESET + ")");

        alternator.setItemMeta(meta);
    }

    private static Optional<Integer> getModeValue(ItemMeta meta) {
        PersistentDataContainer container = meta.getPersistentDataContainer();
        return Optional.ofNullable(container.get(ALTERNATOR_MODE, PersistentDataType.INTEGER));
    }

    public static Mode getCurrentMode(ItemStack alternator) {
        ItemMeta meta = alternator.getItemMeta();
        if (meta == null) return Mode.CHANGE;
        return Mode.values()[getModeValue(meta).orElse(0)];
    }

    public static boolean isAlternatorItem(ItemStack stack) {
        if (!stack.hasItemMeta()) return false;
        ItemMeta meta = stack.getItemMeta();
        if (meta == null) return false;
        return meta.getPersistentDataContainer().has(ALTERNATOR_MODE, PersistentDataType.INTEGER);
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

        public Mode getNext() {
            return this == CHANGE ? PLACE : CHANGE;
        }
    }
}
