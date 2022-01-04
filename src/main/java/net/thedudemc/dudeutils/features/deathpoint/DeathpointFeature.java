package net.thedudemc.dudeutils.features.deathpoint;

import net.thedudemc.dudeutils.features.Feature;
import net.thedudemc.dudeutils.features.FeatureListener;

public class DeathpointFeature extends Feature {

    @Override
    public String getName() {
        return "deathpoint";
    }

    @Override
    public FeatureListener getListener() {
        return new DeatpointListener(this);
    }
}
