package net.thedudemc.dudeutils.init;

import net.thedudemc.dudeutils.DudeUtils;
import net.thedudemc.dudeutils.event.*;

public class PluginEvents {

    public static void register(DudeUtils plugin) {
        plugin.getServer().getPluginManager().registerEvents(new ChatEvents(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PortalEvents(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new BlacklistEvents(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new AllyEvents(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new InventoryEvents(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerEvents(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new SleepEvent(), plugin);
    }
}
