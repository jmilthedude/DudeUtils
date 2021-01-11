package net.thedudemc.dudeutils.data;

import com.google.gson.annotations.Expose;
import net.thedudemc.dudeutils.features.allies.AllyGroup;

import java.util.ArrayList;
import java.util.List;

public class AllySaveData extends SaveData {


    @Expose
    public List<AllyGroup> ALLY_GROUPS = new ArrayList<>();


    @Override
    public String getName() {
        return "allies";
    }

    @Override
    protected void reset() {
        ALLY_GROUPS.add(new AllyGroup("player1")
                .addAlliedPlayer("player2")
                .addAlliedPlayer("player3"));
        ALLY_GROUPS.add(new AllyGroup("player4")
                .addAlliedPlayer("player5")
                .addAlliedPlayer("player6"));
    }

    public AllyGroup getOrCreateGroup(String owner) {
        for (AllyGroup group : ALLY_GROUPS) {
            if (group.getOwner().equalsIgnoreCase(owner))
                return group;
        }
        return new AllyGroup(owner);
    }

    public void addAllyGroup(AllyGroup group) {
        for (AllyGroup existing : ALLY_GROUPS) {
            if (existing.getOwner().equalsIgnoreCase(group.getOwner()))
                return;
        }
        ALLY_GROUPS.add(group);
    }

}
