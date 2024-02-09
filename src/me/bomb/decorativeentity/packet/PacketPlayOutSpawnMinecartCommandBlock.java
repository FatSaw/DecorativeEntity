package me.bomb.decorativeentity.packet;

import java.util.UUID;

public final class PacketPlayOutSpawnMinecartCommandBlock extends PacketPlayOutSpawnMinecart {

	public PacketPlayOutSpawnMinecartCommandBlock(int id, UUID uuid, double x, double y, double z, byte yaw, byte pitch) {
		super(id, uuid, x, y, z, yaw, pitch, 6);
	}
	
}
