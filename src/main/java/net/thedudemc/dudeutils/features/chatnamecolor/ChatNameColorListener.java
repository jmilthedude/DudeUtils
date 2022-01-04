package net.thedudemc.dudeutils.features.chatnamecolor;

import net.thedudemc.dudeutils.data.ChatNameColorData;
import net.thedudemc.dudeutils.features.Feature;
import net.thedudemc.dudeutils.features.FeatureListener;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class ChatNameColorListener extends FeatureListener {

    public ChatNameColorListener(Feature feature) {
        super(feature);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (!feature.isEnabled()) return;
        Player player = event.getPlayer();
        ChatNameColorData data = (ChatNameColorData) feature.getSaveData();
        ChatColor color = data.getColor(player);
        event.setFormat(ChatColor.WHITE + "<" + color + "%s" + ChatColor.WHITE + ">" + ChatColor.RESET + " %s");
    }

    @EventHandler
    public void onTabList(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ChatNameColorData data = (ChatNameColorData) feature.getSaveData();
        ChatColor color = data.getColor(player);
        player.setPlayerListName(color + player.getName());
    }
}
