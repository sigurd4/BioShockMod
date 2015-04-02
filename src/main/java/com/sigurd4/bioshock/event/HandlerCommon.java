package com.sigurd4.bioshock.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.sigurd4.bioshock.extendedentity.ExtendedPlayer;
import com.sigurd4.bioshock.reference.RefMod;

public class HandlerCommon
{
	// minecraftforge events for both sides here!
	
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event)
	{
		if(event.entity instanceof EntityPlayer && ExtendedPlayer.get((EntityPlayer)event.entity) == null)
		{
			ExtendedPlayer.register((EntityPlayer)event.entity);
		}
		
		if(event.entity instanceof EntityPlayer && event.entity.getExtendedProperties(RefMod.MODID) == null)
		{
			event.entity.registerExtendedProperties(RefMod.MODID, new ExtendedPlayer((EntityPlayer)event.entity));
		}
	}
}
