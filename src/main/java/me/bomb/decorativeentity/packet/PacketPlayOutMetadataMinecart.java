package me.bomb.decorativeentity.packet;

import java.io.IOException;

public class PacketPlayOutMetadataMinecart extends PacketPlayOutMetadata {
	
	public boolean hasminecartshakingpower;
	public int minecartshakingpower;
	
	public boolean hasminecartshakingdirection;
	public int minecartshakingdirection;
	
	public boolean hasminecartshakingmultiplier;
	public float minecartshakingmultiplier;
	
	public boolean hasminecartcustomblockid;
	public int minecartcustomblockid;
	
	public boolean hasminecartcustomblockpositiony;
	public int minecartcustomblockpositiony;

	public boolean hasminecartcustomblockshow;
	public boolean minecartcustomblockshow;
	

	private final boolean finish;
	
	protected PacketPlayOutMetadataMinecart(int id, boolean finish) {
		super(id);
		this.finish = finish;
	}

	public PacketPlayOutMetadataMinecart(int id) {
		super(id);
		this.finish = true;
	}
	protected void write(PacketDataSerializer packetdataserializer) throws IOException {
		super.write(packetdataserializer);
		if(hasminecartshakingpower) {
            packetdataserializer.writeByte(6);
            packetdataserializer.writeNum(1); //VARINT
            packetdataserializer.writeNum(minecartshakingpower);
        }
		if(hasminecartshakingdirection) {
            packetdataserializer.writeByte(7);
            packetdataserializer.writeNum(1); //VARINT
            packetdataserializer.writeNum(minecartshakingdirection);
        }
		if(hasminecartshakingmultiplier) {
            packetdataserializer.writeByte(8);
            packetdataserializer.writeNum(2); //FLOAT
            packetdataserializer.writeFloat(minecartshakingmultiplier);
        }
		if(hasminecartcustomblockid) {
            packetdataserializer.writeByte(9);
            packetdataserializer.writeNum(1); //VARINT
            packetdataserializer.writeNum(minecartcustomblockid);
        }
		if(hasminecartcustomblockpositiony) {
            packetdataserializer.writeByte(10);
            packetdataserializer.writeNum(1); //VARINT
            packetdataserializer.writeNum(minecartcustomblockpositiony);
        }
		if(hasminecartcustomblockshow) {
            packetdataserializer.writeByte(11);
            packetdataserializer.writeNum(6); //BOOLEAN
            packetdataserializer.writeBoolean(minecartcustomblockshow);
        }
		if(finish) {
	        packetdataserializer.writeByte(255);
		}
	}

}
