package net.thedudemc.dudeutils.init;

import net.thedudemc.dudeutils.DudeUtils;
import net.thedudemc.dudeutils.features.*;

import java.util.HashMap;
import java.util.Set;

public class PluginFeatures {

    public static HashMap<String, Feature> registry = new HashMap<>();

    public static MagnetFeature MAGNET_FEATURE;
    public static AllyFeature ALLY_FEATURE;
    public static DeathpointFeature DEATHPOINT_FEATURE;
    public static NetherWaterFeature NETHER_WATER_FEATURE;
    public static PortalUtilityFeature PORTAL_UTILITY_FEATURE;
    public static BlacklistFeature BLACKLIST_FEATURE;
    public static ChatNameColorFeature CHAT_NAME_COLOR_FEATURE;

    public static void register() {
        MAGNET_FEATURE = register(new MagnetFeature());
        ALLY_FEATURE = register(new AllyFeature());
        DEATHPOINT_FEATURE = register(new DeathpointFeature());
        NETHER_WATER_FEATURE = register(new NetherWaterFeature());
        PORTAL_UTILITY_FEATURE = register(new PortalUtilityFeature());
        BLACKLIST_FEATURE = register(new BlacklistFeature());
        CHAT_NAME_COLOR_FEATURE = register(new ChatNameColorFeature());
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
