package com.sigurd4.bioshock.passives;

import java.util.HashMap;
import java.util.Iterator;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.sigurd4.bioshock.item.ItemWeaponMelee;

public class PassiveBrittleSkinned extends Passive
{
	public final float damageMultiplier;
	public final float seconds;
	public final HashMap<EntityPlayer, HashMap<EntityLivingBase, Integer>> timer = new HashMap<EntityPlayer, HashMap<EntityLivingBase, Integer>>();
	
	public PassiveBrittleSkinned(String id, String name, Passive[] required, Passive[] incapatible, Type type, float damageMultiplier, float seconds)
	{
		super(id, name, required, incapatible, type);
		this.damageMultiplier = damageMultiplier;
		this.seconds = seconds;
	}
	
	@Override
	public void LivingAttackEvent(LivingHurtEvent event)
	{
		if(event.source.getDamageType().equals("player") && ((EntityPlayer)event.source.getEntity()).getHeldItem() != null && ((EntityPlayer)event.source.getEntity()).getHeldItem().getItem() instanceof ItemWeaponMelee)
		{
			EntityPlayer player = (EntityPlayer)event.source.getEntity();
			if(this.timer.get(player) == null)
			{
				this.timer.put(player, new HashMap<EntityLivingBase, Integer>());
			}
			
			if(this.timer.get(player).get(event.entityLiving) != null && this.timer.get(player).get(event.entityLiving) > 0)
			{
				event.ammount *= this.damageMultiplier;
			}
			this.timer.get(player).put(event.entityLiving, (int)Math.ceil(this.seconds * 40));
		}
	}
	
	@Override
	public void LivingUpdateEvent(LivingEvent.LivingUpdateEvent event)
	{
		if(event.entity instanceof EntityPlayer && this.timer.get(event.entity) != null)
		{
			Iterator keys = this.timer.get(event.entity).keySet().iterator();
			while(keys.hasNext())
			{
				EntityLivingBase key = (EntityLivingBase)keys.next();
				if(this.timer.get(event.entity).get(key) != null && this.timer.get(event.entity).get(key) > 0)
				{
					this.timer.get(event.entity).put(key, this.timer.get(event.entity).get(key) - 1);
				}
			}
		}
	}
}
