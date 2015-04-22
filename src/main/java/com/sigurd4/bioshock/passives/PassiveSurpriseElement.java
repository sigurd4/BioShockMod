package com.sigurd4.bioshock.passives;

import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.sigurd4.bioshock.element.Element;

public class PassiveSurpriseElement extends Passive
{
	public final float chance;
	
	public PassiveSurpriseElement(String id, String name, Passive[] required, Passive[] incapatible, Type type, float chance)
	{
		super(id, name, required, incapatible, type);
		this.chance = chance;
	}
	
	@Override
	public void LivingAttackEvent(LivingHurtEvent event)
	{
		if(event.source.isProjectile() && event.source.getEntity() != event.source.getSourceOfDamage() && event.source.getSourceOfDamage() instanceof EntityBullet)
		{
			if(event.entity.worldObj.rand.nextFloat() <= this.chance)
			{
				int i = event.entity.worldObj.rand.nextInt(3 + 1);
				switch(i)
				{
				case 0:
				{
					Element.addToEntityElement(event.entityLiving, Element.FIRE, 300);
					break;
				}
				case 1:
				{
					Element.addToEntityElement(event.entityLiving, Element.ELECTRICITY, 200);
					break;
				}
				case 2:
				{
					Element.addToEntityElement(event.entityLiving, Element.ICE, 400);
					break;
				}
				}
				if(i > 2)
				{
					return;
				}
			}
		}
	}
}
