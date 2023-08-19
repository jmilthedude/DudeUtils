package net.thedudemc.dudeutils.init;

import net.thedudemc.dudeutils.DudeUtils;
import net.thedudemc.dudeutils.features.Feature;
import net.thedudemc.dudeutils.tasks.PluginTask;
import net.thedudemc.dudeutils.util.Log;
import net.thedudemc.dudeutils.util.Tickable;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;

import java.util.HashMap;

public class PluginTasks {

    public static final HashMap<NamespacedKey, PluginTask> registry = new HashMap<>();

    private static long currentTick = 1;

    public static void init() {
        Log.info("Initializing Tasks");
        // registerTasks(new ExperienceCollectionTask());

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
                .filter(tickable -> tickable.shouldTick(currentTick))
                .forEach(Tickable::tick);

        PluginFeatures.registry.values()
                .stream()
                .filter(feature -> feature instanceof Tickable)
                .filter(Feature::isEnabled)
                .map(feature -> (Tickable) feature)
                .filter(tickable -> tickable.shouldTick(currentTick))
                .forEach(Tickable::tick);

        currentTick++;
    }
}
