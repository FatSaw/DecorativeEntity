package me.bomb.decorativeentity.packet;

import java.io.IOException;
import java.util.UUID;

class PacketPlayOutSpawnInsentient extends PacketPlayOutSpawnEntityLiving {
	
	public boolean hasinsentient;
	public byte insentient;
	
	protected PacketPlayOutSpawnInsentient(int id, UUID uuid, int type, double x, double y, double z, byte yaw, byte pitch, byte headPitch, short velocityX, short velocityY, short velocityZ) {
		super(id, uuid, type, x, y, z, yaw, pitch, headPitch, velocityX, velocityY, velocityZ);
	}
	
	protected void write(PacketDataSerializer packetdataserializer) throws IOException {
		super.write(packetdataserializer);
		if(hasinsentient) {
            packetdataserializer.writeByte(11);
            packetdataserializer.writeNum(0); //BYTE
            packetdataserializer.writeByte(insentient);
        }
	}
	
}
