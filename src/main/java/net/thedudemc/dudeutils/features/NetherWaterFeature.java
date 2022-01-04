package net.thedudemc.dudeutils.features;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

public class NetherWaterFeature extends Feature {
    @Override
    public String getName() {
        return "nether_water";
    }

    @Override
    public void onEnabled() {

    }

    @Override
    public void onDisabled() {

    }


    @EventHandler
    public void onWaterBucketUse(PlayerBucketEmptyEvent event) {
        if (!isEnabled()) return;

        Block block = event.getBlockClicked();
        Block water = block.getRelative(event.getBlockFace());
        World world = block.getWorld();
        if (world.getEnvironment() != World.Environment.NETHER) return;

        water.setType(Material.WATER);
        water.getState().update();
    }

}
