package com.sigurd4.bioshock.passives;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.sigurd4.bioshock.extendedentity.ExtendedPlayer;
import com.sigurd4.bioshock.plasmids.IDamageSourcePlasmid;

public class PassiveFilthyLeech extends Passive
{
	public final float amount;
	
	public PassiveFilthyLeech(String id, String name, Passive[] required, Passive[] incapatible, Type type, float amount)
	{
		super(id, name, required, incapatible, type);
		this.amount = amount;
	}
	
	@Override
	public void killEntity(LivingHurtEvent event)
	{
		if(event.source instanceof IDamageSourcePlasmid)
		{
			ExtendedPlayer props = ExtendedPlayer.get((EntityPlayer)event.source.getEntity());
			props.setEve(props.getEve() + (int)(props.getMaxEve() * this.amount));
		}
	}
}
