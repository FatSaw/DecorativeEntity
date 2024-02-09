package me.bomb.decorativeentity.packet;

import java.io.IOException;

public final class PacketPlayOutSpawnWeather extends Packet {
	
	public int id;
	public double x;
	public double y;
	public double z;

	public PacketPlayOutSpawnWeather(int id, double x, double y, double z) {
		super((byte)2);
		this.id = id;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	protected void write(PacketDataSerializer packetdataserializer) throws IOException {
		packetdataserializer.writeNum(id);
		packetdataserializer.writeByte(1); //ONLY ONE TYPE EXSISTS
		packetdataserializer.writeDouble(x);
		packetdataserializer.writeDouble(y);
		packetdataserializer.writeDouble(z);
	}
}
