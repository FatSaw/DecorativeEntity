package me.bomb.decorativeentity.packet;

import java.io.IOException;
import java.util.UUID;

public final class PacketPlayOutSpawnPainting extends Packet {
	
	public int id;
	public UUID uuid;
	public String name;
	public long position;
	public EnumDirection direction;
    
	public PacketPlayOutSpawnPainting(int id, UUID uuid, String name, int x, int y, int z, EnumDirection direction) {
		this(id, uuid, name, ((long) x & 0x3ffffffL) << 0x26 | ((long) y & 0xfffL) << 0x1a | ((long) z & 0x3ffffffL) << 0, direction);
	}

	public PacketPlayOutSpawnPainting(int id, UUID uuid, String name, long position, EnumDirection direction) {
		super((byte)4);
		this.id = id;
		this.uuid = uuid;
		this.name = name;
		this.position = position;
		this.direction = direction;
	}
	
	protected void write(PacketDataSerializer packetdataserializer) throws IOException {
		packetdataserializer.writeNum(id);
		packetdataserializer.writeUUID(uuid);
		packetdataserializer.writeString(name);
		packetdataserializer.writeLong(position);
		packetdataserializer.writeByte(direction.id);
	}
	
	public enum EnumDirection {
		NORTH((byte) 2),SOUTH((byte) 0),WEST((byte) 1),EAST((byte) 3);
		private EnumDirection(byte id) {
			this.id = id;
		}
		public byte id; 
	}

}
