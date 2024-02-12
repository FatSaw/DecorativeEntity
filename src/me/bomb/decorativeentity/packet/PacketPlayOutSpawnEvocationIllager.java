package me.bomb.decorativeentity.packet;

import java.io.IOException;
import java.util.UUID;

public class PacketPlayOutSpawnEvocationIllager extends PacketPlayOutSpawnInsentient {

	public boolean hasillagerflag;
	public byte illagerflag;
	
	protected PacketPlayOutSpawnEvocationIllager(int id, UUID uuid, int type, double x, double y, double z, byte yaw, byte pitch, byte headPitch, short velocityX, short velocityY, short velocityZ) {
		super(id, uuid, type, x, y, z, yaw, pitch, headPitch, velocityX, velocityY, velocityZ);
	}
	
	public PacketPlayOutSpawnEvocationIllager(int id, UUID uuid, double x, double y, double z, byte yaw, byte pitch, byte headPitch, short velocityX, short velocityY, short velocityZ) {
		super(id, uuid, 34, x, y, z, yaw, pitch, headPitch, velocityX, velocityY, velocityZ);
	}
	
	protected final void write(PacketDataSerializer packetdataserializer) throws IOException {
		super.write(packetdataserializer);
		if(hasillagerflag) {
            packetdataserializer.writeByte(12);
            packetdataserializer.writeNum(0); //BYTE
            packetdataserializer.writeByte(illagerflag);
        }
        packetdataserializer.writeByte(255);
	}

}
