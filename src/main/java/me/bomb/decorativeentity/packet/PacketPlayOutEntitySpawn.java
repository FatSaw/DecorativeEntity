package me.bomb.decorativeentity.packet;

import java.io.IOException;
import java.util.UUID;

class PacketPlayOutEntitySpawn extends Packet {

	public int id;
	public UUID uuid;
	private byte type;
	public double x;
	public double y;
	public double z;
	public byte yaw;
	public byte pitch;
	private int subtypeid;
	public short velocityX = 0;
	public short velocityY = 0;
	public short velocityZ = 0;
    
	protected PacketPlayOutEntitySpawn(int id, UUID uuid, byte type, double x, double y, double z, byte yaw, byte pitch, int subtypeid, short velocityX, short velocityY, short velocityZ) {
		super((byte) 0);
		this.id = id;
		this.uuid = uuid;
		this.type = type;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
		this.subtypeid = subtypeid;
		this.velocityX = velocityX;
		this.velocityY = velocityY;
		this.velocityZ = velocityZ;
	}
	
	protected final void write(PacketDataSerializer packetdataserializer) throws IOException {
		super.write(packetdataserializer);
		packetdataserializer.writeNum(id);
		packetdataserializer.writeUUID(uuid);
		packetdataserializer.writeByte(type);
		packetdataserializer.writeDouble(x);
		packetdataserializer.writeDouble(y);
		packetdataserializer.writeDouble(z);
		packetdataserializer.writeByte(yaw);
		packetdataserializer.writeByte(pitch);
		packetdataserializer.writeInt(subtypeid);
		packetdataserializer.writeShort(velocityX);
		packetdataserializer.writeShort(velocityY);
		packetdataserializer.writeShort(velocityZ);
	}

}
