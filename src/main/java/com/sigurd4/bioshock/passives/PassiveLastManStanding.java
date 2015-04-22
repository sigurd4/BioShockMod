package com.sigurd4.bioshock.passives;

import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class PassiveLastManStanding extends PassiveDeathBenefit
{
	public final float threshold;
	
	public PassiveLastManStanding(String id, String name, Passive[] required, Passive[] incapatible, Type type, float amount, float threshold)
	{
		super(id, name, required, incapatible, type, amount);
		this.threshold = threshold;
	}
	
	@Override
	public void killEntity(LivingHurtEvent event)
	{
		super.killEntity(event);
	}
}
