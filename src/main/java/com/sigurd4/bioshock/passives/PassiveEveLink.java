package com.sigurd4.bioshock.passives;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import com.sigurd4.bioshock.item.ItemConsumable;
import com.sigurd4.bioshock.item.ItemConsumable.ConsumableEffect;
import com.sigurd4.bioshock.item.ItemConsumable.EnumConsumableType;

public class PassiveEveLink extends Passive
{
	public final int amount;
	
	public PassiveEveLink(String id, String name, Passive[] required, Passive[] incapatible, Type type, int amount)
	{
		super(id, name, required, incapatible, type);
		this.amount = amount;
	}
	
	@Override
	public ConsumableEffect consumableUsed(EntityPlayer player, ItemStack stack, ConsumableEffect results, boolean eaten)
	{
		if(((ItemConsumable)stack.getItem()).type == EnumConsumableType.MEDICAL)
		{
			results.eve += this.amount;
		}
		return results;
	}
}
