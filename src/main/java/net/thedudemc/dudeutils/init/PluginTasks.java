package net.thedudemc.dudeutils.init;

import net.thedudemc.dudeutils.DudeUtils;
import net.thedudemc.dudeutils.features.Tickable;
import net.thedudemc.dudeutils.tasks.ExperienceCollectionTask;
import net.thedudemc.dudeutils.tasks.PluginTask;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;

import java.util.HashMap;

public class PluginTasks {

    public static final HashMap<NamespacedKey, PluginTask> registry = new HashMap<>();

    private static long currentTick = 0;

    public static void init() {
        registerTasks(new ExperienceCollectionTask());

        run();
    }

    private static void registerTasks(PluginTask... tasks) {
        for (PluginTask task : tasks) {
            registry.put(task.getKey(), task);
        }
    }

    private static void run() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(DudeUtils.getInstance(), PluginTasks::tick, 0L, 1L);
    }

    private static void tick() {
        registry.values()
                .stream()
                .filter(task -> task.frequency() % currentTick == 0)
                .forEach(Tickable::tick);

        PluginFeatures.registry.values()
                .stream()
                .filter(feature -> feature instanceof Tickable)
                .map(feature -> (Tickable) feature)
                .filter(tickable -> tickable.frequency() % currentTick == 0)
                .forEach(Tickable::tick);

        currentTick++;
    }
}
