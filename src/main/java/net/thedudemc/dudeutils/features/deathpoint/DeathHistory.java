package net.thedudemc.dudeutils.features.deathpoint;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DeathHistory {
    @Expose private UUID player;
    @Expose private List<DeathLocation> locations = new ArrayList<>();

    public DeathHistory(UUID player) {
        this.player = player;
    }

    public void addLocation(DeathLocation location) {
        locations.add(0, location);
        if (locations.size() >= 4) {
            locations.remove(3);
        }
    }

    public List<DeathLocation> getDeathpoints() {
        return new ArrayList<>(this.locations);
    }
}
