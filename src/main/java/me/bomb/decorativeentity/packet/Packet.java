package me.bomb.decorativeentity.packet;

//PACKETS WITH FULL ACCESS
//WIRHOUT NMS TYPES

import java.io.IOException;

public abstract class Packet {
	
	private final byte packetid;
	
	protected Packet(byte packetid) {
		this.packetid = packetid;
	}
    
    protected void write(PacketDataSerializer packetdataserializer) throws IOException {
    	packetdataserializer.writeByte(packetid);
    }
    
}
