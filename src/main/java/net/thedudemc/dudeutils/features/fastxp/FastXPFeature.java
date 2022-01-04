package net.thedudemc.dudeutils.features.fastxp;

import net.thedudemc.dudeutils.features.Feature;
import net.thedudemc.dudeutils.features.FeatureListener;
import net.thedudemc.dudeutils.util.Tickable;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;

import java.util.Random;

public class FastXPFeature extends Feature implements Tickable {

    private final Random rand = new Random();

    @Override
    public String getName() {
        return "fast_xp";
    }

    @Override
    public FeatureListener getListener() {
        return null;
    }

    @Override
    public void tick() {
        if (!isEnabled()) return;
        Bukkit.getOnlinePlayers()
                .forEach(player -> player.getNearbyEntities(.5, .5, .5)
                        .stream()
                        .filter(entity -> entity.getType().equals(EntityType.EXPERIENCE_ORB))
                        .map(entity -> (ExperienceOrb) entity)
                        .forEach(orb -> {
                            player.giveExp(orb.getExperience());
                            orb.remove();
                        })
                );
    }
}
