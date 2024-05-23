package me.bomb.decorativeentity.packet;

import java.util.UUID;

public final class PacketPlayOutSpawnBoat extends PacketPlayOutEntitySpawn {

	public PacketPlayOutSpawnBoat(int id, UUID uuid, double x, double y, double z, byte yaw, byte pitch) {
		super(id, uuid, (byte) 1, x, y, z, yaw, pitch, 0, (short)0, (short)0, (short)0);
	}

}
