package me.bomb.decorativeentity.packet;

import java.io.IOException;
import java.util.UUID;

class PacketPlayOutSpawnEntityLiving extends Packet {

	public int id;
	public UUID uuid;
	private int type;
	public double x;
	public double y;
	public double z;
	public byte yaw;
	public byte pitch;
	public byte headPitch;
	public short velocityX = 0;
	public short velocityY = 0;
	public short velocityZ = 0;

	protected PacketPlayOutSpawnEntityLiving(int id, UUID uuid, int type, double x, double y, double z, byte yaw, byte pitch, byte headPitch, short velocityX, short velocityY, short velocityZ) {
		super((byte) 0x02);
		this.id = id;
		this.uuid = uuid;
		this.type = type;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
		this.headPitch = headPitch;
		this.velocityX = velocityX;
		this.velocityY = velocityY;
		this.velocityZ = velocityZ;
	}

	protected void write(PacketDataSerializer packetdataserializer) throws IOException {
		super.write(packetdataserializer);
		packetdataserializer.writeNum(id);
		packetdataserializer.writeUUID(uuid);
		packetdataserializer.writeNum(type);
		packetdataserializer.writeDouble(x);
		packetdataserializer.writeDouble(y);
		packetdataserializer.writeDouble(z);
		packetdataserializer.writeByte(yaw);
		packetdataserializer.writeByte(pitch);
		packetdataserializer.writeByte(headPitch);
		packetdataserializer.writeShort(velocityX);
		packetdataserializer.writeShort(velocityY);
		packetdataserializer.writeShort(velocityZ);
	}

}
