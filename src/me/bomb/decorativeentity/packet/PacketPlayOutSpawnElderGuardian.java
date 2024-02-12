package me.bomb.decorativeentity.packet;

import java.util.UUID;

public final class PacketPlayOutSpawnElderGuardian extends PacketPlayOutSpawnGuardian {

	public PacketPlayOutSpawnElderGuardian(int id, UUID uuid, double x, double y, double z, byte yaw, byte pitch, byte headPitch, short velocityX, short velocityY, short velocityZ) {
		super(id, uuid, 4, x, y, z, yaw, pitch, headPitch, velocityX, velocityY, velocityZ);
	}

}
