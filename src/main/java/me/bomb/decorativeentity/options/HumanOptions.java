package me.bomb.decorativeentity.options;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.logging.Logger;

import me.bomb.decorativeentity.packet.EnumGamemode;
import me.bomb.decorativeentity.packet.Packet;
import me.bomb.decorativeentity.packet.PacketPlayOutEntityHeadRotation;
import me.bomb.decorativeentity.packet.PacketPlayOutMetadataPlayer;
import me.bomb.decorativeentity.packet.PacketPlayOutPlayerInfo;
import me.bomb.decorativeentity.packet.PacketPlayOutPlayerSpawn;
import me.bomb.decorativeentity.packet.PacketPlayOutSetPassengers;
import me.bomb.decorativeentity.packet.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;
import me.bomb.decorativeentity.packet.PacketPlayOutPlayerInfo.PlayerInfoData;

public final class HumanOptions extends Options {
	
	public HumanOptions(Logger logger, File file) {
		super(file, 0x0800);
		
		final int minid = sc.getIntOrDefault("entityid\0min", -98302), maxid = sc.getIntOrDefault("entityid\0max", -65535);
		
		String[] worlds = sc.getSubKeys("worlds\0");
		if(worlds!=null) {
			for(String worldname : worlds) {
				int entityid = minid;
				HashMap<Long, HashSet<HumanOptionsEntry>> aworldoptions = new HashMap<Long, HashSet<HumanOptionsEntry>>();
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
					String name = sc.getStringOrDefault(worldentitykey.concat("name"), "");
					String potinocolorkey = worldentitykey.concat("potioncolor");
					boolean haspotioncolor = sc.hasKey(potinocolorkey);
					int potioncolor = sc.getHexIntOrDefault(potinocolorkey, 0x00000000);
					String skinvalue = sc.getStringOrDefault(worldentitykey.concat("skin\0value"), "");
					String skinsignature = sc.getStringOrDefault(worldentitykey.concat("skin\0signature"), "");
					final float yaw = (float) sc.getDoubleOrDefault(worldentitykey.concat("yaw"), 0), pitch = (float) sc.getDoubleOrDefault(worldentitykey.concat("pitch"), 0);
					int sittingon = sc.getIntOrDefault(worldentitykey.concat("sittingon"), 0);
					int chunkx = ((int) x) >> 4, chunkz = ((int) z) >> 4;
					long chunkpos = (((long)chunkx) << 32) | (chunkz & 0xFFFFFFFFL);
					HashSet<HumanOptionsEntry> chunkoptions = aworldoptions.get(chunkpos);
					if(chunkoptions==null) chunkoptions = new HashSet<HumanOptionsEntry>();
					chunkoptions.add(new HumanOptionsEntry(UUID.randomUUID(), name, skinvalue, skinsignature, x, y, z, (byte) ((int) (yaw * 256.0F / 360.0F)), (byte) ((int) (pitch * 256.0F / 360.0F)), entityid, sittingon, haspotioncolor, potioncolor));
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
						PlayerInfoData infodata = new PlayerInfoData(option.name, option.skinvalue, option.skinsignature, -1, EnumGamemode.NOTSET, null);
						playerinfoadd.playerinfodata.put(option.uuid, infodata);
						playerinforemove.playerinfodata.put(option.uuid, null);
						
						PacketPlayOutPlayerSpawn npcspawnpacket = new PacketPlayOutPlayerSpawn(option.npcid, option.uuid, option.x, option.y, option.z, option.yaw, option.pitch);
						humanspawns.add(npcspawnpacket);
						PacketPlayOutMetadataPlayer metadataplayer = new PacketPlayOutMetadataPlayer(option.npcid);
						metadataplayer.hasskinparts = true;
						metadataplayer.skinparts = 0x7F; //ENABLE ALL
						if(option.haspotioncolor) {
							metadataplayer.haspotioncolor = true;
							metadataplayer.potioncolor = option.potioncolor;
						}
						humanspawns.add(metadataplayer);
						PacketPlayOutEntityHeadRotation rotatepacket = new PacketPlayOutEntityHeadRotation(option.npcid, option.yaw);
						humanspawns.add(rotatepacket);
						if(option.sittingon != 0) {
							humanspawns.add(new PacketPlayOutSetPassengers(option.sittingon, option.npcid));
						}
					}
					humanspawns.add(playerinfoadd);
					humanspawns.add(playerinforemove);
					packetoptions.put(entry.getKey(), humanspawns.toArray(new Packet[value.size()]));
				}
				
				packets.put(worldname, packetoptions);
			}
		}
		this.sc = null;
		if(logger==null) return;
		for(Entry<String, HashMap<Long, Packet[]>> entry : packets.entrySet()) {
			String world = entry.getKey();
			int chunks = entry.getValue().size();
			logger.info("HumanNPC chunk loaded for world '" + world + "' : " + chunks);
		}
	}
	
	protected final class HumanOptionsEntry {
		protected final UUID uuid;
		protected final String name, skinvalue, skinsignature;
		protected final double x, y, z;
		protected final byte yaw, pitch;
		protected final int npcid, sittingon, potioncolor;
		protected final boolean haspotioncolor;
		
		private HumanOptionsEntry(UUID uuid, String name, String skinvalue, String skinsignature, double x, double y, double z, byte yaw, byte pitch, int npcid, int sittingon, boolean haspotioncolor, int potioncolor) {
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
			this.haspotioncolor = haspotioncolor;
			this.potioncolor = potioncolor;
		}
	}
	
}
