package com.sigurd4.bioshock.passives;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.sigurd4.bioshock.item.ItemWeaponMelee;

public class PassiveBloodlust extends Passive
{
	public final float amount;
	
	public PassiveBloodlust(String id, String name, Passive[] required, Passive[] incapatible, Type type, float amount)
	{
		super(id, name, required, incapatible, type);
		this.amount = amount;
	}
	
	@Override
	public void LivingAttackEvent(LivingHurtEvent event)
	{
		if(event.source.getDamageType().equals("player") && ((EntityPlayer)event.source.getEntity()).getHeldItem() != null && ((EntityPlayer)event.source.getEntity()).getHeldItem().getItem() instanceof ItemWeaponMelee)
		{
			((EntityPlayer)event.source.getEntity()).heal(event.ammount * this.amount);
		}
	}
}
