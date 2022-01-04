package net.thedudemc.dudeutils.features.ally;

import net.thedudemc.dudeutils.features.Feature;
import net.thedudemc.dudeutils.features.FeatureListener;

public class AllyFeature extends Feature {


    @Override
    public String getName() {
        return "ally";
    }

    @Override
    public FeatureListener getListener() {
        return new AllyListener(this);
    }

}
