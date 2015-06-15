package com.sigurd4.bioshock.plasmids;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IPlasmidProjectile
{
	public double getInitialSpeed();
	
	public void onImpact(EntityThrowable projectile, MovingObjectPosition pos);
	
	public void onEntityUpdate(EntityThrowable projectile);
	
	public float getGravityVelocity(EntityThrowable projectile);
	
	public boolean attackEntityFrom(EntityThrowable projectile, DamageSource src, float dmg);
	
	public boolean interactFirst(EntityThrowable projectile, EntityPlayer player);
	
	public DamageSource getDamageSource(EntityThrowable projectile);
	
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(EntityThrowable projectile, double distance);
}
