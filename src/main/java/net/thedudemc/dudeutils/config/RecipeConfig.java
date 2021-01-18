package net.thedudemc.dudeutils.config;


import com.google.gson.annotations.Expose;

import java.util.HashMap;

public class RecipeConfig extends Config {

    @Expose public HashMap<String, Boolean> CUSTOM_RECIPES = new HashMap<>();

    @Override
    public String getName() {
        return "Recipes";
    }

    @Override
    protected void reset() {
        CUSTOM_RECIPES.put("dudeutils:blackstone", true);
        CUSTOM_RECIPES.put("dudeutils:alternator", true);
    }
}
