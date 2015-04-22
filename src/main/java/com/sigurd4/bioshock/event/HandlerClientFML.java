package com.sigurd4.bioshock.event;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.sigurd4.bioshock.audio.AudioHandler;
import com.sigurd4.bioshock.item.IItemPickupSound;
import com.sigurd4.bioshock.item.ItemWeaponMelee;
import com.sigurd4.bioshock.key.KeyBindings;
import com.sigurd4.bioshock.particles.ParticleHandler;

@SideOnly(Side.CLIENT)
public class HandlerClientFML
{
	// fml events for client only here!
	@SubscribeEvent
	public void clientTickEvent(ClientTickEvent event)
	{
		Minecraft mc = Minecraft.getMinecraft();
		World world = mc.theWorld;
		EntityPlayer player = mc.thePlayer;
		
		if(!mc.isGamePaused())
		{
			AudioHandler.onUpdate();
		}
		if(player != null)
		{
			if(mc.inGameHasFocus)
			{
				if(player.inventory != null)
				{
					if(mc.thePlayer.inventory.currentItem != IItemPickupSound.Variables.lastSlot || Minecraft.getMinecraft().thePlayer.getHeldItem() == null)
					{
						IItemPickupSound.Variables.playRightClickSound = true;
						IItemPickupSound.Variables.lastSlot = Minecraft.getMinecraft().thePlayer.inventory.currentItem;
					}
					if(mc.thePlayer.getHeldItem() != null && Minecraft.getMinecraft().thePlayer.getHeldItem().getItem() instanceof IItemPickupSound && IItemPickupSound.Variables.playRightClickSound)
					{
						IItemPickupSound.Variables.playRightClickSound = false;
						((IItemPickupSound)Minecraft.getMinecraft().thePlayer.getHeldItem().getItem()).playSelectSound(Minecraft.getMinecraft().thePlayer, Minecraft.getMinecraft().thePlayer.getHeldItem());
					}
					if(mc.thePlayer.getHeldItem() != null && Minecraft.getMinecraft().thePlayer.getHeldItem().getItem() instanceof ItemWeaponMelee && IItemPickupSound.Variables.playRightClickSound)
					{
						IItemPickupSound.Variables.playRightClickSound = false;
						((ItemWeaponMelee)Minecraft.getMinecraft().thePlayer.getHeldItem().getItem()).playSelectSound(Minecraft.getMinecraft().thePlayer, Minecraft.getMinecraft().thePlayer.getHeldItem());
					}
				}
			}
			//note to self: this is to prevent bugged out, client-side potion effects.
			Object[] pes = player.getActivePotionEffects().toArray();
			for(int i = 0; i < pes.length; ++i)
			{
				if(pes[i] instanceof PotionEffect)
				{
					PotionEffect pe = (PotionEffect)pes[i];
					if(pe.getDuration() < 1)
					{
						player.removePotionEffect(pe.getPotionID());
					}
				}
			}
		}
		
		KeyBindings.clientTickEvent(event);
		
		ParticleHandler.update();
	}
	
	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event)
	{
		KeyBindings.onKeyInput(event);
	}
}