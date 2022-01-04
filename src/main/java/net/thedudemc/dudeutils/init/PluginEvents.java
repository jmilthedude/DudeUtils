package net.thedudemc.dudeutils.init;

import net.thedudemc.dudeutils.DudeUtils;
import net.thedudemc.dudeutils.event.MiscEvents;
import net.thedudemc.dudeutils.event.PetTransferEvent;
import net.thedudemc.dudeutils.event.PlayerEvents;
import org.bukkit.plugin.Plugin;

public class PluginEvents {

    public static void init() {
        Plugin plugin = DudeUtils.getInstance();

        plugin.getServer().getPluginManager().registerEvents(new MiscEvents(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerEvents(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PetTransferEvent(), plugin);
    }
}
