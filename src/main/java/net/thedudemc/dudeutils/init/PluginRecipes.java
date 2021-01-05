package net.thedudemc.dudeutils.init;

import net.thedudemc.dudeutils.DudeUtils;
import net.thedudemc.dudeutils.recipe.BlackstoneRecipe;
import net.thedudemc.dudeutils.recipe.PluginRecipe;
import org.bukkit.NamespacedKey;

import java.util.HashMap;

public class PluginRecipes {

    public static HashMap<NamespacedKey, PluginRecipe> REGISTRY = new HashMap<>();

    public static PluginRecipe BLACKSTONE;

    public static void register(DudeUtils plugin) {
        BLACKSTONE = registerRecipe(plugin, new BlackstoneRecipe());

    }

    public static void removeDisabled(DudeUtils plugin) {
        for (String s : PluginConfigs.RECIPES.CUSTOM_RECIPES.keySet()) {
            NamespacedKey key = new NamespacedKey(plugin, s.split(":")[1]);
            if (REGISTRY.containsKey(key)) {
                if (plugin.getServer().removeRecipe(key))
                    REGISTRY.remove(key);
            }
        }
    }

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    private static PluginRecipe registerRecipe(DudeUtils plugin, PluginRecipe recipe) {
        plugin.getServer().addRecipe(recipe.get());
        REGISTRY.put(recipe.getKey(), recipe);
        return recipe;
    }


}
