package net.thedudemc.dudeutils.init;

import net.thedudemc.dudeutils.DudeUtils;
import net.thedudemc.dudeutils.recipe.AlternatorRecipe;
import net.thedudemc.dudeutils.recipe.BlackstoneRecipe;
import net.thedudemc.dudeutils.recipe.PluginRecipe;
import org.bukkit.NamespacedKey;

import java.util.HashMap;

public class PluginRecipes {

    public static HashMap<NamespacedKey, PluginRecipe> REGISTRY = new HashMap<>();

    public static PluginRecipe BLACKSTONE;
    public static PluginRecipe ALTERNATOR;

    public static void register(DudeUtils plugin) {
        BLACKSTONE = registerRecipe(plugin, new BlackstoneRecipe());
        if (PluginFeatures.ALTERNATOR.isEnabled()) {
            ALTERNATOR = registerRecipe(plugin, new AlternatorRecipe());
        }

    }

    public static void removeDisabled(DudeUtils plugin) {
        for (String s : PluginConfigs.RECIPES.CUSTOM_RECIPES.keySet()) {
            NamespacedKey key = new NamespacedKey(plugin, s.split(":")[1]);
            boolean enabled = PluginConfigs.RECIPES.CUSTOM_RECIPES.get(s);
            if (!enabled) {
                if (REGISTRY.containsKey(key)) {
                    if (plugin.getServer().removeRecipe(key))
                        REGISTRY.remove(key);
                }
            }
        }
    }

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    private static PluginRecipe registerRecipe(DudeUtils plugin, PluginRecipe recipe) {
        REGISTRY.put(recipe.getKey(), recipe);
        plugin.getServer().addRecipe(recipe.get());
        return recipe;
    }


}
