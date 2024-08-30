package me.bomb.decorativeentity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import me.bomb.decorativeentity.packet.Packet;
import me.bomb.decorativeentity.packet.PacketPlayOutMetadataArmorStand;
import me.bomb.decorativeentity.packet.PacketPlayOutSpawnArmorStand;

final class ArmorstandOptions {
	
	private final HashMap<String, HashMap<Long, Packet[]>> packets = new HashMap<String, HashMap<Long, Packet[]>>();
	
	protected ArmorstandOptions(JavaPlugin plugin) {
		packets.clear();
		File workingdirectory = plugin.getDataFolder();
		if(!workingdirectory.exists()) {
			workingdirectory.mkdirs();
		}
		File configfile = new File(workingdirectory, "armorstand.yml");
		YamlConfiguration config = null;
		if(configfile.exists()) {
			config = YamlConfiguration.loadConfiguration(configfile);
		} else {
			config = new YamlConfiguration();
			World main = Bukkit.getWorlds().get(0);
			Location spawn = main.getSpawnLocation();
			config.set("entityid.min", -65535);
			config.set("entityid.max", -32767);
			String worldsection = "worlds.".concat(main.getName()).concat(".hologramm.");
			config.set(worldsection.concat("name"), "STANDNAME");
			config.set(worldsection.concat("x"), spawn.getX() + 0.5d);
			config.set(worldsection.concat("y"), spawn.getY());
			config.set(worldsection.concat("z"), spawn.getZ() + 0.5d);
			config.set(worldsection.concat("yaw"), 0);
			config.set(worldsection.concat("pitch"), 0);
			try {
				config.save(configfile);
			} catch (IOException e) {
			}
		}
		ConfigurationSection entityidcs = config.getConfigurationSection("entityid");
		int minid = -65535, maxid = -32767;
		if(entityidcs!=null) {
			minid = entityidcs.getInt("min", minid);
			maxid = entityidcs.getInt("max", maxid);
		}
		ConfigurationSection worldscs = config.getConfigurationSection("worlds");
		if(worldscs!=null) {
			for(String worndname : worldscs.getKeys(false)) {
				int entityid = minid;
				HashMap<Long, HashSet<ArmorstandOptionsEntry>> aworldoptions = new HashMap<Long, HashSet<ArmorstandOptionsEntry>>();
				ConfigurationSection worldcs = worldscs.getConfigurationSection(worndname);
				if(worldcs==null) continue;
				for(String blockname : worldcs.getKeys(false)) {
					if(entityid>=maxid) {
						break;
					}
					ConfigurationSection blockcs = worldcs.getConfigurationSection(blockname);
					if(blockcs==null) continue;
					double x = blockcs.getDouble("x", Double.NaN), y = blockcs.getDouble("y", Double.NaN), z = blockcs.getDouble("z", Double.NaN);
					if(x==Double.NaN||y==Double.NaN||z==Double.NaN) continue;
					String name = blockcs.getString("name", "");
					float yaw = (float) blockcs.getDouble("yaw", 0), pitch = (float) blockcs.getDouble("pitch", 0);
					int chunkx = ((int) x) >> 4, chunkz = ((int) z) >> 4;
					long chunkpos = (((long)chunkx) << 32) | (chunkz & 0xFFFFFFFFL);
					HashSet<ArmorstandOptionsEntry> chunkoptions = aworldoptions.get(chunkpos);
					if(chunkoptions==null) chunkoptions = new HashSet<ArmorstandOptionsEntry>();
					chunkoptions.add(new ArmorstandOptionsEntry(UUID.randomUUID(), name, x, y, z, (byte) ((int) (yaw * 256.0F / 360.0F)), (byte) ((int) (pitch * 256.0F / 360.0F)), entityid));
					++entityid;
					aworldoptions.put(chunkpos, chunkoptions);
				}
				HashMap<Long, Packet[]> packetoptions = new HashMap<Long, Packet[]>();
				for(Entry<Long, HashSet<ArmorstandOptionsEntry>> entry : aworldoptions.entrySet()) {
					HashSet<ArmorstandOptionsEntry> value = entry.getValue();
					ArrayList<Packet> sdandspawns = new ArrayList<Packet>();
					for(ArmorstandOptionsEntry option : value) {
						PacketPlayOutSpawnArmorStand armorstandspawnpacket = new PacketPlayOutSpawnArmorStand(option.npcid, option.uuid, option.x, option.y, option.z, option.yaw, option.pitch);
						PacketPlayOutMetadataArmorStand armorstandmetadatapacket = new PacketPlayOutMetadataArmorStand(option.npcid);
						
						armorstandmetadatapacket.hasentityflags = true;
						armorstandmetadatapacket.entityflags = 0x20; //Invisible
						armorstandmetadatapacket.hasnogravity = true;
						armorstandmetadatapacket.nogravity = true;
						if(!option.name.isEmpty()) {
							armorstandmetadatapacket.hascustomname = true;
							armorstandmetadatapacket.customname = option.name;
							armorstandmetadatapacket.hasvisiblecustomname = true;
							armorstandmetadatapacket.visiblecustomname = true;
						}
						
						armorstandmetadatapacket.hasarmorstandflag = true;
						armorstandmetadatapacket.armorstandflag = 0x19; //small, no baseplate, marker
						sdandspawns.add(armorstandspawnpacket);
						sdandspawns.add(armorstandmetadatapacket);
					}
					packetoptions.put(entry.getKey(), sdandspawns.toArray(new Packet[value.size()]));
				}
				packets.put(worndname, packetoptions);
			}
		}
	}
	
	protected Packet[] getPackets(String worldname, long chunkpos) {
		HashMap<Long, Packet[]> packetoptions = packets.get(worldname);
		return packetoptions == null ? null : packetoptions.get(chunkpos);
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
