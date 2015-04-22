package com.sigurd4.bioshock.passives;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class PassiveDeathBenefit extends Passive
{
	public final float amount;
	
	public PassiveDeathBenefit(String id, String name, Passive[] required, Passive[] incapatible, Type type, float amount)
	{
		super(id, name, required, incapatible, type);
		this.amount = amount;
	}
	
	@Override
	public void killEntity(LivingHurtEvent event)
	{
		((EntityPlayer)event.source.getEntity()).heal(this.amount);
	}
}
