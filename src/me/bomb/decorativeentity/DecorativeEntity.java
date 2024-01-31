package me.bomb.decorativeentity;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class DecorativeEntity extends JavaPlugin {
	public void onEnable() {
		MinecartOptions minecartoptions = new MinecartOptions(this);
		ArmorstandOptions armorstandoptions = new ArmorstandOptions(this);
		PacketCache cache = new PacketCache(minecartoptions,armorstandoptions);
		Bukkit.getPluginManager().registerEvents(new EventListener(cache), this);
	}
}
