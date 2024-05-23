package me.bomb.decorativeentity.packet;

import java.io.IOException;
import java.util.UUID;

public final class PacketPlayOutSpawnZombieVillager extends PacketPlayOutSpawnZombie {
	
	public boolean hasconverting;
	public boolean converting;
	public boolean hasprofession;
	public int profession;

	protected PacketPlayOutSpawnZombieVillager(int id, UUID uuid, double x, double y, double z, byte yaw, byte pitch, byte headPitch, short velocityX, short velocityY, short velocityZ) {
		super(id, uuid, 27, x, y, z, yaw, pitch, headPitch, velocityX, velocityY, velocityZ, false);
	}
	
	protected final void write(PacketDataSerializer packetdataserializer) throws IOException {
		super.write(packetdataserializer);
        if(hasconverting) {
            packetdataserializer.writeByte(15);
            packetdataserializer.writeNum(6); //BOOLEAN
            packetdataserializer.writeBoolean(converting);
        }
        if(hasprofession) {
            packetdataserializer.writeByte(16);
            packetdataserializer.writeNum(1); //VARINT
            packetdataserializer.writeNum(profession);
        }
        packetdataserializer.writeByte(255);
	}

}
