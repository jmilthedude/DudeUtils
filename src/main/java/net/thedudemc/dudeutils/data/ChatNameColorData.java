package net.thedudemc.dudeutils.data;

import com.google.gson.annotations.Expose;
import net.thedudemc.dudeutils.features.ChatNameColorFeature;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class ChatNameColorData extends SaveData {

    @Expose private HashMap<UUID, String> nameColors = new HashMap<>();

    @Override
    public String getName() {
        return "chat_name_color";
    }

    @Override
    protected void reset() {

    }

    public void setColor(Player player, String color) {
        this.setColor(player.getUniqueId(), color);
    }

    private void setColor(UUID playerId, String color) {
        if (nameColors.containsKey(playerId)) {
            String existing = nameColors.get(playerId);
            if (existing.equalsIgnoreCase(color)) return;
            nameColors.put(playerId, color);
        } else {
            nameColors.put(playerId, color);
            this.markDirty();
        }
    }

    public ChatColor getColor(Player player) {
        return this.getColor(player.getUniqueId());
    }

    private ChatColor getColor(UUID playerId) {
        return ChatNameColorFeature.getColor(nameColors.getOrDefault(playerId, ChatColor.WHITE.name()));
    }


}
