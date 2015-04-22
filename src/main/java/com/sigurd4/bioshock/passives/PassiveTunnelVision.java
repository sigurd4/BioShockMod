package com.sigurd4.bioshock.passives;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import com.sigurd4.bioshock.entity.projectile.EntityBullet;
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
	
	public void bulletFire(EntityPlayer player, ItemStack stack, EntityBullet bullet)
	{
		ExtendedPlayer props = ExtendedPlayer.get(player);
		if(props.isZoomHeldDown)
		{
			bullet.damage *= this.multiplierZooming;
		}
		else
		{
			bullet.damage *= this.multiplierNotZooming;
		}
	}
	
	public void bulletFire(EntityPlayer player, ItemStack stack, EntityCrossbowBolt bolt)
	{
		ExtendedPlayer props = ExtendedPlayer.get(player);
		if(props.isZoomHeldDown)
		{
			bolt.damage *= this.multiplierZooming;
		}
		else
		{
			bolt.damage *= this.multiplierNotZooming;
		}
	}
}
