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
	public ArrayList<TrapBolt> traps = new ArrayList<TrapBolt>();
	public ArrayList<Item> items = new ArrayList<Item>();
	public float fallDamageMultiplier = 0;
	
	private final EntityLivingBase entity;
	
	public ExtendedLivingBase(EntityLivingBase entity)
	{
		this.entity = entity;
		this.traps.clear();
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
		
		this.traps.clear();
		if(properties != null)
		{
			properties = new NBTTagCompound();
		}
		NBTTagList list = (NBTTagList)properties.getTag("Traps");
		for(int i = 0; list != null && i < list.tagCount(); ++i)
		{
			NBTTagCompound trapCompound = list.getCompoundTagAt(i);
			UUID shootingEntityUUID = null;
			if(trapCompound.hasKey("ShootingEntityUUIDMost", 4) && trapCompound.hasKey("ShootingEntityUUIDLeast", 4))
			{
				shootingEntityUUID = new UUID(trapCompound.getLong("ShootingEntityUUIDMost"), trapCompound.getLong("ShootingEntityUUIDLeast"));
			}
			if(trapCompound.hasKey("ShootingEntityPersistentIDMSB") && trapCompound.hasKey("ShootingEntityPersistentIDLSB"))
			{
				shootingEntityUUID = new UUID(trapCompound.getLong("ShootingEntityPersistentIDMSB"), trapCompound.getLong("ShootingEntityPersistentIDLSB"));
			}
			Entity shootingEntity = null;
			for(int i2 = 0; i2 < this.entity.worldObj.loadedEntityList.size(); ++i2)
			{
				Object entity2 = this.entity.worldObj.loadedEntityList.get(i2);
				
				if(entity2 instanceof Entity && shootingEntityUUID.equals(((Entity)entity2).getUniqueID()))
				{
					shootingEntity = (Entity)entity2;
					break;
				}
			}
			if(shootingEntity == null)
			{
				NBTTagCompound entityCompound = trapCompound.getCompoundTag("ShootingEntity");
				if(entityCompound != null)
				{
					Entity entity2 = EntityList.createEntityFromNBT(entityCompound, this.entity.worldObj);
					if(entity2 != null)
					{
						shootingEntity = entity2;
					}
				}
			}
			int stackID = trapCompound.getInteger("ItemStackID");
			int trapTimer = trapCompound.getInteger("TrapTimer");
			double x = trapCompound.getDouble("MotionX");
			double y = trapCompound.getDouble("MotionY");
			double z = trapCompound.getDouble("MotionZ");
			if(shootingEntity != null)
			{
				TrapBolt trap = new TrapBolt(shootingEntity, x, y, z, trapTimer, stackID);
				this.traps.add(trap);
			}
		}
		this.fallDamageMultiplier = properties.getFloat("FallDamageMultiplier");
	}
	
	@Override
	public void saveNBTData(NBTTagCompound compound)
	{
		NBTTagCompound properties = new NBTTagCompound();
		compound.setTag(RefMod.MODID, properties);
		NBTTagList list = new NBTTagList();
		for(int i = 0; i < this.traps.size(); ++i)
		{
			NBTTagCompound trapCompound = new NBTTagCompound();
			NBTTagCompound entityCompound = new NBTTagCompound();
			this.traps.get(i).shootingEntity.writeToNBT(entityCompound);
			trapCompound.setTag("ShootingEntity", entityCompound);
			trapCompound.setInteger("ItemStackID", this.traps.get(i).item);
			trapCompound.setInteger("TrapTimer", this.traps.get(i).trapTimer);
			trapCompound.setDouble("MotionX", this.traps.get(i).trapMotionX);
			trapCompound.setDouble("MotionY", this.traps.get(i).trapMotionY);
			trapCompound.setDouble("MotionZ", this.traps.get(i).trapMotionZ);
			list.appendTag(trapCompound);
		}
		properties.setTag("Traps", list);
		properties.setFloat("FallDamageMultiplier", this.fallDamageMultiplier);
	}
	
	public void noTrap(int i)
	{
		if(this.traps.get(i).item > 0 && this.traps.get(i).item < this.items.size())
		{
			this.dropItem(this.traps.get(i).item);
		}
		this.traps.remove(i);
	}
	
	public void dropItems()
	{
		for(int i = 0; i < this.items.size(); ++i)
		{
			this.dropItem(i);
		}
	}
	
	public void dropItem(int i)
	{
		if(!this.entity.worldObj.isRemote)
		{
			this.entity.dropItem(this.items.get(i), 1);
		}
		this.items.remove(i);
	}
	
	public void onUpdate()
	{
		if(knockback.containsKey(this.entity) && knockback.get(this.entity) != null)
		{
			bullet2(this.entity);
		}
		for(int i = 0; i < this.traps.size(); ++i)
		{
			if(this.traps.get(i).trapTimer > 0)
			{
				--this.traps.get(i).trapTimer;
			}
			else if(this.traps.get(i).trapTimer == 0)
			{
				this.fireTrap(i);
				this.noTrap(i);
			}
		}
	}
	
	public void fireTrap(int i)
	{
		this.entity.worldObj.playSoundAtEntity(this.entity, RefMod.MODID + ":" + "item.weapon.crossbow.fire", 0.2F, 1.2F + this.entity.worldObj.rand.nextFloat() * 0.3F);
		
		EntityElectricTrap bolt = new EntityElectricTrap(this.entity.worldObj, this.traps.get(i).shootingEntity, this.entity, this.traps.get(i).trapMotionX, this.traps.get(i).trapMotionY - 0.1, this.traps.get(i).trapMotionZ, 1F, true);
		bolt.damageName = "crossbow_electric_bolt";
		bolt.silent = false;
		if(this.traps.get(i).item > 0 && this.traps.get(i).item < this.items.size())
		{
			this.items.remove(this.traps.get(i).item);
		}
		
		if(!this.entity.worldObj.isRemote)
		{
			this.entity.worldObj.spawnEntityInWorld(bolt);
		}
	}
	
	public void hitEntityWithTrapBolt(EntityCrossbowBolt bolt, boolean b)
	{
		TrapBolt trap = new TrapBolt(bolt.shootingEntity, bolt.motionX, bolt.motionY, bolt.motionZ, 30, b ? this.items.size() - 1 : -1);
		this.traps.add(trap);
		if(this.entity instanceof EntityLivingBase && (this.entity.getHealth() <= 0 || this.entity.isDead))
		{
			this.fireTrap(this.traps.size() - 1);
			this.noTrap(this.traps.size() - 1);
		}
	}
	
	public boolean hitEntityWithBolt(EntityCrossbowBolt bolt)
	{
		if(!bolt.getPreventPickup())
		{
			this.items.add(Item.getItemById(bolt.getItem()));
			return true;
		}
		return false;
	}
	
	protected class TrapBolt
	{
		public final double trapMotionX;
		public final double trapMotionY;
		public final double trapMotionZ;
		public final Entity shootingEntity;
		public final int item;
		public int trapTimer;
		
		protected TrapBolt(Entity shootingEntity, double trapMotionX, double trapMotionY, double trapMotionZ, int trapTimer, int item)
		{
			this.shootingEntity = shootingEntity;
			this.trapMotionX = trapMotionX;
			this.trapMotionY = trapMotionY;
			this.trapMotionZ = trapMotionZ;
			this.item = item;
			this.trapTimer = 30;
		}
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