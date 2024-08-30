package me.bomb.decorativeentity.packet;

import java.util.UUID;

public final class PacketPlayOutSpawnMinecartSpawner extends PacketPlayOutEntitySpawn {

	public PacketPlayOutSpawnMinecartSpawner(int id, UUID uuid, double x, double y, double z, byte yaw, byte pitch) {
		super(id, uuid, 50, x, y, z, yaw, pitch, 4, (short)0, (short)0, (short)0);
	}
	
}
