package com.sigurd4.bioshock.passives;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import com.sigurd4.bioshock.item.ItemConsumable.ConsumableEffect;

public class PassiveExtraNutrition extends Passive
{
	public final float multiplier;
	
	public PassiveExtraNutrition(String id, String name, Passive[] required, Passive[] incapatible, Type type, float multiplier)
	{
		super(id, name, required, incapatible, type);
		this.multiplier = multiplier;
	}
	
	@Override
	public ConsumableEffect consumableUsed(EntityPlayer player, ItemStack stack, ConsumableEffect results, boolean eaten)
	{
		if(results.food > 0)
		{
			results.food *= this.multiplier;
		}
		if(results.health > 0)
		{
			results.health *= this.multiplier;
		}
		return results;
	}
}
