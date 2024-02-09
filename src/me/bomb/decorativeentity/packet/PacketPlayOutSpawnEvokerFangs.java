package me.bomb.decorativeentity.packet;

import java.util.UUID;

public final class PacketPlayOutSpawnEvokerFangs extends PacketPlayOutEntitySpawn {

	public PacketPlayOutSpawnEvokerFangs(int id, UUID uuid, double x, double y, double z, byte yaw, byte pitch, short velocityX, short velocityY, short velocityZ) {
		super(id, uuid, (byte) 79, x, y, z, yaw, pitch, 0, velocityX, velocityY, velocityZ);
	}

}
