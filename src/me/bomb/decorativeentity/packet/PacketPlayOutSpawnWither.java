package me.bomb.decorativeentity.packet;

import java.io.IOException;
import java.util.UUID;

public final class PacketPlayOutSpawnWither extends PacketPlayOutSpawnInsentient {

	public boolean hascenterheadtarget;
	public int centerheadtarget;
	public boolean hasleftheadtarget;
	public int leftheadtarget;
	public boolean hasrightheadtarget;
	public int rightheadtarget;
	public boolean hasinvulnerabletime;
	public int invulnerabletime;
	
	public PacketPlayOutSpawnWither(int id, UUID uuid, double x, double y, double z, byte yaw, byte pitch, byte headPitch, short velocityX, short velocityY, short velocityZ) {
		super(id, uuid, 64, x, y, z, yaw, pitch, headPitch, velocityX, velocityY, velocityZ);
	}
	
	protected final void write(PacketDataSerializer packetdataserializer) throws IOException {
		super.write(packetdataserializer);
		if(hascenterheadtarget) {
            packetdataserializer.writeByte(12);
            packetdataserializer.writeNum(1); //VARINT
            packetdataserializer.writeNum(centerheadtarget);
        }
		if(hasleftheadtarget) {
            packetdataserializer.writeByte(13);
            packetdataserializer.writeNum(1); //VARINT
            packetdataserializer.writeNum(leftheadtarget);
        }
		if(hasrightheadtarget) {
            packetdataserializer.writeByte(14);
            packetdataserializer.writeNum(1); //VARINT
            packetdataserializer.writeNum(rightheadtarget);
        }
		if(hasinvulnerabletime) {
            packetdataserializer.writeByte(15);
            packetdataserializer.writeNum(1); //VARINT
            packetdataserializer.writeNum(invulnerabletime);
        }
        packetdataserializer.writeByte(255);
	}

}
