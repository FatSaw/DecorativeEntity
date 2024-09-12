package me.bomb.decorativeentity.options;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.logging.Logger;

import me.bomb.decorativeentity.packet.BlockPosition;
import me.bomb.decorativeentity.packet.Packet;
import me.bomb.decorativeentity.packet.PacketPlayOutMetadataFallingBlock;
import me.bomb.decorativeentity.packet.PacketPlayOutSpawnFallingBlock;

public final class FallingBlockOptions extends Options {

	public FallingBlockOptions(Logger logger, File file) {
		super(file, 0x0200);

		final int minid = sc.getIntOrDefault("entityid\0min", -163836), maxid = sc.getIntOrDefault("entityid\0max", -131069);
		String[] worlds = sc.getSubKeys("worlds\0");
		if (worlds != null) {
			for (String worldname : worlds) {
				int entityid = minid;
				HashMap<Long, HashSet<FallingBlockOptionsEntry>> aworldoptions = new HashMap<Long, HashSet<FallingBlockOptionsEntry>>();
				String worldkey = "worlds\0".concat(worldname).concat("\0");
				String[] entitys = sc.getSubKeys(worldkey);
				if (entitys == null) continue;
				for (String entityname : entitys) {
					if (entityid >= maxid) {
						break;
					}
					String worldentitykey = worldkey.concat(entityname).concat("\0");
					String[] entityoptions = sc.getSubKeys(worldentitykey);
					if (entityoptions == null) continue;
					final double x = sc.getDoubleOrDefault(worldentitykey.concat("x"), Double.NaN), y = sc.getDoubleOrDefault(worldentitykey.concat("y"), Double.NaN), z = sc.getDoubleOrDefault(worldentitykey.concat("z"), Double.NaN);
					if (x == Double.NaN || y == Double.NaN || z == Double.NaN) continue;
					int blockid = sc.getHexIntOrDefault(worldentitykey.concat("blockid"), 0);
					final float yaw = (float) sc.getDoubleOrDefault(worldentitykey.concat("yaw"), 0), pitch = (float) sc.getDoubleOrDefault(worldentitykey.concat("pitch"), 0);
					int chunkx = ((int) x) >> 4, chunkz = ((int) z) >> 4;
					long chunkpos = (((long) chunkx) << 32) | (chunkz & 0xFFFFFFFFL);
					String spawnkey = worldentitykey.concat("spawn\0");
					String[] spawnsectionkeys = sc.getSubKeys(spawnkey);
					boolean hasspawnx = false, hasspawny = false, hasspawnz = false;
					if (spawnsectionkeys != null) {
						int spawnsectionlength = spawnsectionkeys.length;
						while (--spawnsectionlength > -1) {
							if (spawnsectionkeys[spawnsectionlength].equals("x")) {
								hasspawnx = true;
							}
							if (spawnsectionkeys[spawnsectionlength].equals("y")) {
								hasspawny = true;
							}
							if (spawnsectionkeys[spawnsectionlength].equals("z")) {
								hasspawnz = true;
							}
						}
					}
					BlockPosition spawnlocation = hasspawnx && hasspawny && hasspawnz ? new BlockPosition(sc.getIntOrDefault(spawnkey.concat("x"), (int) x), sc.getIntOrDefault(spawnkey.concat("y"), (int) y), sc.getIntOrDefault(spawnkey.concat("z"), (int) z)) : null;
					HashSet<FallingBlockOptionsEntry> chunkoptions = aworldoptions.get(chunkpos);
					if (chunkoptions == null) chunkoptions = new HashSet<FallingBlockOptionsEntry>();
					chunkoptions.add(new FallingBlockOptionsEntry(UUID.randomUUID(), x, y, z, (byte) ((int) (yaw * 256.0F / 360.0F)), (byte) ((int) (pitch * 256.0F / 360.0F)), entityid, blockid, spawnlocation != null, spawnlocation));
					++entityid;
					aworldoptions.put(chunkpos, chunkoptions);
				}
				HashMap<Long, Packet[]> packetoptions = new HashMap<Long, Packet[]>();
				for (Entry<Long, HashSet<FallingBlockOptionsEntry>> entry : aworldoptions.entrySet()) {
					HashSet<FallingBlockOptionsEntry> value = entry.getValue();
					ArrayList<Packet> fallingblockspawns = new ArrayList<Packet>();
					for (FallingBlockOptionsEntry option : value) {
						PacketPlayOutSpawnFallingBlock fallingblockspawnpacket = new PacketPlayOutSpawnFallingBlock(option.npcid, option.uuid, option.x, option.y, option.z, option.yaw, option.pitch, option.blockid);
						PacketPlayOutMetadataFallingBlock fallingblockmetadatapacket = new PacketPlayOutMetadataFallingBlock(option.npcid);

						fallingblockmetadatapacket.hasnogravity = true;
						fallingblockmetadatapacket.nogravity = true;

						if (option.hasspawnlocation) {
							fallingblockmetadatapacket.hasspawnlocation = true;
							fallingblockmetadatapacket.spawnlocation = option.spawnlocation;
						}

						fallingblockspawns.add(fallingblockspawnpacket);
						fallingblockspawns.add(fallingblockmetadatapacket);
					}
					packetoptions.put(entry.getKey(), fallingblockspawns.toArray(new Packet[fallingblockspawns.size()]));
				}
				packets.put(worldname, packetoptions);
			}
		}

		this.sc = null;
		if (logger == null) return;
		for (Entry<String, HashMap<Long, Packet[]>> entry : packets.entrySet()) {
			String worldname = entry.getKey();
			int chunks = entry.getValue().size();
			logger.info("FallingBlock chunk loaded for world '" + worldname + "' : " + chunks);
		}
	}

	protected final class FallingBlockOptionsEntry {
		protected final UUID uuid;
		protected final double x, y, z;
		protected final byte yaw, pitch;
		protected final int npcid;
		protected final int blockid;
		protected final boolean hasspawnlocation;
		protected final BlockPosition spawnlocation;

		private FallingBlockOptionsEntry(UUID uuid, double x, double y, double z, byte yaw, byte pitch, int npcid, int blockid, boolean hasspawnlocation, BlockPosition spawnlocation) {
			this.uuid = uuid;
			this.x = x;
			this.y = y;
			this.z = z;
			this.yaw = yaw;
			this.pitch = pitch;
			this.npcid = npcid;
			this.blockid = blockid;
			this.hasspawnlocation = hasspawnlocation;
			this.spawnlocation = spawnlocation;
		}

	}

}
