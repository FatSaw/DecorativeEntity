package me.bomb.decorativeentity.packet;

import java.io.IOException;
import java.util.UUID;

public final class PacketPlayOutPlayerSpawn extends Packet {
	
	public int id;
	public UUID uuid;
	public double x;
	public double y;
	public double z;
	public byte yaw;
	public byte pitch;
	
	public PacketPlayOutPlayerSpawn(int id, UUID uuid, double x, double y, double z, byte yaw, byte pitch) {
		super((byte) 0x04);
		this.id = id;
		this.uuid = uuid;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
	}

    protected void write(PacketDataSerializer packetdataserializer) throws IOException {
    	super.write(packetdataserializer);
        packetdataserializer.writeNum(this.id);
        packetdataserializer.writeUUID(this.uuid);
        packetdataserializer.writeDouble(this.x);
        packetdataserializer.writeDouble(this.y);
        packetdataserializer.writeDouble(this.z);
        packetdataserializer.writeByte(this.yaw);
        packetdataserializer.writeByte(this.pitch);
    }
}
