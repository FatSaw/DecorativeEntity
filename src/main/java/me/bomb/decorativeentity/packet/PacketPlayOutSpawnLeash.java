package me.bomb.decorativeentity.packet;

import java.util.UUID;

public final class PacketPlayOutSpawnLeash extends PacketPlayOutEntitySpawn {

	public PacketPlayOutSpawnLeash(int id, UUID uuid, int x, int y, int z, byte yaw, byte pitch) {
		super(id, uuid, (byte)77, x, y, z, yaw, pitch, 0, (short)0, (short)0, (short)0);
	}

}
