package net.thedudemc.dudeutils.data;

import com.google.gson.annotations.Expose;
import org.bukkit.Material;

import javax.annotation.Nonnull;
import java.util.HashMap;

public class BlacklistSaveData extends SaveData {

    @Expose
    public HashMap<String, String[]> BLACKLIST = new HashMap<>();

    @Override
    public String getName() {
        return "blacklist";
    }

    @Override
    protected void reset() {
        BLACKLIST.put("player1", new String[]{
                Material.STICK.toString(),
                Material.ROTTEN_FLESH.toString(),
                Material.DEAD_BUSH.toString()
        });
        BLACKLIST.put("player2", new String[]{
                Material.STICK.toString(),
                Material.ROTTEN_FLESH.toString(),
                Material.DEAD_BUSH.toString()
        });
        this.markDirty();
    }

    public boolean hasItems(String name) {
        return BLACKLIST.containsKey(name);
    }


    @Nonnull
    public Material[] getItems(String name) {
        Material[] materials = new Material[BLACKLIST.get(name).length];
        for (int i = 0; i < materials.length; i++) {
            materials[i] = Material.getMaterial(BLACKLIST.get(name)[i]);
        }
        return materials;
    }

    public void addItems(String name, Material[] materials) {
        String[] mats = new String[materials.length];
        int i = 0;
        for (Material m : materials) {
            mats[i++] = m.toString();
        }
        BLACKLIST.put(name, mats);
        this.markDirty();
    }
}
