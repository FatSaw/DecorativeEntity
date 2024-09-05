package me.bomb.decorativeentity.options;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.Map.Entry;

import me.bomb.decorativeentity.packet.Packet;
import me.bomb.decorativeentity.packet.PacketPlayOutMetadataMinecart;
import me.bomb.decorativeentity.packet.PacketPlayOutSpawnMinecartTNT;

public final class MinecartOptions extends Options {
	
	public MinecartOptions(Logger logger, File file) {
		super(file, 0x0200);
		
		final int minid = sc.getIntOrDefault("entityid\0min", -32767), maxid = sc.getIntOrDefault("entityid\0max", 0);
		String[] worlds = sc.getSubKeys("worlds\0");
		if(worlds!=null) {
			for(String worldname : worlds) {
				int entityid = minid;
				HashMap<Long, HashSet<MinecartOptionsEntry>> aworldoptions = new HashMap<Long, HashSet<MinecartOptionsEntry>>();
				String worldkey = "worlds\0".concat(worldname).concat("\0");
				String[] entitys = sc.getSubKeys(worldkey);
				if(entitys==null) continue;
				for(String entityname : entitys) {
					if(entityid>=maxid) {
						break;
					}
					String worldentitykey = worldkey.concat(entityname).concat("\0");
					String[] entityoptions = sc.getSubKeys(worldentitykey);
					if(entityoptions==null) continue;
					final double x = sc.getDoubleOrDefault(worldentitykey.concat("x"), Double.NaN), y = sc.getDoubleOrDefault(worldentitykey.concat("y"), Double.NaN), z = sc.getDoubleOrDefault(worldentitykey.concat("z"), Double.NaN);
					if(x==Double.NaN||y==Double.NaN||z==Double.NaN) continue;
					int blockid = sc.getHexIntOrDefault(worldentitykey.concat("blockid"), 0);
					//blockid += sc.getIntOrDefault(worldentitykey.concat("block\0data"), 0) << 12;
					final float yaw = (float) sc.getDoubleOrDefault(worldentitykey.concat("yaw"), 0), pitch = (float) sc.getDoubleOrDefault(worldentitykey.concat("pitch"), 0);
					int offset = sc.getHexIntOrDefault(worldentitykey.concat("offset"), 0);
					int chunkx = ((int) x) >> 4, chunkz = ((int) z) >> 4;
					long chunkpos = (((long)chunkx) << 32) | (chunkz & 0xFFFFFFFFL);
					HashSet<MinecartOptionsEntry> chunkoptions = aworldoptions.get(chunkpos);
					if(chunkoptions==null) chunkoptions = new HashSet<MinecartOptionsEntry>();
					chunkoptions.add(new MinecartOptionsEntry(UUID.randomUUID(), blockid, x, y, z, (byte) ((int) (yaw * 256.0F / 360.0F)), (byte) ((int) (pitch * 256.0F / 360.0F)), offset, entityid));
					++entityid;
					aworldoptions.put(chunkpos, chunkoptions);
				}
				HashMap<Long, Packet[]> packetoptions = new HashMap<Long, Packet[]>();
				for(Entry<Long, HashSet<MinecartOptionsEntry>> entry : aworldoptions.entrySet()) {
					HashSet<MinecartOptionsEntry> value = entry.getValue();
					ArrayList<Packet> cartspawns = new ArrayList<Packet>();
					for(MinecartOptionsEntry option : value) {
						cartspawns.add(new PacketPlayOutSpawnMinecartTNT(option.npcid, option.uuid, option.x, option.y, option.z, option.yaw, option.pitch));
						PacketPlayOutMetadataMinecart metadataminecartpacket = new PacketPlayOutMetadataMinecart(option.npcid);
						
						metadataminecartpacket.hasminecartcustomblockid = true;
						metadataminecartpacket.minecartcustomblockid = option.blockid;
						
						metadataminecartpacket.hasminecartcustomblockpositiony = true;
						metadataminecartpacket.minecartcustomblockpositiony = option.offset;
						
						metadataminecartpacket.hasminecartcustomblockshow = true;
						metadataminecartpacket.minecartcustomblockshow = true;
						
						cartspawns.add(metadataminecartpacket);
					}
					packetoptions.put(entry.getKey(), cartspawns.toArray(new Packet[value.size()]));
				}
				packets.put(worldname, packetoptions);
			}
		}
		this.sc = null;
		if(logger==null) return;
		for(Entry<String, HashMap<Long, Packet[]>> entry : packets.entrySet()) {
			String world = entry.getKey();
			int chunks = entry.getValue().size();
			logger.info("Minecart chunk loaded for world '" + world + "' : " + chunks);
		}
	}
	
	protected final class MinecartOptionsEntry {
		protected final UUID uuid;
		protected final double x, y, z;
		protected final byte yaw, pitch;
		protected final int blockid, offset, npcid;
		
		private MinecartOptionsEntry(UUID uuid, int blockid, double x, double y, double z, byte yaw, byte pitch, int offset, int npcid) {
			this.uuid = uuid;
			this.blockid = blockid;
			this.x = x;
			this.y = y;
			this.z = z;
			this.yaw = yaw;
			this.pitch = pitch;
			this.offset = offset;
			this.npcid = npcid;
		}
	}
	
}
