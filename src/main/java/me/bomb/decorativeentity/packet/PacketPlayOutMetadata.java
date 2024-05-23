package me.bomb.decorativeentity.packet;

import java.io.IOException;

class PacketPlayOutMetadata extends Packet {
	public int id; 
	
	public boolean hasentityflags;
	public byte entityflags;

	public boolean hasair;
	public int air;
	
	public boolean hascustomname;
	public String customname;
	
	public boolean hasvisiblecustomname;
	public boolean visiblecustomname;
	
	public boolean hassilent;
	public boolean silent;
	
	public boolean hasnogravity;
	public boolean nogravity;
	
	protected PacketPlayOutMetadata(int id) {
		super((byte) 60);
		this.id = id;
	}
	
	protected void write(PacketDataSerializer packetdataserializer) throws IOException {
		super.write(packetdataserializer);
		packetdataserializer.writeNum(id);
		if(hasentityflags) {
            packetdataserializer.writeByte(0); //FLAGS ID
            packetdataserializer.writeNum(0); //BYTE
            packetdataserializer.writeByte(entityflags); //FLAGS
        }
        if(hasair) {
            packetdataserializer.writeByte(1); //AIR ID
            packetdataserializer.writeNum(1); //VARINT
            packetdataserializer.writeNum(air); //AIR
        }
        if(hascustomname) {
            packetdataserializer.writeByte(2); //CUSTOMNAME ID
            packetdataserializer.writeNum(3); //STRING
            packetdataserializer.writeString(customname); //CUSTOMNAME
        }
        if(hasvisiblecustomname) {
        	packetdataserializer.writeByte(3); //VISIBLECUSTOMNAME ID
            packetdataserializer.writeNum(6); //BOOLEAN
            packetdataserializer.writeBoolean(visiblecustomname);//VISIBLECUSTOMNAME
        }
        if(hassilent) {
            packetdataserializer.writeByte(4); //SILENT ID
            packetdataserializer.writeNum(6); //BOOLEAN
            packetdataserializer.writeBoolean(silent);//NOGRAVITY
        }
        if(hasnogravity) {
            packetdataserializer.writeByte(5); //NOGRAVITY ID
            packetdataserializer.writeNum(6); //BOOLEAN
            packetdataserializer.writeBoolean(nogravity); //NOGRAVITY
        }
	}

}
