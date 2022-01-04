package net.thedudemc.dudeutils.init;

import net.thedudemc.dudeutils.DudeUtils;
import net.thedudemc.dudeutils.recipe.AlternatorRecipe;
import net.thedudemc.dudeutils.recipe.BlackstoneRecipe;
import net.thedudemc.dudeutils.recipe.PluginRecipe;
import net.thedudemc.dudeutils.util.Log;
import org.bukkit.NamespacedKey;

import java.util.HashMap;

public class PluginRecipes {

    public static HashMap<NamespacedKey, PluginRecipe> REGISTRY = new HashMap<>();

    public static PluginRecipe BLACKSTONE;
    public static PluginRecipe ALTERNATOR;

    public static void init() {
        Log.info("Initializing Recipes");
        register();
        removeDisabled();
    }

    private static void register() {
        BLACKSTONE = registerRecipe(DudeUtils.getInstance(), new BlackstoneRecipe());
        if (PluginFeatures.ALTERNATOR.isEnabled()) {
            ALTERNATOR = registerRecipe(DudeUtils.getInstance(), new AlternatorRecipe());
        }

    }

    private static void removeDisabled() {
        for (String s : PluginConfigs.RECIPES.CUSTOM_RECIPES.keySet()) {
            NamespacedKey key = new NamespacedKey(DudeUtils.getInstance(), s.split(":")[1]);
            boolean enabled = PluginConfigs.RECIPES.CUSTOM_RECIPES.get(s);
            if (!enabled) {
                if (REGISTRY.containsKey(key)) {
                    if (DudeUtils.getInstance().getServer().removeRecipe(key))
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
