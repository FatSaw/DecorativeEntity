package me.bomb.decorativeentity.packet;

import java.io.IOException;
import java.util.UUID;

public final class PacketPlayOutSpawnLlama extends PacketPlayOutSpawnChestedHorse {

	public boolean hasstrength;
	public int strength;
	public boolean hascarpetcolor;
	public int carpetcolor;
	public boolean hasvariant;
	public int variant;
	
	protected PacketPlayOutSpawnLlama(int id, UUID uuid, double x, double y, double z, byte yaw, byte pitch, byte headPitch, short velocityX, short velocityY, short velocityZ) {
		super(id, uuid, 103, x, y, z, yaw, pitch, headPitch, velocityX, velocityY, velocityZ);
	}
	
	protected void write(PacketDataSerializer packetdataserializer) throws IOException {
		super.write(packetdataserializer);
        if(hasstrength) {
            packetdataserializer.writeByte(16);
            packetdataserializer.writeNum(1); //VARINT
            packetdataserializer.writeNum(strength);
        }
        if(hascarpetcolor) {
            packetdataserializer.writeByte(17);
            packetdataserializer.writeNum(1); //VARINT
            packetdataserializer.writeNum(carpetcolor);
        }
        if(hasvariant) {
            packetdataserializer.writeByte(18);
            packetdataserializer.writeNum(1); //VARINT
            packetdataserializer.writeNum(variant);
        }
		packetdataserializer.writeByte(255);
	}

}
