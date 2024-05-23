package me.bomb.decorativeentity.packet;

import java.io.IOException;
import java.util.UUID;

public final class PacketPlayOutSpawnArmorStand extends PacketPlayOutSpawnEntityLiving {

	public boolean hasarmorstandflag;
	public byte armorstandflag;
	public boolean hasarmorstandheadrotation;
	public float headrotationx, headrotationy, headrotationz;
	public boolean hasarmorstandbodyrotation;
	public float bodyrotationx, bodyrotationy, bodyrotationz;
	public boolean hasarmorstandleftarmrotation;
	public float leftarmrotationx, leftarmrotationy, leftarmrotationz;
	public boolean hasarmorstandrightarmrotation;
	public float rightarmrotationx, rightarmrotationy, rightarmrotationz;
	public boolean hasarmorstandleftlegrotation;
	public float leftlegrotationx, leftlegrotationy, leftlegrotationz;
	public boolean hasarmorstandrightlegrotation;
	public float rightlegrotationx, rightlegrotationy, rightlegrotationz;
	
	public PacketPlayOutSpawnArmorStand(int id, UUID uuid, double x, double y, double z, byte yaw, byte pitch) {
		super(id, uuid, (byte)30, x, y, z, yaw, pitch, (byte)0, (short)0, (short)0, (short)0);
		//super(id, uuid, (byte)78, x, y, z, yaw, pitch, 0, (short)0, (short)0, (short)0);
	}
	
	protected final void write(PacketDataSerializer packetdataserializer) throws IOException {
		super.write(packetdataserializer);
		if(hasarmorstandflag) {
			packetdataserializer.writeByte(11);
			packetdataserializer.writeNum(0); //BYTE
	        packetdataserializer.writeByte(armorstandflag);
		}
		if(hasarmorstandheadrotation) {
			packetdataserializer.writeByte(12);
			packetdataserializer.writeNum(7); //Rotation
	        packetdataserializer.writeFloat(headrotationx);
	        packetdataserializer.writeFloat(headrotationy);
	        packetdataserializer.writeFloat(headrotationz);
		}

		if(hasarmorstandbodyrotation) {
			packetdataserializer.writeByte(13);
			packetdataserializer.writeNum(7); //Rotation
	        packetdataserializer.writeFloat(bodyrotationx);
	        packetdataserializer.writeFloat(bodyrotationy);
	        packetdataserializer.writeFloat(bodyrotationz);
		}
		if(hasarmorstandleftarmrotation) {
			packetdataserializer.writeByte(14);
			packetdataserializer.writeNum(7); //Rotation
	        packetdataserializer.writeFloat(leftarmrotationx);
	        packetdataserializer.writeFloat(leftarmrotationy);
	        packetdataserializer.writeFloat(leftarmrotationz);
		}
		if(hasarmorstandrightarmrotation) {
			packetdataserializer.writeByte(15);
			packetdataserializer.writeNum(7); //Rotation
	        packetdataserializer.writeFloat(rightarmrotationx);
	        packetdataserializer.writeFloat(rightarmrotationy);
	        packetdataserializer.writeFloat(rightarmrotationz);
		}
		if(hasarmorstandleftlegrotation) {
			packetdataserializer.writeByte(16);
			packetdataserializer.writeNum(7); //Rotation
	        packetdataserializer.writeFloat(leftlegrotationx);
	        packetdataserializer.writeFloat(leftlegrotationy);
	        packetdataserializer.writeFloat(leftlegrotationz);
		}
		if(hasarmorstandrightlegrotation) {
			packetdataserializer.writeByte(17);
			packetdataserializer.writeNum(7); //Rotation
	        packetdataserializer.writeFloat(rightlegrotationx);
	        packetdataserializer.writeFloat(rightlegrotationy);
	        packetdataserializer.writeFloat(rightlegrotationz);
		}
        
        packetdataserializer.writeByte(255);
	}


}
