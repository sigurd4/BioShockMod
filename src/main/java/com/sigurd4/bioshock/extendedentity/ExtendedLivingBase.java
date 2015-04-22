package com.sigurd4.bioshock.extendedentity;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

import com.sigurd4.bioshock.reference.RefMod;

public class ExtendedLivingBase implements IExtendedEntityProperties, IEntityAdditionalSpawnData
{
	public float fallDamageMultiplier = 0;
	
	private final EntityLivingBase entity;
	
	public ExtendedLivingBase(EntityLivingBase entity)
	{
		this.entity = entity;
	}
	
	/**
	 * Returns ExtendedPlayer properties for player
	 * This method is for convenience only; it will make your code look nicer
	 */
	public static final ExtendedLivingBase get(EntityLivingBase entity)
	{
		return (ExtendedLivingBase)entity.getExtendedProperties(RefMod.MODID);
	}
	
	/**
	 * Used to register these extended properties for the player during
	 * EntityConstructing event
	 * This method is for convenience only; it makes my code look nicer.
	 */
	public static final void register(EntityLivingBase entity)
	{
		entity.registerExtendedProperties(RefMod.MODID, new ExtendedLivingBase(entity));
	}
	
	@Override
	public void loadNBTData(NBTTagCompound compound)
	{
		NBTTagCompound properties = (NBTTagCompound)compound.getTag(RefMod.MODID);
		if(properties != null)
		{
			properties = new NBTTagCompound();
		}
		this.fallDamageMultiplier = properties.getFloat("FallDamageMultiplier");
	}
	
	@Override
	public void saveNBTData(NBTTagCompound compound)
	{
		NBTTagCompound properties = new NBTTagCompound();
		compound.setTag(RefMod.MODID, properties);
		properties.setFloat("FallDamageMultiplier", this.fallDamageMultiplier);
	}
	public void onUpdate()
	{
		if(knockback.containsKey(this.entity) && knockback.get(this.entity) != null)
		{
			bullet2(this.entity);
		}
	
	@Override
	public void init(Entity entity, World world)
	{
	}
	
	private static HashMap<Entity, Double> knockback = new HashMap<Entity, Double>();
	
	public static void bullet1(Entity entity, float f)
	{
		f = 1 - f;
		if(f > 1)
		{
			f = 1;
		}
		if(f < 0)
		{
			f = 0;
		}
		if(entity.hurtResistantTime > 0)
		{
			double kb = 0;
			if(entity instanceof EntityLivingBase)
			{
				kb = ((EntityLivingBase)entity).getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getBaseValue();
				if(kb < 0.9)
				{
					((EntityLivingBase)entity).getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(f);
				}
			}
			entity.velocityChanged = false;
			entity.hurtResistantTime = 0;
			knockback.put(entity, kb);
		}
	}
	
	public static void knockback(Entity entity, float f)
	{
		f = 1 - f;
		if(f > 1)
		{
			f = 1;
		}
		if(f < 0)
		{
			f = 0;
		}
		double kb = 0;
		if(entity instanceof EntityLivingBase)
		{
			kb = ((EntityLivingBase)entity).getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getBaseValue();
			if(kb < 0.9)
			{
				((EntityLivingBase)entity).getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(f);
			}
		}
		knockback.put(entity, kb);
	}
	
	public static void bullet2(Entity entity)
	{
		if(knockback.containsKey(entity) && knockback.get(entity) != null)
		{
			if(entity instanceof EntityLivingBase)
			{
				((EntityLivingBase)entity).getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(knockback.get(entity));
			}
			knockback.remove(entity);
		}
	}
	
	/**
	 * Called by the server when constructing the spawn packet.
	 * Data should be added to the provided stream.
	 *
	 * @param buffer
	 *            The packet data stream
	 */
	@Override
	public void writeSpawnData(ByteBuf buf)
	{
		NBTTagCompound compound = new NBTTagCompound();
		this.saveNBTData(compound);
		ByteBufUtils.writeTag(buf, compound);
	}
	
	/**
	 * Called by the client when it receives a Entity spawn packet.
	 * Data should be read out of the stream in the same way as it was written.
	 *
	 * @param data
	 *            The packet data stream
	 */
	@Override
	public void readSpawnData(ByteBuf buf)
	{
		NBTTagCompound compound = ByteBufUtils.readTag(buf);
		if(compound != null)
		{
			this.loadNBTData(compound);
		}
	}
	
	public static class LootEntry
	{
		public Item item;
		public int stacksize;
		public int damage;
		public int chance;
		
		public LootEntry(Item item, int stacksize, int damage, int chance)
		{
			this.item = item;
			this.stacksize = stacksize;
			this.damage = damage;
			this.chance = chance;
		}
		
		public LootEntry copy()
		{
			return new LootEntry(this.item, this.stacksize, this.damage, this.chance);
		}
		
		public LootEntry getProcessedLoot(Random rand)
		{
			int a = this.stacksize > 1 ? rand.nextInt(this.stacksize) + 1 : 1;
			LootEntry copy = this.copy();
			if(this.item.getMaxDamage() > 0 && this.damage < 0)
			{
				int b = 0;
				for(int i = a; i > 0; i -= this.item.getMaxDamage())
				{
					++b;
					a -= this.item.getMaxDamage();
					if(a <= 0)
					{
						i = a = 0;
					}
				}
				copy.damage = this.item.getMaxDamage() - b;
			}
			copy.stacksize = a;
			return copy;
		}
		
		public EntityItem drop(Entity entity)
		{
			if(!entity.worldObj.isRemote)
			{
				LootEntry copy = this.getProcessedLoot(entity.worldObj.rand);
				return entity.entityDropItem(new ItemStack(copy.item, copy.stacksize, copy.damage), 0);
			}
			return null;
		}
	}
	
	public static boolean canBlindedEntityTarget(EntityMob mob, EntityLivingBase target)
	{
		if(target != null && mob != null && mob.worldObj != null)
		{
			if(!target.isInvisible() && (!target.isSprinting() || mob.worldObj.rand.nextFloat() > 0.1 || Math.sqrt(target.motionX * target.motionX + target.motionY * target.motionY + target.motionZ * target.motionZ) > 20 * mob.worldObj.rand.nextFloat()) && (target.isSneaking() || Math.sqrt(target.motionX * target.motionX + target.motionY * target.motionY + target.motionZ * target.motionZ) > 0.1 * mob.worldObj.rand.nextFloat() || mob.worldObj.rand.nextFloat() > 0.03))
			{
				return true;
			}
		}
		return false;
	}
	
	public static boolean canBlindedEntityTarget(EntityMob mob)
	{
		if(mob.getAttackTarget() != null && mob.getAttackTarget() instanceof EntityLivingBase)
		{
			return canBlindedEntityTarget(mob, mob.getAttackTarget());
		}
		return true;
	}
}
