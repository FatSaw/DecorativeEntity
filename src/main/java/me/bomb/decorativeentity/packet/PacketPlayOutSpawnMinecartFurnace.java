package me.bomb.decorativeentity.packet;

import java.util.UUID;

public final class PacketPlayOutSpawnMinecartFurnace extends PacketPlayOutEntitySpawn {

	public PacketPlayOutSpawnMinecartFurnace(int id, UUID uuid, double x, double y, double z, byte yaw, byte pitch) {
		super(id, uuid, 48, x, y, z, yaw, pitch, 2, (short)0, (short)0, (short)0);
	}
	
}
