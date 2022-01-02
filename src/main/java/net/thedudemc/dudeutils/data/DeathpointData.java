package net.thedudemc.dudeutils.data;

import com.google.gson.annotations.Expose;
import net.thedudemc.dudeutils.features.deathpoint.DeathHistory;
import net.thedudemc.dudeutils.features.deathpoint.DeathLocation;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class DeathpointData extends SaveData {

    @Expose
    public HashMap<UUID, DeathHistory> deathHistoryMap = new HashMap<>();

    @Override
    public String getName() {
        return "deathpoint";
    }

    @Override
    protected void reset() {

    }

    public DeathHistory getDeathHistory(Player player) {
        if (deathHistoryMap.containsKey(player.getUniqueId())) return deathHistoryMap.get(player.getUniqueId());

        DeathHistory history = deathHistoryMap.put(player.getUniqueId(), new DeathHistory(player.getUniqueId()));
        this.markDirty();
        return history;
    }

    public DeathLocation setDeathpoint(Player player) {
        DeathLocation location = DeathLocation.fromLocation(player.getLocation());
        if (location != null) {
            location.setInventory(player.getInventory());
            DeathHistory history = deathHistoryMap.computeIfAbsent(player.getUniqueId(), DeathHistory::new);
            history.addLocation(location);
            this.markDirty();
        }
        return location;
    }

    public List<DeathLocation> getDeathpoints(Player player) {
        return getDeathHistory(player).getDeathpoints();
    }

    public DeathLocation getLatestDeathLocation(Player player) {
        return getDeathpoints(player).isEmpty() ? null : getDeathpoints(player).get(0);
    }
}
