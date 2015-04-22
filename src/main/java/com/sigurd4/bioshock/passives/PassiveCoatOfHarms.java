package com.sigurd4.bioshock.passives;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.sigurd4.bioshock.item.ItemWeaponMelee;

public class PassiveCoatOfHarms extends Passive
{
	public final float chance;
	
	public PassiveCoatOfHarms(String id, String name, Passive[] required, Passive[] incapatible, Type type, float chance)
	{
		super(id, name, required, incapatible, type);
		this.chance = chance;
	}
	
	@Override
	public void LivingAttackEvent(LivingHurtEvent event)
	{
		if(event.source.getDamageType().equals("player") && ((EntityPlayer)event.source.getEntity()).getHeldItem() != null && ((EntityPlayer)event.source.getEntity()).getHeldItem().getItem() instanceof ItemWeaponMelee)
		{
			if(event.entity.worldObj.rand.nextFloat() <= this.chance && event.entityLiving.getHealth() <= event.entityLiving.getMaxHealth() / 3)
			{
				event.ammount *= 3F;
				((EntityPlayer)event.source.getEntity()).onCriticalHit(event.entity);
			}
		}
	}
}
