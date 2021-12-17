package net.thedudemc.dudeutils.event;

import net.thedudemc.dudeutils.DudeUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

import java.util.stream.Collectors;

public class SleepEvent implements Listener {

    @EventHandler
    public void onSleep(PlayerBedEnterEvent event) {
        World world = event.getPlayer().getWorld();
        Bukkit.getScheduler().scheduleSyncDelayedTask(DudeUtils.getInstance(), () -> {
            world.setWeatherDuration(0);
            world.setThunderDuration(0);
            world.setThundering(false);
            world.setStorm(false);
        }, 5L);
    }

    public static void run() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(DudeUtils.getInstance(), new Runnable() {
            @Override
            public void run() {

                Bukkit.getOnlinePlayers()
                        .stream()
                        .filter(LivingEntity::isSleeping)
                        .map(Entity::getWorld).collect(Collectors.toSet())
                        .forEach(world -> setTime(world, getNextTime(world)))
                ;
            }

            private long getNextTime(World world) {
                return world.getFullTime() + 100L;
            }

            private void setTime(World world, long time) {
                world.setFullTime(time);
            }
        }, 0L, 1L);
    }
}
