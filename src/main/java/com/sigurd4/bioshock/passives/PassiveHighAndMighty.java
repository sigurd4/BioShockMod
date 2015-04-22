package com.sigurd4.bioshock.passives;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class PassiveHighAndMighty extends PassiveWinterShield
{
	public final float damageMultiplier;
	public final float chance;
	
	public PassiveHighAndMighty(String id, String name, Passive[] required, Passive[] incapatible, Type type, float seconds, float damageMultiplier, float chance)
	{
		super(id, name, required, incapatible, type, seconds);
		this.damageMultiplier = damageMultiplier;
		this.chance = chance;
	}
	
	@Override
	public void landFromSkyline(EntityPlayer player)
	{
	}
	
	@Override
	public void hookOnSkyline(EntityPlayer player)
	{
		if(player.worldObj.rand.nextFloat() <= this.chance)
		{
			super.hookOnSkyline(player);
		}
	}
	
	@Override
	public void LivingAttackEvent(LivingHurtEvent event)
	{
		if(event.source.getEntity() instanceof EntityPlayer && this.timer.containsKey(event.source.getEntity()) && this.timer.get(event.source.getEntity()) > 0)
		{
			event.ammount *= this.damageMultiplier;
		}
	}
}
