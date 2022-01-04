package net.thedudemc.dudeutils.features;

import net.thedudemc.dudeutils.DudeUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerBedEnterEvent;

import java.util.stream.Collectors;

public class SinglePlayerSleepFeature extends Feature implements Tickable {

    @Override
    public String getName() {
        return "single_player_sleep";
    }

    @Override
    public void onEnabled() {
    }

    @Override
    public void onDisabled() {

    }

    @Override
    public void tick() {
        if (this.isEnabled()) handleSleeping();
    }

    private void handleSleeping() {
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


    @EventHandler
    public void onSleep(PlayerBedEnterEvent event) {
        if (!isEnabled()) return;
        World world = event.getPlayer().getWorld();
        Bukkit.getScheduler().scheduleSyncDelayedTask(DudeUtils.getInstance(), () -> {
            world.setWeatherDuration(0);
            world.setThunderDuration(0);
            world.setThundering(false);
            world.setStorm(false);
        }, 5L);
    }
}
