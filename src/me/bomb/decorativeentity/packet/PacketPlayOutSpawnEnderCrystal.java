package me.bomb.decorativeentity.packet;

import java.util.UUID;

public final class PacketPlayOutSpawnEnderCrystal extends PacketPlayOutEntitySpawn {

	public PacketPlayOutSpawnEnderCrystal(int id, UUID uuid, double x, double y, double z, byte yaw, byte pitch) {
		super(id, uuid, (byte)51, x, y, z, yaw, pitch, 0, (short)0, (short)0, (short)0);
	}

}
