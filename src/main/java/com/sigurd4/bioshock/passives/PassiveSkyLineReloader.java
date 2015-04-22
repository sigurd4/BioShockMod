package com.sigurd4.bioshock.passives;

import net.minecraft.entity.player.EntityPlayer;

import com.sigurd4.bioshock.extendedentity.ExtendedPlayer;

public class PassiveSkyLineReloader extends Passive
{
	public PassiveSkyLineReloader(String id, String name, Passive[] required, Passive[] incapatible, Type type)
	{
		super(id, name, required, incapatible, type);
	}
	
	@Override
	public void landFromSkyline(EntityPlayer player)
	{
		ExtendedPlayer props = ExtendedPlayer.get(player);
		props.reloadAllWeapons();
	}
	
	@Override
	public void hookOnSkyline(EntityPlayer player)
	{
		ExtendedPlayer props = ExtendedPlayer.get(player);
		props.reloadAllWeapons();
	}
}
