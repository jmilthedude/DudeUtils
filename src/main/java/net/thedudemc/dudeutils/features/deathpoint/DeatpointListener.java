package net.thedudemc.dudeutils.features.deathpoint;

import net.thedudemc.dudeutils.data.DeathpointData;
import net.thedudemc.dudeutils.features.Feature;
import net.thedudemc.dudeutils.features.FeatureListener;
import net.thedudemc.dudeutils.util.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeatpointListener extends FeatureListener {

    public DeatpointListener(Feature feature) {
        super(feature);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (!feature.isEnabled()) return;
        DeathpointData data = (DeathpointData) feature.getSaveData();
        Player player = event.getEntity();
        DeathLocation location = data.setDeathpoint(player);
        player.sendMessage("You died at: " + StringUtils.getCoordinateString(location));
    }
}
