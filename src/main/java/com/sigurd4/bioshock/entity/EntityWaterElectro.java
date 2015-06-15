package com.sigurd4.bioshock.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.sigurd4.bioshock.Stuff;
import com.sigurd4.bioshock.element.Element;
import com.sigurd4.bioshock.itemtags.ItemTagFloat;
import com.sigurd4.bioshock.itemtags.ItemTagInteger;
import com.sigurd4.bioshock.plasmids.IPlasmidProjectile;
import com.sigurd4.bioshock.plasmids.Plasmid;

public class EntityWaterElectro extends Entity implements IEntityPlasmid
{
	public static final ItemTagInteger PLASMID = new ItemTagInteger("Plasmid", -1, -1, Plasmid.getPlasmids().size() - 1, false);
	public static final ItemTagInteger MAX_TIME = new ItemTagInteger("MaxTime", 10, 0, Integer.MAX_VALUE, false);
	public static final ItemTagInteger SPAWN_LIMITER = new ItemTagInteger("SpawnLimiter", 0, 0, Integer.MAX_VALUE, false);
	public static final ItemTagFloat DAMAGE = new ItemTagFloat("Damage", 2F, 0F, Float.MAX_VALUE, false);
	
	public static HashMap<BlockPos, Electro> electrosAll = new HashMap<BlockPos, Electro>();
	public HashMap<BlockPos, Electro> electros = new HashMap<BlockPos, Electro>();
	public int maxTime = MAX_TIME.getDefault();
	public int spawnLimiter = SPAWN_LIMITER.getDefault();
	public EntityLivingBase thrower;
	public float damage = DAMAGE.getDefault();
	public Plasmid plasmid;
	
	protected class Electro
	{
		public final World worldObj;
		public int ticksExisted;
		public final BlockPos coords;
		private final EntityWaterElectro entity;
		
		protected Electro(World world, int x, int y, int z, EntityWaterElectro entity)
		{
			this.worldObj = world;
			this.coords = new BlockPos(x, y, z);
			this.ticksExisted = 0;
			this.entity = entity;
		}
		
		protected void onUpdate()
		{
			if(electrosAll.get(this.coords) != null && this.entity != null)
			{
				if(electrosAll.get(this.coords) != this)
				{
					if(electrosAll.get(this.coords).entity == null || this.entity.ticksExisted < electrosAll.get(this.coords).entity.ticksExisted)
					{
						if(electrosAll.get(this.coords).entity != null)
						{
							electrosAll.get(this.coords).setDead();
						}
						electrosAll.put(this.coords, this);
					}
					else
					{
						this.setDead();
					}
				}
			}
			else
			{
				electrosAll.put(this.coords, this);
			}
			if(EntityWaterElectro.this.rand.nextFloat() < 1 / 2)
			{
				this.worldObj.playSoundEffect(this.coords.getX(), this.coords.getY(), this.coords.getZ(), "mob.enderman.idle", 4.0F, 0.6F);
				this.worldObj.playSoundEffect(this.coords.getX(), this.coords.getY(), this.coords.getZ(), "mob.silverfish.say", 4.0F, 0.6F);
			}
			this.worldObj.spawnParticle(EnumParticleTypes.REDSTONE, true, this.coords.getX() + Stuff.Randomization.r(0.5) + 0.5, this.coords.getY() + Stuff.Randomization.r(0.5) + 0.5, this.coords.getZ() + Stuff.Randomization.r(0.5) + 0.5, 0.4, 0.9, 1.0);
			if(this.ticksExisted == 1)
			{
				this.spread();
			}
			List<EntityLivingBase> entities = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(this.coords.getX() - 0.5, this.coords.getY() - 0.5, this.coords.getZ() - 0.5, this.coords.getX() + 1.5, this.coords.getY() + 1.5, this.coords.getZ() + 1.5));
			for(int i = 0; i < entities.size(); ++i)
			{
				EntityLivingBase target = entities.get(i);
				if(target.isInWater() && !target.isDead)
				{
					this.damageEntity(target);
				}
			}
			++this.ticksExisted;
		}
		
		public void setDead()
		{
			electrosAll.remove(this.coords);
			this.entity.electros.remove(this.coords);
		}
		
		protected void spread()
		{
			if(this.entity.electros.size() < 200)
			{
				for(int x = -1; x <= 1; ++x)
				{
					for(int y = -1; y <= 1; ++y)
					{
						for(int z = -1; z <= 1; ++z)
						{
							if(x == 0 && y == 0 || x == 0 && z == 0 || y == 0 && z == 0)
							{
								BlockPos c = new BlockPos(x, y, z).add(this.coords);
								if(electrosAll.get(c) == null || electrosAll.get(c).coords != null && !electrosAll.get(c).coords.equals(c))
								{
									if(this.worldObj.getBlockState(c).getBlock().getMaterial() == Material.water)
									{
										this.entity.electros.put(c, new Electro(this.worldObj, c.getX() + x, c.getY() + y, c.getZ() + z, this.entity));
									}
								}
							}
						}
					}
				}
			}
		}
		
