package net.thedudemc.dudeutils.event;

import net.thedudemc.dudeutils.features.allies.AllyGroup;
import net.thedudemc.dudeutils.init.PluginData;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class AllyEvents implements Listener {

    @EventHandler
    public void onTarget(EntityTargetEvent event) {
        if (!(event.getEntity() instanceof Wolf)) {
            return;
        }
        Wolf attacker = (Wolf) event.getEntity();
        Entity target = event.getTarget();
        if (target == null)
            return;
        if (attacker.getOwner() == null)
            return;
        AnimalTamer tamer = attacker.getOwner();
        OfflinePlayer owner = Bukkit.getOfflinePlayer(tamer.getUniqueId());

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        Team team = scoreboard.getEntryTeam(owner.getName());
        if (team != null)
            if (isAlly(team, target.getName())) {
                event.setTarget(null);
            }
        if (isAlly(owner.getName(), target.getName())) {
            event.setTarget(null);
        }
    }

    private boolean isAlly(Team team, String target) {
        for (String s : team.getEntries()) {
            if (target.equalsIgnoreCase(s)) {
                return true;
            }
        }
        return false;
    }

    private boolean isAlly(String owner, String target) {
        AllyGroup group = PluginData.ALLY_DATA.getAllyGroup(owner);
        if (group == null) {
            return false;
        }
        if (group.getAllies().contains(target)) {
            return true;
        }
        return false;
    }

}
