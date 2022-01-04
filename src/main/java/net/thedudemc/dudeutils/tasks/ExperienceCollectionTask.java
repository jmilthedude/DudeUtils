package net.thedudemc.dudeutils.tasks;

import net.thedudemc.dudeutils.DudeUtils;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;

import javax.annotation.Nonnull;
import java.util.Random;

public class ExperienceCollectionTask extends PluginTask {

    private static final Random rand = new Random();

    @Override
    public void tick() {
        Bukkit.getOnlinePlayers()
                .forEach(player -> player.getNearbyEntities(.5, .5, .5)
                        .stream()
                        .filter(entity -> entity.getType().equals(EntityType.EXPERIENCE_ORB))
                        .map(entity -> (ExperienceOrb) entity)
                        .forEach(orb -> {
                            player.giveExp(orb.getExperience());
                            orb.remove();
                            player.playSound(player.getLocation(),
                                    Sound.ENTITY_EXPERIENCE_ORB_PICKUP,
                                    SoundCategory.PLAYERS,
                                    Math.random() > .95d ? .7f : 0f,
                                    (rand.nextFloat() - rand.nextFloat()) * 0.35F + 0.9F);
                        }));
    }

    @Nonnull
    @Override
    public NamespacedKey getKey() {
        return DudeUtils.getKey("experience_collection");
    }
}
