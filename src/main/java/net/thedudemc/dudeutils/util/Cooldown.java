package net.thedudemc.dudeutils.util;

import java.util.UUID;

public class Cooldown {
    private final UUID playerId;
    private int ticksRemaining;

    public Cooldown(UUID playerId, int cooldown) {
        this.playerId = playerId;
        this.ticksRemaining = cooldown;
    }

    public void update() {
        ticksRemaining--;
    }

    public boolean isComplete() {
        return ticksRemaining <= 0;
    }

    public UUID getPlayerId() {
        return playerId;
    }
}
