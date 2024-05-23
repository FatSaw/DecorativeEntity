package me.bomb.decorativeentity.packet;

import java.io.IOException;
import java.util.UUID;

public final class PacketPlayOutPlayerSpawn extends Packet {
	
	public int id;
	public UUID uuid;
	public double x;
	public double y;
	public double z;
	public byte yaw;
	public byte pitch;
	//METADATA
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
	
	public boolean hasadditionalhealth;
	public float additionalhealth;
	
	public boolean hasscore;
	public int score;
	
	public boolean hasskinparts;
	public byte skinparts;
	
	public boolean hasmainhand;
	public byte mainhand;
	
	public PacketPlayOutPlayerSpawn(int id, UUID uuid, double x, double y, double z, byte yaw, byte pitch) {
		super((byte) 5);
		this.id = id;
		this.uuid = uuid;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
	}

    protected void write(PacketDataSerializer packetdataserializer) throws IOException {
    	super.write(packetdataserializer);
        packetdataserializer.writeNum(this.id);
        packetdataserializer.writeUUID(this.uuid);
        packetdataserializer.writeDouble(this.x);
        packetdataserializer.writeDouble(this.y);
        packetdataserializer.writeDouble(this.z);
        packetdataserializer.writeByte(this.yaw);
        packetdataserializer.writeByte(this.pitch);
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
        if(hasadditionalhealth) {
            packetdataserializer.writeByte(11); //ADDHEALTH ID
            packetdataserializer.writeNum(2); //FLOAT
            packetdataserializer.writeFloat(additionalhealth); //ADDHEALTH DATA
        }
        if(hasscore) {
            packetdataserializer.writeByte(12); //SCORE ID
            packetdataserializer.writeNum(1); //VARINT
            packetdataserializer.writeNum(score); //SCORE
        }
        if(hasskinparts) {
            packetdataserializer.writeByte(13); //SKINPARTS ID
            packetdataserializer.writeNum(0); //BYTE
            packetdataserializer.writeByte(skinparts);//SKINPARTS
        }
        if(hasmainhand) {
            packetdataserializer.writeByte(14); //MAINHAND ID
            packetdataserializer.writeNum(0); //BYTE
            packetdataserializer.writeByte(mainhand); //MAINHAND DATA
        }
        packetdataserializer.writeByte(255);
    }
}
