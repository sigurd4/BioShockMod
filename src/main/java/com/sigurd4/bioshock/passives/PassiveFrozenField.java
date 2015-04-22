package com.sigurd4.bioshock.passives;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.sigurd4.bioshock.element.Element;
import com.sigurd4.bioshock.item.ItemWeaponMelee;
import com.sigurd4.bioshock.plasmids.PlasmidWinterBlast;

public class PassiveFrozenField extends PassiveElementalBoost
{
	public final float freezeChance;
	
	public PassiveFrozenField(String id, String name, Passive[] required, Passive[] incapatible, Type type, float hurtMultiplier, float freezeChance)
	{
		super(id, name, required, incapatible, type, Element.ICE, hurtMultiplier, 1);
		this.freezeChance = freezeChance;
	}
	
	@Override
	public void LivingAttackEvent(LivingHurtEvent event)
	{
		if(((EntityPlayer)event.source.getEntity()).getHeldItem() != null && ((EntityPlayer)event.source.getEntity()).getHeldItem().getItem() instanceof ItemWeaponMelee)
		{
			if(event.entity.worldObj.rand.nextFloat() / (1 + (float)Element.getEntityElement(event.entityLiving, Element.WATER) / 100) <= this.freezeChance || Element.getEntityElement(event.entityLiving, Element.WATER) > 400)
			{
				PlasmidWinterBlast.freeze(event.entityLiving);
				Element.setEntityElement(event.entityLiving, Element.FIRE, 0);
			}
			Element.addTowardsEntityElement(event.entityLiving, Element.WATER, 20, 100);
			Element.addToEntityElement(event.entityLiving, Element.FIRE, -50);
		}
	}
}
