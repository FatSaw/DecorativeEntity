package me.bomb.decorativeentity;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class DecorativeEntity extends JavaPlugin {
	
	public void onEnable() {
		MinecartOptions minecartoptions = new MinecartOptions(this);
		ArmorstandOptions armorstandoptions = new ArmorstandOptions(this);
		HumanOptions humanoptions = new HumanOptions(this);
		PacketSender sender = new PacketSender(minecartoptions, armorstandoptions, humanoptions);
		Bukkit.getPluginManager().registerEvents(new EventListener(sender), this);
	}
}
