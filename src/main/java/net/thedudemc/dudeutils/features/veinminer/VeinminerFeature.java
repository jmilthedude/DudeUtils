package net.thedudemc.dudeutils.features.veinminer;

import net.thedudemc.dudeutils.features.Feature;
import net.thedudemc.dudeutils.features.FeatureListener;

public class VeinminerFeature extends Feature {
    @Override
    public String getName() {
        return "veinminer";
    }

    @Override
    public FeatureListener getListener() {
        return new VeinminerListener(this);
    }
}
