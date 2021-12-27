package net.thedudemc.dudeutils.init;

import net.thedudemc.dudeutils.event.SleepEvent;

public class PluginTasks {

    public static void run() {
        if (PluginConfigs.FEATURES.ENABLED.get("SinglePlayerSleep")) SleepEvent.run();
    }
}
