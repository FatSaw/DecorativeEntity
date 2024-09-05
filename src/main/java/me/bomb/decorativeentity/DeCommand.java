package me.bomb.decorativeentity;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R3.block.CraftBlock;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_16_R3.Block;
import net.minecraft.server.v1_16_R3.IBlockData;

public final class DeCommand implements CommandExecutor {

	private final DecorativeEntity decorativeentity;

	protected DeCommand(DecorativeEntity decorativeentity) {
		this.decorativeentity = decorativeentity;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		boolean noreloadperm, nostateidperm;
		if ((noreloadperm = !sender.hasPermission("decorativeentity.reload")) || (nostateidperm = !sender.hasPermission("decorativeentity.stateid"))) {
			sender.sendMessage("No permission");
			return true;
		}
		if (args.length < 1) {
			sender.sendMessage("Avilable args: reload");
			return true;
		}
		String subcmd = args[0].toLowerCase();
		if (subcmd.equals("reload")) {
			if (noreloadperm) {
				sender.sendMessage("No permission to reload config");
				return true;
			}
			decorativeentity.reload();
			sender.sendMessage("Reloading configs");
			return true;
		}
		if (subcmd.equals("stateid")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("This subcommand only for players!");
				return true;
			}
			if (nostateidperm) {
				sender.sendMessage("No permission to get blockstate id");
				return true;
			}
			Player player = (Player) sender;
			org.bukkit.block.Block block = null;
			IBlockData nmsblock = null;
			int blockcombinedid = 0;
			if ((block = player.getTargetBlock(null, 10)) == null || (nmsblock = ((CraftBlock) block).getNMS()) == null || (blockcombinedid = Block.getCombinedId(nmsblock)) == 0) {
				sender.sendMessage("§eNo target block");
			}
			String hexcombinedid = Integer.toHexString(blockcombinedid);
			sender.sendMessage("§bBlock id: §a".concat(hexcombinedid));
			return true;
		}
		sender.sendMessage("Unknown action");
		return true;
	}

}
