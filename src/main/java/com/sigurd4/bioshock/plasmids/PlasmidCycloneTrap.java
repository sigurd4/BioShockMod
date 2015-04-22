package com.sigurd4.bioshock.plasmids;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing.AxisDirection;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.sigurd4.bioshock.Stuff;
import com.sigurd4.bioshock.element.Element;
import com.sigurd4.bioshock.entity.projectile.EntityPlasmidProjectile;
import com.sigurd4.bioshock.extendedentity.ExtendedLivingBase;
import com.sigurd4.bioshock.itemtags.ItemTagBoolean;
import com.sigurd4.bioshock.itemtags.ItemTagInteger;
import com.sigurd4.bioshock.reference.RefMod;

public class PlasmidCycloneTrap extends Plasmid implements IPlasmidProjectile
{
	public static final ItemTagBoolean onGround = new ItemTagBoolean("onGround", false, false);
	public static final ItemTagInteger onGroundTime = new ItemTagInteger("onGroundTime", 0, 0, Integer.MAX_VALUE, false);
	
	public static float damage = 1;
	public static int time = 20;
	public static int age = 10000;
	
	public PlasmidCycloneTrap(String id, int cost1, int cost2, boolean type, String... description)
	{
		super(id, cost1, cost2, type, description);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		player.playSound(RefMod.MODID + ":" + "item.weapon.wrench.swing", 0.5F, 0.9F + world.rand.nextFloat() * 0.2F);
		for(int i = 0; i < 5; ++i)
		{
			world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, true, player.posX + Stuff.Randomization.r(player.width), player.posY - player.height / 2 + Stuff.Randomization.r(player.height), player.posZ + Stuff.Randomization.r(player.width), 0, 0, 0);
		}
		for(int i = 0; i < 4; ++i)
		{
			world.spawnParticle(EnumParticleTypes.CRIT, true, player.posX + Stuff.Randomization.r(player.width), player.posY - player.height / 2 + Stuff.Randomization.r(player.height), player.posZ + Stuff.Randomization.r(player.width), 0, 0, 0);
		}
		for(int i = 0; i < 3; ++i)
		{
			world.spawnParticle(EnumParticleTypes.CLOUD, true, player.posX + Stuff.Randomization.r(player.width), player.posY - player.height / 2 + Stuff.Randomization.r(player.height), player.posZ + Stuff.Randomization.r(player.width), 0, 0, 0);
		}
		
		if(!world.isRemote)
		{
			world.spawnEntityInWorld(new EntityPlasmidProjectile(world, player, this));
		}
		player.swingItem();
		Element.addToEntityElement(player, Element.FIRE, -10);
		Element.addToEntityElement(player, Element.WATER, -20);
		
