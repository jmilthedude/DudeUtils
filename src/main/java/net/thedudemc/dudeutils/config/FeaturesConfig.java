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
        ENABLED.put("ChatNameColor", true);
        ENABLED.put("Magnet", false);
        ENABLED.put("NetherWater", false);
        ENABLED.put("PortalUtility", true);
        ENABLED.put("VeinMiner", false);
        ENABLED.put("VillagerUtility", true);
        ENABLED.put("SinglePlayerSleep", true);
        ENABLED.put("Blacklist", true);
        ENABLED.put("Allies", true);
        ENABLED.put("InventorySorter", true);
        ENABLED.put("SlimeChunkCheck", true);
        ENABLED.put("Alternator", true);
    }
}
