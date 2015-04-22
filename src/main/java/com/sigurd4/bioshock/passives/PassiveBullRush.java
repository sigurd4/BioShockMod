package com.sigurd4.bioshock.passives;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.sigurd4.bioshock.item.ItemWeaponMelee;

public class PassiveBullRush extends Passive
{
	public final float knockbackMultiplier;
	
	public PassiveBullRush(String id, String name, Passive[] required, Passive[] incapatible, Type type, float knockback)
	{
		super(id, name, required, incapatible, type);
		this.knockbackMultiplier = knockback;
	}
	
	@Override
	public void LivingAttackEvent(LivingHurtEvent event)
	{
		if(event.source.getDamageType().equals("player") && ((EntityPlayer)event.source.getEntity()).getHeldItem() != null && ((EntityPlayer)event.source.getEntity()).getHeldItem().getItem() instanceof ItemWeaponMelee)
		{
			EntityPlayer player = (EntityPlayer)event.source.getEntity();
			double d1 = player.posX - event.entityLiving.posX;
			double d0;
			
			for(d0 = player.posZ - event.entityLiving.posZ; d1 * d1 + d0 * d0 < 1.0E-4D; d0 = (Math.random() - Math.random()) * 0.01D)
			{
				d1 = (Math.random() - Math.random()) * 0.01D;
			}
			
			event.entityLiving.attackedAtYaw = (float)(Math.atan2(d0, d1) * 180.0D / Math.PI) - event.entityLiving.rotationYaw;
			float amount = event.ammount * (this.knockbackMultiplier - 1);
			if(amount < 0)
			{
				amount = 0;
			}
			int i = (int)Math.floor(this.knockbackMultiplier - 1);
			amount /= i;
			for(int i2 = 0; i2 < i; ++i2)
			{
				event.entityLiving.knockBack(player, amount, d1, d0);
			}
		}
	}
}
