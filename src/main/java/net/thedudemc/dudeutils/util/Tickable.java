package net.thedudemc.dudeutils.util;

public interface Tickable {
    void tick();

    default int frequency() {
        return 1;
    }

    default boolean shouldTick(long currentTick) {
        if (frequency() == 1) {
            return true;
        } else return currentTick % frequency() == 0;
    }
}
