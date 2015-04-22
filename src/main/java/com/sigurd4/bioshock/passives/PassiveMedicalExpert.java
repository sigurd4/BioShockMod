package com.sigurd4.bioshock.passives;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import com.sigurd4.bioshock.item.ItemConsumable;
import com.sigurd4.bioshock.item.ItemConsumable.ConsumableEffect;
import com.sigurd4.bioshock.item.ItemConsumable.EnumConsumableType;

public class PassiveMedicalExpert extends Passive
{
	public final float multiplier;
	
	public PassiveMedicalExpert(String id, String name, Passive[] required, Passive[] incapatible, Type type, float multiplier)
	{
		super(id, name, required, incapatible, type);
		this.multiplier = multiplier;
	}
	
	@Override
	public ConsumableEffect consumableUsed(EntityPlayer player, ItemStack stack, ConsumableEffect results, boolean eaten)
	{
		if(((ItemConsumable)stack.getItem()).type == EnumConsumableType.MEDICAL)
		{
			results.health *= this.multiplier;
		}
		return results;
	}
}
