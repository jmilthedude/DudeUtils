package net.thedudemc.dudeutils.init;

import net.thedudemc.dudeutils.DudeUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;

import java.util.Random;

public class PluginTasks {

    private static final Random rand = new Random();

    public static void run() {
        //Speed up xp collection, reduces lag
        Bukkit.getScheduler().scheduleSyncRepeatingTask(DudeUtils.getInstance(), () -> Bukkit.getOnlinePlayers()
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
                        })), 0L, 10L);
    }
}
