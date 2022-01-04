package net.thedudemc.dudeutils.features.netherwater;

import net.thedudemc.dudeutils.features.Feature;
import net.thedudemc.dudeutils.features.FeatureListener;

public class NetherWaterFeature extends Feature {
    @Override
    public String getName() {
        return "nether_water";
    }

    @Override
    public FeatureListener getListener() {
        return new NetherWaterListener(this);
    }

}
