package net.thedudemc.dudeutils.features.singleplayersleep;

import net.thedudemc.dudeutils.features.Feature;
import net.thedudemc.dudeutils.features.FeatureListener;
import net.thedudemc.dudeutils.util.Tickable;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.stream.Collectors;

public class SinglePlayerSleepFeature extends Feature implements Tickable {

    @Override
    public String getName() {
        return "single_player_sleep";
    }

    @Override
    public FeatureListener getListener() {
        return new SinglePlayerSleepListener(this);
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

}
