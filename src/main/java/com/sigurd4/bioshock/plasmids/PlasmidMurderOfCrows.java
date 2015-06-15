package com.sigurd4.bioshock.plasmids;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.AxisDirection;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.sigurd4.bioshock.Stuff;
import com.sigurd4.bioshock.element.Element;
import com.sigurd4.bioshock.entity.monster.EntityCrowBird;
import com.sigurd4.bioshock.entity.projectile.EntityPlasmidProjectile;

public class PlasmidMurderOfCrows extends Plasmid implements IPlasmidProjectile
{
	public PlasmidMurderOfCrows(String id, int cost1, int cost2, boolean type, String... description)
	{
		super(id, cost1, cost2, type, description);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		for(int i = 0; i < 16; ++i)
		{
			world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, true, player.posX + Stuff.Randomization.r(0.3), player.posY + player.getEyeHeight() + Stuff.Randomization.r(0.3), player.posZ + Stuff.Randomization.r(0.3), Stuff.Randomization.r(0.03), Stuff.Randomization.r(0.03), Stuff.Randomization.r(0.03));
		}
		for(int i = 0; i < 8; ++i)
		{
			world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, true, player.posX + Stuff.Randomization.r(0.5), player.posY + player.getEyeHeight() + Stuff.Randomization.r(0.5), player.posZ + Stuff.Randomization.r(0.5), Stuff.Randomization.r(0.03), Stuff.Randomization.r(0.03), Stuff.Randomization.r(0.03));
		}
		for(int i = 0; i < 22; ++i)
		{
			world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, true, player.posX + Stuff.Randomization.r(1), player.posY - 1.0 + Stuff.Randomization.r(1), player.posZ + Stuff.Randomization.r(1), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1));
		}
		for(int i = 0; i < 11; ++i)
		{
			world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, true, player.posX + Stuff.Randomization.r(0.5), player.posY - 1.0 + Stuff.Randomization.r(0.5), player.posZ + Stuff.Randomization.r(0.5), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1));
		}
		for(int i = 0; i < 8; ++i)
		{
			world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, true, player.posX + Stuff.Randomization.r(0.5), player.posY - 1.0 + Stuff.Randomization.r(1), player.posZ + Stuff.Randomization.r(0.5), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1));
		}
		
		if(!world.isRemote)
		{
			world.spawnEntityInWorld(new EntityPlasmidProjectile(world, player, this));
			
			player.addPotionEffect(new PotionEffect(Potion.poison.id, 20, 0, true, false));
			player.addPotionEffect(new PotionEffect(Potion.hunger.id, 80, 0, true, false));
			player.addPotionEffect(new PotionEffect(Potion.blindness.id, 20, 0));
			player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 10, 5, true, false));
			player.addPotionEffect(new PotionEffect(Potion.confusion.id, 20, 0, true, false));
			player.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 160, 0, true, false));
			player.addPotionEffect(new PotionEffect(Potion.weakness.id, 80, 0, true, false));
		}
		player.swingItem();
		
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
			world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, true, player.posX + Stuff.Randomization.r(0.3), player.posY + player.getEyeHeight() + Stuff.Randomization.r(0.3), player.posZ + Stuff.Randomization.r(0.3), Stuff.Randomization.r(0.03), Stuff.Randomization.r(0.03), Stuff.Randomization.r(0.03));
		}
		for(int i = 0; i < 32; ++i)
		{
			world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, true, player.posX + Stuff.Randomization.r(0.5), player.posY + player.getEyeHeight() + Stuff.Randomization.r(0.5), player.posZ + Stuff.Randomization.r(0.5), Stuff.Randomization.r(0.03), Stuff.Randomization.r(0.03), Stuff.Randomization.r(0.03));
		}
		for(int i = 0; i < 44; ++i)
		{
			world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, true, player.posX + Stuff.Randomization.r(1), player.posY - 1.0 + Stuff.Randomization.r(1), player.posZ + Stuff.Randomization.r(1), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1));
		}
		for(int i = 0; i < 22; ++i)
		{
			world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, true, player.posX + Stuff.Randomization.r(0.5), player.posY - 1.0 + Stuff.Randomization.r(0.5), player.posZ + Stuff.Randomization.r(0.5), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1));
		}
		for(int i = 0; i < 16; ++i)
		{
			world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, true, player.posX + Stuff.Randomization.r(0.5), player.posY - 1.0 + Stuff.Randomization.r(1), player.posZ + Stuff.Randomization.r(0.5), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1));
		}
		
		if(!world.isRemote)
		{
			player.addPotionEffect(new PotionEffect(Potion.poison.id, 160, 0, true, false));
			player.addPotionEffect(new PotionEffect(Potion.hunger.id, 480, 1, true, false));
			player.addPotionEffect(new PotionEffect(Potion.blindness.id, 160, 0));
			player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 160, 5, true, false));
			player.addPotionEffect(new PotionEffect(Potion.confusion.id, 160, 0, true, false));
			player.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 320, 0, true, false));
			player.addPotionEffect(new PotionEffect(Potion.weakness.id, 320, 0, true, false));
		}
		
		return stack;
	}
	
	@Override
	public double getInitialSpeed()
	{
		return 1.2;
	}
	
	@Override
	public void onImpact(EntityThrowable projectile_, MovingObjectPosition pos)
	{
		EntityPlasmidProjectile projectile = (EntityPlasmidProjectile)projectile_;
		Vec3 vec = projectile.getPositionVector();
		
		if(pos.entityHit != null)
		{
			vec = new Vec3(pos.entityHit.posX, pos.entityHit.posY, pos.entityHit.posZ);
			if(pos.entityHit instanceof EntityLivingBase)
			{
				((EntityLivingBase)pos.entityHit).addPotionEffect(new PotionEffect(Potion.blindness.id, 160, 0, true, false));
			}
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
		}
		for(double i = 0; i < 1; i += 0.5D)
		{
			for(int i2 = 0; i2 < 5; ++i2)
			{
				float f = (float)i2 / 5;
				projectile.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, true, projectile.posX + Stuff.Randomization.r(0.3), projectile.posY + Stuff.Randomization.r(0.3), projectile.posZ + Stuff.Randomization.r(0.3), Stuff.Randomization.r(0.1 + i + f * 0.1), Stuff.Randomization.r(0.1 + f * 0.1) + 0.3 + f * 0.2, Stuff.Randomization.r(0.1 + i + f * 0.1));
			}
			for(int i2 = 0; i2 < 16; ++i2)
			{
				float f = (float)i2 / 16;
				projectile.worldObj.spawnParticle(EnumParticleTypes.BLOCK_CRACK, true, projectile.posX + Stuff.Randomization.r(0.3), projectile.posY + Stuff.Randomization.r(0.3), projectile.posZ + Stuff.Randomization.r(0.3), Stuff.Randomization.r(0.2 + i), Stuff.Randomization.r(0.2 + i) + 0.2 + 0.3 * f, Stuff.Randomization.r(0.2 + i), new int[]{Block.getStateId(Blocks.obsidian.getDefaultState())});
			}
			for(int i2 = 0; i2 < 8; ++i2)
			{
				float f = (float)i2 / 8;
				projectile.worldObj.spawnParticle(EnumParticleTypes.BLOCK_CRACK, true, projectile.posX + Stuff.Randomization.r(0.3), projectile.posY + Stuff.Randomization.r(0.3), projectile.posZ + Stuff.Randomization.r(0.3), Stuff.Randomization.r(0.2 + i), Stuff.Randomization.r(0.2 + i) + 0.2 + 0.3 * f, Stuff.Randomization.r(0.2 + i), new int[]{Block.getStateId(Blocks.coal_block.getDefaultState())});
			}
			for(int i2 = 0; i2 < 3; ++i2)
			{
				projectile.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, true, projectile.posX + Stuff.Randomization.r(0.3), projectile.posY + Stuff.Randomization.r(0.3) + 0.2, projectile.posZ + Stuff.Randomization.r(0.3), Stuff.Randomization.r(0.1 + i), Stuff.Randomization.r(0.1 + i), Stuff.Randomization.r(0.1 + i));
			}
			for(int i2 = 0; i2 < 6; ++i2)
			{
				float f = (float)i2 / 6;
				projectile.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, true, projectile.posX + Stuff.Randomization.r(0.2), projectile.posY + Stuff.Randomization.r(0.2) + 0.2, projectile.posZ + Stuff.Randomization.r(0.2), Stuff.Randomization.r(0.2 + i + f * 0.2), Stuff.Randomization.r(0.2 + i + f * 0.2), Stuff.Randomization.r(0.2 + i + f * 0.2));
			}
		}
		for(int i = 0; i < 5; ++i)
		{
			this.summonCrow(projectile, vec, pos);
		}
		projectile.setDead();
	}
	
	@Override
	public void onEntityUpdate(EntityThrowable projectile)
	{
		for(int i = 0; i < 4; ++i)
		{
			projectile.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, true, projectile.posX + Stuff.Randomization.r(0.3), projectile.posY + Stuff.Randomization.r(0.3), projectile.posZ + Stuff.Randomization.r(0.3), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1));
		}
		for(int i = 0; i < 7; ++i)
		{
			projectile.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, true, projectile.posX + Stuff.Randomization.r(0.01), projectile.posY + Stuff.Randomization.r(0.01), projectile.posZ + Stuff.Randomization.r(0.01), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1));
		}
		for(int i = 0; i < 6; ++i)
		{
			projectile.worldObj.spawnParticle(EnumParticleTypes.BLOCK_CRACK, true, projectile.posX + Stuff.Randomization.r(0.3), projectile.posY + Stuff.Randomization.r(0.3), projectile.posZ + Stuff.Randomization.r(0.3), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1), new int[]{Block.getStateId(Blocks.coal_block.getDefaultState())});
		}
		for(int i = 0; i < 12; ++i)
		{
			projectile.worldObj.spawnParticle(EnumParticleTypes.BLOCK_CRACK, true, projectile.posX + Stuff.Randomization.r(0.3), projectile.posY + Stuff.Randomization.r(0.3), projectile.posZ + Stuff.Randomization.r(0.3), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1), new int[]{Block.getStateId(Blocks.obsidian.getDefaultState())});
		}
	}
	
	@Override
	public float getGravityVelocity(EntityThrowable projectile)
	{
		return 0.03F;
	}
	
	@Override
	public boolean attackEntityFrom(EntityThrowable projectile, DamageSource src, float dmg)
	{
		this.onImpact(projectile, new MovingObjectPosition(projectile.getPositionVector(), EnumFacing.random(rand)));
		return projectile.isDead;
	}
	
	@Override
	public boolean interactFirst(EntityThrowable projectile, EntityPlayer player)
	{
		return false;
	}
	
	@Override
	public DamageSource getDamageSource(EntityThrowable projectile)
	{
		return Element.murderOfCrows(projectile);
	}
	
	protected static void summonCrow(EntityThrowable projectile, Vec3 vec, MovingObjectPosition pos)
	{
		EntityCrowBird crow = new EntityCrowBird(projectile.worldObj, projectile.getThrower());
		crow.setLocationAndAngles(vec.xCoord + Stuff.Randomization.r(0.2), vec.yCoord + Stuff.Randomization.r(0.2), vec.zCoord + Stuff.Randomization.r(0.2), projectile.rotationYaw + Stuff.Randomization.r(50), 0.0F);
		if(projectile.getThrower() instanceof EntityPlayer)
		{
			crow.throwerName = ((EntityPlayer)projectile.getThrower()).getName();
		}
		crow.target = pos.entityHit;
		projectile.worldObj.spawnEntityInWorld(crow);
	}
	
	@Override
	public boolean isInRangeToRenderDist(EntityThrowable projectile, double distance)
	{
		return false;
	}
}
