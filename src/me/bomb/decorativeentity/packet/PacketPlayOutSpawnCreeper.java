package me.bomb.decorativeentity.packet;

import java.io.IOException;
import java.util.UUID;

public class PacketPlayOutSpawnCreeper extends PacketPlayOutSpawnInsentient {

	public boolean hasstate;
	public int state;
	public boolean hascharged;
	public boolean charged;
	public boolean hasignited;
	public boolean ignited;
	
	public PacketPlayOutSpawnCreeper(int id, UUID uuid, double x, double y, double z, byte yaw, byte pitch, byte headPitch, short velocityX, short velocityY, short velocityZ) {
		super(id, uuid, 50, x, y, z, yaw, pitch, headPitch, velocityX, velocityY, velocityZ);
	}
	
	protected final void write(PacketDataSerializer packetdataserializer) throws IOException {
		super.write(packetdataserializer);
        if(hasstate) {
            packetdataserializer.writeByte(12);
            packetdataserializer.writeNum(1); //VARINT
            packetdataserializer.writeNum(state);
        }
        if(hascharged) {
        	packetdataserializer.writeByte(13);
            packetdataserializer.writeNum(6); //BOOLEAN
            packetdataserializer.writeBoolean(charged);
        }
        if(hasignited) {
        	packetdataserializer.writeByte(14);
            packetdataserializer.writeNum(6); //BOOLEAN
            packetdataserializer.writeBoolean(ignited);
        }
        packetdataserializer.writeByte(255);
	}

}
