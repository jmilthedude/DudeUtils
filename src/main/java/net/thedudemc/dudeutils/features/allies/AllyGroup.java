package net.thedudemc.dudeutils.features.allies;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class AllyGroup {

    @Expose private String owner;
    @Expose private List<String> allies = new ArrayList<>();

    public AllyGroup(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public List<String> getAllies() {
        return allies;
    }

    public AllyGroup addAlliedPlayer(String ally) {
        allies.add(ally);
        return this;
    }

    public AllyGroup removeAlliedPlayer(String ally) {
        allies.remove(ally);
        return this;
    }

    public void addAlliedPlayersFromArray(String[] players) {
        for (String s : players) {
            if (!allies.contains(s))
                allies.add(s);
        }
    }

}
