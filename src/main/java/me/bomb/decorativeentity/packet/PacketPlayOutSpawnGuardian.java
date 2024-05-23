package me.bomb.decorativeentity.packet;

import java.io.IOException;
import java.util.UUID;

public class PacketPlayOutSpawnGuardian extends PacketPlayOutSpawnInsentient {
	
	public boolean hasretractingspikes;
	public boolean retractingspikes;
	public boolean hastargetid;
	public int targetid;
	
	protected PacketPlayOutSpawnGuardian(int id, UUID uuid, int type, double x, double y, double z, byte yaw, byte pitch, byte headPitch, short velocityX, short velocityY, short velocityZ) {
		super(id, uuid, type, x, y, z, yaw, pitch, headPitch, velocityX, velocityY, velocityZ);
	}
	public PacketPlayOutSpawnGuardian(int id, UUID uuid, double x, double y, double z, byte yaw, byte pitch, byte headPitch, short velocityX, short velocityY, short velocityZ) {
		super(id, uuid, 68, x, y, z, yaw, pitch, headPitch, velocityX, velocityY, velocityZ);
	}
	protected final void write(PacketDataSerializer packetdataserializer) throws IOException {
		super.write(packetdataserializer);
		if(hasretractingspikes) {
            packetdataserializer.writeByte(12);
            packetdataserializer.writeNum(6); //BOOLEAN
            packetdataserializer.writeBoolean(retractingspikes);
        }
        if(hastargetid) {
            packetdataserializer.writeByte(13);
            packetdataserializer.writeNum(1); //VARINT
            packetdataserializer.writeNum(targetid);
        }
        packetdataserializer.writeByte(255);
	}
}
