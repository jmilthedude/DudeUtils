package net.thedudemc.dudeutils.features;

import net.thedudemc.dudeutils.data.DeathpointData;
import net.thedudemc.dudeutils.features.deathpoint.DeathLocation;
import net.thedudemc.dudeutils.util.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathpointFeature extends Feature {

    @Override
    public String getName() {
        return "deathpoint";
    }

    @Override
    public void doEnable() {

    }

    @Override
    public void doDisable() {

    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (!isEnabled()) return;
        DeathpointData data = this.getSaveData();
        Player player = event.getEntity();
        DeathLocation location = data.setDeathpoint(player);
        player.sendMessage("You died at: " + StringUtils.getCoordinateString(location));
    }
}
