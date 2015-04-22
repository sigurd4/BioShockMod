package com.sigurd4.bioshock.passives;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.sigurd4.bioshock.item.ItemWeaponMelee;

public class PassiveKillToLive extends PassiveDeathBenefit
{
	public final float chance;
	
	public PassiveKillToLive(String id, String name, Passive[] required, Passive[] incapatible, Type type, float amount, float chance)
	{
		super(id, name, required, incapatible, type, amount);
		this.chance = chance;
	}
	
	@Override
	public void killEntity(LivingHurtEvent event)
	{
		if(event.source.getDamageType().equals("player") && ((EntityPlayer)event.source.getEntity()).getHeldItem() != null && ((EntityPlayer)event.source.getEntity()).getHeldItem().getItem() instanceof ItemWeaponMelee)
		{
			if(event.entity.worldObj.rand.nextFloat() <= this.chance)
			{
				super.killEntity(event);
			}
		}
	}
}
