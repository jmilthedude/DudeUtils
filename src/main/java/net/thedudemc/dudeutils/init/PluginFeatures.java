package net.thedudemc.dudeutils.init;

import net.thedudemc.dudeutils.DudeUtils;
import net.thedudemc.dudeutils.features.Feature;
import net.thedudemc.dudeutils.features.ally.AllyFeature;
import net.thedudemc.dudeutils.features.alternator.AlternatorFeature;
import net.thedudemc.dudeutils.features.blacklist.BlacklistFeature;
import net.thedudemc.dudeutils.features.chatnamecolor.ChatNameColorFeature;
import net.thedudemc.dudeutils.features.deathpoint.DeathpointFeature;
import net.thedudemc.dudeutils.features.disableendermengriefing.DisableEndermanGriefingFeature;
import net.thedudemc.dudeutils.features.elytra.HardenedElytraFeature;
import net.thedudemc.dudeutils.features.fastxp.FastXPFeature;
import net.thedudemc.dudeutils.features.inventorysort.InventorySortFeature;
import net.thedudemc.dudeutils.features.magnet.MagnetFeature;
import net.thedudemc.dudeutils.features.netherwater.NetherWaterFeature;
import net.thedudemc.dudeutils.features.portalutility.PortalUtilityFeature;
import net.thedudemc.dudeutils.features.shulkerdropstwo.ShulkerDropsTwoFeature;
import net.thedudemc.dudeutils.features.slimechunkcheck.SlimeChunkCheckFeature;
import net.thedudemc.dudeutils.features.spawnlight.SpawnLightLevelFeature;
import net.thedudemc.dudeutils.features.transferpet.TransferPetFeature;
import net.thedudemc.dudeutils.features.veinminer.VeinminerFeature;
import net.thedudemc.dudeutils.features.villagerutility.VillagerUtilityFeature;
import net.thedudemc.dudeutils.features.xpstorage.XPStorageFeature;
import net.thedudemc.dudeutils.util.Log;

import java.util.HashMap;
import java.util.Set;

public class PluginFeatures {

    public static HashMap<String, Feature> registry = new HashMap<>();

    public static MagnetFeature MAGNET;
    public static AllyFeature ALLY;
    public static DeathpointFeature DEATHPOINT;
    public static NetherWaterFeature NETHER_WATER;
    public static PortalUtilityFeature PORTAL_UTILITY;
    public static BlacklistFeature BLACKLIST;
    public static ChatNameColorFeature CHAT_NAME_COLOR;
    public static VeinminerFeature VEINMINER;
    public static AlternatorFeature ALTERNATOR;
    public static DisableEndermanGriefingFeature DISABLE_ENDERMAN_GRIEFING;
    public static ShulkerDropsTwoFeature SHULKER_DROPS_TWO;
    public static VillagerUtilityFeature VILLAGER_UTILITY;
    public static InventorySortFeature INVENTORY_SORT;
    public static SlimeChunkCheckFeature SLIME_CHUNK_CHECK;
    public static FastXPFeature FAST_XP;
    public static SpawnLightLevelFeature SPAWN_LIGHT_LEVEL;
    public static XPStorageFeature XP_STORAGE;
    public static HardenedElytraFeature HARDENED_ELYTRA;
    public static TransferPetFeature TRANSFER_PET;

    public static void init() {
        Log.info("Initializing Features");
        MAGNET = register(new MagnetFeature());
        ALLY = register(new AllyFeature());
        DEATHPOINT = register(new DeathpointFeature());
        NETHER_WATER = register(new NetherWaterFeature());
        PORTAL_UTILITY = register(new PortalUtilityFeature());
        BLACKLIST = register(new BlacklistFeature());
        CHAT_NAME_COLOR = register(new ChatNameColorFeature());
        VEINMINER = register(new VeinminerFeature());
        ALTERNATOR = register(new AlternatorFeature());
        DISABLE_ENDERMAN_GRIEFING = register(new DisableEndermanGriefingFeature());
        SHULKER_DROPS_TWO = register(new ShulkerDropsTwoFeature());
        VILLAGER_UTILITY = register(new VillagerUtilityFeature());
        INVENTORY_SORT = register(new InventorySortFeature());
        SLIME_CHUNK_CHECK = register(new SlimeChunkCheckFeature());
        FAST_XP = register(new FastXPFeature());
        SPAWN_LIGHT_LEVEL = register(new SpawnLightLevelFeature());
        XP_STORAGE = register(new XPStorageFeature());
        HARDENED_ELYTRA = register(new HardenedElytraFeature());
        TRANSFER_PET = register(new TransferPetFeature());
    }

    private static <T extends Feature> T register(T feature) {
        if(feature.getListener() != null) {
            DudeUtils.getInstance().getServer().getPluginManager().registerEvents(feature.getListener(), DudeUtils.getInstance());
            Log.info(" -registered " + feature.getName() + " listener");
        }
        registry.put(feature.getName(), feature);
        return feature;
    }

    public static Feature getFeature(String name) {
        if (registry.containsKey(name)) {
            return registry.get(name);
        }
        throw new RuntimeException("No feature found with that name: " + name);
    }

    public static Set<String> getNames() {
        return registry.keySet();
    }

}
