package net.thedudemc.dudeutils.features.fastxp;

import net.thedudemc.dudeutils.features.Feature;
import net.thedudemc.dudeutils.features.FeatureListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class FastXPListener extends FeatureListener {

    public FastXPListener(Feature feature) {
        super(feature);
    }

    @EventHandler
    public void onXpPickup(EntityPickupItemEvent event) {

    }


}
