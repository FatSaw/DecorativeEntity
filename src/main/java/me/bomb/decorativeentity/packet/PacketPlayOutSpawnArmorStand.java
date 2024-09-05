package me.bomb.decorativeentity.packet;

import java.util.UUID;

public final class PacketPlayOutSpawnArmorStand extends PacketPlayOutSpawnEntityLiving {

	public PacketPlayOutSpawnArmorStand(int id, UUID uuid, double x, double y, double z, byte yaw, byte pitch) {
		super(id, uuid, 1, x, y, z, yaw, pitch, (byte) 0, (short) 0, (short) 0, (short) 0);
	}

}
