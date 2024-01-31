package me.bomb.decorativeentity;

import net.minecraft.server.v1_12_R1.DamageSource;
import net.minecraft.server.v1_12_R1.Entity;
import net.minecraft.server.v1_12_R1.EntityMinecartAbstract;
import net.minecraft.server.v1_12_R1.IBlockData;
import net.minecraft.server.v1_12_R1.World;

public final class CustomMinecartBlock extends EntityMinecartAbstract {

	public CustomMinecartBlock(World world, double x, double y, double z, float yaw, float pitch, IBlockData iblockdata, int offset, int id) {
		super(world, x, y, z);
		this.yaw = yaw;
		this.pitch = pitch;
		super.setDisplayBlock(iblockdata);
		super.setDisplayBlockOffset(offset);
		h(id);
	}

	@Override
	public EnumMinecartType v() {
		return EnumMinecartType.TNT;
	}
	
	@Override
	public boolean damageEntity(DamageSource damagesource, float f) {
		return false; //DO NOT DAMAGE
	}
	
	@Override
	public void collide(Entity entity) {
		//DO NOT COLLIDE
	}
	@Override
	public void setDisplayBlock(IBlockData iblockdata) {
		//DO NOT CHANGE
	}
	@Override
	public void setDisplayBlockOffset(int i) {
		//DO NOT CHANGE
	}
	@Override
	public void a(DamageSource damagesource) {
		//DO NOT DAMAGE
	}
	@Override
	public boolean isInteractable() {
        return false;
    }
	@Override
	public void B_() {
		//DO NOT TICK
	}
	@Override
	public boolean isCollidable() {
        return false;
    }
	@Override
	public boolean A() {
		return true; //SAVE
	}
}
