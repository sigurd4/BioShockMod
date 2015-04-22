package com.sigurd4.bioshock.passives;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;

import com.sigurd4.bioshock.M;
import com.sigurd4.bioshock.plasmids.PlasmidHold;

public class PassiveNaturalCamouflage extends Passive
{
	public static HashMap<EntityPlayer, Integer> timers = new HashMap<EntityPlayer, Integer>();
	
	public PassiveNaturalCamouflage(String id, String name, Passive[] required, Passive[] incapatible, Type type)
	{
		super(id, name, required, incapatible, type);
	}
	
	@Override
	public void LivingUpdateEvent(LivingEvent.LivingUpdateEvent event)
	{
		if(event.entity instanceof EntityPlayer)
		{
			int timer = 160;
			EntityPlayer player = (EntityPlayer)event.entity;
			if(!timers.containsKey(player) || player.motionX != 0 || player.motionZ != 0 || !player.onGround)
			{
				timers.put(player, timer);
				if(player.isInvisible())
				{
					((PlasmidHold)M.items.plasmids.drinkable.peeping_tom.plasmid).notHeld(new ItemStack(M.items.plasmids.drinkable.peeping_tom), player.worldObj, player, player.inventory.currentItem, true);
					((PlasmidHold)M.items.plasmids.drinkable.peeping_tom.plasmid).unClicked(new ItemStack(M.items.plasmids.drinkable.peeping_tom), player.worldObj, player, player.inventory.currentItem, true);
				}
				else
				{
					((PlasmidHold)M.items.plasmids.drinkable.peeping_tom.plasmid).notHeld(new ItemStack(M.items.plasmids.drinkable.peeping_tom), player.worldObj, player, player.inventory.currentItem, true);
				}
			}
			else if(timers.get(player) > 0)
			{
				timers.put(player, timers.get(player) - 1);
				if(player.isInvisible())
				{
					((PlasmidHold)M.items.plasmids.drinkable.peeping_tom.plasmid).notHeld(new ItemStack(M.items.plasmids.drinkable.peeping_tom), player.worldObj, player, player.inventory.currentItem, true);
					((PlasmidHold)M.items.plasmids.drinkable.peeping_tom.plasmid).unClicked(new ItemStack(M.items.plasmids.drinkable.peeping_tom), player.worldObj, player, player.inventory.currentItem, true);
				}
				else
				{
					((PlasmidHold)M.items.plasmids.drinkable.peeping_tom.plasmid).notHeld(new ItemStack(M.items.plasmids.drinkable.peeping_tom), player.worldObj, player, player.inventory.currentItem, true);
				}
			}
			else
			{
				if(player.isInvisible())
				{
					int i = 4;
					if(player.ticksExisted % i == i - 1)
					{
						((PlasmidHold)M.items.plasmids.drinkable.peeping_tom.plasmid).held(new ItemStack(M.items.plasmids.drinkable.peeping_tom), player.worldObj, player, player.inventory.currentItem, true);
					}
				}
				else
				{
					((PlasmidHold)M.items.plasmids.drinkable.peeping_tom.plasmid).clicked(new ItemStack(M.items.plasmids.drinkable.peeping_tom), player.worldObj, player);
				}
			}
			if(timers.containsKey(player) && timers.get(player) > timer)
			{
				timers.put(player, timer);
			}
		}
	}
}
