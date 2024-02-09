package me.bomb.decorativeentity.packet;

import java.io.IOException;

public class PacketPlayOutEntityDestroy extends Packet {
	
	public int[] ids;

	protected PacketPlayOutEntityDestroy(int... ids) {
		super((byte) 50);
		this.ids = ids;
	}
	
	@Override
	protected void write(PacketDataSerializer packetdataserializer) throws IOException {
		super.write(packetdataserializer);
		int count = ids.length;
		packetdataserializer.writeNum(count);
		while(--count>-1) {
			packetdataserializer.writeNum(ids[count]);
		}
	}

}
