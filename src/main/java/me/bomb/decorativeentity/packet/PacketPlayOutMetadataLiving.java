package me.bomb.decorativeentity.packet;

import java.io.IOException;

class PacketPlayOutMetadataLiving extends PacketPlayOutMetadata {
	
	public boolean hashandstate;
	public byte handstate;
	
	public boolean hashealth;
	public float health;
	
	public boolean haspotioncolor;
	public int potioncolor;
	
	public boolean haspotionambient;
	public boolean potionambient;
	
	public boolean hasarrowcount;
	public int arrowcount;
	
	public boolean hasabsorptionhearts;
	public int absorptionhearts;
	
	public boolean hasbedlocation;
	public BlockPosition bedlocation;

	protected PacketPlayOutMetadataLiving(int id) {
		super(id);
	}
	
	protected void write(PacketDataSerializer packetdataserializer) throws IOException {
		super.write(packetdataserializer);
        if(hashandstate) {
            packetdataserializer.writeByte(7); //HANDSTATE ID
            packetdataserializer.writeNum(0); //BYTE
            packetdataserializer.writeByte(handstate);//HANDSTATE
        }
		if(hashealth) {
            packetdataserializer.writeByte(8); //HEALTH ID
            packetdataserializer.writeNum(2); //FLOAT
            packetdataserializer.writeFloat(health); //HEALTH DATA
        }
        if(haspotioncolor) {
            packetdataserializer.writeByte(9); //POTIONCOLOR ID
            packetdataserializer.writeNum(1); //VARINT
            packetdataserializer.writeNum(potioncolor); //POTIONCOLOR
        }
        if(haspotionambient) {
            packetdataserializer.writeByte(10); //POTIONAMBIENT ID
            packetdataserializer.writeNum(7); //BOOLEAN
            packetdataserializer.writeBoolean(potionambient);//POTIONAMBIENT
        }
        if(hasarrowcount) {
            packetdataserializer.writeByte(11); //ARROWCOUNT ID
            packetdataserializer.writeNum(1); //VARINT
            packetdataserializer.writeNum(arrowcount); //ARROWCOUNT
        }
        if(hasabsorptionhearts) {
            packetdataserializer.writeByte(12); //ABSORPTIONHEARTS ID
            packetdataserializer.writeNum(1); //VARINT
            packetdataserializer.writeNum(absorptionhearts); //ABSORPTIONHEARTS
        }
        if(hasbedlocation) {
            packetdataserializer.writeByte(13); //BEDLOCATION ID
            packetdataserializer.writeNum(10); //OPTIONALBLOCKPOS
            boolean present = bedlocation != null;
            packetdataserializer.writeBoolean(present);
            if(present) {
            	packetdataserializer.writeLong(bedlocation.asLong()); //BEDLOCATION
            }
        }
        
	}

}
