package net.thedudemc.dudeutils.features.xpstorage;

import net.thedudemc.dudeutils.features.Feature;
import net.thedudemc.dudeutils.features.FeatureListener;

public class XPStorageFeature extends Feature {
    @Override
    public String getName() {
        return "xp_storage";
    }

    @Override
    public FeatureListener getListener() {
        return new XPStorageListener(this);
    }
}
