package com.sigurd4.bioshock.passives;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.sigurd4.bioshock.element.Element;
import com.sigurd4.bioshock.item.ItemWeaponMelee;

public class PassiveBurningHalo extends Passive
{
	public final float chance;
	public final int fire;
	public final Element element;
	
	public PassiveBurningHalo(String id, String name, Passive[] required, Passive[] incapatible, Type type, float chance, int fire, Element element)
	{
		super(id, name, required, incapatible, type);
		this.chance = chance;
		this.fire = fire;
		this.element = element;
	}
	
	@Override
	public void LivingAttackEvent(LivingHurtEvent event)
	{
		if(event.source.getDamageType().equals("player") && ((EntityPlayer)event.source.getEntity()).getHeldItem() != null && ((EntityPlayer)event.source.getEntity()).getHeldItem().getItem() instanceof ItemWeaponMelee)
		{
			if(event.entity.worldObj.rand.nextFloat() <= this.chance)
			{
				Element.addTowardsEntityElement(event.entityLiving, this.element, this.fire * 20 * 8, this.fire * 20 * 4);
			}
		}
	}
}
