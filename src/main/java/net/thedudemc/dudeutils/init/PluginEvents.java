package net.thedudemc.dudeutils.init;

import net.thedudemc.dudeutils.DudeUtils;
import net.thedudemc.dudeutils.event.*;
import net.thedudemc.dudeutils.event.PortalEvents;

public class PluginEvents {

    public static void register(DudeUtils plugin) {
        plugin.getServer().getPluginManager().registerEvents(new ChatEvents(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PortalEvents(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new BlacklistEvents(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new InventoryEvents(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new SleepEvent(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new VeinMinerEvent(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new NetherWaterEvent(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new VillagerEvents(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new AlternatorEvents(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new ShulkerEvents(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new EndermanEvents(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerEvents(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PetTransferEvent(), plugin);
    }
}
