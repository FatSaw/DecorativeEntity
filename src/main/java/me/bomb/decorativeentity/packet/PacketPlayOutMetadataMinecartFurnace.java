package me.bomb.decorativeentity.packet;

import java.io.IOException;

public final class PacketPlayOutMetadataMinecartFurnace extends PacketPlayOutMetadataMinecart {

	public boolean hasminecartfurnacepowered;
	public boolean minecartfurnacepowered;
	
	public PacketPlayOutMetadataMinecartFurnace(int id) {
		super(id, false);
	}
	
	protected void write(PacketDataSerializer packetdataserializer) throws IOException {
		super.write(packetdataserializer);

		if(hasminecartfurnacepowered) {
            packetdataserializer.writeByte(12);
            packetdataserializer.writeNum(6); //BOOLEAN
            packetdataserializer.writeBoolean(minecartfurnacepowered);
        }
        packetdataserializer.writeByte(255);
	}

}
