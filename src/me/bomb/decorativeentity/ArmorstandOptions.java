package me.bomb.decorativeentity;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

final class ArmorstandOptions {
	
	private final HashMap<String, HashMap<Long, ArmorstandOptionsEntry[]>> options = new HashMap<String, HashMap<Long, ArmorstandOptionsEntry[]>>();
	
	protected ArmorstandOptions(JavaPlugin plugin) {
		options.clear();
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
		int minid = -32767, maxid = 0;;
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
					chunkoptions.add(new ArmorstandOptionsEntry(name, x, y, z, yaw, pitch, entityid));
					++entityid;
					aworldoptions.put(chunkpos, chunkoptions);
				}
				HashMap<Long, ArmorstandOptionsEntry[]> worldoptions = new HashMap<Long, ArmorstandOptionsEntry[]>();
				for(Entry<Long, HashSet<ArmorstandOptionsEntry>> entry : aworldoptions.entrySet()) {
					HashSet<ArmorstandOptionsEntry> value = entry.getValue();
					worldoptions.put(entry.getKey(), value.toArray(new ArmorstandOptionsEntry[value.size()]));
				}
				options.put(worndname, worldoptions);
			}
		}
	}
	
	protected ArmorstandOptionsEntry[] getOptions(String worldname, long chunkpos) {
		HashMap<Long, ArmorstandOptionsEntry[]> worldoptions = options.get(worldname);
		return worldoptions == null ? null : worldoptions.get(chunkpos);
	}
	
	protected final class ArmorstandOptionsEntry {
		protected final String name;
		protected final double x, y, z;
		protected final float yaw, pitch;
		protected final int npcid;
		
		private ArmorstandOptionsEntry(String name, double x, double y, double z, float yaw, float pitch, int npcid) {
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
