package me.bomb.decorativeentity.packet;

import java.io.IOException;
import java.util.UUID;

public final class PacketPlayOutSpawnVindicationIllager extends PacketPlayOutSpawnInsentient {

	public boolean hasvindicationflag;
	public byte vindicationflag;
	
	public PacketPlayOutSpawnVindicationIllager(int id, UUID uuid, double x, double y, double z, byte yaw, byte pitch, byte headPitch, short velocityX, short velocityY, short velocityZ) {
		super(id, uuid, 36, x, y, z, yaw, pitch, headPitch, velocityX, velocityY, velocityZ);
	}
	
	protected final void write(PacketDataSerializer packetdataserializer) throws IOException {
		super.write(packetdataserializer);
		if(hasvindicationflag) {
            packetdataserializer.writeByte(12);
            packetdataserializer.writeNum(0); //BYTE
            packetdataserializer.writeByte(vindicationflag);
        }
        packetdataserializer.writeByte(255);
	}

}
