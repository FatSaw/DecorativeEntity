package me.bomb.decorativeentity.packet;

import java.io.IOException;
import java.util.UUID;

public final class PacketPlayOutSpawnEnderDragon extends PacketPlayOutSpawnInsentient {

	public boolean hasphaze;
	public int phaze;
	
	public PacketPlayOutSpawnEnderDragon(int id, UUID uuid, double x, double y, double z, byte yaw, byte pitch, byte headPitch, short velocityX, short velocityY, short velocityZ) {
		super(id, uuid, 63, x, y, z, yaw, pitch, headPitch, velocityX, velocityY, velocityZ);
	}
	
	protected final void write(PacketDataSerializer packetdataserializer) throws IOException {
		super.write(packetdataserializer);
		if(hasphaze) {
            packetdataserializer.writeByte(12);
            packetdataserializer.writeNum(1); //VARINT
            packetdataserializer.writeNum(phaze);
        }
        packetdataserializer.writeByte(255);
	}

}
