package com.sigurd4.bioshock.passives;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.sigurd4.bioshock.item.ItemWeaponMelee;

public class PassiveExecutioner extends PassiveWrenchJockey
{
	public final float chance;
	
	public PassiveExecutioner(String id, String name, Passive[] required, Passive[] incapatible, Type type, float damageMultiplier, float chance)
	{
		super(id, name, required, incapatible, type, damageMultiplier);
		this.chance = chance;
	}
	
	@Override
	public void LivingAttackEvent(LivingHurtEvent event)
	{
		super.LivingAttackEvent(event);
		if(event.source.getDamageType().equals("player") && ((EntityPlayer)event.source.getEntity()).getHeldItem() != null && ((EntityPlayer)event.source.getEntity()).getHeldItem().getItem() instanceof ItemWeaponMelee)
		{
			if(event.entity.worldObj.rand.nextFloat() <= this.chance)
			{
				event.ammount *= 1.5F;
				((EntityPlayer)event.source.getEntity()).onCriticalHit(event.entity);
			}
		}
	}
}
