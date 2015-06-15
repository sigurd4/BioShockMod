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
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.sigurd4.bioshock.Stuff;
import com.sigurd4.bioshock.element.Element;
import com.sigurd4.bioshock.entity.projectile.EntityPlasmidBullet;
import com.sigurd4.bioshock.itemtags.ItemTagFloat;

public class PlasmidReturnToSender extends PlasmidHold implements IPlasmidProjectile
{
	public static final ItemTagFloat BULLETSHIELDDAMAGE = new ItemTagFloat("BulletShieldDamage", 0F, 0F, Float.MAX_VALUE, true);
	public static final ItemTagFloat BULLETSHIELDMASS = new ItemTagFloat("BulletShieldPower", 0F, 0F, Float.MAX_VALUE, true);
	public static final ItemTagFloat BULLETSHIELDSPEED = new ItemTagFloat("BulletShieldSpeed", 0F, 0F, Float.MAX_VALUE, true);
	
	public PlasmidReturnToSender(String id, int cost1, int cost2, boolean type, int consumeRate, String... description)
	{
		super(id, cost1, cost2, type, consumeRate, description);
	}
	
	@Override
	public void clicked(ItemStack stack, World world, EntityPlayer player)
	{
		for(int i = 0; i < 25; ++i)
		{
			world.spawnParticle(EnumParticleTypes.REDSTONE, true, player.posX + Stuff.Randomization.r(1), player.posY - 1.0 + Stuff.Randomization.r(1), player.posZ + Stuff.Randomization.r(1), 0, 0.5, 0);
		}
		for(int i = 0; i < 14; ++i)
		{
			world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, true, player.posX + Stuff.Randomization.r(1), player.posY - 1.0 + Stuff.Randomization.r(1), player.posZ + Stuff.Randomization.r(1), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1), new int[]{Block.getIdFromBlock(Blocks.iron_block)});
		}
	}
	
	@Override
	public void notHeld(ItemStack stack, World world, Entity entity, int slot, boolean isSelected)
	{
		
	}
	
	@Override
	public void held(ItemStack stack, World world, Entity entity, int slot, boolean isSelected)
	{
		for(int i = 0; i < 3; ++i)
		{
			world.spawnParticle(EnumParticleTypes.REDSTONE, true, entity.posX + Stuff.Randomization.r(1), entity.posY - 1.0 + Stuff.Randomization.r(1), entity.posZ + Stuff.Randomization.r(1), 0, 0.5, 0);
		}
		for(int i = 0; i < 3; ++i)
		{
			world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, true, entity.posX + Stuff.Randomization.r(1), entity.posY - 1.0 + Stuff.Randomization.r(1), entity.posZ + Stuff.Randomization.r(1), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1), new int[]{Block.getIdFromBlock(Blocks.iron_block)});
		}
	}
	
	@Override
	public void unClicked(ItemStack stack, World world, Entity entity, int slot, boolean isSelected)
	{
		if(BULLETSHIELDSPEED.get(stack) > 0 || BULLETSHIELDMASS.get(stack) > 0 || BULLETSHIELDDAMAGE.get(stack) > 0)
		{
			for(int i = 0; i < 25; ++i)
			{
				world.spawnParticle(EnumParticleTypes.REDSTONE, true, entity.posX + Stuff.Randomization.r(1), entity.posY - 1.0 + Stuff.Randomization.r(1), entity.posZ + Stuff.Randomization.r(1), 0, 0.5F, 0);
			}
			for(int i = 0; i < 14; ++i)
			{
				world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, true, entity.posX + Stuff.Randomization.r(1), entity.posY - 1.0 + Stuff.Randomization.r(1), entity.posZ + Stuff.Randomization.r(1), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1), new int[]{Block.getIdFromBlock(Blocks.iron_block)});
			}
			if(entity instanceof EntityLivingBase)
			{
				EntityPlasmidBullet projectile = new EntityPlasmidBullet(world, (EntityLivingBase)entity, this, BULLETSHIELDSPEED.get(stack), BULLETSHIELDMASS.get(stack), BULLETSHIELDDAMAGE.get(stack), 4 / 16);
				if(!world.isRemote)
				{
					world.spawnEntityInWorld(projectile);
				}
			}
		}
		BULLETSHIELDDAMAGE.set(stack, 0F);
		BULLETSHIELDMASS.set(stack, 0F);
		BULLETSHIELDSPEED.set(stack, 0F);
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player)
	{
		for(int i = 0; i < 5; ++i)
		{
			world.spawnParticle(EnumParticleTypes.REDSTONE, true, player.posX + Stuff.Randomization.r(1), player.posY - 1.0 + Stuff.Randomization.r(1), player.posZ + Stuff.Randomization.r(1), 0, 0.5F, 0);
		}
		for(int i = 0; i < 40; ++i)
		{
			world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, true, player.posX + Stuff.Randomization.r(1), player.posY - 1.0 + Stuff.Randomization.r(1), player.posZ + Stuff.Randomization.r(1), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1), new int[]{Block.getIdFromBlock(Blocks.iron_block)});
		}
		
		if(!world.isRemote)
		{
			player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 160, 2));
			player.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 320, 10, true, false));
			player.addPotionEffect(new PotionEffect(Potion.resistance.id, 80, 1, true, false));
		}
		
		return stack;
	}
	
	@Override
	public double getInitialSpeed()
	{
		return 1;
	}
	
	@Override
	public void onImpact(EntityThrowable projectile_, MovingObjectPosition pos)
	{
		EntityPlasmidBullet projectile = (EntityPlasmidBullet)projectile_;
		for(int i = 0; i < 32; ++i)
		{
			projectile.worldObj.spawnParticle(EnumParticleTypes.BLOCK_CRACK, true, projectile.posX + Stuff.Randomization.r(1), projectile.posY - 1.0 + Stuff.Randomization.r(1), projectile.posZ + Stuff.Randomization.r(1), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1), new int[]{Block.getStateId(Blocks.iron_block.getDefaultState())});
		}
		projectile.worldObj.playSoundAtEntity(projectile, "step.stone", 0.1F + projectile.force() / 40, 2);
		projectile.worldObj.playSoundAtEntity(projectile, "random.anvil_land", this.rand.nextFloat() * (0.02F + projectile.force() / 8), 1 + this.rand.nextFloat());
	}
	
	@Override
	public void onEntityUpdate(EntityThrowable projectile)
	{
		
	}
	
	@Override
	public float getGravityVelocity(EntityThrowable projectile)
	{
		return 0.03F;
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
		return Element.returnToSender(projectile.getThrower());
	}
	
	@Override
	public boolean isInRangeToRenderDist(EntityThrowable projectile, double distance)
	{
		return true;
	}
}
