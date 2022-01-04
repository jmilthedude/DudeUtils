package net.thedudemc.dudeutils.features.deathpoint;

import com.google.gson.annotations.Expose;
import net.thedudemc.dudeutils.util.ItemStackUtils;
import net.thedudemc.dudeutils.util.Log;
import net.thedudemc.dudeutils.util.StringUtils;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.io.IOException;
import java.time.Instant;

public class DeathLocation {
    @Expose
    private int x, y, z;
    @Expose
    private String dimension;
    @Expose
    private long time;
    @Expose
    private String inventory;

    public DeathLocation(int x, int y, int z, String dimension) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.dimension = dimension;
        this.time = Instant.now().toEpochMilli();
    }

    public static DeathLocation fromLocation(Location location) {
        if (location.getWorld() == null) return null;
        return new DeathLocation(
                location.getBlockX(),
                location.getBlockY(),
                location.getBlockZ(),
                location.getWorld()
                        .getEnvironment()
                        .toString()
                        .toLowerCase()
        );
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public String getDimension() {
        return dimension;
    }

    public long getTimeSince() {
        return Instant.now().toEpochMilli() - this.time;
    }

    @Override
    public String toString() {
        return StringUtils.getCoordinateString(this) + " - " + StringUtils.formatDuration(this.getTimeSince());
    }

    public void setInventory(PlayerInventory inventory) {
        this.inventory = ItemStackUtils.playerInventoryToBase64(inventory);
    }

    public ItemStack[] getInventory() {
        try {
            return ItemStackUtils.itemStackArrayFromBase64(this.inventory);
        } catch (IOException exception) {
            Log.error("Error converting string to inventory.");
            Log.error(exception.getMessage());
        }
        return new ItemStack[0];
    }
}
