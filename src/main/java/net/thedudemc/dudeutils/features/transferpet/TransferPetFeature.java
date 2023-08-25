package net.thedudemc.dudeutils.features.transferpet;

import net.thedudemc.dudeutils.features.Feature;
import net.thedudemc.dudeutils.features.FeatureListener;

public class TransferPetFeature extends Feature {
    @Override
    public String getName() {
        return "transfer_pet";
    }

    @Override
    public FeatureListener getListener() {
        return new TransferPetListener(this);
    }
}
