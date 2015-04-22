package com.sigurd4.bioshock.passives;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.sigurd4.bioshock.extendedentity.ExtendedPlayer;

public class PassiveBloodToSalts extends Passive
{
	public final float amount;
	public final float chance;
	
	public PassiveBloodToSalts(String id, String name, Passive[] required, Passive[] incapatible, Type type, float amount, float chance)
	{
		super(id, name, required, incapatible, type);
		this.amount = amount;
		this.chance = chance;
	}
	
	@Override
	public void killEntity(LivingHurtEvent event)
	{
		if(event.entity.worldObj.rand.nextFloat() <= this.chance)
		{
			ExtendedPlayer props = ExtendedPlayer.get((EntityPlayer)event.source.getEntity());
			props.setEve(props.getEve() + (int)(props.getMaxEve() * this.amount));
		}
	}
}
