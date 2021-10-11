package net.thedudemc.dudeutils.init;

import net.thedudemc.dudeutils.data.AllySaveData;
import net.thedudemc.dudeutils.data.BlacklistSaveData;
import net.thedudemc.dudeutils.data.DeathpointData;
import net.thedudemc.dudeutils.data.MagnetData;

public class PluginData {


    public static BlacklistSaveData BLACKLIST_DATA;
    public static AllySaveData ALLY_DATA;
    public static MagnetData MAGNET_DATA;
    public static DeathpointData DEATHPOINT;

    public static void register() {
        BLACKLIST_DATA = (BlacklistSaveData) new BlacklistSaveData().readData();
        ALLY_DATA = (AllySaveData) new AllySaveData().readData();
        MAGNET_DATA = (MagnetData) new MagnetData().readData();
        DEATHPOINT = (DeathpointData) new DeathpointData().readData();
    }

    public static void save() {
        BLACKLIST_DATA.writeData();
        ALLY_DATA.writeData();
        MAGNET_DATA.writeData();
        DEATHPOINT.writeData();
    }

}
