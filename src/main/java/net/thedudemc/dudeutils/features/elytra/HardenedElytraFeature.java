package net.thedudemc.dudeutils.features.elytra;

import net.thedudemc.dudeutils.features.Feature;
import net.thedudemc.dudeutils.features.FeatureListener;

public class HardenedElytraFeature extends Feature {
    @Override
    public String getName() {
        return "hardened_elytra";
    }

    @Override
    public FeatureListener getListener() {
        return new HardenedElytraListener(this);
    }
}
