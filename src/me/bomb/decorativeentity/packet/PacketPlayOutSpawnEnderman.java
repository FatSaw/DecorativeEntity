package me.bomb.decorativeentity.packet;

import java.io.IOException;
import java.util.UUID;

public final class PacketPlayOutSpawnEnderman extends PacketPlayOutSpawnInsentient {
	
	public boolean hascarriedblock;
	public int carriedblock;
	public boolean hasscreaming;
	public boolean screaming;
	
	public PacketPlayOutSpawnEnderman(int id, UUID uuid, double x, double y, double z, byte yaw, byte pitch, byte headPitch, short velocityX, short velocityY, short velocityZ) {
		super(id, uuid, 58, x, y, z, yaw, pitch, headPitch, velocityX, velocityY, velocityZ);
	}

	protected void write(PacketDataSerializer packetdataserializer) throws IOException {
    	super.write(packetdataserializer);
    	
    	if(hascarriedblock) {
        	packetdataserializer.writeByte(12);
            packetdataserializer.writeNum(12); //OptBlockID
            packetdataserializer.writeNum(carriedblock);
        }

        if(hasscreaming) {
        	packetdataserializer.writeByte(13);
            packetdataserializer.writeNum(6); //BOOLEAN
            packetdataserializer.writeBoolean(screaming);
        }
	}
	
}
