package net.thedudemc.dudeutils.features.elytra;

import net.thedudemc.dudeutils.features.Feature;
import net.thedudemc.dudeutils.features.FeatureListener;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class HardenedElytraListener extends FeatureListener {
    public HardenedElytraListener(Feature feature) {
        super(feature);
    }

    @EventHandler
    public void onAnvilCombine(PrepareAnvilEvent event) {
        AnvilInventory anvilInventory = event.getInventory();
        String name = anvilInventory.getRenameText();
        ItemStack left = anvilInventory.getItem(0);
        ItemStack right = anvilInventory.getItem(1);

        if (validIngredients(left, right)) return;

        Map<Enchantment, Integer> elytraEnchantments = Objects.requireNonNull(left).getEnchantments();
        Map<Enchantment, Integer> chestplateEnchantments = Objects.requireNonNull(right).getEnchantments();

        Map<Enchantment, Integer> enchantments = combineEnchantments(chestplateEnchantments, elytraEnchantments);

        ItemStack elytra = createElytra(name, enchantments);

        event.setResult(elytra);
        anvilInventory.setRepairCost(30);
        anvilInventory.setMaximumRepairCost(30);
        anvilInventory.setRepairCostAmount(1);
    }

    private ItemStack createElytra(String name, Map<Enchantment, Integer> combined) {
        ItemStack output = new ItemStack(Material.ELYTRA);
        output.addUnsafeEnchantments(combined);
        ItemMeta meta = output.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            List<String> lore = List.of(" ", "Hardened!");
            meta.setLore(lore);
            output.setItemMeta(meta);
        }
        return output;
    }

    private Map<Enchantment, Integer> combineEnchantments(Map<Enchantment, Integer> chestplateEnchantments, Map<Enchantment, Integer> elytraEnchantments) {
        HashMap<Enchantment, Integer> combined = new HashMap<>();
        combined.putAll(chestplateEnchantments);
        combined.putAll(elytraEnchantments);
        return combined;
    }

    private boolean validIngredients(ItemStack left, ItemStack right) {
        if (left == null || right == null) return false;
        if (left.getType() == Material.ELYTRA && right.getType() == Material.NETHERITE_CHESTPLATE) return false;

        return validChestplate(right);
    }

    private boolean validChestplate(ItemStack chestplate) {
        Map<Enchantment, Integer> chestplateEnchantments = chestplate.getEnchantments();
        if (!chestplateEnchantments.containsKey(Enchantment.PROTECTION_ENVIRONMENTAL)) return false;
        return chestplateEnchantments.get(Enchantment.PROTECTION_ENVIRONMENTAL) >= 4;
    }
}
