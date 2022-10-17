package net.thedudemc.dudeutils.recipe;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

public abstract class PluginRecipe {

    protected NamespacedKey key;

    public PluginRecipe(NamespacedKey key) {
        this.key = key;
    }

    public NamespacedKey getKey() {
        return key;
    }

    public abstract Recipe get();

    public ShapedRecipe getShaped(NamespacedKey key, Material material, int amount, Ingredient[] ingredients, String... shape) {
        ShapedRecipe recipe = new ShapedRecipe(key, new ItemStack(material, amount));
        recipe.shape(shape);
        for (Ingredient ingredient : ingredients) {
            recipe.setIngredient(ingredient.getKey(), ingredient.getMaterial());
        }
        return recipe;
    }

    public ShapelessRecipe getShapeless(NamespacedKey key, Material material, int amount, Material... ingredients) {
        ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(material, amount));
        for (Material ingredient : ingredients) {
            recipe.addIngredient(ingredient);
        }
        return recipe;
    }

    public ShapedRecipe getShapedCustom(NamespacedKey key, ItemStack stack, Ingredient[] ingredients, String... shape) {
        ShapedRecipe recipe = new ShapedRecipe(key, stack);
        recipe.shape(shape);
        for (Ingredient ingredient : ingredients) {
            recipe.setIngredient(ingredient.getKey(), ingredient.getMaterial());
        }
        return recipe;
    }

    public Recipe getAnvilRecipe(NamespacedKey key, ItemStack stack, Ingredient[] ingredients, String... shape) {
        ShapedRecipe recipe = new ShapedRecipe(key, stack);
        recipe.shape(shape);
        for (Ingredient ingredient : ingredients) {
            recipe.setIngredient(ingredient.getKey(), ingredient.getMaterial());
        }
        return recipe;
    }

}
