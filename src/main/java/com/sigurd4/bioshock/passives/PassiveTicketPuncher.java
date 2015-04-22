package com.sigurd4.bioshock.passives;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.sigurd4.bioshock.extendedentity.ExtendedPlayer;
import com.sigurd4.bioshock.item.ItemWeaponMelee;

public class PassiveTicketPuncher extends Passive
{
	public final float damageMultiplier;
	public final float reachMultiplier;
	public final float shieldUsage;
	
	public PassiveTicketPuncher(String id, String name, Passive[] required, Passive[] incapatible, Type type, float damageMultiplier, float reachMultiplier, float shieldUsage)
	{
		super(id, name, required, incapatible, type);
		this.damageMultiplier = damageMultiplier;
		this.reachMultiplier = reachMultiplier;
		this.shieldUsage = shieldUsage;
	}
	
	@Override
	public void LivingAttackEvent(LivingHurtEvent event)
	{
		if(event.source.getDamageType().equals("player") && ((EntityPlayer)event.source.getEntity()).getHeldItem() != null && ((EntityPlayer)event.source.getEntity()).getHeldItem().getItem() instanceof ItemWeaponMelee)
		{
			ExtendedPlayer props = ExtendedPlayer.get((EntityPlayer)event.source.getEntity());
			if(props.getShields() > 0)
			{
				props.consumeShields(props.getMaxShields() * this.shieldUsage);
				if(props.getShieldsTimer() < 320)
				{
					props.setShieldsTimer(320);
				}
				event.ammount *= this.damageMultiplier;
			}
		}
	}
}
