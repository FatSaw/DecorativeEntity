package me.bomb.decorativeentity.packet;

public final class BlockPosition {

	public final int x, y, z;

	public BlockPosition(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public long asLong() {
		return toLong(this.x, this.y, this.z);
	}

	public static long toLong(int x, int y, int z) {
		long result = 0L;

		result |= ((long) x & 0x3FFFFFFL) << 0x26;
		result |= ((long) y & 0x0FFFL) << 0;
		result |= ((long) z & 0x3FFFFFFL) << 0x0C;
		return result;
	}

	public static BlockPosition fromLong(long value) {
		return new BlockPosition(xFromLong(value), yFromLong(value), zFromLong(value));
	}

	public static int xFromLong(long value) {
		return (int) (value << 64 - 0x26 - 0x1A >> 64 - 0x1A);
	}

	public static int yFromLong(long value) {
		return (int) (value << 64 - 0x0C >> 64 - 0x0C);
	}

	public static int zFromLong(long value) {
		return (int) (value << 64 - 0x0C - 0x1A >> 64 - 0x1A);
	}

}
