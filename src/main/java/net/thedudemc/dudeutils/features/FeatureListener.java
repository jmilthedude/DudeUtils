package net.thedudemc.dudeutils.features;

import org.bukkit.event.Listener;

public abstract class FeatureListener implements Listener {

    protected final Feature feature;

    public FeatureListener(Feature feature) {
        this.feature = feature;
    }

}
