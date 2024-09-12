package me.bomb.decorativeentity;

import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import me.bomb.decorativeentity.packet.PacketEncoder;
import me.bomb.decorativeentity.util.ObjectContainer;
import net.minecraft.server.v1_16_R3.PacketDataSerializer;
import net.minecraft.server.v1_16_R3.PacketPlayInSettings;
import net.minecraft.server.v1_16_R3.PacketPlayOutMapChunk;

final class PacketListener extends ChannelDuplexHandler {

	private final PacketSender sender;
	private final Player player;
	private final ObjectContainer<String> sc;
	private final PacketDataSerializer packetdataserializer;
	private final ChannelPromise voidpromise;
	private final PacketEncoder encoder;

	protected PacketListener(PacketSender sender, Player player) {
		this.sender = sender;
		this.player = player;
		this.sc = new ObjectContainer<>();
		this.packetdataserializer = new PacketDataSerializer(Unpooled.buffer(8, 8));
		Channel channel = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel;
		this.voidpromise = channel.voidPromise();
		this.encoder = new PacketEncoder();
		channel.pipeline().addBefore("packet_handler", "de_chunkposget", this);
		channel.pipeline().addBefore("encoder", "de_encoder", this.encoder);
	}
	
	@Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object packet) throws Exception {
		super.channelRead(channelHandlerContext, packet);
		if (packet instanceof PacketPlayInSettings) {
			PacketPlayInSettings settingspacket = (PacketPlayInSettings) packet;
			if(settingspacket.locale != null) {
				sc.value = settingspacket.locale;
			}
		}
        
    }

	@Override
	public void write(ChannelHandlerContext context, Object packet, ChannelPromise channelPromise) throws Exception {
		super.write(context, packet, channelPromise);
		if (packet instanceof PacketPlayOutMapChunk) {
			PacketPlayOutMapChunk chunkpacket = (PacketPlayOutMapChunk) packet;
			if (!chunkpacket.f()) {
				return; // DO NOT PROCESS IF CHUNK NOT FULL
			}
			packetdataserializer.resetReaderIndex();
			packetdataserializer.resetWriterIndex();
			try {
				chunkpacket.b(packetdataserializer);
				return;
			} catch (IndexOutOfBoundsException e) { // DO NOT READ FULL CHUNK WE NEED ONLY FIRST 8 BYTES
			}
			sender.sendPacketsForChunk(context, voidpromise, encoder, player, sc, packetdataserializer.readLong());
		}

	}

}
