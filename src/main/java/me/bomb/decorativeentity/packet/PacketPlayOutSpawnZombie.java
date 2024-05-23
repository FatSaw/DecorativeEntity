package me.bomb.decorativeentity.packet;

import java.io.IOException;
import java.util.UUID;

public class PacketPlayOutSpawnZombie extends PacketPlayOutSpawnInsentient {
	
	public boolean hasbaby;
	public boolean baby;
	public boolean hashandsup;
	public boolean handsup;
	private final boolean finigh;

	protected PacketPlayOutSpawnZombie(int id, UUID uuid, int type, double x, double y, double z, byte yaw, byte pitch, byte headPitch, short velocityX, short velocityY, short velocityZ, boolean finish) {
		super(id, uuid, type, x, y, z, yaw, pitch, headPitch, velocityX, velocityY, velocityZ);
		this.finigh = finish;
	}
	public PacketPlayOutSpawnZombie(int id, UUID uuid, double x, double y, double z, byte yaw, byte pitch, byte headPitch, short velocityX, short velocityY, short velocityZ) {
		super(id, uuid, 54, x, y, z, yaw, pitch, headPitch, velocityX, velocityY, velocityZ);
		this.finigh = true;
	}
	
	protected void write(PacketDataSerializer packetdataserializer) throws IOException {
		super.write(packetdataserializer);
		if(hasbaby) {
            packetdataserializer.writeByte(12);
            packetdataserializer.writeNum(6); //BOOLEAN
            packetdataserializer.writeBoolean(baby);
        }
        if(hashandsup) {
            packetdataserializer.writeByte(14);
            packetdataserializer.writeNum(6); //BOOLEAN
            packetdataserializer.writeBoolean(handsup);
        }
        if(finigh) {
            packetdataserializer.writeByte(255);
        }
	}

}
