package net.thedudemc.dudeutils.config;

import com.google.gson.annotations.Expose;
import net.thedudemc.dudeutils.DudeUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VeinMinerConfig extends Config {
    @Expose
    public int BLOCK_LIMIT;
    @Expose
    private List<String> MATERIALS;
    @Expose
    public int DAMAGE_PER_BLOCK;
    @Expose
    public float EXHAUSTION_PER_BLOCK;
    @Expose
    public boolean DROP_AT_PLAYER;

    public static final NamespacedKey KEY = new NamespacedKey(DudeUtils.getInstance(), "VeinMiner");

    @Override
    public String getName() {
        return "VeinMiner";
    }

    @Override
    protected void reset() {
        BLOCK_LIMIT = 32;
        MATERIALS = Arrays.asList(
                Material.COAL_ORE.name(),
                Material.DEEPSLATE_COAL_ORE.name(),
                Material.IRON_ORE.name(),
                Material.DEEPSLATE_IRON_ORE.name(),
                Material.GOLD_ORE.name(),
                Material.DEEPSLATE_GOLD_ORE.name(),
                Material.DIAMOND_ORE.name(),
                Material.DEEPSLATE_DIAMOND_ORE.name(),
                Material.EMERALD_ORE.name(),
                Material.DEEPSLATE_EMERALD_ORE.name(),
                Material.REDSTONE_ORE.name(),
                Material.DEEPSLATE_REDSTONE_ORE.name(),
                Material.LAPIS_ORE.name(),
                Material.DEEPSLATE_LAPIS_ORE.name(),
                Material.COPPER_ORE.name(),
                Material.DEEPSLATE_COPPER_ORE.name(),
                Material.NETHER_GOLD_ORE.name(),
                Material.NETHER_QUARTZ_ORE.name(),
                Material.NETHERITE_SCRAP.name(),
                Material.OBSIDIAN.name()
        );
        DAMAGE_PER_BLOCK = 1;
        EXHAUSTION_PER_BLOCK = .005f;
        DROP_AT_PLAYER = true;
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

    public static void applyOrRemoveVeinMinerUpgrade(ItemStack stack) {
        ItemMeta meta = stack.getItemMeta();
        if (meta != null) {
            PersistentDataContainer data = meta.getPersistentDataContainer();
            Byte value = (byte) 1;
            if (data.has(KEY, PersistentDataType.BYTE)) {
                value = data.get(KEY, PersistentDataType.BYTE);
                if (value != null) {
                    data.set(KEY, PersistentDataType.BYTE, (value == (byte) 0 ? (byte) 1 : (byte) 0));
                }
            } else {
                data.set(KEY, PersistentDataType.BYTE, value);
            }
            stack.setItemMeta(meta);
        }
    }

    public int getDamagePerBlock() {
        return DAMAGE_PER_BLOCK;
    }

    public float getExhaustion() {
        return EXHAUSTION_PER_BLOCK;
    }

    public boolean shouldDropAtPlayer() {
        return this.DROP_AT_PLAYER;
    }
}
