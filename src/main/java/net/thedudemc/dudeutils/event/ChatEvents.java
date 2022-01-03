package net.thedudemc.dudeutils.event;

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



}
