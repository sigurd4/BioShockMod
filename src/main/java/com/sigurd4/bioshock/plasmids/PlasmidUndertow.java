package com.sigurd4.bioshock.plasmids;

import net.minecraft.block.BlockFire;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing.AxisDirection;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.sigurd4.bioshock.Stuff;
import com.sigurd4.bioshock.element.Element;
import com.sigurd4.bioshock.entity.IEntityPlasmid;
import com.sigurd4.bioshock.entity.projectile.EntityPlasmidProjectile;
import com.sigurd4.bioshock.extendedentity.ExtendedLivingBase;
import com.sigurd4.bioshock.itemtags.ItemTagFloat;

public class PlasmidUndertow extends Plasmid implements IPlasmidProjectile
{
	public final static ItemTagFloat mass = new ItemTagFloat("mass", 5.0F, 0F, 5F, false);
	
	public PlasmidUndertow(String id, int cost1, int cost2, boolean type, String... description)
	{
		super(id, cost1, cost2, type, description);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		EntityPlasmidProjectile p = new EntityPlasmidProjectile(world, player, this);
		
		for(int i = 0; i < 32; ++i)
		{
			world.spawnParticle(EnumParticleTypes.WATER_SPLASH, true, p.posX + Stuff.Randomization.r(p.width / 2), p.posY + world.rand.nextFloat() * p.height, p.posZ + Stuff.Randomization.r(p.width / 2), -p.motionX * 2 + Stuff.Randomization.r(0.2), -p.motionY * 2 + Stuff.Randomization.r(0.2), -p.motionZ * 2 + Stuff.Randomization.r(0.2));
		}
		
		for(int i = 0; i < 16; ++i)
		{
			world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, true, p.posX + Stuff.Randomization.r(p.width / 2), p.posY + world.rand.nextFloat() * p.height, p.posZ + Stuff.Randomization.r(p.width / 2), -p.motionX * 0.8 + Stuff.Randomization.r(0.2), -p.motionY * 0.8 + Stuff.Randomization.r(0.2), -p.motionZ * 0.8 + Stuff.Randomization.r(0.2));
		}
		
