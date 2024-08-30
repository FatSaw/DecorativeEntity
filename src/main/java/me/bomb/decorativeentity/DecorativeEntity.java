package me.bomb.decorativeentity;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class DecorativeEntity extends JavaPlugin {
	
	public void onEnable() {
		File workingdirectory = this.getDataFolder();
		if(!workingdirectory.exists()) {
			workingdirectory.mkdirs();
		}
		Logger logger = getLogger();
		MinecartOptions minecartoptions = new MinecartOptions(logger, new File(workingdirectory, "minecart.yml"));
		ArmorstandOptions armorstandoptions = new ArmorstandOptions(logger, new File(workingdirectory, "armorstand.yml"));
		HumanOptions humanoptions = new HumanOptions(logger, new File(workingdirectory, "human.yml"));
		PacketSender sender = new PacketSender(minecartoptions, armorstandoptions, humanoptions);
		Bukkit.getPluginManager().registerEvents(new EventListener(sender), this);
	}
}
