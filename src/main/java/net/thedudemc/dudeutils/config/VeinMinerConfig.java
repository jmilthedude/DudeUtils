package net.thedudemc.dudeutils.config;

import com.google.gson.annotations.Expose;
import net.thedudemc.dudeutils.init.PluginConfigs;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VeinMinerConfig extends Config {
    @Expose
    public int BLOCK_LIMIT;
    @Expose
    private List<String> MATERIALS;
    @Expose
    private String REQUIRED_LORE;
    @Expose
    public int DAMAGE_PER_BLOCK;
    @Expose
    public int BLOCKS_PER_FOOD;

    @Override
    public String getName() {
        return "VeinMiner";
    }

    @Override
    protected void reset() {
        BLOCK_LIMIT = 32;
        MATERIALS = Arrays.asList(
                "COAL_ORE",
                "IRON_ORE",
                "GOLD_ORE",
                "DIAMOND_ORE",
                "EMERALD_ORE",
                "REDSTONE_ORE",
                "LAPIS_ORE",
                "NETHER_GOLD_ORE",
                "NETHER_QUARTZ_ORE",
                "NETHERITE_SCRAP"
        );
        REQUIRED_LORE = "VeinMiner!";
        DAMAGE_PER_BLOCK = 4;
        BLOCKS_PER_FOOD = 4;
    }

    public int getBlockLimit() {
        return BLOCK_LIMIT;
    }

    public List<Material> getMaterials() {
        List<Material> materials = new ArrayList<>();
        MATERIALS.forEach(s -> {
            Material material = Material.getMaterial(s);
            if (material != null) materials.add(material);
        });
        return materials;
    }

    public String getRequiredLore() {
        return REQUIRED_LORE;
    }

    public static ItemStack applyOrRemoveVeinMinerUpgrade(ItemStack stack) {
        ItemMeta meta = stack.getItemMeta();
        if (meta != null) {
            String requiredLore = PluginConfigs.VEINMINER.getRequiredLore();
            if (meta.hasLore()) {
                List<String> lore = meta.getLore();
                if (lore.contains(requiredLore)) {
                    lore.remove(requiredLore);
                } else {
                    lore.add(requiredLore);
                }
                meta.setLore(lore);
            } else {
                meta.setLore(Arrays.asList(requiredLore));
            }
            stack.setItemMeta(meta);
        }
        return stack;
    }

    public int getDamagePerBlock() {
        return DAMAGE_PER_BLOCK;
    }

    public int getBlocksPerFood() {
        return BLOCKS_PER_FOOD;
    }
}
