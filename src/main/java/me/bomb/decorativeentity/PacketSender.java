package me.bomb.decorativeentity;

import org.bukkit.World;

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
	
	protected void sendPacketsForChunk(ChannelHandlerContext context, ChannelPromise promise, PacketEncoder encoder, World world, long chunkpos) {
		String worldname = world.getName();
		boolean sent = false;
		Packet[] minecartpackets = this.minecartoptions.getPackets(worldname, chunkpos);
		if (minecartpackets != null) {
			sent = true;
			for(Packet packet : minecartpackets) {
				try {
					encoder.write(context, packet, promise);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		Packet[] armorstandpackets = this.armorstandoptions.getPackets(worldname, chunkpos);
		if (armorstandpackets != null) {
			sent = true;
			for(Packet packet : armorstandpackets) {
				try {
					encoder.write(context, packet, promise);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		Packet[] humanpackets = this.humanoptions.getPackets(worldname, chunkpos);
		if (humanpackets != null) {
			sent = true;
			for(Packet packet : humanpackets) {
				try {
					encoder.write(context, packet, promise);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if(!sent) return;
		try {
			encoder.flush(context);
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}
	
}
