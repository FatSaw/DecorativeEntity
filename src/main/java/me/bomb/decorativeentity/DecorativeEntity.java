package me.bomb.decorativeentity;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class DecorativeEntity extends JavaPlugin {
	
	private final PacketSender sender;
	
	public DecorativeEntity() {
		this.sender = new PacketSender();
	}
	
	public void onEnable() {
		this.reload();
		PluginCommand decorativeentitycommand = getCommand("decorativeentity");
		decorativeentitycommand.setExecutor(new DeCommand(this));
		Bukkit.getPluginManager().registerEvents(new EventListener(sender), this);
	}
	
	protected void reload() {
		File workingdirectory = this.getDataFolder();
		if(!workingdirectory.exists()) {
			workingdirectory.mkdirs();
		}
		Logger logger = getLogger();
		MinecartOptions minecartoptions = new MinecartOptions(logger, new File(workingdirectory, "minecart.yml"));
		ArmorstandOptions armorstandoptions = new ArmorstandOptions(logger, new File(workingdirectory, "armorstand.yml"));
		HumanOptions humanoptions = new HumanOptions(logger, new File(workingdirectory, "human.yml"));
		this.sender.minecartoptions = minecartoptions;
		this.sender.armorstandoptions = armorstandoptions;
		this.sender.humanoptions = humanoptions;
	}
}
