package net.thedudemc.dudeutils.features.inventorysort;

import net.thedudemc.dudeutils.features.Feature;
import net.thedudemc.dudeutils.features.FeatureListener;

public class InventorySortFeature extends Feature {

    @Override
    public String getName() {
        return "inventory_sort";
    }

    @Override
    public FeatureListener getListener() {
        return new InventorySortListener(this);
    }


}
