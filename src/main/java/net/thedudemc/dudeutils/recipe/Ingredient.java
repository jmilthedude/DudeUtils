package net.thedudemc.dudeutils.recipe;

import org.bukkit.Material;

public class Ingredient {
    private char key;
    private Material material;

    public Ingredient(char key, Material material) {
        this.key = key;
        this.material = material;
    }

    public char getKey() {
        return key;
    }

    public void setKey(char key) {
        this.key = key;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}
