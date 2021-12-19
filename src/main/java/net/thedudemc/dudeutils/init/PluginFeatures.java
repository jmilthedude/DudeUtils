package net.thedudemc.dudeutils.init;

import net.thedudemc.dudeutils.features.Feature;
import net.thedudemc.dudeutils.features.MagnetFeature;

import java.util.HashMap;
import java.util.Set;

public class PluginFeatures {

    public static HashMap<String, Feature> registry = new HashMap<>();

    public static MagnetFeature MAGNET_FEATURE;

    public static void register() {
        MAGNET_FEATURE = register(new MagnetFeature());
    }

    private static <T extends Feature> T register(T feature) {
        registry.put(feature.getName(), feature);
        return feature;
    }

    public static Feature getFeature(String name) {
        if (registry.containsKey(name)) {
            return registry.get(name);
        }
        return null;
    }

    public static Set<String> getNames() {
        return registry.keySet();
    }

}
