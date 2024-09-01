package me.bomb.decorativeentity;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public final class DeCommand implements CommandExecutor {
	
	private final DecorativeEntity decorativeentity;
	
	protected DeCommand(DecorativeEntity decorativeentity) {
		this.decorativeentity = decorativeentity;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("decorativeentity.reload")) {
			sender.sendMessage("No permission");
			return true;
		}
		if(args.length < 1) {
			sender.sendMessage("Avilable args: reload");
			return true;
		}
		if(args[0].toLowerCase().equals("reload")) {
			decorativeentity.reload();
			sender.sendMessage("Reloading configs");
			return true;
		}
		sender.sendMessage("Unknown action");
		return true;
	}

}
