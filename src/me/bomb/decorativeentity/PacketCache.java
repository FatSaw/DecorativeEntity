package me.bomb.decorativeentity;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.World;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;

import me.bomb.decorativeentity.ArmorstandOptions.ArmorstandOptionsEntry;
import me.bomb.decorativeentity.MinecartOptions.MinecartOptionsEntry;
import net.minecraft.server.v1_12_R1.Block;
import net.minecraft.server.v1_12_R1.EntityArmorStand;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_12_R1.PacketPlayOutSpawnEntity;
import net.minecraft.server.v1_12_R1.PacketPlayOutSpawnEntityLiving;

final class PacketCache {
	
	private final HashMap<MinecartOptionsEntry[],ArrayList<Object>> cacheminecarts = new HashMap<MinecartOptionsEntry[],ArrayList<Object>>();
	private final HashMap<ArmorstandOptionsEntry[],ArrayList<Object>> cachearmorstands = new HashMap<ArmorstandOptionsEntry[],ArrayList<Object>>();
	
	protected final MinecartOptions minecartoptions;
	protected final ArmorstandOptions armorstandoptions;
	
	protected PacketCache(MinecartOptions minecartoptions, ArmorstandOptions armorstandoptions) {
		this.minecartoptions = minecartoptions;
		this.armorstandoptions = armorstandoptions;
	}

	protected ArrayList<Object> getPackets(World world, int chunkx, int chunkz, MinecartOptionsEntry[] options) {
		ArrayList<Object> cartspawns = cacheminecarts.get(options);
		if(cartspawns==null) {
			cartspawns = new ArrayList<Object>();
			net.minecraft.server.v1_12_R1.World nmsworld = ((CraftWorld)world).getHandle();
			for(MinecartOptionsEntry option : options) {
				CustomMinecartBlock entity = new CustomMinecartBlock(nmsworld, option.x, option.y, option.z, option.yaw, option.pitch, Block.getByName(option.material).getBlockData(), option.offset, option.npcid);
				cartspawns.add(new PacketPlayOutSpawnEntity(entity, 10, entity.v().a()));
				cartspawns.add(new PacketPlayOutEntityMetadata(option.npcid, entity.getDataWatcher(), false));
			}
			cacheminecarts.put(options, cartspawns);
		}
		return cartspawns;
	}
	
	protected ArrayList<Object> getPackets(World world, int chunkx, int chunkz, ArmorstandOptionsEntry[] options) {
		ArrayList<Object> sdandspawns = cachearmorstands.get(options);
		if(sdandspawns==null) {
			sdandspawns = new ArrayList<Object>();
			net.minecraft.server.v1_12_R1.World nmsworld = ((CraftWorld)world).getHandle();
			for(ArmorstandOptionsEntry option : options) {
				EntityArmorStand entity = new EntityArmorStand(nmsworld, option.x, option.y, option.z);
				entity.yaw = option.yaw;
				entity.pitch = option.pitch;
				entity.setCustomName(option.name);
				entity.setCustomNameVisible(true);
				entity.setNoGravity(true);
				entity.setMarker(true);
				entity.setInvisible(true);
				entity.setInvulnerable(true);
				entity.h(option.npcid);
				sdandspawns.add(new PacketPlayOutSpawnEntityLiving(entity));
			}
			cachearmorstands.put(options, sdandspawns);
		}
		return sdandspawns;
	}
}
