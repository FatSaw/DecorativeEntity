package me.bomb.decorativeentity.packet;

import java.util.UUID;

public final class PacketPlayOutSpawnAreaEffectCloud extends PacketPlayOutEntitySpawn {

	public PacketPlayOutSpawnAreaEffectCloud(int id, UUID uuid, double x, double y, double z) {
		super(id, uuid, (byte)3, x, y, z, (byte)0, (byte)0, 0, (short)0, (short)0, (short)0);
	}

}
