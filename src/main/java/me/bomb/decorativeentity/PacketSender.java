package me.bomb.decorativeentity;

import org.bukkit.World;
import org.bukkit.entity.Player;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import me.bomb.decorativeentity.packet.Packet;
import me.bomb.decorativeentity.packet.PacketEncoder;

final class PacketSender {
	
	protected MinecartOptions minecartoptions;
	protected ArmorstandOptions armorstandoptions;
	protected HumanOptions humanoptions;
	
	protected PacketSender() {
	}
	
	protected void sendPacketsForChunk(ChannelHandlerContext context, ChannelPromise promise, PacketEncoder encoder, Player player, long chunkpos) {
		World world = player.getWorld();
		String worldname = world.getName();
		int sent = 0;
		
		if(minecartoptions!=null) {
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
		}
		
		if(armorstandoptions!=null) {
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
		}

		Packet humanremove = null;
		
		if(humanoptions!=null) {
			Packet[] humanpackets = this.humanoptions.getPackets(worldname, chunkpos);
			if (humanpackets != null) {
				int max = humanpackets.length;
				if (max > 0) {
					humanremove = humanpackets[--max];
				}
				int i = 0;
				while(i < max) {
					try {
						encoder.write(context, humanpackets[i], promise);
						++sent;
					} catch (Exception e) {
						e.printStackTrace();
					}
					++i;
				}
			}
		}
		
		if(sent == 0) {
			return;
		}
		try {
			encoder.flush(context);
		} catch (Exception e) { 
			e.printStackTrace();
		}
		if(humanremove!=null) {
			try {
				encoder.write(context, humanremove, promise);
				++sent;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//final int x = (int) (chunkpos >> 32) & 0xFFFFFFFF, z = (int) chunkpos & 0xFFFFFFFF;
		//player.sendMessage("§f§l[§e§lDE_DEBUG§f§l]§r World: §e'§a" + world.getName() + "§e'§r Sent §e'§a" + sent + "§e'§r packets for chunk X: §e'§a" + x + "§e'§r Z: §e'§a" + z  + "§e'§r."); //DEBUG
	}
	
}
