package net.thedudemc.dudeutils.features;

public interface Tickable {
    void tick();

    default int frequency() {
        return 1;
    }
}
