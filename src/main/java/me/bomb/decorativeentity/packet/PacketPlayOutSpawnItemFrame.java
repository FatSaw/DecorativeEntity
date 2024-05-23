package me.bomb.decorativeentity.packet;

import java.util.UUID;

public final class PacketPlayOutSpawnItemFrame extends PacketPlayOutEntitySpawn {

	public PacketPlayOutSpawnItemFrame(int id, UUID uuid, int x, int y, int z, byte yaw, byte pitch, int subtypeid) {
		super(id, uuid, (byte)71, x, y, z, yaw, pitch, subtypeid & 0x0000007, (short)0, (short)0, (short)0);
	}

}
