package me.bomb.decorativeentity.packet;

import java.util.UUID;

public final class PacketPlayOutSpawnEndCrystal extends PacketPlayOutEntitySpawn {

	public PacketPlayOutSpawnEndCrystal(int id, UUID uuid, double x, double y, double z, byte yaw, byte pitch) {
		super(id, uuid, 18, x, y, z, yaw, pitch, 0, (short)0, (short)0, (short)0);
	}
	
}
