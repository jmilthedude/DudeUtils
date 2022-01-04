package net.thedudemc.dudeutils.features.alternator;

import net.thedudemc.dudeutils.features.Feature;
import net.thedudemc.dudeutils.features.FeatureListener;

public class AlternatorFeature extends Feature {
    @Override
    public String getName() {
        return "alternator";
    }

    @Override
    public FeatureListener getListener() {
        return new AlternatorListener(this);
    }


}
