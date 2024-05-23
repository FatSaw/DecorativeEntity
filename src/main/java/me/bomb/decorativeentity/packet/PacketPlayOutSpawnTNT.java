package me.bomb.decorativeentity.packet;

import java.util.UUID;

public final class PacketPlayOutSpawnTNT extends PacketPlayOutEntitySpawn {

	public PacketPlayOutSpawnTNT(int id, UUID uuid, double x, double y, double z, byte yaw, byte pitch, short velocityX, short velocityY, short velocityZ) {
		super(id, uuid, (byte) 50, x, y, z, yaw, pitch, 0, velocityX, velocityY, velocityZ);
	}

}
