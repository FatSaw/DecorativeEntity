package me.bomb.decorativeentity.packet;

import java.util.UUID;

public final class PacketPlayOutSpawnMinecartTNT extends PacketPlayOutEntitySpawn {

	public PacketPlayOutSpawnMinecartTNT(int id, UUID uuid, double x, double y, double z, byte yaw, byte pitch) {
		super(id, uuid, 51, x, y, z, yaw, pitch, 3, (short)0, (short)0, (short)0);
	}
	
}
