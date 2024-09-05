package me.bomb.decorativeentity;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import me.bomb.decorativeentity.options.ArmorstandOptions;
import me.bomb.decorativeentity.options.EndCrystalOptions;
import me.bomb.decorativeentity.options.FallingBlockOptions;
import me.bomb.decorativeentity.options.HumanOptions;
import me.bomb.decorativeentity.options.MinecartOptions;

public final class DecorativeEntity extends JavaPlugin {

	private final PacketSender sender;

	public DecorativeEntity() {
		this.sender = new PacketSender(this, Bukkit.getScheduler());
	}

	public void onEnable() {
		this.reload();
		PluginCommand decorativeentitycommand = getCommand("decorativeentity");
		decorativeentitycommand.setExecutor(new DeCommand(this));
		Bukkit.getPluginManager().registerEvents(new EventListener(sender), this);
	}

	protected void reload() {
		File workingdirectory = this.getDataFolder();
		if (!workingdirectory.exists()) {
			workingdirectory.mkdirs();
		}
		Logger logger = getLogger();
		MinecartOptions minecartoptions = new MinecartOptions(logger, new File(workingdirectory, "minecart.yml"));
		ArmorstandOptions armorstandoptions = new ArmorstandOptions(logger, new File(workingdirectory, "armorstand.yml"));
		EndCrystalOptions endcrystaloptions = new EndCrystalOptions(logger, new File(workingdirectory, "endcrystal.yml"));
		FallingBlockOptions fallingblockoptions = new FallingBlockOptions(logger, new File(workingdirectory, "fallingblock.yml"));
		HumanOptions humanoptions = new HumanOptions(logger, new File(workingdirectory, "human.yml"));
		this.sender.minecartoptions = minecartoptions;
		this.sender.armorstandoptions = armorstandoptions;
		this.sender.endcrystaloptions = endcrystaloptions;
		this.sender.fallingblockoptions = fallingblockoptions;
		this.sender.humanoptions = humanoptions;
	}
}
