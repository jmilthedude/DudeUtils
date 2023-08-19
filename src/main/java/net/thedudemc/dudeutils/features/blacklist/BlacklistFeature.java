package net.thedudemc.dudeutils.features.blacklist;

import net.thedudemc.dudeutils.features.Feature;
import net.thedudemc.dudeutils.features.FeatureListener;
import net.thedudemc.dudeutils.gui.BlacklistGui;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class BlacklistFeature extends Feature {

    public static final HashMap<UUID, BlacklistGui> openedInventoryMap = new HashMap<>();

    public static boolean hasBlacklistOpen(Player player) {
        return openedInventoryMap.containsKey(player.getUniqueId());
    }

    @Override
    public String getName() {
        return "blacklist";
    }

    @Override
    public FeatureListener getListener() {
        return new BlacklistListener(this);
    }


}
