package net.thedudemc.dudeutils.init;

import net.thedudemc.dudeutils.config.*;
import net.thedudemc.dudeutils.util.Log;

public class PluginConfigs {

    public static FeaturesConfig FEATURES;
    public static MagnetConfig MAGNET;
    public static PortalConfig PORTAL_UTILITY;
    public static RecipeConfig RECIPES;
    public static SpecialConfig SPECIAL;
    public static VeinMinerConfig VEINMINER;
    public static GhastLogConfig GHAST_LOG;

    public static void init() {
        Log.info("Initializing Configs");
        FEATURES = (FeaturesConfig) new FeaturesConfig().readConfig();
        MAGNET = (MagnetConfig) new MagnetConfig().readConfig();
        PORTAL_UTILITY = (PortalConfig) new PortalConfig().readConfig();
        RECIPES = (RecipeConfig) new RecipeConfig().readConfig();
        SPECIAL = (SpecialConfig) new SpecialConfig().readConfig();
        VEINMINER = (VeinMinerConfig) new VeinMinerConfig().readConfig();
        GHAST_LOG = (GhastLogConfig) new GhastLogConfig().readConfig();
    }

    public static void save() {
        FEATURES.writeConfig();
        MAGNET.writeConfig();
        PORTAL_UTILITY.writeConfig();
        RECIPES.writeConfig();
        SPECIAL.writeConfig();
        VEINMINER.writeConfig();
        GHAST_LOG.writeConfig();
    }
}
