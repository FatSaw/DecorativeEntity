package me.bomb.decorativeentity.packet;

import java.io.IOException;
import java.util.UUID;

public final class PacketPlayOutSpawnBlaze extends PacketPlayOutSpawnInsentient {
	
	public boolean hasblazeflag;
	public byte blazeflag;

	protected PacketPlayOutSpawnBlaze(int id, UUID uuid, double x, double y, double z, byte yaw, byte pitch, byte headPitch, short velocityX, short velocityY, short velocityZ) {
		super(id, uuid, 61, x, y, z, yaw, pitch, headPitch, velocityX, velocityY, velocityZ);
	}
	
	protected final void write(PacketDataSerializer packetdataserializer) throws IOException {
		super.write(packetdataserializer);
		if(hasblazeflag) {
            packetdataserializer.writeByte(12);
            packetdataserializer.writeNum(0); //BYTE
            packetdataserializer.writeByte(blazeflag);
        }
        packetdataserializer.writeByte(255);
	}

}
