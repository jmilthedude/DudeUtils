package net.thedudemc.dudeutils.event;

import net.thedudemc.dudeutils.init.PluginConfigs;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scoreboard.Team;

public class ChatEvents implements Listener {

    private boolean enableChatNameColor;

    public ChatEvents() {
        this.enableChatNameColor = true;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (PluginConfigs.CHAT.ENABLE_NAME_COLORS) {
            Player p = event.getPlayer();
            Team team = p.getScoreboard().getEntryTeam(p.getName());
            if (team != null) {
                ChatColor color = team.getColor();
                event.setFormat(ChatColor.WHITE + "<" + color + "%s" + ChatColor.WHITE + ">" + ChatColor.RESET + " %s");
            }
        }
    }

}
