package com.sigurd4.bioshock.passives;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent;

public class PassiveSportsBoost extends Passive
{
	public final float multiplier;
	
	public PassiveSportsBoost(String id, String name, Passive[] required, Passive[] incapatible, Type type, float multiplier)
	{
		super(id, name, required, incapatible, type);
		this.multiplier = multiplier;
	}
	
	@Override
	public void LivingUpdateEvent(LivingEvent.LivingUpdateEvent event)
	{
		int max = 3;
		if(event.entity.onGround && Math.sqrt(event.entity.motionX * event.entity.motionX + event.entity.motionZ * event.entity.motionZ) < max)
		{
			((EntityPlayer)event.entity).moveForward *= this.multiplier;
			((EntityPlayer)event.entity).moveStrafing *= this.multiplier;
		}
	}
}
