package me.bomb.decorativeentity.packet;

import java.util.UUID;

public final class PacketPlayOutSpawnFireworks extends PacketPlayOutEntitySpawn {

	public PacketPlayOutSpawnFireworks(int id, UUID uuid, double x, double y, double z, byte yaw, byte pitch, int subtypeid, short velocityX, short velocityY, short velocityZ) {
		super(id, uuid, (byte) 76, x, y, z, yaw, pitch, subtypeid, velocityX, velocityY, velocityZ);
	}

}