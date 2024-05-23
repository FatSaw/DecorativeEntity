package me.bomb.decorativeentity.packet;

import java.io.IOException;
import java.util.UUID;

class PacketPlayOutSpawnAgeable extends PacketPlayOutSpawnInsentient {
	
	public boolean hasage;
	public boolean age;

	protected PacketPlayOutSpawnAgeable(int id, UUID uuid, int type, double x, double y, double z, byte yaw, byte pitch, byte headPitch, short velocityX, short velocityY, short velocityZ) {
		super(id, uuid, type, x, y, z, yaw, pitch, headPitch, velocityX, velocityY, velocityZ);
	}
	
	protected void write(PacketDataSerializer packetdataserializer) throws IOException {
		super.write(packetdataserializer);
		if(hasage) {
            packetdataserializer.writeByte(12);
            packetdataserializer.writeNum(6); //BOOLEAN
            packetdataserializer.writeBoolean(age);
        }
	}

}
