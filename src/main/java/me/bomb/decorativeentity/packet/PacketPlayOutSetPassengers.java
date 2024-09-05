package me.bomb.decorativeentity.packet;

import java.io.IOException;

public final class PacketPlayOutSetPassengers extends Packet {

	public int vehicleid;
	public int[] passengerid;

	public PacketPlayOutSetPassengers(int vehicleid, int... passengerid) {
		super((byte) 0x4B);
		this.vehicleid = vehicleid;
		this.passengerid = passengerid;
	}

	protected void write(PacketDataSerializer packetdataserializer) throws IOException {
		super.write(packetdataserializer);
		packetdataserializer.writeNum(this.vehicleid);
		int i = passengerid.length;
		packetdataserializer.writeNum(i);
		while (--i > -1) {
			packetdataserializer.writeNum(passengerid[i]);
		}
	}

}
