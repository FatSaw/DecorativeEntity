package me.bomb.decorativeentity;

import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.minecraft.server.v1_12_R1.PacketDataSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutMapChunk;

final class PacketHandler extends ChannelDuplexHandler {
	
	private final PacketCache cache;
	private final Player player;
	private final PacketDataSerializer packetdataserializer;
	
	protected PacketHandler(PacketCache cache, Player player) {
		this.cache = cache;
		this.player = player;
		this.packetdataserializer = new PacketDataSerializer(Unpooled.buffer(8, 8));
		((CraftPlayer) player).getHandle().playerConnection.networkManager.channel.pipeline().addBefore("packet_handler", "decorativeentity", this);
	}
	
	@Override
    public void write(ChannelHandlerContext context, Object packet, ChannelPromise channelPromise) throws Exception {
		super.write(context, packet, channelPromise);
		if(packet instanceof PacketPlayOutMapChunk) {
			PacketPlayOutMapChunk chunkpacket = (PacketPlayOutMapChunk) packet;
			if(!chunkpacket.e()) {
				return; //DO NOT PROCESS IF CHUNK NOT FULL
			}
			packetdataserializer.resetReaderIndex();
			packetdataserializer.resetWriterIndex();
			try {
				chunkpacket.b(packetdataserializer);
				return;
			} catch (IndexOutOfBoundsException e) { //DO NOT READ FULL CHUNK WE NEED ONLY FIRST 8 BYTES
			}
			cache.sendPacketsForChunk(context, player.getWorld(), packetdataserializer.readLong());
		}
		
	}
}
