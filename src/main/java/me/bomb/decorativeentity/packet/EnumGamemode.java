package me.bomb.decorativeentity.packet;

public enum EnumGamemode {
	NOTSET((byte) -1), SURVIVAL((byte) 0), CREATIVE((byte) 1), ADVENTURE((byte) 2), SPECTATOR((byte) 3);
	private EnumGamemode(byte id) {
		this.id = id;
	}
	private static final EnumGamemode[] byid;
	static {
		byid = new EnumGamemode[5];
		byid[0] = NOTSET;
		byid[1] = SURVIVAL;
		byid[2] = CREATIVE;
		byid[3] = ADVENTURE;
		byid[4] = SPECTATOR;
	}
	public static EnumGamemode getById(int id) {
		++id;
		if(id<0||id>4) {
			return null;
		}
		return byid[id];
	}
	public final byte id;
}
