package me.bomb.decorativeentity.packet;

import java.util.UUID;

public final class PacketPlayOutSpawnItem extends PacketPlayOutEntitySpawn {

	public PacketPlayOutSpawnItem(int id, UUID uuid, double x, double y, double z, byte yaw, byte pitch) {
		super(id, uuid, (byte)2, x, y, z, yaw, pitch, 1, (short)0, (short)0, (short)0);
	}

}
