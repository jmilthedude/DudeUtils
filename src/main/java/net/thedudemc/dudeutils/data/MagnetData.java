package net.thedudemc.dudeutils.data;

import com.google.gson.annotations.Expose;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MagnetData extends SaveData {

    @Expose public List<String> magnetizedPlayers = new ArrayList<>();

    @Override
    public String getName() {
        return "magnet";
    }

    @Override
    protected void reset() {

    }

    public void addPlayer(Player p) {
        magnetizedPlayers.add(p.getName());
        this.markDirty();
    }

    public void removePlayer(Player p) {
        magnetizedPlayers.remove(p.getName());
        this.markDirty();
    }

    @Override
    public void writeData() {
        super.writeData();
    }
}
