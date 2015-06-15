package com.sigurd4.bioshock.event;

import net.minecraft.client.Minecraft;
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

import com.sigurd4.bioshock.Stuff;
import com.sigurd4.bioshock.extendedentity.ExtendedPlayer;
import com.sigurd4.bioshock.gui.GuiModHud;
import com.sigurd4.bioshock.gui.GuiModHud.RenderOrder;
import com.sigurd4.bioshock.item.IItemPickupSound;
import com.sigurd4.bioshock.item.IItemWeapon;
import com.sigurd4.bioshock.item.ItemArmorDivingSuit;
import com.sigurd4.bioshock.item.ItemWeaponRanged;

@SideOnly(Side.CLIENT)
public class HandlerClient
{
	// minecraftforge events for client only here!
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
	public void playerRenderEvent(RenderPlayerEvent.Specials event)
	{
		EntityPlayer player = (EntityPlayer)event.entity;
		if(player.getHeldItem() != null)
		{
			if(player.getHeldItem().getItem() instanceof ItemWeaponRanged && ItemWeaponRanged.heldUp(player.getHeldItem()))
			{
				event.renderer.getPlayerModel().aimedBow = true;
			}
		}
		Stuff.Render.setPlayerRenderer(event.entityPlayer, event.renderer);
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
	public void FOVUpdateEvent(FOVUpdateEvent event)
	{
		if(event.entity instanceof EntityPlayer)
		{
			EntityPlayer player = event.entity;
			ExtendedPlayer props = ExtendedPlayer.get(player);
			if(player.getHeldItem() != null)
			{
				ItemStack stack = player.getHeldItem();
				if(stack.getItem() instanceof ItemWeaponRanged && ((ItemWeaponRanged)stack.getItem()).isHeldUp(stack) && props.isZoomHeldDown && (Minecraft.getMinecraft().gameSettings.thirdPersonView == 0 || Minecraft.getMinecraft().gameSettings.thirdPersonView == 1))
				{
					event.newfov = event.fov - ((ItemWeaponRanged)stack.getItem()).getZoom(player.worldObj, player, stack);
				}
			}
		}
	}
	
	@SubscribeEvent
	public void fogDensity(EntityViewRenderEvent.FogDensity event)
	{
		/*EntityCrossbowBolt bolt = EntityCrossbowBolt.getClosestGasCloud(event.entity);
		if(bolt != null)
		{
			float x = (bolt.getDistanceToEntity(event.entity) - 7) * 40;
			if(x < 0)
			{
				x = 0;
			}
			x += 3F;
			GL11.glFogf(GL11.GL_FOG_START, 1);
			if(event.density > x)
			{
				event.density = x;
			}
			event.setCanceled(true);
			GL11.glFogf(GL11.GL_FOG_END, 1 + x);
		}*/
	}
	
	@SubscribeEvent
	public void fogColors(EntityViewRenderEvent.FogColors event)
	{
		/*EntityCrossbowBolt bolt = EntityCrossbowBolt.getClosestGasCloud(event.entity);
		if(bolt != null && bolt.getDistanceToEntity(event.entity) < 8.7)
		{
			event.blue = 230;
			event.red = 230;
			event.green = 230;
		}*/
	}
	
	@SubscribeEvent
	public void livingHurtEvent(LivingHurtEvent event)
	{
		if(event.source == DamageSource.fall)
		{
			if(event.entity instanceof EntityLivingBase)
			{
				if(((EntityLivingBase)event.entity).getEquipmentInSlot(1) != null)
				{
					if(((EntityLivingBase)event.entity).getEquipmentInSlot(1).getItem() instanceof ItemArmorDivingSuit)
					{
						event.entity.worldObj.playSoundAtEntity(event.entity, "step.stone", 0.4F + 0.6F / 8, 2);
						event.entity.worldObj.playSoundAtEntity(event.entity, "random.anvil_land", 0.002F * (0.02F + 0.6F / 8), 4.1F);
					}
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
