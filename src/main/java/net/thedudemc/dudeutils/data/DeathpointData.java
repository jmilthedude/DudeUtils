package net.thedudemc.dudeutils.data;

import com.google.gson.annotations.Expose;
import net.thedudemc.dudeutils.features.deathpoint.DeathLocation;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class DeathpointData extends SaveData {

    @Expose
    public HashMap<UUID, DeathLocation> LOCATIONS = new HashMap<>();

    @Override
    public String getName() {
        return "Deathpoint";
    }

    @Override
    protected void reset() {

    }

    public DeathLocation setDeathpoint(Player player) {
        DeathLocation location = DeathLocation.fromLocation(player.getLocation());
        LOCATIONS.put(player.getUniqueId(), location);
        this.markDirty();
        return location;
    }

    public DeathLocation getDeathpoint(Player player) {
        return LOCATIONS.get(player.getUniqueId());
    }
}
