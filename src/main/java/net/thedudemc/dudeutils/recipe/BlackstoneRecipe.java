package net.thedudemc.dudeutils.recipe;

import net.thedudemc.dudeutils.DudeUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;

public class BlackstoneRecipe extends PluginRecipe {

    public BlackstoneRecipe() {
        super(new NamespacedKey(DudeUtils.getInstance(), "blackstone"));
    }

    @Override
    public Recipe get() {
        return this.getShaped(key, Material.BLACKSTONE, 8,
                new Ingredient[]{
                        new Ingredient('m', Material.COBBLESTONE),
                        new Ingredient('x', Material.BLACK_DYE)
                },
                "mmm", "mxm", "mmm");
    }
}
