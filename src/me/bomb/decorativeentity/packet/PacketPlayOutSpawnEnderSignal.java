package me.bomb.decorativeentity.packet;

import java.util.UUID;

public final class PacketPlayOutSpawnEnderSignal extends PacketPlayOutEntitySpawn {

	public PacketPlayOutSpawnEnderSignal(int id, UUID uuid, byte type, double x, double y, double z) {
		super(id, uuid, (byte)72, x, y, z, (byte)0, (byte)0, 0, (short)0, (short)0, (short)0);
	}

}
