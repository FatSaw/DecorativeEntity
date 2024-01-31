package me.bomb.decorativeentity;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public final class EventListener implements Listener {
	
	private final PacketCache cache;
	
	protected EventListener(PacketCache cache) {
		this.cache = cache;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		new PacketHandler(cache, event.getPlayer());
	}

}
