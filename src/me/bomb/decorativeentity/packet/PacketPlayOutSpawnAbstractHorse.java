package me.bomb.decorativeentity.packet;

import java.io.IOException;
import java.util.UUID;

class PacketPlayOutSpawnAbstractHorse extends PacketPlayOutSpawnAgeable {

	public boolean hashorseflag;
	public byte horseflag;
	public boolean hasowner;
	public UUID owner;
	
	protected PacketPlayOutSpawnAbstractHorse(int id, UUID uuid, int type, double x, double y, double z, byte yaw, byte pitch, byte headPitch, short velocityX, short velocityY, short velocityZ) {
		super(id, uuid, type, x, y, z, yaw, pitch, headPitch, velocityX, velocityY, velocityZ);
	}
	
	protected void write(PacketDataSerializer packetdataserializer) throws IOException {
		super.write(packetdataserializer);
		if(hashorseflag) {
            packetdataserializer.writeByte(13);
            packetdataserializer.writeNum(0); //BYTE
            packetdataserializer.writeByte(horseflag);
        }
		if(hasowner) {
			boolean ownerp = owner != null;
            packetdataserializer.writeByte(14);
            packetdataserializer.writeNum(11); //OptUUID 
            packetdataserializer.writeBoolean(ownerp);
            if(ownerp) packetdataserializer.writeUUID(owner);
        }
	}

}
