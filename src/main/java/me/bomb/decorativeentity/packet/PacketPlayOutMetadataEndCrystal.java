package me.bomb.decorativeentity.packet;

import java.io.IOException;

public final class PacketPlayOutMetadataEndCrystal extends PacketPlayOutMetadata {

	public boolean hasbeamtarget;
	public BlockPosition beamtarget;

	public boolean hasbottom;
	public boolean bottom;

	public PacketPlayOutMetadataEndCrystal(int id) {
		super(id);
	}

	protected void write(PacketDataSerializer packetdataserializer) throws IOException {
		super.write(packetdataserializer);
		if (hasbeamtarget) {
			packetdataserializer.writeByte(7); // BEAMTARGET ID
			packetdataserializer.writeNum(10); // OPTIONALBLOCKPOS
			boolean present = beamtarget != null;
			packetdataserializer.writeBoolean(present);
			if (present) {
				packetdataserializer.writeLong(beamtarget.asLong()); // BEAMTARGET
			}
		}
		if (hasbottom) {
			packetdataserializer.writeByte(8);
			packetdataserializer.writeNum(7); // BOOLEAN
			packetdataserializer.writeBoolean(bottom);
		}
		packetdataserializer.writeByte(255);
	}
}
