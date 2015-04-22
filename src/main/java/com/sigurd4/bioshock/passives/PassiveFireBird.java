package com.sigurd4.bioshock.passives;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;

import com.sigurd4.bioshock.element.Element;

public class PassiveFireBird extends Passive
{
	public final float seconds;
	public final float range;
	
	public PassiveFireBird(String id, String name, Passive[] required, Passive[] incapatible, Type type, float seconds, float range)
	{
		super(id, name, required, incapatible, type);
		this.seconds = seconds;
		this.range = range;
	}
	
	@Override
	public void landFromSkyline(EntityPlayer player)
	{
		List entities = player.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(player.posX - this.range, player.posY - this.range, player.posZ - this.range, player.posX + this.range, player.posY + this.range, player.posZ + this.range));
		for(int i = 0; i < entities.size(); ++i)
		{
			if(entities.get(i) instanceof EntityLivingBase)
			{
				EntityLivingBase entity = (EntityLivingBase)entities.get(i);
				if(entity != player && player.getDistanceToEntity(entity) <= this.range)
				{
					this.action(player, entity);
				}
			}
		}
	}
	
	protected void action(EntityPlayer player, EntityLivingBase victim)
	{
		Element.addTowardsEntityElement(victim, Element.FIRE, (int)(this.seconds * 20 * 8), (int)(this.seconds * 20 * 4));
	}
}
