package me.bomb.decorativeentity.packet;

import java.io.IOException;
import java.util.UUID;

class PacketPlayOutSpawnSkeletonAbstract extends PacketPlayOutSpawnInsentient {

	public boolean hasswingingarms;
	public boolean swingingarms;
	
	protected PacketPlayOutSpawnSkeletonAbstract(int id, UUID uuid, int type, double x, double y, double z, byte yaw, byte pitch, byte headPitch, short velocityX, short velocityY, short velocityZ) {
		super(id, uuid, type, x, y, z, yaw, pitch, headPitch, velocityX, velocityY, velocityZ);
	}
	
	protected void write(PacketDataSerializer packetdataserializer) throws IOException {
		super.write(packetdataserializer);
		if(hasswingingarms) {
            packetdataserializer.writeByte(11);
            packetdataserializer.writeNum(6); //BOOLEAN
            packetdataserializer.writeBoolean(swingingarms);
        }
        packetdataserializer.writeByte(255);
	}

}
