package me.bomb.decorativeentity.packet;

import java.util.UUID;

public final class PacketPlayOutSpawnMinecartHopper extends PacketPlayOutEntitySpawn {

	public PacketPlayOutSpawnMinecartHopper(int id, UUID uuid, double x, double y, double z, byte yaw, byte pitch) {
		super(id, uuid, 49, x, y, z, yaw, pitch, 5, (short) 0, (short) 0, (short) 0);
	}

}
