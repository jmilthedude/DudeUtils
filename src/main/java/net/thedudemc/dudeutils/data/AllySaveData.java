package net.thedudemc.dudeutils.data;

import com.google.gson.annotations.Expose;

import java.util.*;

public class AllySaveData extends SaveData {


    @Expose
    public HashMap<UUID, List<UUID>> allyGroups = new HashMap<>();

    @Override
    public String getName() {
        return "ally";
    }

    @Override
    protected void reset() {
    }

    public boolean addAlly(UUID owner, UUID ally) {
        List<UUID> allies = allyGroups.computeIfAbsent(owner, o -> new ArrayList<>());
        if (!allies.contains(ally)) {
            allies.add(ally);
            this.markDirty();
            return true;
        }
        return false;
    }

    public boolean removeAlly(UUID owner, UUID ally) {
        List<UUID> allies = allyGroups.computeIfAbsent(owner, o -> new ArrayList<>());
        if (allies.remove(ally)) {
            this.markDirty();
            return true;
        }
        return false;
    }

    public List<UUID> getAllies(UUID owner) {
        List<UUID> allies = allyGroups.computeIfAbsent(owner, o -> new ArrayList<>());
        this.markDirty();
        return allies;
    }
}
