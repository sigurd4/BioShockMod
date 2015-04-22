package com.sigurd4.bioshock.passives;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class PassiveEveSaver extends Passive
{
	public final float multiplier;
	
	public PassiveEveSaver(String id, String name, Passive[] required, Passive[] incapatible, Type type, float multiplier)
	{
		super(id, name, required, incapatible, type);
		this.multiplier = multiplier;
	}
	
	@Override
	public float plasmidUse(ItemStack stack, EntityPlayer player, float eve)
	{
		return eve * this.multiplier;
	}
}
