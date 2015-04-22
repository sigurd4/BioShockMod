package com.sigurd4.bioshock.event;

import java.util.HashMap;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import com.sigurd4.bioshock.extendedentity.ExtendedPlayer;
import com.sigurd4.bioshock.gui.GuiModHud;
import com.sigurd4.bioshock.gui.GuiModHud.RenderOrder;
import com.sigurd4.bioshock.item.IItemPickupSound;
import com.sigurd4.bioshock.item.IItemWeapon;
import com.sigurd4.bioshock.item.ItemWeaponRanged;

@SideOnly(Side.CLIENT)
public class HandlerClient
{
	// minecraftforge events for client only here!
	
	public static HashMap<EntityPlayer, RenderPlayer> playerRenders = new HashMap();
	
	@SubscribeEvent
	public void PlayerUseItemEventStart(PlayerUseItemEvent.Start event)
	{
		if(event.entityPlayer.getHeldItem() != null && event.entityPlayer.getHeldItem().getItem() instanceof IItemPickupSound)
		{
			if(IItemPickupSound.Variables.playRightClickSound)
			{
				((IItemPickupSound)event.entityPlayer.getHeldItem().getItem()).playRightClickSound(event.entityPlayer.getHeldItem(), event.entityPlayer.worldObj, event.entityPlayer);
				IItemPickupSound.Variables.playRightClickSound = false;
			}
		}
	}
	
	@SubscribeEvent
	public void playerRenderEventPost(RenderPlayerEvent.Post event)
	{
		playerRenders.put(event.entityPlayer, event.renderer);
	}
	
	@SubscribeEvent
	public void RenderGameOverlayEvent(RenderGameOverlayEvent event)
	{
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		GuiModHud hud = new GuiModHud(Minecraft.getMinecraft());
		hud.renderGameOverlay(player, event instanceof RenderGameOverlayEvent.Pre ? RenderOrder.PRE : RenderOrder.POST, event.type);
		if(player.getHeldItem() != null)
		{
			ItemStack stack = player.getHeldItem();
			if(stack.getItem() instanceof IItemWeapon && !((IItemWeapon)stack.getItem()).showCrosshair())
			{
				if(event.type == ElementType.CROSSHAIRS && event.isCancelable())
				{
					event.setCanceled(true);
				}
			}
		}
	}
	@SubscribeEvent
	public void PlaySoundEvent(PlaySoundEvent event)
	{
		if(Minecraft.getMinecraft() != null && Minecraft.getMinecraft().theWorld != null)
		{
			EntityPlayer player = Minecraft.getMinecraft().theWorld.getClosestPlayer(event.sound.getXPosF(), event.sound.getYPosF(), event.sound.getZPosF(), 1.7);
			if(player != null)
			{
				ExtendedPlayer props = ExtendedPlayer.get(player);
				if(props != null)
				{
					props.passiveController.playSound(event);
				}
				if(event.result != null && event.sound.getVolume() > event.result.getVolume())
				{
					props = null;
				}
			}
		}
	}
}
