package net.thedudemc.dudeutils.features.singleplayersleep;

import net.thedudemc.dudeutils.DudeUtils;
import net.thedudemc.dudeutils.features.Feature;
import net.thedudemc.dudeutils.features.FeatureListener;
import net.thedudemc.dudeutils.init.PluginData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class SinglePlayerSleepListener extends FeatureListener {
    public SinglePlayerSleepListener(Feature feature) {
        super(feature);
    }

    @EventHandler
    public void onSleep(PlayerBedEnterEvent event) {
        if (!feature.isEnabled()) return;
        Player player = event.getPlayer();

        World world = player.getWorld();
        if(event.getBedEnterResult().equals(PlayerBedEnterEvent.BedEnterResult.OK)) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(DudeUtils.getInstance(), () -> {
                world.setWeatherDuration(0);
                world.setThunderDuration(0);
                world.setThundering(false);
                world.setStorm(false);
            }, 5L);
            ChatColor color = PluginData.CHAT_NAME_COLOR_DATA.getColor(player);
            Bukkit.broadcastMessage(color + player.getName() + ChatColor.RESET + " is sleeping.");
        }
    }
}
