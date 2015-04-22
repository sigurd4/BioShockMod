package com.sigurd4.bioshock.passives;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.sigurd4.bioshock.extendedentity.ExtendedPlayer;
import com.sigurd4.bioshock.item.ItemConsumable.ConsumableEffect;

public class PassiveShelteredLife extends Passive
{
	public final float seconds;
	public final HashMap<EntityPlayer, Integer> timer = new HashMap<EntityPlayer, Integer>();
	
	public PassiveShelteredLife(String id, String name, Passive[] required, Passive[] incapatible, Type type, float seconds)
	{
		super(id, name, required, incapatible, type);
		this.seconds = seconds;
	}
	
	@Override
	public ConsumableEffect consumableUsed(EntityPlayer player, ItemStack stack, ConsumableEffect results, boolean eaten)
	{
		if(eaten)
		{
			this.timer.put(player, (int)Math.ceil(this.seconds * 40));
		}
		return results;
	}
	
	@Override
	public void LivingHurtEvent(LivingHurtEvent event)
	{
		if(event.entity instanceof EntityPlayer && this.timer.containsKey(event.entity) && this.timer.get(event.entity) > 0)
		{
			event.ammount = 0;
			ExtendedPlayer.get((EntityPlayer)event.entity).setShieldsKnockbackResistance();
		}
	}
	
	@Override
	public void LivingUpdateEvent(LivingEvent.LivingUpdateEvent event)
	{
		if(event.entity instanceof EntityPlayer && this.timer.containsKey(event.entity) && this.timer.get(event.entity) > 0)
		{
			this.timer.put((EntityPlayer)event.entity, (Integer)(this.timer.get(event.entity) - 1));
		}
	}
}
