package net.thedudemc.dudeutils.init;

import net.thedudemc.dudeutils.DudeUtils;
import net.thedudemc.dudeutils.features.*;

import java.util.HashMap;
import java.util.Set;

public class PluginFeatures {

    public static HashMap<String, Feature> registry = new HashMap<>();

    public static MagnetFeature MAGNET;
    public static AllyFeature ALLY;
    public static DeathpointFeature DEATHPOINT;
    public static NetherWaterFeature NETHER_WATER;
    public static PortalUtilityFeature PORTAL_UTILITY;
    public static BlacklistFeature BLACKLIST;
    public static ChatNameColorFeature CHAT_NAME_COLOR;
    public static VeinminerFeature VEINMINER;
    public static AlternatorFeature ALTERNATOR;
    public static DisableEndermanGriefingFeature DISABLE_ENDERMAN_GRIEFING;
    public static ShulkerDropsTwoFeature SHULKER_DROPS_TWO;

    public static void register() {
        MAGNET = register(new MagnetFeature());
        ALLY = register(new AllyFeature());
        DEATHPOINT = register(new DeathpointFeature());
        NETHER_WATER = register(new NetherWaterFeature());
        PORTAL_UTILITY = register(new PortalUtilityFeature());
        BLACKLIST = register(new BlacklistFeature());
        CHAT_NAME_COLOR = register(new ChatNameColorFeature());
        VEINMINER = register(new VeinminerFeature());
        ALTERNATOR = register(new AlternatorFeature());
        DISABLE_ENDERMAN_GRIEFING = register(new DisableEndermanGriefingFeature());
        SHULKER_DROPS_TWO = register(new ShulkerDropsTwoFeature());
    }

    private static <T extends Feature> T register(T feature) {
        DudeUtils.getInstance().getServer().getPluginManager().registerEvents(feature, DudeUtils.getInstance());
        registry.put(feature.getName(), feature);
        return feature;
    }

    public static Feature getFeature(String name) {
        if (registry.containsKey(name)) {
            return registry.get(name);
        }
        throw new RuntimeException("No feature found with that name: " + name);
    }

    public static Set<String> getNames() {
        return registry.keySet();
    }

}
