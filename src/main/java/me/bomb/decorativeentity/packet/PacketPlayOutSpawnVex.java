package me.bomb.decorativeentity.packet;

import java.io.IOException;
import java.util.UUID;

public final class PacketPlayOutSpawnVex extends PacketPlayOutSpawnInsentient {

	public boolean hasvexflag;
	public byte vexflag;
	
	public PacketPlayOutSpawnVex(int id, UUID uuid, double x, double y, double z, byte yaw, byte pitch, byte headPitch, short velocityX, short velocityY, short velocityZ) {
		super(id, uuid, 35, x, y, z, yaw, pitch, headPitch, velocityX, velocityY, velocityZ);
	}
	
	protected final void write(PacketDataSerializer packetdataserializer) throws IOException {
		super.write(packetdataserializer);
		if(hasvexflag) {
            packetdataserializer.writeByte(12);
            packetdataserializer.writeNum(0); //BYTE
            packetdataserializer.writeByte(vexflag);
        }
        packetdataserializer.writeByte(255);
	}

}
