package me.bomb.decorativeentity.packet;

import java.io.IOException;
import java.util.UUID;

public final class PacketPlayOutSpawnHorse extends PacketPlayOutSpawnAbstractHorse {

	public boolean hashorsevariant;
	public int horsevariant;
	public boolean hashorsearmor;
	public int horsearmor;
	
	public PacketPlayOutSpawnHorse(int id, UUID uuid, double x, double y, double z, byte yaw, byte pitch, byte headPitch, short velocityX, short velocityY, short velocityZ) {
		super(id, uuid, 100, x, y, z, yaw, pitch, headPitch, velocityX, velocityY, velocityZ);
	}
	
	protected void write(PacketDataSerializer packetdataserializer) throws IOException {
		super.write(packetdataserializer);
		if(hashorsevariant) {
            packetdataserializer.writeByte(15);
            packetdataserializer.writeNum(1); //VARINT
            packetdataserializer.writeNum(horsevariant);
        }
		if(hashorsearmor) {
            packetdataserializer.writeByte(16);
            packetdataserializer.writeNum(1); //VARINT
            packetdataserializer.writeNum(horsearmor);
        }
        packetdataserializer.writeByte(255);
	}

}
