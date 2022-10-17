package net.thedudemc.dudeutils.data;

import com.google.gson.annotations.Expose;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class XPStorageData extends SaveData {


    @Expose
    public HashMap<UUID, Experience> storedXp = new HashMap<>();

    @Override
    public String getName() {
        return "xp_storage";
    }

    @Override
    protected void reset() {
    }

    public void collect(Player player) {
        Experience exp = storedXp.remove(player.getUniqueId());
        if (exp == null) return;
        player.giveExp(exp.getExperience());
        player.sendMessage("You collected " + player.getLevel() + "." + (int) (player.getExp() * 100) + " levels!");
        player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, .7f, 1.0f);
        this.markDirty();
    }

    public void store(Player player) {
        Experience experience = storedXp.computeIfAbsent(player.getUniqueId(), id -> new Experience(0));
        int currentExperience = player.getTotalExperience();
        if (currentExperience == 0) return;
        experience.setExperience(experience.getExperience() + currentExperience);
        player.setTotalExperience(0);
        player.setLevel(0);
        player.setExp(0f);
        player.sendMessage("You just stored " + currentExperience + " points.");
        player.playSound(player, Sound.ENTITY_GENERIC_DRINK, .7f, 1.0f);
        this.markDirty();
    }

    public void query(Player player) {
        Experience experience = storedXp.computeIfAbsent(player.getUniqueId(), id -> new Experience(0));
        player.sendMessage("You have " + experience.getExperience() + " points stored.");
        player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, .7f, 1.0f);
    }

    public static class Experience {
        @Expose int experience;

        public Experience(int currentExperience) {
            this.experience = currentExperience;
        }

        public int getExperience() {
            return experience;
        }

        public void setExperience(int experience) {
            this.experience = experience;
        }
    }
}
