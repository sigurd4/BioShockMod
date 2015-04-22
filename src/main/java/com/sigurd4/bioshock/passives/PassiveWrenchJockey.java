package com.sigurd4.bioshock.passives;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.sigurd4.bioshock.item.ItemWeaponMelee;

public class PassiveWrenchJockey extends Passive
{
	public final float damageMultiplier;
	
	public PassiveWrenchJockey(String id, String name, Passive[] required, Passive[] incapatible, Type type, float damageMultiplier)
	{
		super(id, name, required, incapatible, type);
		this.damageMultiplier = damageMultiplier;
	}
	
	@Override
	public void LivingAttackEvent(LivingHurtEvent event)
	{
		if(event.source.getDamageType().equals("player") && ((EntityPlayer)event.source.getEntity()).getHeldItem() != null && ((EntityPlayer)event.source.getEntity()).getHeldItem().getItem() instanceof ItemWeaponMelee)
		{
			event.ammount *= this.damageMultiplier;
		}
	}
}
