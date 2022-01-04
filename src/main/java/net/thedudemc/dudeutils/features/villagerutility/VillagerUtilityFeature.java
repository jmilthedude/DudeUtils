package net.thedudemc.dudeutils.features.villagerutility;

import net.thedudemc.dudeutils.features.Feature;
import net.thedudemc.dudeutils.features.FeatureListener;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

public class VillagerUtilityFeature extends Feature {
    @Override
    public String getName() {
        return "villager_utility";
    }

    @Override
    public FeatureListener getListener() {
        return new VillagerUtilityListener(this);
    }
    public static final List<Material> possibleWorkstations = Arrays.asList(
            Material.BLAST_FURNACE,
            Material.SMOKER,
            Material.CARTOGRAPHY_TABLE,
            Material.BREWING_STAND,
            Material.COMPOSTER,
            Material.BARREL,
            Material.FLETCHING_TABLE,
            Material.CAULDRON,
            Material.LECTERN,
            Material.STONECUTTER,
            Material.LOOM,
            Material.GRINDSTONE,
            Material.SMITHING_TABLE
    );

}
