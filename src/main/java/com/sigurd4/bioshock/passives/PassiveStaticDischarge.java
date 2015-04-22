package com.sigurd4.bioshock.passives;

import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.sigurd4.bioshock.element.Element;

public class PassiveStaticDischarge extends Passive
{
	public final Element element;
	public final float damage;
	public final float chance;
	
	public PassiveStaticDischarge(String id, String name, Passive[] required, Passive[] incapatible, Type type, Element element, float damage, float chance)
	{
		super(id, name, required, incapatible, type);
		this.element = element;
		this.damage = damage;
		this.chance = chance;
	}
	
	@Override
	public void LivingHurtEvent(LivingHurtEvent event)
	{
		if(!event.source.isUnblockable() && event.ammount > 0)
		{
			if(event.source.getEntity() instanceof EntityLivingBase && !event.source.getEntity().isEntityInvulnerable(event.source) && event.source.getEntity() != null)
			{
				if(event.source.getEntity().attackEntityFrom(Element.electricity(event.entity), event.ammount / 2 * (1 + this.damage / 5)))
				{
					if(event.entity.worldObj.rand.nextFloat() <= this.chance)
					{
						Element.setEntityElement((EntityLivingBase)event.source.getEntity(), this.element, (int)(this.damage * 20), false);
					}
					Element.addToEntityElement(event.entityLiving, this.element, (int)(this.damage * 2));
				}
			}
		}
	}
}
