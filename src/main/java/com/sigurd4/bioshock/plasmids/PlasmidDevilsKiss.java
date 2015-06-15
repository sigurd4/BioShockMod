package com.sigurd4.bioshock.plasmids;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
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
import com.sigurd4.bioshock.entity.EntityFlame;
import com.sigurd4.bioshock.entity.projectile.EntityPlasmidProjectile;
import com.sigurd4.bioshock.itemtags.ItemTagInteger;

public class PlasmidDevilsKiss extends PlasmidIncinerate implements IPlasmidProjectile
{
	public static final ItemTagInteger fuse = new ItemTagInteger("fuse", -1, -1, Integer.MAX_VALUE, false);
	
	public static float damage = 1;
	
	public PlasmidDevilsKiss(String id, int cost1, int cost2, boolean type, String... description)
	{
		super(id, cost1, cost2, type, description);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		world.playSoundEffect(player.posX, player.posY, player.posZ, "mob.ghast.fireball", 1.0F, 0.6F + 0.3F);
		world.playSoundEffect(player.posX, player.posY, player.posZ, "fire.fire", 1.0F, 0.6F + 0.3F);
		for(int i = 0; i < 5; ++i)
		{
			world.spawnParticle(EnumParticleTypes.LAVA, true, player.posX, player.posY - 1.2, player.posZ, Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1));
		}
		for(int i = 0; i < 4; ++i)
		{
			world.spawnParticle(EnumParticleTypes.LAVA, true, player.posX, player.posY - 1.2, player.posZ, Stuff.Randomization.r(0.5), Stuff.Randomization.r(0.5), Stuff.Randomization.r(0.5));
		}
		for(int i = 0; i < 8; ++i)
		{
			world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, true, player.posX + Stuff.Randomization.r(0.3), player.posY + player.getEyeHeight() + Stuff.Randomization.r(0.3), player.posZ + Stuff.Randomization.r(0.3), Stuff.Randomization.r(0.03), Stuff.Randomization.r(0.03), Stuff.Randomization.r(0.03));
		}
		for(int i = 0; i < 4; ++i)
		{
			world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, true, player.posX + Stuff.Randomization.r(0.5), player.posY + player.getEyeHeight() + Stuff.Randomization.r(0.5), player.posZ + Stuff.Randomization.r(0.5), Stuff.Randomization.r(0.03), Stuff.Randomization.r(0.03), Stuff.Randomization.r(0.03));
		}
		
		if(!world.isRemote)
		{
			int a = rand.nextInt(4 + 1);
			player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, a * 20, 0));
			player.setFire(a);
			player.addPotionEffect(new PotionEffect(Potion.weakness.id, 80, 0, true, false));
			player.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 80, 0, true, false));
			
			world.spawnEntityInWorld(new EntityPlasmidProjectile(world, player, this));
		}
		player.swingItem();
		Element.addToEntityElement(player, Element.FIRE, 120);
		
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
			world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, true, player.posX + Stuff.Randomization.r(0.6), player.posY + player.getEyeHeight() + Stuff.Randomization.r(0.6), player.posZ + Stuff.Randomization.r(0.6), Stuff.Randomization.r(0.03), Stuff.Randomization.r(0.03), Stuff.Randomization.r(0.03));
		}
		for(int i = 0; i < 32; ++i)
		{
			world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, true, player.posX + Stuff.Randomization.r(1), player.posY + player.getEyeHeight() + Stuff.Randomization.r(1), player.posZ + Stuff.Randomization.r(1), Stuff.Randomization.r(0.03), Stuff.Randomization.r(0.03), Stuff.Randomization.r(0.03));
		}
		if(!world.isRemote)
		{
			player.addPotionEffect(new PotionEffect(Potion.blindness.id, 40, 0, true, false));
		}
		return super.onItemUseFinish(stack, world, player);
	}
	
	@Override
	public double getInitialSpeed()
	{
		return 1.1;
	}
	
	@Override
	public void onImpact(EntityThrowable projectile_, MovingObjectPosition pos)
	{
		EntityPlasmidProjectile projectile = (EntityPlasmidProjectile)projectile_;
		Vec3 vec = projectile.getPositionVector();
		
		if(pos.entityHit != null)
		{
			vec = new Vec3(pos.entityHit.posX, pos.entityHit.posY, pos.entityHit.posZ);
			damageEntity(projectile, pos.entityHit, damage);
			if(this.fuse.get(projectile.getCompound(), true) < 10)
			{
				this.fuse.set(projectile.getCompound(), 10);
			}
			projectile.motionX *= 4 / 10;
			projectile.motionY *= 4 / 10;
			projectile.motionZ *= 4 / 10;
		}
		else
		{
			vec = new Vec3(pos.getBlockPos().getX() + 0.5, pos.getBlockPos().getY() + 0.5, pos.getBlockPos().getZ() + 0.5);
			
			switch(pos.sideHit.getAxis())
			{
			case Y:
				vec = vec.addVector(0, 0.5 * (pos.sideHit.getAxisDirection() == AxisDirection.POSITIVE ? 1 : -1), 0);
				projectile.motionY = -projectile.motionY / 10;
				break;
			case Z:
				vec = vec.addVector(0, 0, 0.5 * (pos.sideHit.getAxisDirection() == AxisDirection.POSITIVE ? 1 : -1));
				projectile.motionZ = -projectile.motionZ / 10;
				break;
			case X:
				vec = vec.addVector(0.5 * (pos.sideHit.getAxisDirection() == AxisDirection.POSITIVE ? 1 : -1), 0, 0);
				projectile.motionX = -projectile.motionX / 10;
				break;
			}
			
			if(!projectile.worldObj.isRemote && projectile.worldObj.isAirBlock(new BlockPos(vec)) && this.rand.nextInt(2 + 1) == 0)
			{
				projectile.worldObj.setBlockState(new BlockPos(vec), Blocks.fire.getDefaultState());
			}
			projectile.motionX = projectile.motionX * (8.5 / 10) + Stuff.Randomization.r(0.03);
			projectile.motionY = projectile.motionY * (8.5 / 10) + Stuff.Randomization.r(0.03);
			projectile.motionZ = projectile.motionZ * (8.5 / 10) + Stuff.Randomization.r(0.03);
			if(this.fuse.get(projectile.getCompound(), true) < 0)
			{
				this.fuse.set(projectile.getCompound(), this.rand.nextInt(20 + 1));
			}
		}
		
		if(projectile.worldObj.getBlockState(new BlockPos(vec)).getBlock().getMaterial() == Material.water)
		{
			projectile.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, true, vec.xCoord, vec.yCoord, vec.zCoord, Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1) + 0.2, Stuff.Randomization.r(0.1));
			for(int i = 0; i < 6; ++i)
			{
				projectile.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, true, vec.xCoord, vec.yCoord, vec.zCoord, Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1) + 0.05, Stuff.Randomization.r(0.1));
			}
			for(int i = 0; i < 4; ++i)
			{
				projectile.worldObj.spawnParticle(EnumParticleTypes.CLOUD, true, vec.xCoord, vec.yCoord, vec.zCoord, Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1) + 0.05, Stuff.Randomization.r(0.1));
			}
			projectile.worldObj.playSoundEffect(vec.xCoord, vec.yCoord, vec.zCoord, "random.fizz", 1.0F, 0.6F + 0.3F);
			projectile.setDead();
		}
	}
	
	@Override
	public void onEntityUpdate(EntityThrowable projectile_)
	{
		EntityPlasmidProjectile projectile = (EntityPlasmidProjectile)projectile_;
		if(this.fuse.get(projectile.getCompound(), true) > 30)
		{
			explode(projectile);
		}
		if(this.fuse.get(projectile.getCompound(), true) >= 0)
		{
			this.fuse.add(projectile.getCompound(), 1);
		}
		if(projectile.worldObj.getBlockState(projectile.getPosition()).getBlock().getMaterial() == Material.water)
		{
			projectile.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, true, projectile.posX, projectile.posY, projectile.posZ, Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1) + 0.2, Stuff.Randomization.r(0.1));
			for(int i = 0; i < 5; ++i)
			{
				projectile.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, true, projectile.posX, projectile.posY, projectile.posZ, Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1) + 0.05, Stuff.Randomization.r(0.1));
			}
			for(int i = 0; i < 4; ++i)
			{
				projectile.worldObj.spawnParticle(EnumParticleTypes.CLOUD, true, projectile.posX, projectile.posY, projectile.posZ, Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1) + 0.05, Stuff.Randomization.r(0.1));
			}
			projectile.worldObj.playSoundEffect(projectile.posX, projectile.posY, projectile.posZ, "random.fizz", 1.0F, 0.6F + 0.3F);
			projectile.setDead();
		}
		Vec3 vec = projectile.getPositionVector();
		for(int i = 0; i < 4; ++i)
		{
			vec = new Vec3(projectile.posX + this.rand.nextFloat() * (projectile.prevPosX - projectile.posX), projectile.posY + this.rand.nextFloat() * (projectile.prevPosY - projectile.posY), projectile.posZ + this.rand.nextFloat() * (projectile.prevPosZ - projectile.posZ));
			projectile.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, true, vec.xCoord, vec.yCoord, vec.zCoord, Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1));
		}
		for(int i = 0; i < 3; ++i)
		{
			vec = new Vec3(projectile.posX + this.rand.nextFloat() * (projectile.prevPosX - projectile.posX), projectile.posY + this.rand.nextFloat() * (projectile.prevPosY - projectile.posY), projectile.posZ + this.rand.nextFloat() * (projectile.prevPosZ - projectile.posZ));
			projectile.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, true, vec.xCoord, vec.yCoord, vec.zCoord, Stuff.Randomization.r(0.3) - projectile.motionX / 3, Stuff.Randomization.r(0.3) - projectile.motionY / 3, Stuff.Randomization.r(0.3) - projectile.motionZ / 3);
		}
		for(int i = 0; i < 7; ++i)
		{
			vec = new Vec3(projectile.posX + this.rand.nextFloat() * (projectile.prevPosX - projectile.posX), projectile.posY + this.rand.nextFloat() * (projectile.prevPosY - projectile.posY), projectile.posZ + this.rand.nextFloat() * (projectile.prevPosZ - projectile.posZ));
			projectile.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, true, vec.xCoord, vec.yCoord, vec.zCoord, Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1));
		}
		for(int i = 0; i < 5; ++i)
		{
			vec = new Vec3(projectile.posX + this.rand.nextFloat() * (projectile.prevPosX - projectile.posX), projectile.posY + this.rand.nextFloat() * (projectile.prevPosY - projectile.posY), projectile.posZ + this.rand.nextFloat() * (projectile.prevPosZ - projectile.posZ));
			projectile.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, true, vec.xCoord, vec.yCoord, vec.zCoord, Stuff.Randomization.r(0.2) - projectile.motionX / 2, Stuff.Randomization.r(0.2) - projectile.motionY / 2, Stuff.Randomization.r(0.2) - projectile.motionZ / 2);
		}
		projectile.worldObj.spawnParticle(EnumParticleTypes.FLAME, true, projectile.posX, projectile.posY, projectile.posZ, -projectile.motionX, -projectile.motionY, -projectile.motionZ);
	}
	
	@Override
	public float getGravityVelocity(EntityThrowable projectile)
	{
		return 0.04F;
	}
	
	@Override
	public boolean attackEntityFrom(EntityThrowable projectile, DamageSource src, float dmg)
	{
		return false;
	}
	
	@Override
	public boolean interactFirst(EntityThrowable projectile, EntityPlayer player)
	{
		return false;
	}
	
	@Override
	public DamageSource getDamageSource(EntityThrowable projectile)
	{
		return Element.devilsKiss(projectile.getThrower());
	}
	
	protected static void explode(EntityThrowable projectile_)
	{
		EntityPlasmidProjectile projectile = (EntityPlasmidProjectile)projectile_;
		Vec3 vec = projectile.getPositionVector();
		if(!projectile.worldObj.isRemote && projectile.worldObj.getGameRules().getGameRuleBooleanValue("doFireTick"))
		{
			for(int i = 0; i < 10; ++i)
			{
				BlockPos pos = new BlockPos((int)Math.floor(vec.xCoord) + rand.nextInt(6) - 3, (int)Math.floor(vec.yCoord) + rand.nextInt(6) - 3, (int)Math.floor(vec.zCoord) + rand.nextInt(6) - 3);
				if(vec.distanceTo(Stuff.Coordinates3D.middle(pos)) <= 3)
				{
					if(projectile.worldObj.isAirBlock(pos))
					{
						projectile.worldObj.setBlockState(pos, Blocks.fire.getDefaultState());
					}
					else if(rand.nextBoolean())
					{
						--i;
					}
				}
				else
				{
					--i;
				}
			}
		}
		if(!projectile.worldObj.isRemote)
		{
			projectile.worldObj.createExplosion(projectile, projectile.posX, projectile.posY, projectile.posZ, 0.9F, projectile.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
		}
		spawnFlame(projectile, vec);
		spawnFlame(projectile, vec);
		spawnFlame(projectile, vec);
		EntityFlame flame = spawnFlame(projectile, vec);
		flame.setLocationAndAngles(vec.xCoord, vec.yCoord, vec.zCoord, projectile.rotationYaw, projectile.rotationPitch);
		flame.setPosition(vec.xCoord, vec.yCoord, vec.zCoord);
		flame.motionX = -projectile.motionX * 0.1 + (flame.posX - projectile.posX) * 0.3;
		flame.motionY = -projectile.motionY * 0.1 + (flame.posY - projectile.posY) * 0.3;
		flame.motionZ = -projectile.motionZ * 0.1 + (flame.posZ - projectile.posZ) * 0.3;
		if(!projectile.worldObj.isRemote)
		{
			meltIce(projectile.worldObj, new BlockPos(vec));
			meltSnow(projectile.worldObj, new BlockPos(vec));
			igniteAlcohol(projectile.worldObj, vec);
		}
		setFireToNearbyEntities(projectile, damage, vec, 80);
		
		for(int i = 0; i < 8; ++i)
		{
			projectile.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, true, vec.xCoord + Stuff.Randomization.r(2), vec.yCoord + Stuff.Randomization.r(2), vec.zCoord + Stuff.Randomization.r(2), Stuff.Randomization.r(0.8), Stuff.Randomization.r(0.8), Stuff.Randomization.r(0.8));
		}
		for(int i = 0; i < 8; ++i)
		{
			projectile.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, true, vec.xCoord + Stuff.Randomization.r(2), vec.yCoord + Stuff.Randomization.r(2), vec.zCoord + Stuff.Randomization.r(2), Stuff.Randomization.r(1.8), Stuff.Randomization.r(1.8), Stuff.Randomization.r(1.8));
		}
		for(int i = 0; i < 4; ++i)
		{
			projectile.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, true, vec.xCoord + Stuff.Randomization.r(2), vec.yCoord + Stuff.Randomization.r(2), vec.zCoord + Stuff.Randomization.r(2), Stuff.Randomization.r(1.2), Stuff.Randomization.r(1.2), Stuff.Randomization.r(0.2));
		}
		for(int i = 0; i < 2; ++i)
		{
			projectile.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, true, vec.xCoord + Stuff.Randomization.r(2), vec.yCoord + Stuff.Randomization.r(2), vec.xCoord + Stuff.Randomization.r(2), Stuff.Randomization.r(1.3), Stuff.Randomization.r(1.3), Stuff.Randomization.r(0.3));
		}
		
		for(int i2 = 0; i2 < 2; ++i2)
		{
			for(int i = 0; i < 3; ++i)
			{
				projectile.worldObj.spawnParticle(i2 == 0 ? EnumParticleTypes.FLAME : EnumParticleTypes.LAVA, true, vec.xCoord, vec.yCoord, vec.zCoord, Stuff.Randomization.r(1.2 + 0.2 * i / 3), Stuff.Randomization.r(1.3 + 0.2 * i / 3), Stuff.Randomization.r(1.2 + 0.2 * i / 3));
			}
			for(int i = 0; i < 9; ++i)
			{
				projectile.worldObj.spawnParticle(i2 == 0 ? EnumParticleTypes.FLAME : EnumParticleTypes.LAVA, true, vec.xCoord, vec.yCoord, vec.zCoord, Stuff.Randomization.r(0.5 + 0.5 * i / 9), Stuff.Randomization.r(0.6 + 0.7 * i / 9), Stuff.Randomization.r(0.5 + 0.5 * i / 9));
			}
			for(int i = 0; i < 6; ++i)
			{
				projectile.worldObj.spawnParticle(i2 == 0 ? EnumParticleTypes.FLAME : EnumParticleTypes.LAVA, true, vec.xCoord, vec.yCoord, vec.zCoord, Stuff.Randomization.r(0.5), Stuff.Randomization.r(0.6), Stuff.Randomization.r(0.5));
			}
			for(int i = 0; i < 6; ++i)
			{
				projectile.worldObj.spawnParticle(i2 == 0 ? EnumParticleTypes.FLAME : EnumParticleTypes.LAVA, true, vec.xCoord, vec.yCoord, vec.zCoord, Stuff.Randomization.r(1.5), Stuff.Randomization.r(1.8), Stuff.Randomization.r(1.5));
			}
			for(int i = 0; i < 10; ++i)
			{
				projectile.worldObj.spawnParticle(i2 == 0 ? EnumParticleTypes.FLAME : EnumParticleTypes.LAVA, true, vec.xCoord, vec.yCoord, vec.zCoord, Stuff.Randomization.r(2.0), Stuff.Randomization.r(2.3), Stuff.Randomization.r(2.0));
			}
		}
		for(int i = 0; i < 23; ++i)
		{
			projectile.worldObj.spawnParticle(EnumParticleTypes.FLAME, true, vec.xCoord + Stuff.Randomization.r(0.3), vec.yCoord + Stuff.Randomization.r(0.3), vec.zCoord + Stuff.Randomization.r(0.3), Stuff.Randomization.r(1.0), 0.1, Stuff.Randomization.r(1.0));
		}
		projectile.worldObj.playSoundEffect(vec.xCoord + Stuff.Randomization.r(0.3), vec.yCoord + Stuff.Randomization.r(0.3), vec.zCoord + Stuff.Randomization.r(0.3), "fire.fire", 1.0F, 0.6F + 0.3F);
		
		if(!projectile.worldObj.isRemote)
		{
			projectile.setDead();
		}
	}
	
	protected static void damageEntity(EntityThrowable projectile_, Entity entity, float damage)
	{
		EntityPlasmidProjectile projectile = (EntityPlasmidProjectile)projectile_;
		if(!entity.equals(projectile.getThrower()))
		{
			entity.setFire(80);
			
			if(entity instanceof EntityCreeper && !projectile.worldObj.isRemote)
			{
				((EntityCreeper)entity).func_146079_cb();
			}
			DamageSource damagesource = ((IPlasmidProjectile)projectile.getPlasmid()).getDamageSource(projectile);
			damagesource.setFireDamage();
			entity.attackEntityFrom(damagesource, damage + (rand.nextFloat() - 0.5F) * 4);
			if(entity instanceof EntityLivingBase)
			{
				((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.weakness.id, 80, 0, true, false));
				if(projectile.getThrower() != null)
				{
					((EntityLivingBase)entity).knockBack(projectile.getThrower(), 0.5F, -(projectile.getThrower().posX - entity.posX), -(projectile.getThrower().posZ - entity.posZ));
				}
			}
		}
	}
}
