package com.sigurd4.bioshock.passives;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.sigurd4.bioshock.extendedentity.ExtendedPlayer;

public class PassiveRoarToLife extends PassiveHillRunnersHat
{
	public final float damageMultiplier;
	
	public PassiveRoarToLife(String id, String name, Passive[] required, Passive[] incapatible, Type type, float speedMultiplier, float damageMultiplier, float seconds)
	{
		super(id, name, required, incapatible, type, speedMultiplier, seconds);
		this.damageMultiplier = damageMultiplier;
	}
	
	@Override
	public void LivingAttackEvent(LivingHurtEvent event)
	{
		if(event.entity instanceof EntityPlayer && this.timer.containsKey(event.entity) && this.timer.get(event.entity) > 0)
		{
			event.ammount *= this.damageMultiplier;
		}
	}
	
	@Override
	public void shieldsBreak(LivingHurtEvent event)
	{
		super.shieldsBreak(event);
		if(event.entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)event.entity;
			ExtendedPlayer props = ExtendedPlayer.get(player);
			props.reloadAllWeapons();
		}
	}
}
