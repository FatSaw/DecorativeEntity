package me.bomb.decorativeentity.packet;

import java.util.UUID;

class PacketPlayOutSpawnMinecart extends PacketPlayOutEntitySpawn {

	protected PacketPlayOutSpawnMinecart(int id, UUID uuid, double x, double y, double z, byte yaw, byte pitch, int subtypeid) {
		super(id, uuid, (byte) 10, x, y, z, yaw, pitch, subtypeid, (short)0, (short)0, (short)0);
	}
	
}