		protected void damageEntity(EntityLivingBase entity)
		{
			Random rand = new Random();
			int a = rand.nextInt(40 + 1) + 30;
			double x = entity.posX;
			double y = entity.posX;
			double z = entity.posX;
			for(int i = 0; i < 5; ++i)
			{
				this.worldObj.spawnParticle(EnumParticleTypes.REDSTONE, true, x + Stuff.Randomization.r(3), y + Stuff.Randomization.r(3), z + Stuff.Randomization.r(3), 0.4, 0.9, 1.0);
			}
			this.worldObj.playSoundEffect(x + Stuff.Randomization.r(2), y + Stuff.Randomization.r(2), z + Stuff.Randomization.r(2), "mob.enderman.idle", 7.0F, 0.9F);
			this.worldObj.playSoundEffect(x + Stuff.Randomization.r(2), y + Stuff.Randomization.r(2), z + Stuff.Randomization.r(2), "mob.silverfish.say", 7.0F, 0.9F);
			
			if(!this.worldObj.isRemote)
			{
				DamageSource damagesource = Element.electricity(this.entity.thrower);
				if(this.entity.plasmid != null && this.entity.plasmid instanceof IPlasmidProjectile)
				{
					damagesource = Plasmid.getDamageSource(this.entity.thrower, this.entity.plasmid);
				}
				entity.attackEntityFrom(damagesource, EntityWaterElectro.this.damage + rand.nextFloat() * 5);
				Element.setEntityElement(entity, Element.ELECTRICITY, 500, false);
				entity.addPotionEffect(new PotionEffect(Potion.weakness.id, a, 0));
				entity.addPotionEffect(new PotionEffect(Potion.blindness.id, a - (int)(rand.nextFloat() * a / 1.8), 0, true, false));
				entity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, a, 8, true, false));
			}
		}
	}
	
	public EntityWaterElectro(World world)
	{
		super(world);
		this.setSize(0.95F, 0.95F);
		this.maxTime = 10;
		this.thrower = null;
		this.damage = 2F;
		this.plasmid = null;
	}
	
	public <Plsmd extends Plasmid & IPlasmidProjectile> EntityWaterElectro(World world, int time, Entity thrower, float damage, Plasmid plasmid)
	{
		this(world);
		this.maxTime = time;
		this.thrower = (EntityLivingBase)thrower;
		this.damage = damage;
		this.plasmid = plasmid;
	}
	
	@Override
	public void entityInit()
	{
		
	}
	
	@Override
	public void onEntityUpdate()
	{
		super.onEntityUpdate();
		if(this.electros.size() <= 0)
		{
			this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "mob.enderman.idle", 4.0F, 0.6F);
			this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "mob.silverfish.say", 4.0F, 0.6F);
			this.electros.put(new BlockPos((int)Math.floor(this.posX), (int)Math.floor(this.posY), (int)Math.floor(this.posZ)), new Electro(this.worldObj, (int)Math.floor(this.posX), (int)Math.floor(this.posY), (int)Math.floor(this.posZ), this));
		}
		if(this.ticksExisted > this.maxTime)
		{
			this.setDead();
		}
		Object[] keys = {};
		try
		{
			keys = this.electros.keySet().toArray();
			for(int i = 0; i < keys.length; ++i)
			{
				if(keys[i] instanceof BlockPos && this.electros.get(keys[i]) != null)
				{
					this.electros.get(keys[i]).onUpdate();
				}
			}
		}
		catch(java.util.ConcurrentModificationException exception)
		{
			
		}
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		this.setPlasmidFromIndex(PLASMID.get(compound, true));
		this.maxTime = MAX_TIME.get(compound, true);
		this.spawnLimiter = SPAWN_LIMITER.get(compound, true);
		this.damage = DAMAGE.get(compound, true);
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		PLASMID.set(compound, Plasmid.getIndex(this.plasmid));
		MAX_TIME.set(compound, this.maxTime);
		SPAWN_LIMITER.set(compound, this.spawnLimiter);
		DAMAGE.set(compound, this.damage);
	}
	
	/**
	 * returns true if the entity provided in the argument can be seen.
	 * (Raytrace)
	 */
	public boolean canEntityBeSeen(Entity entity)
	{
		return this.worldObj.rayTraceBlocks(this.getPositionEyes(0), entity.getPositionEyes(0)) == null;
	}
	
	/**
	 * Checks if the entity is in range to render by using the past in distance
	 * and comparing it to its average edge
	 * length * 64 * renderDistanceWeight Args: distance
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double p_70112_1_)
	{
		return false;
	}
	
	@Override
	public void setDead()
	{
		this.electros.clear();
		Object[] keys = {};
		try
		{
			keys = electrosAll.keySet().toArray();
		}
		catch(java.util.ConcurrentModificationException exception)
		{
		}
		for(int i = 0; i < keys.length; ++i)
		{
			if(keys[i] instanceof BlockPos && electrosAll.get(keys[i]) != null)
			{
				if(electrosAll.get(keys[i]).entity == this)
				{
					electrosAll.get(keys[i]).setDead();
				}
			}
		}
		this.electros.clear();
		super.setDead();
	}
	
	@Override
	public Plasmid getPlasmid()
	{
		return this.plasmid;
	}
	
	private void setPlasmidFromIndex(int index)
	{
		if(index >= 0 && index < Plasmid.getPlasmids().size())
		{
			Plasmid plasmid2 = Plasmid.getPlasmids().get(index);
			if(plasmid2 != null && plasmid2 instanceof IPlasmidProjectile)
			{
				this.plasmid = plasmid2;
			}
		}
	}
}
