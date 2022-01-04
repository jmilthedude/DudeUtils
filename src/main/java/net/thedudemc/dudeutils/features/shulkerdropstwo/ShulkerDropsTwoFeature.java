package net.thedudemc.dudeutils.features.shulkerdropstwo;

import net.thedudemc.dudeutils.features.Feature;
import net.thedudemc.dudeutils.features.FeatureListener;

public class ShulkerDropsTwoFeature extends Feature {

    @Override
    public String getName() {
        return "shulker_drops_two";
    }

    @Override
    public FeatureListener getListener() {
        return new ShulkerDropsTwoListener(this);
    }

}
