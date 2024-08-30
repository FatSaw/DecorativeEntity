package me.bomb.decorativeentity;

import org.bukkit.World;
import org.bukkit.entity.Player;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import me.bomb.decorativeentity.packet.Packet;
import me.bomb.decorativeentity.packet.PacketEncoder;

final class PacketSender {
	
	private final MinecartOptions minecartoptions;
	private final ArmorstandOptions armorstandoptions;
	private final HumanOptions humanoptions;
	
	protected PacketSender(MinecartOptions minecartoptions, ArmorstandOptions armorstandoptions, HumanOptions humanoptions) {
		this.minecartoptions = minecartoptions;
		this.armorstandoptions = armorstandoptions;
		this.humanoptions = humanoptions;
	}
	
	protected void sendPacketsForChunk(ChannelHandlerContext context, ChannelPromise promise, PacketEncoder encoder, Player player, long chunkpos) {
		World world = player.getWorld();
		String worldname = world.getName();
		int sent = 0;
		Packet[] minecartpackets = this.minecartoptions.getPackets(worldname, chunkpos);
		if (minecartpackets != null) {
			for(Packet packet : minecartpackets) {
				try {
					encoder.write(context, packet, promise);
					++sent;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		Packet[] armorstandpackets = this.armorstandoptions.getPackets(worldname, chunkpos);
		if (armorstandpackets != null) {
			for(Packet packet : armorstandpackets) {
				try {
					encoder.write(context, packet, promise);
					++sent;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		Packet[] humanpackets = this.humanoptions.getPackets(worldname, chunkpos);
		if (humanpackets != null) {
			for(Packet packet : humanpackets) {
				try {
					encoder.write(context, packet, promise);
					++sent;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if(sent == 0) {
			return;
		}
		//final int x = (int) (chunkpos >> 32) & 0xFFFFFFFF, z = (int) chunkpos & 0xFFFFFFFF;
		//player.sendMessage("§f§l[§e§lDE_DEBUG§f§l]§r World: §e'§a" + world.getName() + "§e'§r Sent §e'§a" + sent + "§e'§r packets for chunk X: §e'§a" + x + "§e'§r Z: §e'§a" + z  + "§e'§r."); //DEBUG
		try {
			encoder.flush(context);
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}
	
}
