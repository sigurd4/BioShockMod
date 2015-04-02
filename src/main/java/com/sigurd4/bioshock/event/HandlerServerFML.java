package com.sigurd4.bioshock.event;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.SERVER)
public class HandlerServerFML
{
	// fml events for server only here!
	
	@SubscribeEvent
	public void playerUpdateEvent(PlayerTickEvent event)
	{
		
	}
}
