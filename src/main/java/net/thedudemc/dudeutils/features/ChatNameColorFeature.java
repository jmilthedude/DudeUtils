package net.thedudemc.dudeutils.features;

import net.thedudemc.dudeutils.data.ChatNameColorData;
import net.thedudemc.dudeutils.init.PluginData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatNameColorFeature extends Feature {

    private static final HashMap<String, ChatColor> colors = new HashMap<String, ChatColor>() {
        {
            put("Aqua", ChatColor.AQUA);
            put("Black", ChatColor.BLACK);
            put("Blue", ChatColor.DARK_BLUE);
            put("Cyan", ChatColor.DARK_AQUA);
            put("DarkRed", ChatColor.DARK_RED);
            put("Gray", ChatColor.DARK_GRAY);
            put("Green", ChatColor.DARK_GREEN);
            put("LightBlue", ChatColor.BLUE);
            put("LightGray", ChatColor.GRAY);
            put("LimeGreen", ChatColor.GREEN);
            put("Magenta", ChatColor.LIGHT_PURPLE);
            put("Orange", ChatColor.GOLD);
            put("Purple", ChatColor.DARK_PURPLE);
            put("Red", ChatColor.RED);
            put("White", ChatColor.WHITE);
            put("Yellow", ChatColor.YELLOW);
        }
    };

    public static List<String> getColorNames() {
        return new ArrayList<>(colors.keySet());
    }

    public static ChatColor getColor(String name) {
        return colors.getOrDefault(name, ChatColor.RESET);
    }

    public void setNameColor(Player player, String color) {
        PluginData.CHAT_NAME_COLOR_DATA.setColor(player, color);
    }

    public void resetNameColor(Player player) {
        PluginData.CHAT_NAME_COLOR_DATA.setColor(player, "RESET");
    }

    @Override
    public String getName() {
        return "chat_name_color";
    }

    @Override
    public void onEnabled() {
    }

    @Override
    public void onDisabled() {
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (!this.isEnabled()) return;
        Player player = event.getPlayer();
        ChatNameColorData data = (ChatNameColorData) this.getSaveData();
        ChatColor color = data.getColor(player);
        event.setFormat(ChatColor.WHITE + "<" + color + "%s" + ChatColor.WHITE + ">" + ChatColor.RESET + " %s");
    }

    @EventHandler
    public void onTabList(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ChatNameColorData data = (ChatNameColorData) this.getSaveData();
        ChatColor color = data.getColor(player);
        player.setPlayerListName(color + player.getName());
    }
}
