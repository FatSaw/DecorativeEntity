package me.bomb.decorativeentity.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder<Packet> {

	@Override
	protected void encode(ChannelHandlerContext context, Packet packet, ByteBuf bytebuf) throws Exception {
		PacketDataSerializer packetdataserializer = new PacketDataSerializer(bytebuf);
        try {
            packet.write(packetdataserializer);
        } catch (Throwable throwable) {
        }
	}

}
