package com.sigurd4.bioshock.passives;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;

import com.sigurd4.bioshock.entity.projectile.IEntityGunProjectile;
import com.sigurd4.bioshock.extendedentity.ExtendedPlayer;

public class PassiveTunnelVision extends Passive
{
	public final float multiplierZooming;
	public final float multiplierNotZooming;
	
	public PassiveTunnelVision(String id, String name, Passive[] required, Passive[] incapatible, Type type, float multiplierZooming, float multiplierNotZooming)
	{
		super(id, name, required, incapatible, type);
		this.multiplierZooming = multiplierZooming;
		this.multiplierNotZooming = multiplierNotZooming;
	}
	
	@Override
	public <T extends EntityThrowable & IEntityGunProjectile> void bulletFire(EntityPlayer player, ItemStack stack, T bullet)
	{
		ExtendedPlayer props = ExtendedPlayer.get(player);
		if(props.isZoomHeldDown)
		{
			bullet.setDamage(bullet.getDamage() * this.multiplierZooming);
		}
		else
		{
			bullet.setDamage(bullet.getDamage() * this.multiplierNotZooming);
		}
	}
}
