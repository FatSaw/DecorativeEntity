package me.bomb.decorativeentity.options;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.logging.Logger;

import me.bomb.decorativeentity.packet.Packet;
import me.bomb.decorativeentity.packet.PacketPlayOutMetadataArmorStand;
import me.bomb.decorativeentity.packet.PacketPlayOutSpawnArmorStand;

public final class ArmorstandOptions extends Options {

	public ArmorstandOptions(Logger logger, File file) {
		super(file, 0x0200);

		final int minid = sc.getIntOrDefault("entityid\0min", -65535), maxid = sc.getIntOrDefault("entityid\0max", -32767);
		String[] worlds = sc.getSubKeys("worlds\0");
		if (worlds != null) {
			for (String worldname : worlds) {
				int entityid = minid;
				HashMap<Long, HashSet<ArmorstandOptionsEntry>> aworldoptions = new HashMap<Long, HashSet<ArmorstandOptionsEntry>>();
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
					String name = sc.getStringOrDefault(worldentitykey.concat("name"), "");
					final float yaw = (float) sc.getDoubleOrDefault(worldentitykey.concat("yaw"), 0), pitch = (float) sc.getDoubleOrDefault(worldentitykey.concat("pitch"), 0);
					int chunkx = ((int) x) >> 4, chunkz = ((int) z) >> 4;
					long chunkpos = (((long) chunkx) << 32) | (chunkz & 0xFFFFFFFFL);
					HashSet<ArmorstandOptionsEntry> chunkoptions = aworldoptions.get(chunkpos);
					if (chunkoptions == null) chunkoptions = new HashSet<ArmorstandOptionsEntry>();
					chunkoptions.add(new ArmorstandOptionsEntry(UUID.randomUUID(), name, x, y, z, (byte) ((int) (yaw * 256.0F / 360.0F)), (byte) ((int) (pitch * 256.0F / 360.0F)), entityid));
					++entityid;
					aworldoptions.put(chunkpos, chunkoptions);
				}
				HashMap<Long, Packet[]> packetoptions = new HashMap<Long, Packet[]>();
				for (Entry<Long, HashSet<ArmorstandOptionsEntry>> entry : aworldoptions.entrySet()) {
					HashSet<ArmorstandOptionsEntry> value = entry.getValue();
					ArrayList<Packet> sdandspawns = new ArrayList<Packet>();
					for (ArmorstandOptionsEntry option : value) {
						PacketPlayOutSpawnArmorStand armorstandspawnpacket = new PacketPlayOutSpawnArmorStand(option.npcid, option.uuid, option.x, option.y, option.z, option.yaw, option.pitch);
						PacketPlayOutMetadataArmorStand armorstandmetadatapacket = new PacketPlayOutMetadataArmorStand(option.npcid);

						armorstandmetadatapacket.hasentityflags = true;
						armorstandmetadatapacket.entityflags = 0x20; // Invisible
						armorstandmetadatapacket.hasnogravity = true;
						armorstandmetadatapacket.nogravity = true;
						if (!option.name.isEmpty()) {
							armorstandmetadatapacket.hascustomname = true;
							armorstandmetadatapacket.customname = option.name;
							armorstandmetadatapacket.hasvisiblecustomname = true;
							armorstandmetadatapacket.visiblecustomname = true;
						}

						armorstandmetadatapacket.hasarmorstandflag = true;
						armorstandmetadatapacket.armorstandflag = 0x19; // small, no baseplate, marker
						sdandspawns.add(armorstandspawnpacket);
						sdandspawns.add(armorstandmetadatapacket);
					}
					packetoptions.put(entry.getKey(), sdandspawns.toArray(new Packet[value.size()]));
				}
				packets.put(worldname, packetoptions);
			}
		}
		this.sc = null;
		if (logger == null) return;
		for (Entry<String, HashMap<Long, Packet[]>> entry : packets.entrySet()) {
			String worldname = entry.getKey();
			int chunks = entry.getValue().size();
			logger.info("Hologramms chunk loaded for world '" + worldname + "' : " + chunks);
		}
	}

	protected final class ArmorstandOptionsEntry {
		protected final UUID uuid;
		protected final String name;
		protected final double x, y, z;
		protected final byte yaw, pitch;
		protected final int npcid;

		private ArmorstandOptionsEntry(UUID uuid, String name, double x, double y, double z, byte yaw, byte pitch, int npcid) {
			this.uuid = uuid;
			this.name = name;
			this.x = x;
			this.y = y;
			this.z = z;
			this.yaw = yaw;
			this.pitch = pitch;
			this.npcid = npcid;
		}
	}

}
