package me.bomb.decorativeentity;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.World;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;

import io.netty.channel.ChannelHandlerContext;
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
	
	private final MinecartOptions minecartoptions;
	private final ArmorstandOptions armorstandoptions;
	
	protected PacketCache(MinecartOptions minecartoptions, ArmorstandOptions armorstandoptions) {
		this.minecartoptions = minecartoptions;
		this.armorstandoptions = armorstandoptions;
	}
	
	protected void sendPacketsForChunk(ChannelHandlerContext context, World world, int x, int z) {
		String worldname = world.getName();
		MinecartOptionsEntry[] minecartoptions = this.minecartoptions.getOptions(worldname, x, z);
		if (minecartoptions != null) {
			for(Object p : this.getPackets(world, minecartoptions)) {
				context.write(p);
			}
		}
		ArmorstandOptionsEntry[] armorstandoptions = this.armorstandoptions.getOptions(worldname, x, z);
		if (armorstandoptions != null) {
			for(Object p : this.getPackets(world, armorstandoptions)) {
				context.write(p);
			}
		}
	}
	
	protected void clear() {
		cacheminecarts.clear();
		cachearmorstands.clear();
	}

	//TODO: MAKE THIS CLASS INDEPENDENT TO NMS VERSION
	private ArrayList<Object> getPackets(World world, MinecartOptionsEntry[] options) {
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
	
	private ArrayList<Object> getPackets(World world, ArmorstandOptionsEntry[] options) {
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
