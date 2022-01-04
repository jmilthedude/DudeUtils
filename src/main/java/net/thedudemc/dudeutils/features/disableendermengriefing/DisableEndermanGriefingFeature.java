package net.thedudemc.dudeutils.features.disableendermengriefing;

import net.thedudemc.dudeutils.features.Feature;
import net.thedudemc.dudeutils.features.FeatureListener;

public class DisableEndermanGriefingFeature extends Feature {
    @Override
    public String getName() {
        return "disable_enderman_griefing";
    }

    @Override
    public FeatureListener getListener() {
        return new DisableEndermanGriefingListener(this);
    }
}
