package com.sigurd4.bioshock.passives;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class PassiveAmmoCap extends Passive
{
	public final float chance;
	
	public PassiveAmmoCap(String id, String name, Passive[] required, Passive[] incapatible, Type type, float chance)
	{
		super(id, name, required, incapatible, type);
		this.chance = chance;
	}
	
	@Override
	public float fireWeaponAmmoModifier(ItemStack stack, EntityPlayer player, float amount)
	{
		if(!player.worldObj.isRemote && player.worldObj.rand.nextFloat() <= this.chance && amount - 1 >= 0)
		{
			return amount - 1;
		}
		else
		{
			return amount;
		}
	}
}
