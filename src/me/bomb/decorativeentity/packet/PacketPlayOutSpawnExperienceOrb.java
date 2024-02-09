package me.bomb.decorativeentity.packet;

import java.io.IOException;

public final class PacketPlayOutSpawnExperienceOrb extends Packet {

	public int id;
	public double x;
	public double y;
	public double z;
	public short count;
	
	public PacketPlayOutSpawnExperienceOrb(int id, double x, double y, double z, short count) {
		super((byte)1);
		this.id = id;
		this.x = x;
		this.y = y;
		this.z = z;
		this.count = count;
	}

	@Override
	protected void write(PacketDataSerializer packetdataserializer) throws IOException {
		packetdataserializer.writeNum(id);
		packetdataserializer.writeDouble(x);
		packetdataserializer.writeDouble(y);
		packetdataserializer.writeDouble(z);
		packetdataserializer.writeShort(count);
	}
	
}
