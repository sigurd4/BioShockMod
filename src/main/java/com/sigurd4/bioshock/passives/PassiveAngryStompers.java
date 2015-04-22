package com.sigurd4.bioshock.passives;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class PassiveAngryStompers extends Passive
{
	public final float damageMultiplier;
	public final float threshold;
	
	public PassiveAngryStompers(String id, String name, Passive[] required, Passive[] incapatible, Type type, float damageMultiplier, float threshold)
	{
		super(id, name, required, incapatible, type);
		this.damageMultiplier = damageMultiplier;
		this.threshold = threshold;
	}
	
	@Override
	public void LivingAttackEvent(LivingHurtEvent event)
	{
		if(((EntityPlayer)event.source.getEntity()).getHealth() <= ((EntityPlayer)event.source.getEntity()).getMaxHealth() * this.threshold)
		{
			event.ammount *= this.damageMultiplier;
		}
	}
}
