package me.bomb.decorativeentity.packet;

import java.io.IOException;

public final class PacketPlayOutTeleport extends Packet {

	public int id;
	public double x, y, z;
	public byte yaw, pitch;
	public boolean onground;

	public PacketPlayOutTeleport(int id, double x, double y, double z, byte yaw, byte pitch, boolean onground) {
		super((byte) 0x56);
		this.id = id;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
		this.onground = onground;
	}

	protected void write(PacketDataSerializer packetdataserializer) throws IOException {
		super.write(packetdataserializer);
		packetdataserializer.writeNum(id);
		packetdataserializer.writeDouble(x);
		packetdataserializer.writeDouble(y);
		packetdataserializer.writeDouble(z);
		packetdataserializer.writeByte(yaw);
		packetdataserializer.writeByte(pitch);
		packetdataserializer.writeBoolean(onground);
	}

}
