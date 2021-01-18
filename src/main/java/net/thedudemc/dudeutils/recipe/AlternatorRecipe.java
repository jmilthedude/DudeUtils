package net.thedudemc.dudeutils.recipe;

import net.thedudemc.dudeutils.DudeUtils;
import net.thedudemc.dudeutils.features.alternator.AlternatorHelper;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;

public class AlternatorRecipe extends PluginRecipe {

    public AlternatorRecipe() {
        super(new NamespacedKey(DudeUtils.getInstance(), "alternator"));
    }

    @Override
    public Recipe get() {
        return this.getShapedCustom(key, AlternatorHelper.getAlternator(),
                new Ingredient[]{
                        new Ingredient('d', Material.DIAMOND),
                        new Ingredient('s', Material.STICK)
                },
                "ddd", "dsd", "ddd");
    }
}
