package net.thedudemc.dudeutils.features.singleplayersleep;

import net.thedudemc.dudeutils.DudeUtils;
import net.thedudemc.dudeutils.features.Feature;
import net.thedudemc.dudeutils.features.FeatureListener;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class SinglePlayerSleepListener extends FeatureListener {
    public SinglePlayerSleepListener(Feature feature) {
        super(feature);
    }

    @EventHandler
    public void onSleep(PlayerBedEnterEvent event) {
        if (!feature.isEnabled()) return;
        World world = event.getPlayer().getWorld();
        Bukkit.getScheduler().scheduleSyncDelayedTask(DudeUtils.getInstance(), () -> {
            world.setWeatherDuration(0);
            world.setThunderDuration(0);
            world.setThundering(false);
            world.setStorm(false);
        }, 5L);
    }
}