		if(!world.isRemote)
		{
			world.spawnEntityInWorld(p);
			
			player.extinguish();
			player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 100 + world.rand.nextInt(50 + 1), 0, true, false));
		}
		player.swingItem();
		Element.addToEntityElement(player, Element.WATER, 160);
		
		return stack;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected)
	{
		
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player)
	{
		for(int i = 0; i < 64; ++i)
		{
			world.spawnParticle(EnumParticleTypes.WATER_SPLASH, true, player.posX, player.posY, player.posZ, Stuff.Randomization.r(0.5), Stuff.Randomization.r(0.5), Stuff.Randomization.r(0.5));
		}
		
		for(int i = 0; i < 16; ++i)
		{
			world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, true, player.posX, player.posY, player.posZ, Stuff.Randomization.r(0.5), Stuff.Randomization.r(0.5), Stuff.Randomization.r(0.5));
		}
		
		if(!world.isRemote)
		{
			player.extinguish();
			player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 100 + world.rand.nextInt(50 + 1), 0, true, false));
			
			player.addPotionEffect(new PotionEffect(Potion.weakness.id, 320, 0, true, false));
			player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 160, 0, true, false));
			player.addPotionEffect(new PotionEffect(Potion.confusion.id, 20, 0, true, false));
			player.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 320, 0, true, false));
		}
		Element.setEntityElement(player, Element.WATER, 600, false);
		
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
		if(pos.entityHit != null && pos.entityHit instanceof IEntityPlasmid && ((IEntityPlasmid)pos.entityHit).getPlasmid() == projectile.getPlasmid())
		{
			return;
		}
		mass.add(projectile.getCompound(), -rand.nextFloat() * 0.02F + 0.005F);
		
		mass.set(projectile.getCompound(), mass.get(projectile.getCompound(), true) * 0.95F);
		
		Vec3 vec = projectile.getPositionVector();
		
		if(pos.entityHit != null)
		{
			pos.entityHit.extinguish();
			
			vec = pos.entityHit.getPositionVector().add(new Vec3(0, pos.entityHit.getYOffset() + pos.entityHit.height / 2, 0));
			
			DamageSource damagesource = ((IPlasmidProjectile)projectile.getPlasmid()).getDamageSource(projectile);
			
			boolean b = pos.entityHit.attackEntityFrom(damagesource, this.rand.nextFloat() * (pos.entityHit instanceof EntityBlaze || pos.entityHit instanceof EntityEnderman ? 27 : 1));
			
			if(b)
			{
				for(int i = 0; i < 26 + rand.nextInt(6 + 1); ++i)
				{
					projectile.worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, true, vec.xCoord + Stuff.Randomization.r(pos.entityHit.width), vec.yCoord + Stuff.Randomization.r(pos.entityHit.height), vec.zCoord + Stuff.Randomization.r(pos.entityHit.width), -projectile.motionX * 2, -projectile.motionY * 2, -projectile.motionZ * 2);
				}
				for(int i = 0; i < 23; ++i)
				{
					projectile.worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, true, projectile.posX + rand.nextFloat() * (projectile.prevPosX - projectile.posX) + Stuff.Randomization.r(projectile.width), projectile.posY + rand.nextFloat() * (projectile.prevPosY - projectile.posY) + rand.nextFloat() * (projectile.height * 2) - projectile.height, projectile.posZ + rand.nextFloat() * (projectile.prevPosZ - projectile.posZ) + Stuff.Randomization.r(projectile.width), -projectile.motionX * 3, -projectile.motionY * 3, -projectile.motionZ * 3);
				}
				
				if(pos.entityHit instanceof EntityLivingBase)
				{
					Element.addToEntityElement((EntityLivingBase)pos.entityHit, Element.WATER, (int)(500 * (mass.get(projectile.getCompound(), true) / 3)));
					if(!projectile.worldObj.isRemote)
					{
						((EntityLivingBase)pos.entityHit).addPotionEffect(new PotionEffect(Potion.fireResistance.id, 20 + rand.nextInt(80 + 1), 0, true, false));
						((EntityLivingBase)pos.entityHit).addPotionEffect(new PotionEffect(Potion.confusion.id, rand.nextInt(20 + 1), 0, true, false));
						((EntityLivingBase)pos.entityHit).addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 80 + rand.nextInt(20 + 1), 0, true, false));
						((EntityLivingBase)pos.entityHit).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 80 + rand.nextInt(40 + 1), 2, true, false));
						((EntityLivingBase)pos.entityHit).addPotionEffect(new PotionEffect(Potion.weakness.id, 180 + rand.nextInt(100 + 1), 1, true, false));
					}
					ExtendedLivingBase props = ExtendedLivingBase.get((EntityLivingBase)pos.entityHit);
					props.fallDamageMultiplier = 1.8F;
					mass.add(projectile.getCompound(), 0.1F);
				}
				
				if(!projectile.worldObj.isRemote && b)
				{
					Vec3 motion = Stuff.Coordinates3D.multiply(Stuff.Coordinates3D.velocity(projectile), 0.8);
					if(motion.xCoord > 0)
					{
						if(pos.entityHit.motionX > motion.xCoord)
						{
							motion = new Vec3(0, motion.yCoord, motion.zCoord);
						}
						if(pos.entityHit.motionX < 0)
						{
							pos.entityHit.motionX *= 0.8F;
						}
					}
					else
					{
						if(pos.entityHit.motionX < motion.xCoord)
						{
							motion = new Vec3(0, motion.yCoord, motion.zCoord);
						}
						if(pos.entityHit.motionX > 0)
						{
							pos.entityHit.motionX *= 0.8F;
						}
					}
					
					if(motion.yCoord > 0)
					{
						if(pos.entityHit.motionY > motion.yCoord)
						{
							motion = new Vec3(motion.xCoord, 0, motion.zCoord);
						}
						if(pos.entityHit.motionY < 0)
						{
							pos.entityHit.motionY *= 0.8F;
						}
					}
					else
					{
						if(pos.entityHit.motionY < motion.yCoord)
						{
							motion = new Vec3(motion.xCoord, 0, motion.zCoord);
						}
						if(pos.entityHit.motionY > 0)
						{
							pos.entityHit.motionY *= 0.8F;
						}
					}
					
					if(motion.zCoord > 0)
					{
						if(pos.entityHit.motionZ > motion.zCoord)
						{
							motion = new Vec3(motion.xCoord, motion.yCoord, 0);
						}
						if(pos.entityHit.motionZ < 0)
						{
							pos.entityHit.motionZ *= 0.8F;
						}
					}
					else
					{
						if(pos.entityHit.motionZ < motion.zCoord)
						{
							motion = new Vec3(motion.xCoord, motion.yCoord, 0);
						}
						if(pos.entityHit.motionZ > 0)
						{
							pos.entityHit.motionZ *= 0.8F;
						}
					}
					
					pos.entityHit.addVelocity(motion.xCoord / 2 + Stuff.Randomization.r(0.2), motion.yCoord / 2 + Stuff.Randomization.r(0.2) + 0.1, motion.zCoord / 2 + Stuff.Randomization.r(0.2));
				}
			}
		}
		else
		{
			IBlockState state = projectile.worldObj.getBlockState(pos.getBlockPos());
			if(state.getBlock().isNormalCube())
			{
				int dir = pos.sideHit.getAxisDirection() == AxisDirection.POSITIVE ? 1 : 0;
				switch(pos.sideHit.getAxis())
				{
				case X:
				{
					vec = new Vec3(Math.floor(vec.xCoord) + dir, vec.yCoord, vec.zCoord);
					for(int i = 0; i < projectile.motionX * 12; ++i)
					{
						projectile.worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, true, vec.xCoord + Stuff.Randomization.r(pos.entityHit.width), vec.yCoord + Stuff.Randomization.r(pos.entityHit.height), vec.zCoord + Stuff.Randomization.r(pos.entityHit.width), -projectile.motionX * 2, -projectile.motionY * 2, -projectile.motionZ * 2);
					}
					projectile.motionX *= -0.2;
					break;
				}
				case Y:
				{
					vec = new Vec3(vec.xCoord, Math.floor(vec.yCoord) + dir, vec.zCoord);
					for(int i = 0; i < projectile.motionY * 12; ++i)
					{
						projectile.worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, true, vec.xCoord + Stuff.Randomization.r(pos.entityHit.width), vec.yCoord + Stuff.Randomization.r(pos.entityHit.height), vec.zCoord + Stuff.Randomization.r(pos.entityHit.width), -projectile.motionX * 2, -projectile.motionY * 2, -projectile.motionZ * 2);
					}
					projectile.motionY *= -0.2;
					break;
				}
				case Z:
				{
					vec = new Vec3(vec.xCoord, vec.yCoord, Math.floor(vec.zCoord) + dir);
					for(int i = 0; i < projectile.motionZ * 12; ++i)
					{
						projectile.worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, true, vec.xCoord + Stuff.Randomization.r(pos.entityHit.width), vec.yCoord + Stuff.Randomization.r(pos.entityHit.height), vec.zCoord + Stuff.Randomization.r(pos.entityHit.width), -projectile.motionX * 2, -projectile.motionY * 2, -projectile.motionZ * 2);
					}
					projectile.motionZ *= -0.2;
					break;
				}
				}
				if(projectile.worldObj.isRemote && state.getBlock().addHitEffects(projectile.worldObj, pos, Minecraft.getMinecraft().effectRenderer))
				{
					double v = Stuff.Coordinates3D.distance(Stuff.Coordinates3D.velocity(projectile));
					double p = v / 4 * (mass.get(projectile.getCompound(), true) + 0.5F);
					for(int i = (int)(((v / 2 + mass.get(projectile.getCompound(), true) / 1.5) * (mass.get(projectile.getCompound(), true) / 1.5 + 0.5F) * 5 - state.getBlock().getBlockHardness(projectile.worldObj, pos.getBlockPos())) / 4 - this.rand.nextFloat()); i > 0; --i)
					{
						projectile.worldObj.spawnParticle(EnumParticleTypes.BLOCK_CRACK, true, projectile.posX + Stuff.Randomization.r(p), projectile.posY + Stuff.Randomization.r(p), projectile.posZ + Stuff.Randomization.r(p), Stuff.Randomization.r(p), Stuff.Randomization.r(p), Stuff.Randomization.r(p));
					}
					projectile.worldObj.spawnParticle(EnumParticleTypes.BLOCK_CRACK, true, projectile.posX + Stuff.Randomization.r(p), projectile.posY + Stuff.Randomization.r(p), projectile.posZ + Stuff.Randomization.r(p), Stuff.Randomization.r(p), Stuff.Randomization.r(p), Stuff.Randomization.r(p));
				}
				for(int i = 0; i < mass.get(projectile.getCompound(), true) * 20 && !projectile.worldObj.isRemote; ++i)
				{
					float f = 1.5F;
					BlockPos pos2 = new BlockPos(rand.nextFloat() * mass.get(projectile.getCompound(), true) * f - mass.get(projectile.getCompound(), true) * f / 2, (rand.nextFloat() * mass.get(projectile.getCompound(), true) * f - mass.get(projectile.getCompound(), true) * f / 2) * 3, rand.nextFloat() * mass.get(projectile.getCompound(), true) * f - mass.get(projectile.getCompound(), true) * f / 2);
					if(projectile.worldObj.getBlockState(pos2.add(vec.xCoord, vec.yCoord, vec.zCoord)).getBlock() == Blocks.fire)
					{
						projectile.worldObj.setBlockToAir(pos2.add(vec.xCoord, vec.yCoord, vec.zCoord));
					}
					if(projectile.worldObj.rayTraceBlocks(Stuff.Coordinates3D.middle(pos2.add(vec.xCoord, vec.yCoord, vec.zCoord)), vec, false, true, false) == null)
					{
						projectile.worldObj.scheduleUpdate(pos2.add(vec.xCoord, vec.yCoord, vec.zCoord), projectile.worldObj.getBlockState(pos2.add(vec.xCoord, vec.yCoord, vec.zCoord)).getBlock(), 2);
						if(state.getBlock().canPlaceBlockAt(projectile.worldObj, pos2.add(vec.xCoord, vec.yCoord, vec.zCoord)))
						{
							if(projectile.worldObj.getBlockState(pos2.add(vec.xCoord, vec.yCoord - 1, vec.zCoord)).getBlock().getMaterial().isSolid())
							{
								projectile.worldObj.setBlockState(pos2.add(vec.xCoord, vec.yCoord, vec.zCoord), Blocks.flowing_water.getDefaultState());
							}
						}
					}
				}
			}
		}
		for(int i = 0; i < projectile.motionY * 40; ++i)
		{
			projectile.worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, true, vec.xCoord, vec.yCoord, vec.zCoord, -projectile.motionX * 8 * rand.nextFloat(), -projectile.motionY * 8 * rand.nextFloat(), -projectile.motionZ * 8 * rand.nextFloat());
		}
	}
	
	@Override
	public void onEntityUpdate(EntityThrowable projectile_)
	{
		EntityPlasmidProjectile projectile = (EntityPlasmidProjectile)projectile_;
		projectile.playSound("liquid.water", 2 * mass.get(projectile.getCompound(), true) / 2, 7);
		if(projectile.getThrower() == null && projectile.worldObj.getClosestPlayerToEntity(projectile, 10) != null)
		{
			projectile.setThrower(projectile.worldObj.getClosestPlayerToEntity(projectile, 10));
		}
		float max = 0.7F * 2;
		float size = 0.7F * mass.get(projectile.getCompound(), true);
		if(size > max)
		{
			size = max;
		}
		projectile.setEntitySize(size, size);
		if(projectile.width > max)
		{
			projectile.setEntitySize(max, projectile.height);
		}
		if(projectile.height > max)
		{
			projectile.setEntitySize(projectile.width, max);
		}
		
		mass.set(projectile.getCompound(), mass.get(projectile.getCompound(), true) * 0.99F);
		if(projectile.ticksExisted > 2)
		{
			mass.add(projectile.getCompound(), -(1 - (float)Math.min(0, Stuff.Coordinates3D.distance(Stuff.Coordinates3D.velocity(projectile)))) / 8);
		}
		
		if(projectile.ticksExisted > 10)
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
		if(mass.get(projectile.getCompound(), true) <= 0.3 || Stuff.Coordinates3D.distance(Stuff.Coordinates3D.velocity(projectile)) < 0.03)
		{
			projectile.setDead();
		}
		
		for(int i = 0; i < 48 * mass.get(projectile.getCompound(), true); ++i)
		{
			projectile.worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, true, projectile.posX + rand.nextFloat() * (projectile.prevPosX - projectile.posX) + Stuff.Randomization.r(projectile.width / 3), projectile.posY + rand.nextFloat() * (projectile.prevPosY - projectile.posY) + rand.nextFloat() * projectile.height / 3 * 2, projectile.posZ + rand.nextFloat() * (projectile.prevPosZ - projectile.posZ) + Stuff.Randomization.r(projectile.width / 3), -projectile.motionX * 2, -projectile.motionY * 2, -projectile.motionZ * 2);
		}
		
		for(int i = 0; i < 24 * mass.get(projectile.getCompound(), true); ++i)
		{
			projectile.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, true, projectile.posX + rand.nextFloat() * (projectile.prevPosX - projectile.posX) + Stuff.Randomization.r(projectile.width / 3), projectile.posY + rand.nextFloat() * (projectile.prevPosY - projectile.posY) + rand.nextFloat() * projectile.height / 3 * 2, projectile.posZ + rand.nextFloat() * (projectile.prevPosZ - projectile.posZ) + Stuff.Randomization.r(projectile.width / 3), -projectile.motionX * 0.8, -projectile.motionY * 0.8, -projectile.motionZ * 0.8);
		}
		
		int range = (int)Math.min(3, Math.max(1, mass.get(projectile.getCompound(), true) / 2));
		for(int x = -range; x < range; ++x)
		{
			for(int y = -range; y < range; ++y)
			{
				for(int z = -range; z < range; ++z)
				{
					if(projectile.worldObj.getBlockState(projectile.getPosition().add(x, y, z)).getBlock() instanceof BlockFire)
					{
						projectile.worldObj.setBlockToAir(projectile.getPosition().add(x, y, z));
					}
				}
			}
		}
	}
	
	@Override
	public float getGravityVelocity(EntityThrowable projectile)
	{
		return 0.04F;
	}
	
	@Override
	public boolean attackEntityFrom(EntityThrowable projectile, DamageSource src, float dmg)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean interactFirst(EntityThrowable projectile, EntityPlayer player)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public DamageSource getDamageSource(EntityThrowable projectile)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
