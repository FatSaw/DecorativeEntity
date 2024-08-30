package me.bomb.decorativeentity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import me.bomb.decorativeentity.packet.EntityPose;
import me.bomb.decorativeentity.packet.EnumGamemode;
import me.bomb.decorativeentity.packet.Packet;
import me.bomb.decorativeentity.packet.PacketPlayOutEntityHeadRotation;
import me.bomb.decorativeentity.packet.PacketPlayOutMetadataPlayer;
import me.bomb.decorativeentity.packet.PacketPlayOutPlayerInfo;
import me.bomb.decorativeentity.packet.PacketPlayOutPlayerSpawn;
import me.bomb.decorativeentity.packet.PacketPlayOutSetPassengers;
import me.bomb.decorativeentity.packet.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;
import me.bomb.decorativeentity.packet.PacketPlayOutPlayerInfo.PlayerInfoData;

final class HumanOptions {
	
	private final HashMap<String, HashMap<Long, Packet[]>> packets = new HashMap<String, HashMap<Long, Packet[]>>();
	
	protected HumanOptions(JavaPlugin plugin) {
		packets.clear();
		File workingdirectory = plugin.getDataFolder();
		if(!workingdirectory.exists()) {
			workingdirectory.mkdirs();
		}
		File configfile = new File(workingdirectory, "human.yml");
		YamlConfiguration config = null;
		if(configfile.exists()) {
			config = YamlConfiguration.loadConfiguration(configfile);
		} else {
			config = new YamlConfiguration();
			World main = Bukkit.getWorlds().get(0);
			Location spawn = main.getSpawnLocation();
			config.set("entityid.min", -98302);
			config.set("entityid.max", -65535);
			String worldsection = "worlds.".concat(main.getName()).concat(".npc.");
			config.set(worldsection.concat("name"), "");
			config.set(worldsection.concat("skin.value"), "");
			config.set(worldsection.concat("skin.signature"), "");
			config.set(worldsection.concat("x"), spawn.getX() + 0.5d);
			config.set(worldsection.concat("y"), spawn.getY());
			config.set(worldsection.concat("z"), spawn.getZ() + 0.5d);
			config.set(worldsection.concat("yaw"), 0);
			config.set(worldsection.concat("pitch"), 0);
			config.set(worldsection.concat("sittingon"), 0);
			try {
				config.save(configfile);
			} catch (IOException e) {
			}
		}
		ConfigurationSection entityidcs = config.getConfigurationSection("entityid");
		int minid = -98302, maxid = -65535;
		if(entityidcs!=null) {
			minid = entityidcs.getInt("min", minid);
			maxid = entityidcs.getInt("max", maxid);
		}
		ConfigurationSection worldscs = config.getConfigurationSection("worlds");
		if(worldscs!=null) {
			for(String worndname : worldscs.getKeys(false)) {
				int entityid = minid;
				HashMap<Long, HashSet<HumanOptionsEntry>> aworldoptions = new HashMap<Long, HashSet<HumanOptionsEntry>>();
				ConfigurationSection worldcs = worldscs.getConfigurationSection(worndname);
				if(worldcs==null) continue;
				for(String entityname : worldcs.getKeys(false)) {
					if(entityid>=maxid) {
						break;
					}
					ConfigurationSection entrycs = worldcs.getConfigurationSection(entityname);
					if(entrycs==null) continue;
					double x = entrycs.getDouble("x", Double.NaN), y = entrycs.getDouble("y", Double.NaN), z = entrycs.getDouble("z", Double.NaN);
					if(x==Double.NaN||y==Double.NaN||z==Double.NaN) continue;
					String name = entrycs.getString("name", "");
					String skinvalue = entrycs.getString("skin.value", "");
					String skinsignature = entrycs.getString("skin.signature", "");
					float yaw = (float) entrycs.getDouble("yaw", 0), pitch = (float) entrycs.getDouble("pitch", 0);
					int sittingon = entrycs.getInt("sittingon", 0);
					int chunkx = ((int) x) >> 4, chunkz = ((int) z) >> 4;
					long chunkpos = (((long)chunkx) << 32) | (chunkz & 0xFFFFFFFFL);
					HashSet<HumanOptionsEntry> chunkoptions = aworldoptions.get(chunkpos);
					if(chunkoptions==null) chunkoptions = new HashSet<HumanOptionsEntry>();
					chunkoptions.add(new HumanOptionsEntry(UUID.randomUUID(), name, skinvalue, skinsignature, x, y, z, (byte) ((int) (yaw * 256.0F / 360.0F)), (byte) ((int) (pitch * 256.0F / 360.0F)), entityid, sittingon));
					++entityid;
					aworldoptions.put(chunkpos, chunkoptions);
				}
				HashMap<Long, Packet[]> packetoptions = new HashMap<Long, Packet[]>();
				for(Entry<Long, HashSet<HumanOptionsEntry>> entry : aworldoptions.entrySet()) {
					HashSet<HumanOptionsEntry> value = entry.getValue();
					PacketPlayOutPlayerInfo playerinfoadd = new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER), playerinforemove = new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.REMOVE_PLAYER);
					ArrayList<Packet> humanspawns = new ArrayList<Packet>();
					humanspawns.add(playerinfoadd);
					for(HumanOptionsEntry option : value) {
						PlayerInfoData infodata = new PlayerInfoData(option.name, option.skinvalue, option.skinsignature, 0, EnumGamemode.CREATIVE, null);
						playerinfoadd.playerinfodata.put(option.uuid, infodata);
						playerinforemove.playerinfodata.put(option.uuid, infodata);
						
						PacketPlayOutPlayerSpawn npcspawnpacket = new PacketPlayOutPlayerSpawn(option.npcid, option.uuid, option.x, option.y, option.z, option.yaw, option.pitch);
						humanspawns.add(npcspawnpacket);
						PacketPlayOutMetadataPlayer metadataplayer = new PacketPlayOutMetadataPlayer(option.npcid);
						metadataplayer.haspose = true;
						metadataplayer.pose = EntityPose.SLEEPING;
						PacketPlayOutEntityHeadRotation rotatepacket = new PacketPlayOutEntityHeadRotation(option.npcid, option.yaw);
						humanspawns.add(rotatepacket);
						if(option.sittingon != 0) {
							humanspawns.add(new PacketPlayOutSetPassengers(option.sittingon, option.npcid));
						}
					}
					humanspawns.add(playerinforemove);
					packetoptions.put(entry.getKey(), humanspawns.toArray(new Packet[value.size()]));
				}
				
				packets.put(worndname, packetoptions);
			}
		}
	}
	
	protected Packet[] getPackets(String worldname, long chunkpos) {
		HashMap<Long, Packet[]> packetoptions = packets.get(worldname);
		return packetoptions == null ? null : packetoptions.get(chunkpos);
	}
	
	protected final class HumanOptionsEntry {
		protected final UUID uuid;
		protected final String name, skinvalue, skinsignature;
		protected final double x, y, z;
		protected final byte yaw, pitch;
		protected final int npcid, sittingon;
		
		private HumanOptionsEntry(UUID uuid, String name, String skinvalue, String skinsignature, double x, double y, double z, byte yaw, byte pitch, int npcid, int sittingon) {
			this.uuid = uuid;
			this.name = name;
			this.skinvalue = skinvalue; 
			this.skinsignature = skinsignature;
			this.x = x;
			this.y = y;
			this.z = z;
			this.yaw = yaw;
			this.pitch = pitch;
			this.npcid = npcid;
			this.sittingon = sittingon;
		}
	}
	
}
