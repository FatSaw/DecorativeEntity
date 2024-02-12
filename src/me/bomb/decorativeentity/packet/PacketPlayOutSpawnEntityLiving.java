package me.bomb.decorativeentity.packet;

import java.io.IOException;
import java.util.UUID;

class PacketPlayOutSpawnEntityLiving extends Packet {
	
	public int id;
	public UUID uuid;
    private int type;
    public double x;
    public double y;
    public double z;
    public byte yaw;
    public byte pitch;
    public byte headPitch;
	public short velocityX = 0;
	public short velocityY = 0;
	public short velocityZ = 0;
	
	public boolean hasflags;
	public byte flags;

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
    
	protected PacketPlayOutSpawnEntityLiving(int id, UUID uuid, int type, double x, double y, double z, byte yaw, byte pitch, byte headPitch, short velocityX, short velocityY, short velocityZ) {
		super((byte) 3);
		this.id = id;
		this.uuid = uuid;
		this.type = type;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
		this.headPitch = headPitch;
		this.velocityX = velocityX;
		this.velocityY = velocityY;
		this.velocityZ = velocityZ;
	}
	
	protected void write(PacketDataSerializer packetdataserializer) throws IOException {
		packetdataserializer.writeNum(id);
		packetdataserializer.writeUUID(uuid);
		packetdataserializer.writeNum(type);
		packetdataserializer.writeDouble(x);
		packetdataserializer.writeDouble(y);
		packetdataserializer.writeDouble(z);
		packetdataserializer.writeByte(yaw);
		packetdataserializer.writeByte(pitch);
		packetdataserializer.writeByte(headPitch);
		packetdataserializer.writeShort(velocityX);
		packetdataserializer.writeShort(velocityY);
		packetdataserializer.writeShort(velocityZ);
		//METADATA
        if(hasflags) {
            packetdataserializer.writeByte(0); //FLAGS ID
            packetdataserializer.writeNum(0); //BYTE
            packetdataserializer.writeByte(flags); //FLAGS
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
        if(hashandstate) {
            packetdataserializer.writeByte(6); //HANDSTATE ID
            packetdataserializer.writeNum(0); //BYTE
            packetdataserializer.writeByte(handstate);//HANDSTATE
        }
        if(hashealth) {
            packetdataserializer.writeByte(7); //HEALTH ID
            packetdataserializer.writeNum(2); //FLOAT
            packetdataserializer.writeFloat(health); //HEALTH DATA
        }
        if(haspotioncolor) {
            packetdataserializer.writeByte(8); //POTIONCOLOR ID
            packetdataserializer.writeNum(1); //VARINT
            packetdataserializer.writeNum(potioncolor); //POTIONCOLOR
        }
        if(haspotionambient) {
            packetdataserializer.writeByte(9); //POTIONAMBIENT ID
            packetdataserializer.writeNum(6); //BOOLEAN
            packetdataserializer.writeBoolean(potionambient);//POTIONAMBIENT
        }
        if(hasarrowcount) {
            packetdataserializer.writeByte(10); //ARROWCOUNT ID
            packetdataserializer.writeNum(1); //VARINT
            packetdataserializer.writeNum(arrowcount); //ARROWCOUNT
        }
        
	}

}
