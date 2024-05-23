package me.bomb.decorativeentity.packet;

import java.util.UUID;

public final class PacketPlayOutSpawnPigZombie extends PacketPlayOutSpawnZombie {

	public PacketPlayOutSpawnPigZombie(int id, UUID uuid, double x, double y, double z, byte yaw, byte pitch, byte headPitch, short velocityX, short velocityY, short velocityZ) {
		super(id, uuid, 57, x, y, z, yaw, pitch, headPitch, velocityX, velocityY, velocityZ, true);
	}

}
