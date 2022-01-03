package net.thedudemc.dudeutils.init;

import net.thedudemc.dudeutils.DudeUtils;
import net.thedudemc.dudeutils.event.*;
import net.thedudemc.dudeutils.event.MiscEvents;

public class PluginEvents {

    public static void register(DudeUtils plugin) {
        plugin.getServer().getPluginManager().registerEvents(new MiscEvents(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new InventoryEvents(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new SleepEvent(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new VillagerEvents(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new ShulkerEvents(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerEvents(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PetTransferEvent(), plugin);
    }
}
