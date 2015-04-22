package com.sigurd4.bioshock.passives;

import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.sigurd4.bioshock.passives.Passive.Type;

public class PassiveArmor extends Passive
{
	public final float hurtMultiplier;

	public PassiveArmor(String id, String name, Passive[] required, Passive[] incapatible, Type type, float hurtMultiplier)
	{
		super(id, name, required, incapatible, type);
		this.hurtMultiplier = hurtMultiplier;
	}

	public void LivingHurtEvent(LivingHurtEvent event)
	{
		if(!event.source.isUnblockable() && event.ammount > 0)
		{
			event.ammount = event.ammount*hurtMultiplier;
			if(event.ammount <= 0)
			{
				event.ammount = 0;
				event.setCanceled(!event.isCancelable());
			}
		}
	}
}
