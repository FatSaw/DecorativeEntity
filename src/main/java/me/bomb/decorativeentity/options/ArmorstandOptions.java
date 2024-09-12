package me.bomb.decorativeentity.options;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
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
					String namesectionkey = worldentitykey.concat("name\0");
					String[] namesection = sc.getSubKeys(namesectionkey);
					HashMap<String, String> langnames = new HashMap<>();
					if(namesection.length!=0) {
						int i = namesection.length;
						while(--i > -1) {
							String langnamekey = namesection[i];
							String langname = sc.getStringOrDefault(namesectionkey.concat(langnamekey), "");
							langnames.put(langnamekey, langname);
						}
					}
					String name = sc.getStringOrDefault(worldentitykey.concat("name"), "");
					final float yaw = (float) sc.getDoubleOrDefault(worldentitykey.concat("yaw"), 0), pitch = (float) sc.getDoubleOrDefault(worldentitykey.concat("pitch"), 0);
					int chunkx = ((int) x) >> 4, chunkz = ((int) z) >> 4;
					long chunkpos = (((long) chunkx) << 32) | (chunkz & 0xFFFFFFFFL);
					HashSet<ArmorstandOptionsEntry> chunkoptions = aworldoptions.get(chunkpos);
					if (chunkoptions == null) chunkoptions = new HashSet<ArmorstandOptionsEntry>();
					chunkoptions.add(new ArmorstandOptionsEntry(UUID.randomUUID(), name, langnames, x, y, z, (byte) ((int) (yaw * 256.0F / 360.0F)), (byte) ((int) (pitch * 256.0F / 360.0F)), entityid));
					++entityid;
					aworldoptions.put(chunkpos, chunkoptions);
				}
				HashMap<String, HashMap<Long, Packet[]>> langpacketoptions = new HashMap<>();
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
						for(Entry<String, String> langentry : option.langnames.entrySet()) {
							long chunkpos = entry.getKey();
							String langkey = langentry.getKey();
							HashMap<Long, Packet[]> alangpacketoptions = langpacketoptions.getOrDefault(langkey, new HashMap<Long, Packet[]>());
							Packet[] langpackets = alangpacketoptions.getOrDefault(chunkpos, new Packet[0]);
							ArrayList<Packet> langsdandspawns = new ArrayList<>(Arrays.asList(langpackets));
							PacketPlayOutMetadataArmorStand langarmorstandmetadatapacket = new PacketPlayOutMetadataArmorStand(option.npcid);
							
							String langname = langentry.getValue();
							langarmorstandmetadatapacket.hascustomname = !langname.isEmpty();
							langarmorstandmetadatapacket.customname = langname;

							langsdandspawns.add(langarmorstandmetadatapacket);
							alangpacketoptions.put(chunkpos, langsdandspawns.toArray(new Packet[langsdandspawns.size()]));
							langpacketoptions.put(langkey, alangpacketoptions);
						}
					}
					packetoptions.put(entry.getKey(), sdandspawns.toArray(new Packet[sdandspawns.size()]));
				}
				packets.put(worldname, packetoptions);
				worldname = worldname.concat("\0");
				for(Entry<String, HashMap<Long, Packet[]>> entry : langpacketoptions.entrySet()) {
					packets.put(worldname.concat(entry.getKey()), entry.getValue());
				}
			}
		}
		this.sc = null;
		if (logger == null) return;
		for (Entry<String, HashMap<Long, Packet[]>> entry : packets.entrySet()) {
			String worldname = entry.getKey();
			int splitlangindex = worldname.indexOf('\0');
			int chunks = entry.getValue().size();
			if(splitlangindex == -1) {
				logger.info("Hologramms chunk loaded for world '" + worldname + "' : " + chunks);
				continue;
			}
			String lang = worldname.substring(splitlangindex);
			worldname = worldname.substring(0, splitlangindex);
			logger.info("Hologramms chunk loaded for world '" + worldname + "' lang '" + lang + "' : " + chunks);
		}
	}

	protected final class ArmorstandOptionsEntry {
		protected final UUID uuid;
		protected final String name;
		protected final HashMap<String, String> langnames;
		protected final double x, y, z;
		protected final byte yaw, pitch;
		protected final int npcid;

		private ArmorstandOptionsEntry(UUID uuid, String name, HashMap<String, String> langnames, double x, double y, double z, byte yaw, byte pitch, int npcid) {
			this.uuid = uuid;
			this.name = name;
			this.langnames = langnames;
			this.x = x;
			this.y = y;
			this.z = z;
			this.yaw = yaw;
			this.pitch = pitch;
			this.npcid = npcid;
		}
	}

}
