package com.sigurd4.bioshock.entity.monster;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.sigurd4.bioshock.Stuff;
import com.sigurd4.bioshock.element.Element;

public class EntityCrowBird extends EntityFlyingMonster
{
	public Entity target;
	public EntityLivingBase thrower;
	public String throwerName;
	
	public EntityCrowBird(World world, EntityLivingBase thrower)
	{
		this(world);
		this.thrower = thrower;
	}
	
	public EntityCrowBird(World world)
	{
		super(world);
		this.setSize(0.5F, 0.9F);
		this.target = null;
	}
	
	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onEntityUpdate()
	{
		if(this.ticksExisted == 800)
		{
			this.setDead();
		}
		this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, true, this.lastTickPosX + Stuff.Randomization.r(0.01), this.lastTickPosY + Stuff.Randomization.r(0.01) + 0.2, this.lastTickPosZ + Stuff.Randomization.r(0.01), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1));
		for(int i = 0; i < 2; ++i)
		{
			this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, true, this.lastTickPosX + Stuff.Randomization.r(0.01), this.lastTickPosY + Stuff.Randomization.r(0.01) + 0.2, this.lastTickPosZ + Stuff.Randomization.r(0.01), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1));
		}
		
		if(this.rand.nextBoolean())
		{
			this.damageRandomNearbyEntity(this.posX, this.posY, this.posZ, (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue());
		}
		
		super.onEntityUpdate();
	}
	
	public void damageRandomNearbyEntity(double x, double y, double z, float damage)
	{
		List<Entity> entities = Stuff.EntitiesInArea.getEntitiesWithinEllipse(this.worldObj, this.getPositionVector(), new Vec3(50, 10, 50), new Vec3(50, 30, 50));
		Entity entity;
		for(int i = 0; i < entities.size(); ++i)
		{
			if(entities.get(i) != null && (entities.get(i) instanceof EntityCrowBird || entities.get(i) == this.getThrower()))
			{
				entities.remove(i);
				--i;
			}
		}
		if(entities.size() > 1)
		{
			entity = entities.get(this.rand.nextInt(entities.size() - 1 + 1));
			for(int i = 1; i < entities.size(); ++i)
			{
				double d1 = 0;
				double d2 = 0;
				if(true)
				{
					double dx = x - entities.get(i).posX;
					if(dx < 0)
					{
						dx *= -1;
					}
					double dy = y - entities.get(i).posY;
					if(dy < 0)
					{
						dy *= -1;
					}
					double dz = z - entities.get(i).posZ;
					if(dz < 0)
					{
						dz *= -1;
					}
					d1 = Math.sqrt(dx * dx + dy * dy + dz * dz);
				}
				if(true)
				{
					double dx = x - entity.posX;
					if(dx < 0)
					{
						dx *= -1;
					}
					double dy = y - entity.posY;
					if(dy < 0)
					{
						dy *= -1;
					}
					double dz = z - entity.posZ;
					if(dz < 0)
					{
						dz *= -1;
					}
					d2 = Math.sqrt(dx * dx + dy * dy + dz * dz);
				}
				if(d1 < d2)
				{
					int crows = 0;
					List<Entity> entities2 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this.getThrower(), new AxisAlignedBB(entity.posX - 1, entity.posY - 1, entity.posZ - 1, entity.posX + 1, entity.posY + 1 + entity.height, entity.posZ + 1));
					for(int i2 = 0; i2 < entities2.size(); ++i2)
					{
						if(entities2.get(i2) != null && entities2.get(i2) instanceof EntityCrowBird)
						{
							++crows;
						}
					}
					if(crows <= 1)
					{
						entity = entities.get(i);
					}
				}
			}
		}
		else if(entities.size() > 0)
		{
			entity = entities.get(entities.size() - 1);
		}
		else
		{
			entity = this;
			this.ticksExisted += 2;
		}
		boolean flag = false;
		if(entity instanceof EntityPlayer)
		{
			if(((EntityPlayer)entity).equals(this.getThrower()) || ((EntityPlayer)entity).getName() == this.throwerName)
			{
				flag = true;
			}
		}
		double dx = x - entity.posX;
		if(dx < 0)
		{
			dx *= -1;
		}
		double dy = y - entity.posY;
		if(dy < 0)
		{
			dy *= -1;
		}
		double dz = z - entity.posZ;
		if(dz < 0)
		{
			dz *= -1;
		}
		if(Math.sqrt(dx * dx + dy * dy + dz * dz) >= 4)
		{
			float f = 0.4F;
			if(entity.posX > x)
			{
				this.posX += f;
			}
			else if(entity.posX < x)
			{
				this.posX -= f;
			}
			if(entity.posY > y)
			{
				this.posY += f;
			}
			else if(entity.posY < y)
			{
				this.posY -= f;
			}
			if(entity.posZ > z)
			{
				this.posZ += f;
			}
			else if(entity.posZ < z)
			{
				this.posZ -= f;
			}
		}
		if(Math.sqrt(dx * dx + dy * dy + dz * dz) < 4 && entities.size() > 0 && !(entity instanceof EntityCrowBird) && entity instanceof EntityLivingBase && !flag)
		{
			int crows = 0;
			List<Entity> entities2 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this.getThrower(), new AxisAlignedBB(entity.posX - 1, entity.posY - 1, entity.posZ - 1, entity.posX + 1, entity.posY + 1 + entity.height, entity.posZ + 1));
			for(int i = 0; i < entities2.size(); ++i)
			{
				if(entities2.get(i) != null && entities2.get(i) instanceof EntityCrowBird)
				{
					++crows;
				}
			}
			if(crows <= this.rand.nextInt(3 + 1) + 1)
			{
				this.setPosition(entity.posX, entity.posY + entity.getEyeHeight() * 2 / 3, entity.posZ);
				if(!this.worldObj.isRemote)
				{
					DamageSource damagesource = Element.murderOfCrows(this.getThrower());
					entity.attackEntityFrom(damagesource, 1F);
					if(entity instanceof EntityLiving && !(entity instanceof EntityCreeper))
					{
						((EntityLiving)entity).setAttackTarget(this);
					}
					((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.weakness.id, 240, 2));
					((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.confusion.id, 60, 0, true, false));
					if(entity instanceof EntityPlayer)
					{
						((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.blindness.id, 60, 0));
					}
					((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 60, 2, true, false));
					((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.hunger.id, 120, 3, true, false));
					((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 60, 1, true, false));
					((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.poison.id, 30, 0, true, false));
				}
				if(this.rand.nextBoolean())
				{
					++this.ticksExisted;
				}
				for(int i2 = 0; i2 < 2 && (i2 < 1 || !(entity instanceof EntityPlayer)); ++i2)
				{
					for(int i = 0; i < 4; ++i)
					{
						this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, true, entity.posX + Stuff.Randomization.r(0.3), entity.posY + Stuff.Randomization.r(0.3), entity.posZ + Stuff.Randomization.r(0.3), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1) + 0.3, Stuff.Randomization.r(0.1));
					}
					for(int i = 0; i < 4; ++i)
					{
						this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, true, entity.posX + Stuff.Randomization.r(0.3), entity.posY + Stuff.Randomization.r(0.3), entity.posZ + Stuff.Randomization.r(0.3), Stuff.Randomization.r(0.2), Stuff.Randomization.r(0.2) + 0.1, Stuff.Randomization.r(0.2));
					}
					for(int i = 0; i < 6; ++i)
					{
						this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, true, entity.posX + Stuff.Randomization.r(0.01), entity.posY + Stuff.Randomization.r(0.01), entity.posZ + Stuff.Randomization.r(0.01), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1));
					}
				}
			}
		}
	}
	
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		this.motionY *= 0.6000000238418579D;
	}
	
	/**
	 * Returns the volume for the sounds this mob makes.
	 */
	@Override
	protected float getSoundVolume()
	{
		return 0.2F;
	}
	
	/**
	 * Gets the pitch of living sounds in living entities.
	 */
	@Override
	protected float getSoundPitch()
	{
		return super.getSoundPitch() * 0.2F;
	}
	
	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound()
	{
		return "mob.bat.idle";
	}
	
	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound()
	{
		return "mob.bat.hurt";
	}
	
	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound()
	{
		return "mob.bat.death";
	}
	
	@Override
	protected void collideWithEntity(Entity entity)
	{
		DamageSource damagesource = Element.murderOfCrows(this.getThrower());
		entity.attackEntityFrom(damagesource, (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue());
		for(int i = 0; i < 4; ++i)
		{
			this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, true, entity.posX + Stuff.Randomization.r(0.3), entity.posY + Stuff.Randomization.r(0.3), entity.posZ + Stuff.Randomization.r(0.3), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1) + 0.3, Stuff.Randomization.r(0.1));
		}
		for(int i = 0; i < 4; ++i)
		{
			this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, true, entity.posX + Stuff.Randomization.r(0.3), entity.posY + Stuff.Randomization.r(0.3), entity.posZ + Stuff.Randomization.r(0.3), Stuff.Randomization.r(0.2), Stuff.Randomization.r(0.2) + 0.1, Stuff.Randomization.r(0.2));
		}
		for(int i = 0; i < 6; ++i)
		{
			this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, true, entity.posX + Stuff.Randomization.r(0.01), entity.posY + Stuff.Randomization.r(0.01), entity.posZ + Stuff.Randomization.r(0.01), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1));
		}
	}
	
	@Override
	protected void collideWithNearbyEntities()
	{
	}
	
	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(0.2);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1.0D);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(100.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(5.0);
	}
	
	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they
	 * walk on. used for spiders and wolves to
	 * prevent them from trampling crops
	 */
	@Override
	protected boolean canTriggerWalking()
	{
		return false;
	}
	
	/**
	 * Called when the mob is falling. Calculates and applies fall damage.
	 */
	@Override
	public void fall(float distance, float damageMultiplier)
	{
	}
	
	/**
	 * Takes in the distance the entity has fallen this tick and whether its on
	 * the ground to update the fall distance
	 * and deal fall damage if landing on the ground. Args:
	 * distanceFallenThisTick, onGround
	 */
	@Override
	protected void func_180433_a(double p_180433_1_, boolean p_180433_3_, Block p_180433_4_, BlockPos p_180433_5_)
	{
	}
	
	/**
	 * Return whether this entity should NOT trigger a pressure plate or a
	 * tripwire.
	 */
	@Override
	public boolean doesEntityNotTriggerPressurePlate()
	{
		return true;
	}
	
	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);
		this.throwerName = par1NBTTagCompound.getString("Owner");
	}
	
	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setString("Owner", this.throwerName);
	}
	
	@Override
	public void setDead()
	{
		super.setDead();
		for(int i = 0; i < 7; ++i)
		{
			this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, true, this.posX + Stuff.Randomization.r(0.3), this.posY + Stuff.Randomization.r(0.3) + 0.2, this.posZ + Stuff.Randomization.r(0.3), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1));
		}
		for(int i = 0; i < 4; ++i)
		{
			this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, true, this.posX + Stuff.Randomization.r(0.2), this.posY + Stuff.Randomization.r(0.2) + 0.2, this.posZ + Stuff.Randomization.r(0.2), Stuff.Randomization.r(0.2), Stuff.Randomization.r(0.2), Stuff.Randomization.r(0.2));
		}
		for(int i = 0; i < 3; ++i)
		{
			this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, true, this.posX + Stuff.Randomization.r(0.2), this.posY + Stuff.Randomization.r(0.2) + 0.2, this.posZ + Stuff.Randomization.r(0.2), Stuff.Randomization.r(0.3), Stuff.Randomization.r(0.3), Stuff.Randomization.r(0.3));
		}
		for(int i = 0; i < 4; ++i)
		{
			this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, true, this.posX + Stuff.Randomization.r(0.2), this.posY + Stuff.Randomization.r(0.2) + 0.2, this.posZ + Stuff.Randomization.r(0.2), Stuff.Randomization.r(0.4), Stuff.Randomization.r(0.4), Stuff.Randomization.r(0.4));
		}
	}
	
	public EntityLivingBase getThrower()
	{
		if(this.thrower == null && this.throwerName != null && this.throwerName.length() > 0)
		{
			this.thrower = this.worldObj.getPlayerEntityByName(this.throwerName);
		}
		
		return this.thrower;
	}
}
