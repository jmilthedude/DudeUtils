package net.thedudemc.dudeutils.init;

import net.thedudemc.dudeutils.event.SleepEvent;
import net.thedudemc.dudeutils.features.magnet.MagnetHelper;
import net.thedudemc.dudeutils.features.portal.PortalParticles;

public class PluginTasks {

    public static void run() {
        if (PluginConfigs.PORTAL_UTILITY.SPAWN_PARTICLES) PortalParticles.run();
        if (PluginConfigs.FEATURES.ENABLED.get("Magnet")) MagnetHelper.run();
        if (PluginConfigs.FEATURES.ENABLED.get("SinglePlayerSleep")) SleepEvent.run();
    }
}
