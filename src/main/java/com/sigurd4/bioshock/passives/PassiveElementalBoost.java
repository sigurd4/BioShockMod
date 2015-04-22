package com.sigurd4.bioshock.passives;

import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.sigurd4.bioshock.element.Element;
import com.sigurd4.bioshock.passives.Passive.Type;

public class PassiveElementalBoost extends Passive
{
	public final Element element;
	public final float hurtMultiplier;
	public final float damageMultiplier;

	public PassiveElementalBoost(String id, String name, Passive[] required, Passive[] incapatible, Type type, Element element, float hurtMultiplier, float damageMultiplier)
	{
		super(id, name, required, incapatible, type);
		this.element = element;
		this.hurtMultiplier = hurtMultiplier;
		this.damageMultiplier = damageMultiplier;
	}

	@Override
	public void LivingAttackEvent(LivingHurtEvent event)
	{
		if(event.ammount > 0 && isCorrectDamageType(event.source))
		{
			event.ammount *= damageMultiplier;
		}
	}

	@Override
	public void LivingHurtEvent(LivingHurtEvent event)
	{
		if(!event.source.isUnblockable() && event.ammount > 0 && isCorrectDamageType(event.source))
		{
			event.ammount *= hurtMultiplier;
			if(event.ammount <= 0)
			{
				event.ammount = 0;
				event.setCanceled(!event.isCancelable());
			}
		}
	}

	public boolean isCorrectDamageType(DamageSource source)
	{
		return element.isElement(source);
	}
}
