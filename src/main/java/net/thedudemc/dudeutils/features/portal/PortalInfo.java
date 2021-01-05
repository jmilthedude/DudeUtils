package net.thedudemc.dudeutils.features.portal;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PortalInfo {

	private Player player;
	private Location location;

	public PortalInfo(Player player, Location location) {
		this.player = player;
		this.location = location;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

}
