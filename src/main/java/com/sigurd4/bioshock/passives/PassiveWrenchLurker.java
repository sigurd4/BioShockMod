package com.sigurd4.bioshock.passives;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.sigurd4.bioshock.element.Element;

public class PassiveWrenchLurker extends PassiveWrenchJockey
{
	public final float volumeMultiplier;
	
	public PassiveWrenchLurker(String id, String name, Passive[] required, Passive[] incapatible, Type type, float damageMultiplier, float volumeMultiplier)
	{
		super(id, name, required, incapatible, type, damageMultiplier);
		this.volumeMultiplier = volumeMultiplier;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void PlaySoundEvent(PlaySoundEvent event)
	{
		EntityPlayer player = Minecraft.getMinecraft().theWorld.getClosestPlayer(event.sound.getXPosF(), event.sound.getYPosF(), event.sound.getZPosF(), 20);
		if(player != null && event.name != null)
		{
			if(event.name.startsWith("step.") && player.onGround)
			{
				event.result = new PositionedSoundRecord(event.sound.getSoundLocation(), event.sound.getVolume() * this.volumeMultiplier, event.sound.getPitch(), event.sound.getXPosF(), event.sound.getYPosF(), event.sound.getZPosF());
			}
		}
	}
	
	@Override
	public void LivingAttackEvent(LivingHurtEvent event)
	{
		if(event.source.getEntity() != null)
		{
			if(Element.getEntityElement(event.entityLiving, Element.ELECTRICITY) > 100 || event.entityLiving.getAITarget() == null || event.entityLiving.getAITarget() != event.source.getEntity() || event.entityLiving.canEntityBeSeen(event.source.getEntity()))
			{
				super.LivingAttackEvent(event);
			}
		}
	}
}
