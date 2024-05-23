package me.bomb.decorativeentity.packet;

import java.util.UUID;

public final class PacketPlayOutSpawnMinecartSpawner extends PacketPlayOutSpawnMinecart {

	public PacketPlayOutSpawnMinecartSpawner(int id, UUID uuid, double x, double y, double z, byte yaw, byte pitch) {
		super(id, uuid, x, y, z, yaw, pitch, 4);
	}
	
}
