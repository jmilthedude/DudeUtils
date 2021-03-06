package net.thedudemc.dudeutils.init;

import net.thedudemc.dudeutils.DudeUtils;
import net.thedudemc.dudeutils.command.*;

public class PluginCommands {

    public static DudeCommand DUDE;
    public static SlimeCommand SLIME;
    public static AllyCommand ALLY;
    public static ColorCommand COLOR;
    public static VeinMinerCommand VEIN_MINER;

    public static void register() {

        DUDE = (DudeCommand) registerCommand(new DudeCommand()).opOnly();
        if (PluginConfigs.FEATURES.ENABLED.get("SlimeChunkCheck")) SLIME = (SlimeCommand) registerCommand(new SlimeCommand().playerOnly());
        if (PluginConfigs.FEATURES.ENABLED.get("Allies")) ALLY = (AllyCommand) registerCommand(new AllyCommand().playerOnly());
        if (PluginConfigs.FEATURES.ENABLED.get("ChatNameColor")) COLOR = (ColorCommand) registerCommand(new ColorCommand().playerOnly());
        if (PluginConfigs.FEATURES.ENABLED.get("VeinMiner")) VEIN_MINER = (VeinMinerCommand) registerCommand(new VeinMinerCommand().playerOnly().opOnly());

    }

    private static PluginCommand registerCommand(PluginCommand command) {
        DudeUtils.getInstance().getCommand(command.getName()).setExecutor(command);
        DudeUtils.getInstance().getCommand(command.getName()).setTabCompleter(command);
        return command;
    }

}
