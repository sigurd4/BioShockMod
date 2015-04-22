package com.sigurd4.bioshock.passives;

import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.sigurd4.bioshock.passives.Passive.Type;

import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemSmeltedEvent;

public class PassiveProlificInventor extends Passive
{
	public final float craftedMultiplier;

	public PassiveProlificInventor(String id, String name, Passive[] required, Passive[] incapatible, Type type, float craftedMultiplier)
	{
		super(id, name, required, incapatible, type);
		this.craftedMultiplier = craftedMultiplier;
	}

	public void ItemCraftedEvent(ItemCraftedEvent event)
	{
		event.crafting.stackSize = (int)Math.floor((float)event.crafting.stackSize*craftedMultiplier);
		if(event.crafting.stackSize > 64)
		{
			event.crafting.stackSize = 64;
		}
		if(event.crafting.stackSize < 1)
		{
			event.crafting.stackSize = 1;
		}
	}

	public void ItemSmeltedEvent(ItemSmeltedEvent event)
	{
		event.smelting.stackSize = (int)Math.floor((float)event.smelting.stackSize*craftedMultiplier);
		if(event.smelting.stackSize > 64)
		{
			event.smelting.stackSize = 64;
		}
		if(event.smelting.stackSize < 1)
		{
			event.smelting.stackSize = 1;
		}
	}
}
