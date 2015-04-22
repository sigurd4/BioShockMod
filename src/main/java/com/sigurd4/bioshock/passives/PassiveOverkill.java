package com.sigurd4.bioshock.passives;

import java.util.HashMap;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.sigurd4.bioshock.element.Element;

public class PassiveOverkill extends Passive
{
	public final float threshold;
	public final float range;
	public final HashMap<EntityLivingBase, Float> damage = new HashMap<EntityLivingBase, Float>();
	public final HashMap<EntityLivingBase, Integer> ticksExisted = new HashMap<EntityLivingBase, Integer>();
	
	public PassiveOverkill(String id, String name, Passive[] required, Passive[] incapatible, Type type, float threshold, float range)
	{
		super(id, name, required, incapatible, type);
		this.threshold = threshold;
		this.range = range;
	}
	
	@Override
	public void LivingAttackEvent(LivingHurtEvent event)
	{
		if(this.damage.get(event.entityLiving) == null || this.ticksExisted.get(event.entityLiving) == null || event.entityLiving.ticksExisted - this.ticksExisted.get(event.entityLiving) > 10 || event.entityLiving.ticksExisted < this.ticksExisted.get(event.entityLiving))
		{
			float f = (event.entityLiving.ticksExisted - this.ticksExisted.get(event.entityLiving)) / 10;
			this.damage.put(event.entityLiving, this.damage.get(event.entityLiving) * f + event.ammount);
			this.ticksExisted.put(event.entityLiving, event.entityLiving.ticksExisted);
		}
	}
	
	@Override
	public void killEntity(LivingHurtEvent event)
	{
		if(this.damage.get(event.entityLiving) != null && this.damage.get(event.entityLiving) >= this.threshold)
		{
			List entities = event.entity.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(event.entity.posX - this.range, event.entity.posY - this.range, event.entity.posZ - this.range, event.entity.posX + this.range, event.entity.posY + this.range, event.entity.posZ + this.range));
			for(int i = 0; i < entities.size(); ++i)
			{
				if(entities.get(i) instanceof EntityLivingBase)
				{
					EntityLivingBase entity = (EntityLivingBase)entities.get(i);
					if(entity != event.entity && entity != event.source.getEntity() && event.entity.getDistanceToEntity(entity) <= this.range)
					{
						Element.addToEntityElement(event.entityLiving, Element.ELECTRICITY, 500);
					}
				}
			}
		}
	}
}
