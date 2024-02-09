package me.bomb.decorativeentity.packet;

import java.util.UUID;

public class PacketPlayOutSpawnTippedArrow extends PacketPlayOutEntitySpawn {

	public PacketPlayOutSpawnTippedArrow(int id, UUID uuid, double x, double y, double z, byte yaw, byte pitch, int trackerid, short velocityX, short velocityY, short velocityZ) {
		super(id, uuid, (byte)60, x, y, z, yaw, pitch, trackerid, velocityX, velocityY, velocityZ);
	}

}
