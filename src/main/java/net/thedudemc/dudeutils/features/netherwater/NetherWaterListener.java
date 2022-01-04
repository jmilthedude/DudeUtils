package net.thedudemc.dudeutils.features.netherwater;

import net.thedudemc.dudeutils.features.Feature;
import net.thedudemc.dudeutils.features.FeatureListener;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

public class NetherWaterListener extends FeatureListener {
    public NetherWaterListener(Feature feature) {
        super(feature);
    }

    @EventHandler
    public void onWaterBucketUse(PlayerBucketEmptyEvent event) {
        if (!feature.isEnabled()) return;

        Block block = event.getBlockClicked();
        Block water = block.getRelative(event.getBlockFace());
        World world = block.getWorld();
        if (world.getEnvironment() != World.Environment.NETHER) return;

        water.setType(Material.WATER);
        water.getState().update();
    }
}