		return stack;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected)
	{
		
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player)
	{
		for(int i = 0; i < 10; ++i)
		{
			world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, true, player.posX + Stuff.Randomization.r(player.width), player.posY - player.height / 2 + Stuff.Randomization.r(player.height), player.posZ + Stuff.Randomization.r(player.width), 0, 0, 0);
		}
		for(int i = 0; i < 8; ++i)
		{
			world.spawnParticle(EnumParticleTypes.CRIT, true, player.posX + Stuff.Randomization.r(player.width), player.posY - player.height / 2 + Stuff.Randomization.r(player.height), player.posZ + Stuff.Randomization.r(player.width), 0, 0, 0);
		}
		for(int i = 0; i < 20; ++i)
		{
			world.spawnParticle(EnumParticleTypes.CLOUD, true, player.posX + Stuff.Randomization.r(player.width), player.posY - player.height / 2 + Stuff.Randomization.r(player.height), player.posZ + Stuff.Randomization.r(player.width), 0, 0, 0);
		}
		
		if(!world.isRemote)
		{
			player.addPotionEffect(new PotionEffect(Potion.weakness.id, 160, 0, true, false));
			float f = 2;
			double x = -MathHelper.sin(player.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float)Math.PI) * f;
			double y = -MathHelper.sin(player.rotationPitch / 180.0F * (float)Math.PI) * f;
			double z = MathHelper.cos(player.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float)Math.PI) * f;
			player.addVelocity(x, y + 2, z);
		}
		Element.addToEntityElement(player, Element.FIRE, -300);
		Element.addToEntityElement(player, Element.WATER, -200);
		
		return stack;
	}
	
	@Override
	public double getInitialSpeed()
	{
		return 2.1;
	}
	
	@Override
	public void onImpact(EntityThrowable projectile_, MovingObjectPosition pos)
	{
		EntityPlasmidProjectile projectile = (EntityPlasmidProjectile)projectile_;
		if(pos.entityHit != null && pos.entityHit instanceof EntityPlasmidProjectile && ((EntityPlasmidProjectile)pos.entityHit).getPlasmid().id == projectile.getPlasmid().id)
		{
			return;
		}
		
		double dx = projectile.posX;
		double dy = projectile.posY;
		double dz = projectile.posZ;
		
		if(pos.entityHit != null)
		{
			if(this.onGround.get(projectile.getCompound(), true) && this.onGroundTime.get(projectile.getCompound(), true) > age)
			{
				if(pos.entityHit == projectile.getThrower())
				{
					this.onGroundTime.add(projectile.getCompound(), 1);
				}
				if(pos.entityHit != projectile.getThrower() || pos.entityHit.fallDistance > 0.5)
				{
					dx = pos.entityHit.posX;
					dy = pos.entityHit.posY;
					dz = pos.entityHit.posZ;
					
					damageEntity(projectile, pos.entityHit);
				}
			}
			else if(pos.entityHit != projectile.getThrower())
			{
				this.onGround.set(projectile.getCompound(), true);
			}
		}
		else
		{
			IBlockState state = projectile.worldObj.getBlockState(pos.getBlockPos());
			switch(pos.sideHit.getAxis())
			{
			case Y:
			{
				if(state.getBlock().isNormalCube())
				{
					dy = pos.sideHit.getAxisDirection() == AxisDirection.POSITIVE ? Math.floor(dy) : Math.ceil(dy);
				}
				projectile.motionY = 0;
				if(pos.sideHit.getAxisDirection() == AxisDirection.NEGATIVE)
				{
					this.onGround.set(projectile.getCompound(), true);
				}
				break;
			}
			case Z:
			{
				if(state.getBlock().isNormalCube())
				{
					dz = pos.sideHit.getAxisDirection() == AxisDirection.POSITIVE ? Math.floor(dz) : Math.ceil(dz);
				}
				projectile.motionZ *= -0.05;
				break;
			}
			case X:
			{
				if(state.getBlock().isNormalCube())
				{
					dx = pos.sideHit.getAxisDirection() == AxisDirection.POSITIVE ? Math.floor(dx) : Math.ceil(dx);
				}
				projectile.motionX *= -0.05;
				break;
			}
			}
		}
	}
	
	@Override
	public void onEntityUpdate(EntityThrowable projectile_)
	{
		EntityPlasmidProjectile projectile = (EntityPlasmidProjectile)projectile_;
		if(projectile.getThrower() == null && projectile.worldObj.getClosestPlayerToEntity(projectile, 10) != null)
		{
			projectile.setThrower(projectile.worldObj.getClosestPlayerToEntity(projectile, 10));
		}
		//this.playSound("liquid.water", 2*mass/2, 7);
		if(this.onGround.get(projectile.getCompound(), true) && this.onGroundTime.get(projectile.getCompound(), true) > age)
		{
			List<Entity> e = projectile.worldObj.getEntitiesWithinAABBExcludingEntity(projectile, projectile.getBoundingBox().expand(2, 1.2, 2));
			boolean b = true;
			for(int i = 0; i < e.size(); ++i)
			{
				if(e.get(i) instanceof EntityLiving)
				{
					EntityLiving entity = (EntityLiving)e.get(i);
					if(entity.getNavigator().getPath() != null && !entity.getNavigator().getPath().isFinished() && entity.getNavigator().getPath().getFinalPathPoint().distanceTo(new PathPoint((int)Math.floor(projectile.posX), (int)Math.floor(projectile.posY + projectile.getYOffset()), (int)Math.floor(projectile.posZ))) < 4)
					{
						b = false;
					}
				}
			}
			if(b)
			{
				for(int i = 0; i < e.size(); ++i)
				{
					if(e.get(i) instanceof EntityLiving)
					{
						EntityLiving entity = (EntityLiving)e.get(i);
						if(entity.getNavigator().getPath() == null || entity.getNavigator().getPath().isFinished() || Math.sqrt(entity.motionX * entity.motionX + entity.motionZ * entity.motionZ) < 0.01)
						{
							entity.getNavigator().setPath(entity.getNavigator().getPathToEntityLiving(projectile), 0.5);
						}
					}
				}
			}
			if(this.onGroundTime.get(projectile.getCompound(), true) > age)
			{
				projectile.setDead();
			}
			projectile.setEntitySize(0.9F, 0.3F);
			for(int i = 0; i < 1; ++i)
			{
				float f = 0.08F;
				float x = Stuff.Randomization.r(f);
				float z = Stuff.Randomization.r(f);
				while(Math.sqrt(x * x + z * z) > projectile.width)
				{
					x = Stuff.Randomization.r(f);
					z = Stuff.Randomization.r(f);
				}
				float w = f / (float)Math.sqrt(x * x + z * z);
				x *= w;
				z *= w;
				projectile.worldObj.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, true, projectile.posX, projectile.posY, projectile.posZ, x, 0.15F, z);
			}
			for(int i = 0; i < 6; ++i)
			{
				float f = 0.7F;
				float x = Stuff.Randomization.r(f);
				float z = Stuff.Randomization.r(f);
				while(Math.sqrt(x * x + z * z) > projectile.width)
				{
					x = Stuff.Randomization.r(f);
					z = Stuff.Randomization.r(f);
				}
				float w = f / (float)Math.sqrt(x * x + z * z);
				x *= w;
				z *= w;
				projectile.worldObj.spawnParticle(EnumParticleTypes.CRIT, true, projectile.posX, projectile.posY, projectile.posZ, x, 0.3F, z);
			}
			for(int i = 0; i < 1; ++i)
			{
				float f = 0.1F;
				float x = Stuff.Randomization.r(f);
				float z = Stuff.Randomization.r(f);
				while(Math.sqrt(x * x + z * z) > projectile.width)
				{
					x = Stuff.Randomization.r(f);
					z = Stuff.Randomization.r(f);
				}
				projectile.worldObj.spawnParticle(EnumParticleTypes.CLOUD, true, projectile.posX, projectile.posY, projectile.posZ, x, 0.0F, z);
			}
		}
		else
		{
			projectile.setEntitySize(0.3F, 0.3F);
			double x = -projectile.motionX;
			double y = -projectile.motionY;
			double z = -projectile.motionZ;
			if(Math.sqrt(projectile.motionX * projectile.motionX + projectile.motionY * projectile.motionY + projectile.motionZ * projectile.motionZ) > 0.02)
			{
				double w = 0.2 / Math.sqrt(x * x + y * y + z * z);
				x *= w;
				y *= w;
				z *= w;
			}
			for(int i = 0; i < 1; ++i)
			{
				projectile.worldObj.spawnParticle(EnumParticleTypes.CLOUD, true, projectile.posX, projectile.posY, projectile.posZ, x + Stuff.Randomization.r(0.2), y + Stuff.Randomization.r(0.2), z + Stuff.Randomization.r(0.2));
			}
			if(Math.sqrt(projectile.motionX * projectile.motionX + projectile.motionY * projectile.motionY + projectile.motionZ * projectile.motionZ) > 0.02)
			{
				for(int i = 0; i < 3; ++i)
				{
					projectile.worldObj.spawnParticle(EnumParticleTypes.CRIT, true, projectile.posX, projectile.posY, projectile.posZ, x + Stuff.Randomization.r(0.2), y + Stuff.Randomization.r(0.2), z + Stuff.Randomization.r(0.2));
				}
				for(int i = 0; i < 3; ++i)
				{
					projectile.worldObj.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, true, projectile.posX, projectile.posY, projectile.posZ, x + Stuff.Randomization.r(0.2), y + Stuff.Randomization.r(0.2), z + Stuff.Randomization.r(0.2));
				}
			}
		}
		if(this.onGround.get(projectile.getCompound(), true))
		{
			this.onGroundTime.add(projectile.getCompound(), 1);
			if(projectile.motionY > 0)
			{
				projectile.motionY = 0;
			}
			if(projectile.motionY < -0.2)
			{
				projectile.motionY = -0.2;
			}
			projectile.motionX = projectile.motionZ = 0;
			if(this.onGroundTime.get(projectile.getCompound(), true) > age)
			{
				List<Entity> e = projectile.worldObj.getEntitiesWithinAABBExcludingEntity(projectile, projectile.getBoundingBox().expand(0.4, 0.5, 0.4));
				for(int i = 0; i < e.size(); ++i)
				{
					if(!(e.get(i) instanceof EntityPlasmidProjectile && ((EntityPlasmidProjectile)e.get(i)).getPlasmid().id == projectile.getPlasmid().id) && Math.sqrt((projectile.posX - e.get(i).posX) * (projectile.posX - e.get(i).posX) + (projectile.posZ - e.get(i).posZ) * (projectile.posZ - e.get(i).posZ)) < projectile.width + 0.4)
					{
						MovingObjectPosition pos = new MovingObjectPosition(e.get(i), projectile.getPositionVector());
						pos.entityHit = e.get(i);
						this.onImpact(projectile, pos);
					}
				}
			}
		}
		else
		{
			this.onGroundTime.set(projectile.getCompound(), 0);
		}
		
		if(projectile.ticksExisted > time && !this.onGround.get(projectile.getCompound(), true))
		{
			if(projectile.getThrower() != null)
			{
				if(Math.sqrt((projectile.posX - projectile.getThrower().posX) * (projectile.posX - projectile.getThrower().posX) + (projectile.posZ - projectile.getThrower().posZ) * (projectile.posZ - projectile.getThrower().posZ) + (projectile.posY - projectile.getThrower().posY) * (projectile.posY - projectile.getThrower().posY) / 2) > 40)
				{
					projectile.setDead();
				}
			}
			else
			{
				projectile.setDead();
			}
		}
	}
	
	@Override
	public float getGravityVelocity(EntityThrowable projectile)
	{
		return 0.02F;
	}
	
	@Override
	public boolean attackEntityFrom(EntityThrowable projectile, DamageSource src, float dmg)
	{
		return this.interactFirst(projectile, src.getEntity() instanceof EntityPlayer ? (EntityPlayer)src.getEntity() : null);
	}
	
	@Override
	public boolean interactFirst(EntityThrowable projectile, EntityPlayer player)
	{
		projectile.setDead();
		if(player != null)
		{
			if(player != projectile.getThrower())
			{
				this.damageEntity(projectile, player);
			}
			else
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public DamageSource getDamageSource(EntityThrowable projectile)
	{
		return Element.cycloneTrap(projectile.getThrower());
	}
	
	protected static boolean damageEntity(EntityThrowable projectile, Entity entity)
	{
		DamageSource damagesource = Element.cycloneTrap(projectile.getThrower());
		
		boolean b = entity.attackEntityFrom(damagesource, rand.nextFloat() + damage);
		
		if(entity instanceof EntityLivingBase && b && entity != projectile.getThrower())
		{
			((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.confusion.id, rand.nextInt(20 + 1), 0, true, false));
			((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 80 + rand.nextInt(20 + 1), 0, true, false));
			((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 80 + rand.nextInt(40 + 1), 1, true, false));
			((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.weakness.id, 180 + rand.nextInt(100 + 1), 1, true, false));
			
			ExtendedLivingBase props = ExtendedLivingBase.get((EntityLivingBase)entity);
			props.fallDamageMultiplier = 1.9F;
		}
		
		if(b)
		{
			entity.fallDistance = 0;
			if(entity.motionY < 0)
			{
				entity.motionY = 0;
			}
			if(entity != projectile.getThrower())
			{
				entity.addVelocity(Stuff.Randomization.r(0.2), 0.9, Stuff.Randomization.r(0.2));
			}
			else
			{
				entity.addVelocity(0, 0.5, 0);
			}
			
			spawnLaunchParticles(projectile);
			spawnLaunchParticles(entity);
			
			projectile.worldObj.playSoundAtEntity(projectile, "random.bow", 0.5F, 0.4F / (rand.nextFloat() * 0.4F + 0.8F));
			
			if(!projectile.worldObj.isRemote && (entity instanceof EntityLivingBase || Math.min(entity.height, entity.width) > 0.5))
			{
				projectile.setDead();
			}
			
		}
		return b;
	}
	
	protected static void spawnLaunchParticles(Entity entity)
	{
		for(int i = 0; i < 36; ++i)
		{
			float f = 0.1F;
			float x = Stuff.Randomization.r(f);
			float z = Stuff.Randomization.r(f);
			while(Math.sqrt(x * x + z * z) > entity.width)
			{
				x = Stuff.Randomization.r(f);
				z = Stuff.Randomization.r(f);
			}
			float w = f / (float)Math.sqrt(x * x + z * z);
			x *= w;
			z *= w;
			entity.worldObj.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, true, entity.posX, entity.posY + entity.getYOffset(), entity.posZ, x, 0.3F, z);
		}
		for(int i = 0; i < 8; ++i)
		{
			float f = 0.2F;
			float x = Stuff.Randomization.r(f);
			float z = Stuff.Randomization.r(f);
			while(Math.sqrt(x * x + z * z) > entity.width)
			{
				x = Stuff.Randomization.r(f);
				z = Stuff.Randomization.r(f);
			}
			if(rand.nextBoolean())
			{
				float w = f / (float)Math.sqrt(x * x + z * z);
				x *= w;
				z *= w;
			}
			entity.worldObj.spawnParticle(EnumParticleTypes.CRIT, true, entity.posX + Stuff.Randomization.r(entity.width), entity.posY + rand.nextFloat() * entity.height + entity.getYOffset(), entity.posZ + Stuff.Randomization.r(entity.width), x, 0.4F, z);
		}
		for(int i = 0; i < 14; ++i)
		{
			float f = 0.1F;
			float x = Stuff.Randomization.r(f);
			float z = Stuff.Randomization.r(f);
			while(Math.sqrt(x * x + z * z) > entity.width)
			{
				x = Stuff.Randomization.r(f);
				z = Stuff.Randomization.r(f);
			}
			entity.worldObj.spawnParticle(EnumParticleTypes.CRIT, true, entity.posX, entity.posY + entity.getYOffset(), entity.posZ, x, 0.4F, z);
		}
	}
}
