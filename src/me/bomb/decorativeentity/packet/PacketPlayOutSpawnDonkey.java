package me.bomb.decorativeentity.packet;

import java.io.IOException;
import java.util.UUID;

public final class PacketPlayOutSpawnDonkey extends PacketPlayOutSpawnChestedHorse {

	public PacketPlayOutSpawnDonkey(int id, UUID uuid, double x, double y, double z, byte yaw, byte pitch, byte headPitch, short velocityX, short velocityY, short velocityZ) {
		super(id, uuid, 31, x, y, z, yaw, pitch, headPitch, velocityX, velocityY, velocityZ);
	}
	
	protected void write(PacketDataSerializer packetdataserializer) throws IOException {
		super.write(packetdataserializer);
		packetdataserializer.writeByte(255);
	}

}