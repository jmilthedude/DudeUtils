package net.thedudemc.dudeutils.config;

import com.google.gson.annotations.Expose;

import java.util.HashMap;

public class FeaturesConfig extends Config {

    @Expose
    public HashMap<String, Boolean> ENABLED = new HashMap<>();

    @Override
    public String getName() {
        return "Features";
    }

    @Override
    protected void reset() {
        ENABLED.put("chat_name_color", true);
        ENABLED.put("magnet", false);
        ENABLED.put("nether_water", false);
        ENABLED.put("portal_utility", true);
        ENABLED.put("veinminer", false);
        ENABLED.put("blacklist", true);
        ENABLED.put("deathpoint", true);
        ENABLED.put("ally", true);
        ENABLED.put("alternator", true);
        ENABLED.put("shulker_drops_two", true);
        ENABLED.put("disable_enderman_griefing", true);
        ENABLED.put("VillagerUtility", true);
        ENABLED.put("SinglePlayerSleep", true);
        ENABLED.put("InventorySorter", true);
        ENABLED.put("SlimeChunkCheck", true);
    }

    public void setEnabled(String name, boolean value) {
        if (ENABLED.get(name) != value) {
            ENABLED.put(name, value);
            this.markDirty();
        }
    }
}
