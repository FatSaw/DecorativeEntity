package me.bomb.decorativeentity.packet;

import java.util.UUID;

public final class PacketPlayOutSpawnSmallFireball extends PacketPlayOutEntitySpawn {

	public PacketPlayOutSpawnSmallFireball(int id, UUID uuid, byte type, double x, double y, double z, byte yaw, byte pitch, int subtypeid, short velocityX, short velocityY, short velocityZ) {
		super(id, uuid, (byte)64, x, y, z, yaw, pitch, subtypeid, velocityX, velocityY, velocityZ);
		// TODO Auto-generated constructor stub
	}

}
