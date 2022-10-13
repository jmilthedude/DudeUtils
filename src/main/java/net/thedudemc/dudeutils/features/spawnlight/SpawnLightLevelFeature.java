package net.thedudemc.dudeutils.features.spawnlight;

import net.thedudemc.dudeutils.features.Feature;
import net.thedudemc.dudeutils.features.FeatureListener;

public class SpawnLightLevelFeature extends Feature {
    @Override
    public String getName() {
        return "spawn_light_level";
    }

    @Override
    public FeatureListener getListener() {
        return new SpawnLightLevelListener(this);
    }
}
