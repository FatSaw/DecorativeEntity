package me.bomb.decorativeentity.packet;

import java.io.IOException;

public final class PacketPlayOutMetadataArmorStand extends PacketPlayOutMetadataLiving {

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

	public PacketPlayOutMetadataArmorStand(int id) {
		super(id);
	}

	protected final void write(PacketDataSerializer packetdataserializer) throws IOException {
		super.write(packetdataserializer);
		if (hasarmorstandflag) {
			packetdataserializer.writeByte(14);
			packetdataserializer.writeNum(0); // BYTE
			packetdataserializer.writeByte(armorstandflag);
		}
		if (hasarmorstandheadrotation) {
			packetdataserializer.writeByte(15);
			packetdataserializer.writeNum(8); // Rotation
			packetdataserializer.writeFloat(headrotationx);
			packetdataserializer.writeFloat(headrotationy);
			packetdataserializer.writeFloat(headrotationz);
		}

		if (hasarmorstandbodyrotation) {
			packetdataserializer.writeByte(16);
			packetdataserializer.writeNum(8); // Rotation
			packetdataserializer.writeFloat(bodyrotationx);
			packetdataserializer.writeFloat(bodyrotationy);
			packetdataserializer.writeFloat(bodyrotationz);
		}
		if (hasarmorstandleftarmrotation) {
			packetdataserializer.writeByte(17);
			packetdataserializer.writeNum(8); // Rotation
			packetdataserializer.writeFloat(leftarmrotationx);
			packetdataserializer.writeFloat(leftarmrotationy);
			packetdataserializer.writeFloat(leftarmrotationz);
		}
		if (hasarmorstandrightarmrotation) {
			packetdataserializer.writeByte(18);
			packetdataserializer.writeNum(8); // Rotation
			packetdataserializer.writeFloat(rightarmrotationx);
			packetdataserializer.writeFloat(rightarmrotationy);
			packetdataserializer.writeFloat(rightarmrotationz);
		}
		if (hasarmorstandleftlegrotation) {
			packetdataserializer.writeByte(19);
			packetdataserializer.writeNum(8); // Rotation
			packetdataserializer.writeFloat(leftlegrotationx);
			packetdataserializer.writeFloat(leftlegrotationy);
			packetdataserializer.writeFloat(leftlegrotationz);
		}
		if (hasarmorstandrightlegrotation) {
			packetdataserializer.writeByte(20);
			packetdataserializer.writeNum(8); // Rotation
			packetdataserializer.writeFloat(rightlegrotationx);
			packetdataserializer.writeFloat(rightlegrotationy);
			packetdataserializer.writeFloat(rightlegrotationz);
		}

		packetdataserializer.writeByte(255);
	}

}
