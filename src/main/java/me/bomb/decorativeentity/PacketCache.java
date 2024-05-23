package me.bomb.decorativeentity;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.World;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import me.bomb.decorativeentity.ArmorstandOptions.ArmorstandOptionsEntry;
import me.bomb.decorativeentity.HumanOptions.HumanOptionsEntry;
import me.bomb.decorativeentity.MinecartOptions.MinecartOptionsEntry;
import me.bomb.decorativeentity.packet.EnumGamemode;
import me.bomb.decorativeentity.packet.PacketEncoder;
import me.bomb.decorativeentity.packet.PacketPlayOutEntityHeadRotation;
import me.bomb.decorativeentity.packet.PacketPlayOutMetadataMinecart;
import me.bomb.decorativeentity.packet.PacketPlayOutPlayerSpawn;
import me.bomb.decorativeentity.packet.PacketPlayOutSpawnArmorStand;
import me.bomb.decorativeentity.packet.PacketPlayOutSpawnMinecartTNT;
import me.bomb.decorativeentity.packet.PacketPlayOutPlayerInfo;
import me.bomb.decorativeentity.packet.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;
import me.bomb.decorativeentity.packet.PacketPlayOutPlayerInfo.PlayerInfoData;

final class PacketCache {
	
	private final HashMap<MinecartOptionsEntry[],ArrayList<Object>> cacheminecarts = new HashMap<MinecartOptionsEntry[],ArrayList<Object>>();
	private final HashMap<ArmorstandOptionsEntry[],ArrayList<Object>> cachearmorstands = new HashMap<ArmorstandOptionsEntry[],ArrayList<Object>>();
	private final HashMap<HumanOptionsEntry[],ArrayList<Object>> cachehumans = new HashMap<HumanOptionsEntry[],ArrayList<Object>>();
	
	private final MinecartOptions minecartoptions;
	private final ArmorstandOptions armorstandoptions;
	private final HumanOptions humanoptions;
	
	protected PacketCache(MinecartOptions minecartoptions, ArmorstandOptions armorstandoptions, HumanOptions humanoptions) {
		this.minecartoptions = minecartoptions;
		this.armorstandoptions = armorstandoptions;
		this.humanoptions = humanoptions;
	}
	
	protected void sendPacketsForChunk(ChannelHandlerContext context, ChannelPromise promise, PacketEncoder encoder, World world, long chunkpos) {
		String worldname = world.getName();
		MinecartOptionsEntry[] minecartoptions = this.minecartoptions.getOptions(worldname, chunkpos);
		if (minecartoptions != null) {
			for(Object p : this.getPackets(world, minecartoptions)) {
				context.write(p, promise);
			}
		}
		ArmorstandOptionsEntry[] armorstandoptions = this.armorstandoptions.getOptions(worldname, chunkpos);
		if (armorstandoptions != null) {
			for(Object p : this.getPackets(world, armorstandoptions)) {
				try {
					encoder.write(context, p, promise);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		HumanOptionsEntry[] humanoptions = this.humanoptions.getOptions(worldname, chunkpos);
		if (humanoptions != null) {
			for(Object p : this.getPackets(world, humanoptions)) {
				try {
					encoder.write(context, p, promise);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	protected void clear() {
		cacheminecarts.clear();
		cachearmorstands.clear();
		cachehumans.clear();
	}

	private ArrayList<Object> getPackets(World world, MinecartOptionsEntry[] options) {
		ArrayList<Object> cartspawns = cacheminecarts.get(options);
		if(cartspawns==null) {
			cartspawns = new ArrayList<Object>();
			for(MinecartOptionsEntry option : options) {
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
			cacheminecarts.put(options, cartspawns);
		}
		return cartspawns;
	}
	
	private ArrayList<Object> getPackets(World world, ArmorstandOptionsEntry[] options) {
		ArrayList<Object> sdandspawns = cachearmorstands.get(options);
		if(sdandspawns==null) {
			sdandspawns = new ArrayList<Object>();
			for(ArmorstandOptionsEntry option : options) {
				PacketPlayOutSpawnArmorStand armorstandspawnpacket = new PacketPlayOutSpawnArmorStand(option.npcid, option.uuid, option.x, option.y, option.z, option.yaw, option.pitch);
				armorstandspawnpacket.hasentityflags = true;
				armorstandspawnpacket.entityflags = 0x20; //Invisible
				armorstandspawnpacket.hasnogravity = true;
				armorstandspawnpacket.nogravity = true;
				armorstandspawnpacket.hascustomname = true;
				armorstandspawnpacket.customname = option.name;
				armorstandspawnpacket.hasvisiblecustomname = true;
				armorstandspawnpacket.visiblecustomname = true;
				armorstandspawnpacket.hasarmorstandflag = true;
				armorstandspawnpacket.armorstandflag = 0x19; //small, no baseplate, marker
				sdandspawns.add(armorstandspawnpacket);
			}
			cachearmorstands.put(options, sdandspawns);
		}
		return sdandspawns;
	}
	private ArrayList<Object> getPackets(World world, HumanOptionsEntry[] options) {

		ArrayList<Object> humanspawns = cachehumans.get(options);
		if(humanspawns==null) {
			humanspawns = new ArrayList<Object>();
			PacketPlayOutPlayerInfo playerinfoadd = new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER), playerinforemove = new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.REMOVE_PLAYER);
			humanspawns.add(playerinfoadd);
			for(HumanOptionsEntry option : options) {
				PlayerInfoData infodata = new PlayerInfoData(option.name, option.skinvalue, option.skinsignature, 0, EnumGamemode.CREATIVE, null);
				playerinfoadd.playerinfodata.put(option.uuid, infodata);
				playerinforemove.playerinfodata.put(option.uuid, null);
				PacketPlayOutPlayerSpawn npcspawnpacket = new PacketPlayOutPlayerSpawn(option.npcid, option.uuid, option.x, option.y, option.z, option.yaw, option.pitch);
				humanspawns.add(npcspawnpacket);
				PacketPlayOutEntityHeadRotation rotatepacket = new PacketPlayOutEntityHeadRotation(option.npcid, option.yaw);
				humanspawns.add(rotatepacket);
			}
			humanspawns.add(playerinforemove);
			cachehumans.put(options, humanspawns);
		}
		
		return humanspawns;
	}
}
