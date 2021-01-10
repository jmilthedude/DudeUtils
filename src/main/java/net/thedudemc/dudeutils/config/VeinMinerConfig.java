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
    private boolean ENABLED;
    @Expose
    private int BLOCK_LIMIT;
    @Expose
    private List<String> MATERIALS;
    @Expose
    private String REQUIRED_LORE;

    @Override
    public String getName() {
        return "VeinMiner";
    }

    @Override
    protected void reset() {
        ENABLED = false;
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
    }

    public boolean isEnabled() {
        return ENABLED;
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

    public static final ItemStack applyVeinMinerUpgrade(ItemStack stack) {
        ItemMeta meta = stack.getItemMeta();
        if (meta != null) {
            if (meta.hasLore()) {
                meta.getLore().add(PluginConfigs.VEINMINER.getRequiredLore());
            } else {
                meta.setLore(Arrays.asList(PluginConfigs.VEINMINER.getRequiredLore()));
            }
            stack.setItemMeta(meta);
        }
        return stack;
    }
}
