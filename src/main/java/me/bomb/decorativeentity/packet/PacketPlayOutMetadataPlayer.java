package me.bomb.decorativeentity.packet;

import java.io.IOException;

public class PacketPlayOutMetadataPlayer extends PacketPlayOutMetadataLiving {

	public boolean hasadditionalhealth;
	public float additionalhealth;

	public boolean hasscore;
	public int score;

	public boolean hasskinparts;
	public byte skinparts;

	public boolean hasmainhand;
	public byte mainhand;

	public PacketPlayOutMetadataPlayer(int id) {
		super(id);
	}

	protected void write(PacketDataSerializer packetdataserializer) throws IOException {
		super.write(packetdataserializer);

		if (hasadditionalhealth) {
			packetdataserializer.writeByte(14); // ADDHEALTH ID
			packetdataserializer.writeNum(2); // FLOAT
			packetdataserializer.writeFloat(additionalhealth); // ADDHEALTH DATA
		}
		if (hasscore) {
			packetdataserializer.writeByte(15); // SCORE ID
			packetdataserializer.writeNum(1); // VARINT
			packetdataserializer.writeNum(score); // SCORE
		}
		if (hasskinparts) {
			packetdataserializer.writeByte(16); // SKINPARTS ID
			packetdataserializer.writeNum(0); // BYTE
			packetdataserializer.writeByte(skinparts);// SKINPARTS
		}
		if (hasmainhand) {
			packetdataserializer.writeByte(17); // MAINHAND ID
			packetdataserializer.writeNum(0); // BYTE
			packetdataserializer.writeByte(mainhand); // MAINHAND DATA
		}
		packetdataserializer.writeByte(255);
	}

}
