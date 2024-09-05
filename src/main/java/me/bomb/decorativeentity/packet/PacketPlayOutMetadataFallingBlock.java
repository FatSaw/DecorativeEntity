package me.bomb.decorativeentity.packet;

import java.io.IOException;

public final class PacketPlayOutMetadataFallingBlock extends PacketPlayOutMetadata {

	public boolean hasspawnlocation;
	public BlockPosition spawnlocation;
	
	public PacketPlayOutMetadataFallingBlock(int id) {
		super(id);
	}
	
	protected void write(PacketDataSerializer packetdataserializer) throws IOException {
		super.write(packetdataserializer);
		if(hasspawnlocation) {
            packetdataserializer.writeByte(7); //SPAWNLOCATION ID
            packetdataserializer.writeNum(9); //BLOCKPOS
            packetdataserializer.writeLong(spawnlocation.asLong()); //SPAWNLOCATION
        }
        packetdataserializer.writeByte(255);
	}

}
