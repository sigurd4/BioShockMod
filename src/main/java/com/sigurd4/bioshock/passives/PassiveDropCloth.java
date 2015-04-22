package com.sigurd4.bioshock.passives;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent;

public class PassiveDropCloth extends Passive
{
	public final float multiplier;
	public final float seconds;
	public final HashMap<EntityPlayer, Integer> timer = new HashMap<EntityPlayer, Integer>();
	
	public PassiveDropCloth(String id, String name, Passive[] required, Passive[] incapatible, Type type, float multiplier, float seconds)
	{
		super(id, name, required, incapatible, type);
		this.multiplier = multiplier;
		this.seconds = seconds;
	}
	
	@Override
	public void LivingUpdateEvent(LivingEvent.LivingUpdateEvent event)
	{
		if(event.entity instanceof EntityPlayer && this.timer.containsKey(event.entity) && this.timer.get(event.entity) > 0)
		{
			int max = 3;
			if(event.entity.onGround && Math.sqrt(event.entity.motionX * event.entity.motionX + event.entity.motionZ * event.entity.motionZ) < max)
			{
				((EntityPlayer)event.entity).moveForward *= this.multiplier;
				((EntityPlayer)event.entity).moveStrafing *= this.multiplier;
				this.timer.put((EntityPlayer)event.entity, (Integer)(this.timer.get(event.entity) - 1));
			}
		}
	}
	
	@Override
	public void landFromSkyline(EntityPlayer player)
	{
		this.timer.put(player, (int)Math.ceil(this.seconds * 40));
	}
}
