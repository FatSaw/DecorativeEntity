package me.bomb.decorativeentity;

import java.io.File;
import java.io.IOException;
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

final class HumanOptions {
	
	private final HashMap<String, HashMap<Long, HumanOptionsEntry[]>> options = new HashMap<String, HashMap<Long, HumanOptionsEntry[]>>();
	
	protected HumanOptions(JavaPlugin plugin) {
		options.clear();
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
			config.set(worldsection.concat("skinvalue"), "");
			config.set(worldsection.concat("skinsignature"), "");
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
					String skinvalue = entrycs.getString("skinvalue", "");
					String skinsignature = entrycs.getString("skinsignature", "");
					float yaw = (float) entrycs.getDouble("yaw", 0), pitch = (float) entrycs.getDouble("pitch", 0);
					int chunkx = ((int) x) >> 4, chunkz = ((int) z) >> 4;
					long chunkpos = (((long)chunkx) << 32) | (chunkz & 0xFFFFFFFFL);
					HashSet<HumanOptionsEntry> chunkoptions = aworldoptions.get(chunkpos);
					if(chunkoptions==null) chunkoptions = new HashSet<HumanOptionsEntry>();
					chunkoptions.add(new HumanOptionsEntry(UUID.randomUUID(), name, skinvalue, skinsignature, x, y, z, (byte) ((int) (yaw * 256.0F / 360.0F)), (byte) ((int) (pitch * 256.0F / 360.0F)), entityid));
					++entityid;
					aworldoptions.put(chunkpos, chunkoptions);
				}
				HashMap<Long, HumanOptionsEntry[]> worldoptions = new HashMap<Long, HumanOptionsEntry[]>();
				for(Entry<Long, HashSet<HumanOptionsEntry>> entry : aworldoptions.entrySet()) {
					HashSet<HumanOptionsEntry> value = entry.getValue();
					worldoptions.put(entry.getKey(), value.toArray(new HumanOptionsEntry[value.size()]));
				}
				options.put(worndname, worldoptions);
			}
		}
	}
	
	protected HumanOptionsEntry[] getOptions(String worldname, long chunkpos) {
		HashMap<Long, HumanOptionsEntry[]> worldoptions = options.get(worldname);
		return worldoptions == null ? null : worldoptions.get(chunkpos);
	}
	
	protected final class HumanOptionsEntry {
		protected final UUID uuid;
		protected final String name, skinvalue, skinsignature;
		protected final double x, y, z;
		protected final byte yaw, pitch;
		protected final int npcid;
		
		private HumanOptionsEntry(UUID uuid, String name, String skinvalue, String skinsignature, double x, double y, double z, byte yaw, byte pitch, int npcid) {
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
		}
	}
	
}
