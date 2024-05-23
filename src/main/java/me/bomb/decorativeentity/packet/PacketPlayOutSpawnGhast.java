package me.bomb.decorativeentity.packet;

import java.io.IOException;
import java.util.UUID;

public final class PacketPlayOutSpawnGhast extends PacketPlayOutSpawnInsentient {

	public boolean hasatacking;
	public boolean atacking;
	
	public PacketPlayOutSpawnGhast(int id, UUID uuid, double x, double y, double z, byte yaw, byte pitch, byte headPitch, short velocityX, short velocityY, short velocityZ) {
		super(id, uuid, 56, x, y, z, yaw, pitch, headPitch, velocityX, velocityY, velocityZ);
	}
	
	protected void write(PacketDataSerializer packetdataserializer) throws IOException {
    	super.write(packetdataserializer);
    	if(hasatacking) {
            packetdataserializer.writeByte(12);
            packetdataserializer.writeNum(6); //BOOLEAN
            packetdataserializer.writeBoolean(atacking);
        }
        packetdataserializer.writeByte(255);
	}

}
