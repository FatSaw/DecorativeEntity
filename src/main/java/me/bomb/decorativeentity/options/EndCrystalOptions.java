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
import me.bomb.decorativeentity.packet.PacketPlayOutMetadataEndCrystal;
import me.bomb.decorativeentity.packet.PacketPlayOutSpawnEndCrystal;

public final class EndCrystalOptions extends Options  {
	
	public EndCrystalOptions(Logger logger, File file) {
		super(file, 0x0200);

		final int minid = sc.getIntOrDefault("entityid\0min", -131069), maxid = sc.getIntOrDefault("entityid\0max", -98302);
		String[] worlds = sc.getSubKeys("worlds\0");
		if(worlds!=null) {
			for(String worldname : worlds) {
				int entityid = minid;
				HashMap<Long, HashSet<EndCrystalOptionsEntry>> aworldoptions = new HashMap<Long, HashSet<EndCrystalOptionsEntry>>();
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
					final float yaw = (float) sc.getDoubleOrDefault(worldentitykey.concat("yaw"), 0), pitch = (float) sc.getDoubleOrDefault(worldentitykey.concat("pitch"), 0);
					int chunkx = ((int) x) >> 4, chunkz = ((int) z) >> 4;
					long chunkpos = (((long)chunkx) << 32) | (chunkz & 0xFFFFFFFFL);
					String beamkey = worldentitykey.concat("beam\0");
					String[] beamsectionkeys = sc.getSubKeys(beamkey);
					boolean hasbeamx = false, hasbeamy = false, hasbeamz = false;
					boolean hasbeamtarget = beamsectionkeys != null;
					if(hasbeamtarget) {
						int beamsectionlength = beamsectionkeys.length;
						while(--beamsectionlength > -1) {
							if(beamsectionkeys[beamsectionlength].equals("x")) {
								hasbeamx = true;
							}
							if(beamsectionkeys[beamsectionlength].equals("y")) {
								hasbeamy = true;
							}
							if(beamsectionkeys[beamsectionlength].equals("z")) {
								hasbeamz = true;
							}
						}
					}
					BlockPosition beamtarget = hasbeamx && hasbeamy && hasbeamz ? new BlockPosition(sc.getIntOrDefault(beamkey.concat("x"), (int)x), sc.getIntOrDefault(beamkey.concat("y"), (int)y), sc.getIntOrDefault(beamkey.concat("z"), (int)z)) : null;
					String bottomkey = worldentitykey.concat("bottom");
					boolean hasbottom = sc.hasKey(bottomkey);
					boolean bottom = sc.getBooleanOrDefault(bottomkey, false);
					HashSet<EndCrystalOptionsEntry> chunkoptions = aworldoptions.get(chunkpos);
					if(chunkoptions==null) chunkoptions = new HashSet<EndCrystalOptionsEntry>();
					chunkoptions.add(new EndCrystalOptionsEntry(UUID.randomUUID(), x, y, z, (byte) ((int) (yaw * 256.0F / 360.0F)), (byte) ((int) (pitch * 256.0F / 360.0F)), entityid, hasbeamtarget, beamtarget, hasbottom, bottom));
					++entityid;
					aworldoptions.put(chunkpos, chunkoptions);
				}
				HashMap<Long, Packet[]> packetoptions = new HashMap<Long, Packet[]>();
				for(Entry<Long, HashSet<EndCrystalOptionsEntry>> entry : aworldoptions.entrySet()) {
					HashSet<EndCrystalOptionsEntry> value = entry.getValue();
					ArrayList<Packet> crystalspawns = new ArrayList<Packet>();
					for(EndCrystalOptionsEntry option : value) {
						PacketPlayOutSpawnEndCrystal endcrystalspawnpacket = new PacketPlayOutSpawnEndCrystal(option.npcid, option.uuid, option.x, option.y, option.z, option.yaw, option.pitch);
						PacketPlayOutMetadataEndCrystal endcrystalmetadatapacket = new PacketPlayOutMetadataEndCrystal(option.npcid);
						
						endcrystalmetadatapacket.hasbeamtarget = option.hasbeamtarget;
						endcrystalmetadatapacket.beamtarget = option.beamtarget;
						
						endcrystalmetadatapacket.hasbottom = option.hasbottom;
						endcrystalmetadatapacket.bottom = option.bottom;
						
						crystalspawns.add(endcrystalspawnpacket);
						crystalspawns.add(endcrystalmetadatapacket);
					}
					packetoptions.put(entry.getKey(), crystalspawns.toArray(new Packet[value.size()]));
				}
			}
		}
		
		
		this.sc = null;
		if(logger==null) return;
		for(Entry<String, HashMap<Long, Packet[]>> entry : packets.entrySet()) {
			String worldname = entry.getKey();
			int chunks = entry.getValue().size();
			logger.info("EndCrystals chunk loaded for world '" + worldname + "' : " + chunks);
		}
	}
	
	protected final class EndCrystalOptionsEntry {
		protected final UUID uuid;
		protected final double x, y, z;
		protected final byte yaw, pitch;
		protected final int npcid;
		protected final boolean hasbeamtarget;
		protected final BlockPosition beamtarget;
		protected final boolean hasbottom;
		protected final boolean bottom;
		
		private EndCrystalOptionsEntry(UUID uuid, double x, double y, double z, byte yaw, byte pitch, int npcid, boolean hasbeamtarget, BlockPosition beamtarget, boolean hasbottom, boolean bottom) {
			this.uuid = uuid;
			this.x = x;
			this.y = y;
			this.z = z;
			this.yaw = yaw;
			this.pitch = pitch;
			this.npcid = npcid;
			this.hasbeamtarget = hasbeamtarget;
			this.beamtarget = beamtarget;
			this.hasbottom = hasbottom;
			this.bottom = bottom;
		}
		
	}

}
