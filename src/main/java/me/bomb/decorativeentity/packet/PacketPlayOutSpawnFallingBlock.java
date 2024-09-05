package me.bomb.decorativeentity.packet;

import java.util.UUID;

public final class PacketPlayOutSpawnFallingBlock extends PacketPlayOutEntitySpawn {

	public PacketPlayOutSpawnFallingBlock(int id, UUID uuid, double x, double y, double z, byte yaw, byte pitch, int subtypeid) {
		super(id, uuid, 26, x, y, z, yaw, pitch, subtypeid, (short) 0, (short) 0, (short) 0);
	}

}
