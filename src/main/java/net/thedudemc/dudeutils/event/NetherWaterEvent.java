package net.thedudemc.dudeutils.event;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

public class NetherWaterEvent implements Listener {

    @EventHandler
    public void onWaterBucketUse(PlayerBucketEmptyEvent event) {
        Block block = event.getBlockClicked();
        Block water = block.getRelative(event.getBlockFace());
        World world = block.getWorld();
        if (world.getEnvironment() != World.Environment.NETHER) return;

        water.setType(Material.WATER);
        water.getState().update();

    }

}
