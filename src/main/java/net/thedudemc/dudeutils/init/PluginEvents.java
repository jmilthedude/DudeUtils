package net.thedudemc.dudeutils.init;

import net.thedudemc.dudeutils.DudeUtils;
import net.thedudemc.dudeutils.event.MiscEvents;
import net.thedudemc.dudeutils.event.PlayerEvents;
import net.thedudemc.dudeutils.util.Log;
import org.bukkit.plugin.Plugin;

public class PluginEvents {

    public static void init() {
        Log.info("Initializing Events");
        Plugin plugin = DudeUtils.getInstance();

        plugin.getServer().getPluginManager().registerEvents(new MiscEvents(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerEvents(), plugin);
    }
}
