package me.bomb.decorativeentity.packet;

import java.util.UUID;

public final class PacketPlayOutSpawnMinecartCommandBlock extends PacketPlayOutEntitySpawn {

	public PacketPlayOutSpawnMinecartCommandBlock(int id, UUID uuid, double x, double y, double z, byte yaw, byte pitch) {
		super(id, uuid, 47, x, y, z, yaw, pitch, 6, (short)0, (short)0, (short)0);
	}
	
}
