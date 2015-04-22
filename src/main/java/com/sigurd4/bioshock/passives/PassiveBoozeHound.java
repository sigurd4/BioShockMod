package com.sigurd4.bioshock.passives;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import com.sigurd4.bioshock.item.ItemConsumable;
import com.sigurd4.bioshock.item.ItemConsumable.ConsumableEffect;
import com.sigurd4.bioshock.item.ItemConsumable.EnumConsumableType;

public class PassiveBoozeHound extends Passive
{
	public PassiveBoozeHound(String id, String name, Passive[] required, Passive[] incapatible, Type type)
	{
		super(id, name, required, incapatible, type);
	}
	
	@Override
	public ConsumableEffect consumableUsed(EntityPlayer player, ItemStack stack, ConsumableEffect results, boolean eaten)
	{
		if(results.drunkness > 0 && ((ItemConsumable)stack.getItem()).type == EnumConsumableType.ALCOHOL)
		{
			if(results.eve < 10)
			{
				results.eve = 10;
			}
		}
		return results;
	}
}
