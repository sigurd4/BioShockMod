package com.sigurd4.bioshock.event;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemPickupEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemSmeltedEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;

import com.sigurd4.bioshock.M;
import com.sigurd4.bioshock.config.Config;
import com.sigurd4.bioshock.extendedentity.ExtendedPlayer;
import com.sigurd4.bioshock.item.IItemPickupSound;
import com.sigurd4.bioshock.item.ItemPlasmid;
import com.sigurd4.bioshock.packet.PacketPlayerData;
import com.sigurd4.bioshock.reference.RefMod;

public class HandlerCommonFML
{
	// fml events for both sides here!
	
	public static HashMap<EntityPlayer, NBTTagCompound> playerDeathData = new HashMap<EntityPlayer, NBTTagCompound>();
	
	@SubscribeEvent
	public void playerUpdateEvent(PlayerTickEvent event)
	{
		ExtendedPlayer props = ExtendedPlayer.get(event.player);
		
		if(event.side == Side.SERVER)
		{
			int i = 10;
			if(event.player instanceof EntityPlayerMP && event.player.worldObj.getWorldTime() % i == i - 1)
			{
				M.network.sendTo(new PacketPlayerData(event.player), (EntityPlayerMP)event.player);
			}
			
			/** makes it so that players keep certain data upon death **/
			if(playerDeathData.get(event.player) != null && event.player.getHealth() > 0)
			{
				props.loadNBTData(playerDeathData.get(event.player), false);
				playerDeathData.remove(event.player);
			}
			if(playerDeathData.get(event.player) == null && event.player.getHealth() <= 0 || event.player.deathTime > 0)
			{
				NBTTagCompound playerData = new NBTTagCompound();
				props.saveNBTData(playerData, false);
				playerDeathData.put(event.player, playerData);
			}
		}
		
		props.update();
	}
	
	@SubscribeEvent
	public void itemPickupEvent(ItemPickupEvent event)
	{
		if(event.pickedUp.getEntityItem() != null && event.pickedUp.getEntityItem().getItem() instanceof IItemPickupSound)
		{
			((IItemPickupSound)event.pickedUp.getEntityItem().getItem()).playPickupSound(event.player, event.pickedUp.getEntityItem());
		}
	}
	
	@SubscribeEvent
	public void tickEvent(TickEvent event)
	{
		++ItemPlasmid.tickd;
		if(ItemPlasmid.tickd > 2)
		{
			ItemPlasmid.tickd = -1;
		}
		M.otherIconsIndex = -1;
	}
	
	@SubscribeEvent
	public void ItemCraftedEvent(ItemCraftedEvent event)
	{
		if(event.player != null)
		{
			ExtendedPlayer props = ExtendedPlayer.get(event.player);
			if(props != null)
			{
				props.passiveController.itemCrafted(event);
			}
		}
	}
	
	@SubscribeEvent
	public void ItemSmeltedEvent(ItemSmeltedEvent event)
	{
		if(event.player != null)
		{
			ExtendedPlayer props = ExtendedPlayer.get(event.player);
			if(props != null)
			{
				props.passiveController.itemCrafted(event);
			}
		}
	}
	
	@SubscribeEvent
	public void OnConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if(event.modID.equals(RefMod.MODID))
		{
			for(int i = 0; i < Config.entries.size(); ++i)
			{
				Config.entries.get(i).set(Config.config);
			}
			if(Config.config.hasChanged())
			{
				Config.config.save();
			}
		}
	}
}
