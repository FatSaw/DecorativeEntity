package me.bomb.decorativeentity.packet;

import java.io.IOException;

public final class PacketPlayOutEntityHeadRotation extends Packet {
	
	public int id;
	public byte yaw;
	
	public PacketPlayOutEntityHeadRotation(int id, byte yaw) {
		super((byte) 54);
		this.id = id;
		this.yaw = yaw;
	}

	@Override
	protected void write(PacketDataSerializer packetdataserializer) throws IOException {
		super.write(packetdataserializer);
		packetdataserializer.writeNum(id);
		packetdataserializer.writeByte(yaw);
	}

}
