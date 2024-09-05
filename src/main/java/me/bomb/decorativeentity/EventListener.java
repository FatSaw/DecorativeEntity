package me.bomb.decorativeentity;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public final class EventListener implements Listener {

	private final PacketSender sender;

	protected EventListener(PacketSender sender) {
		this.sender = sender;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		new PacketListener(sender, event.getPlayer());
	}

}
