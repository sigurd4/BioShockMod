package com.sigurd4.bioshock.plasmids;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.sigurd4.bioshock.M;
import com.sigurd4.bioshock.Stuff;
import com.sigurd4.bioshock.block.BlockIceMelting;
import com.sigurd4.bioshock.block.BlockIceMelting.EnumMeltType;
import com.sigurd4.bioshock.element.Element;
import com.sigurd4.bioshock.entity.projectile.EntityPlasmidProjectile;

public class PlasmidWinterBlast extends Plasmid implements IPlasmidProjectile
{
	public static float damage = 2;
	
	public PlasmidWinterBlast(String id, int cost1, int cost2, boolean type, String... description)
	{
		super(id, cost1, cost2, type, description);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		for(int i = 0; i < 12; ++i)
		{
			world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, true, player.posX - player.width * rand.nextDouble() + player.width, player.posY - player.height * rand.nextDouble(), player.posZ - player.width * rand.nextDouble() + player.width, 0.0D, 0.0D, 0.0D, new int[]{Block.getIdFromBlock(Blocks.ice)});
		}
		if(!world.isRemote)
		{
			world.spawnEntityInWorld(new EntityPlasmidProjectile(world, player, this));
		}
		player.swingItem();
		Element.addToEntityElement(player, Element.ICE, 30);
		
		return stack;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected)
	{
		
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player)
	{
		for(int i = 0; i < 24; ++i)
		{
			world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, true, player.posX - player.width * rand.nextDouble() + player.width, player.posY - player.height * rand.nextDouble(), player.posZ - player.width * rand.nextDouble() + player.width, 0.0D, 0.0D, 0.0D, new int[]{Block.getIdFromBlock(Blocks.ice)});
		}
		
		if(!world.isRemote)
		{
			player.addPotionEffect(new PotionEffect(Potion.weakness.id, 160, 0, true, false));
			player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 80, 4));
			player.addPotionEffect(new PotionEffect(Potion.blindness.id, 10, 0, true, false));
		}
		Element.setEntityElement(player, Element.WATER, 100, false);
		Element.setEntityElement(player, Element.ICE, 600, false);
		
		EntityPlasmidProjectile e = new EntityPlasmidProjectile(player.worldObj, player, this);
		freeze(e, player);
		e.setDead();
		
		return stack;
	}
	
	@Override
	public double getInitialSpeed()
	{
		return 3.0;
	}
	
	@Override
	public void onImpact(EntityThrowable projectile, MovingObjectPosition pos)
	{
		Vec3 vec = projectile.getPositionVector();
		
		if(pos.entityHit != null)
		{
			if(pos.entityHit instanceof EntityLivingBase)
			{
				vec = pos.entityHit.getPositionVector();
				if(!projectile.worldObj.isRemote)
				{
					for(int x = (int)-Math.floor(pos.entityHit.width / 2); x <= (int)Math.floor(pos.entityHit.width / 2); ++x)
					{
						for(int y = 0; y <= (int)Math.floor(pos.entityHit.height); ++y)
						{
							for(int z = (int)-Math.floor(pos.entityHit.width / 2); z <= (int)Math.floor(pos.entityHit.width / 2); ++z)
							{
								BlockPos coords = projectile.getPosition().add(x, y, z);
								if((Math.sqrt(pos.entityHit.getDistanceSqToCenter(coords)) < 2 + rand.nextFloat() * 2 || rand.nextFloat() * Math.sqrt(pos.entityHit.getDistanceSqToCenter(coords)) / 2 <= 2.5) && projectile.worldObj.getBlockState(coords).getBlock().isReplaceable(projectile.worldObj, coords))
								{
									projectile.worldObj.setBlockState(coords, M.blocks.ice_melting.getDefaultState());
								}
							}
						}
					}
					BlockPos coords = projectile.getPosition();
					if((Math.sqrt(pos.entityHit.getDistanceSqToCenter(coords)) < 2 + rand.nextFloat() * 2 || rand.nextFloat() * Math.sqrt(pos.entityHit.getDistanceSqToCenter(coords)) / 2 <= 2.5) && projectile.worldObj.isAirBlock(coords))
					{
						projectile.worldObj.setBlockState(coords, M.blocks.ice_melting.getDefaultState().withProperty(BlockIceMelting.MELTING, 4));
					}
				}
				
				if(!projectile.worldObj.isRemote)
				{
					projectile.setDead();
				}
			}
		}
		else
		{
			Block block = projectile.worldObj.getBlockState(pos.getBlockPos()).getBlock();
			if(block.isNormalCube())
			{
				switch(pos.sideHit)
				{
				case DOWN:
					vec = new Vec3(vec.xCoord, Math.floor(vec.yCoord), vec.zCoord);
				case UP:
					vec = new Vec3(vec.xCoord, Math.ceil(vec.yCoord), vec.zCoord);
				case NORTH:
					vec = new Vec3(vec.xCoord, vec.zCoord, Math.floor(vec.zCoord));
				case SOUTH:
					vec = new Vec3(vec.xCoord, vec.zCoord, Math.ceil(vec.zCoord));
				case EAST:
					vec = new Vec3(Math.floor(vec.xCoord), vec.yCoord, vec.zCoord);
				case WEST:
					vec = new Vec3(Math.ceil(vec.xCoord), vec.yCoord, vec.zCoord);
				}
			}
			
			if(!projectile.worldObj.isRemote)
			{
				projectile.setDead();
			}
		}
		
		if(projectile.worldObj.isAirBlock(new BlockPos(vec)))
		{
			boolean b = false;
			for(int i = 2; i < 7 && !b; ++i)
			{
				b = projectile.worldObj.isBlockNormalCube(new BlockPos(vec.add(new Vec3(0, -i, 0))), false);
			}
			
			if(b && !projectile.worldObj.isRemote)
			{
				EntityFallingBlock fallingBlock = new EntityFallingBlock(projectile.worldObj, Math.floor(vec.xCoord) + 0.5, Math.floor(vec.yCoord) - 0.5, Math.floor(vec.zCoord) + 0.5, M.blocks.ice_melting.getDefaultState());
				fallingBlock.fallTime = 1;
				fallingBlock.shouldDropItem = false;
				projectile.worldObj.spawnEntityInWorld(fallingBlock);
			}
		}
		
		for(int x = -5; x <= 5; ++x)
		{
			for(int y = -5; y <= 5; ++y)
			{
				for(int z = -5; z <= 5; ++z)
				{
					Vec3 soundPos = Stuff.Coordinates3D.middle(pos.getBlockPos().add(x, y, z));
					double chance = Math.sqrt(x * x + y * y + z * z);
					if(chance < 5 && rand.nextDouble() * chance < 1.6)
					{
						if(projectile.worldObj.canBlockFreeze(projectile.getPosition(), false))
						{
							for(int i = 0; i < 20; ++i)
							{
								float f = 4.8F + Stuff.Randomization.r(4);
								projectile.worldObj.spawnParticle(EnumParticleTypes.BLOCK_CRACK, true, soundPos.xCoord, soundPos.yCoord, soundPos.zCoord, Stuff.Randomization.r(f) - projectile.motionX, Stuff.Randomization.r(f) - projectile.motionY, Stuff.Randomization.r(f) - projectile.motionZ, new int[]{Block.getStateId(M.blocks.ice_melting.getDefaultState())});
							}
							if(!projectile.worldObj.isRemote)
							{
								boolean b = false;
								for(int x2 = -5; x2 <= 5; ++x2)
								{
									for(int y2 = -5; y2 <= 5; ++y2)
									{
										for(int z2 = -5; z2 <= 5; ++z2)
										{
											if(!b)
											{
												b = projectile.worldObj.isAirBlock(pos.getBlockPos().add(x2, y2, z2));
											}
										}
									}
								}
								if(b)
								{
									if(projectile.worldObj.canBlockFreeze(projectile.getPosition(), false))
									{
										projectile.worldObj.setBlockState(pos.getBlockPos().add(x, y, z), M.blocks.ice_melting.getDefaultState().withProperty(BlockIceMelting.MELT_TYPE, projectile.worldObj.getBlockState(pos.getBlockPos().add(x, y, z)).getBlock() instanceof BlockDynamicLiquid ? EnumMeltType.AIR : EnumMeltType.WATER));
									}
								}
							}
							projectile.worldObj.playSoundEffect(soundPos.xCoord, soundPos.yCoord, soundPos.zCoord, "mob.silverfish.say", 0.1F, 0.01F);
						}
					}
				}
			}
		}
		
		for(int i = 0; i < 20; ++i)
		{
			float f = 1.4F;
			projectile.worldObj.spawnParticle(EnumParticleTypes.BLOCK_CRACK, true, vec.xCoord, vec.yCoord, vec.zCoord, Stuff.Randomization.r(f) - projectile.motionX, Stuff.Randomization.r(f) - projectile.motionY, Stuff.Randomization.r(f) - projectile.motionZ, new int[]{Block.getStateId(M.blocks.ice_melting.getDefaultState())});
		}
		for(int i = 0; i < 20; ++i)
		{
			float f = 4.8F + Stuff.Randomization.r(4);
			projectile.worldObj.spawnParticle(EnumParticleTypes.BLOCK_CRACK, true, projectile.posX, projectile.posY, projectile.posZ, Stuff.Randomization.r(f) - projectile.motionX, Stuff.Randomization.r(f) - projectile.motionY, Stuff.Randomization.r(f) - projectile.motionZ, new int[]{Block.getStateId(M.blocks.ice_melting.getDefaultState())});
		}
		for(int i = 0; i < 30; ++i)
		{
			float f = (float)i / 30;
			projectile.worldObj.spawnParticle(EnumParticleTypes.REDSTONE, true, vec.xCoord + Stuff.Randomization.r(0.1 + 1.9 * f), vec.yCoord + Stuff.Randomization.r(0.1 + 1.9 * f), vec.zCoord + Stuff.Randomization.r(0.1 + 1.9 * f), 1, 1, 1);
		}
		for(int i = 0; i < 24; ++i)
		{
			projectile.worldObj.spawnParticle(EnumParticleTypes.SNOWBALL, true, projectile.posX, projectile.posY, projectile.posZ, 0, 0, 0);
		}
	}
	
	@Override
	public void onEntityUpdate(EntityThrowable projectile)
	{
		if(projectile.ticksExisted > 10)
		{
			if(projectile.getThrower() != null)
			{
				if(Math.sqrt((projectile.posX - projectile.getThrower().posX) * (projectile.posX - projectile.getThrower().posX) + (projectile.posZ - projectile.getThrower().posZ) * (projectile.posZ - projectile.getThrower().posZ) + 10 * (projectile.posY - projectile.getThrower().posY) * (projectile.posY - projectile.getThrower().posY)) > 30)
				{
					projectile.setDead();
				}
			}
			else
			{
				projectile.setDead();
			}
		}
		
		for(int i = 0; i < 3; ++i)
		{
			projectile.worldObj.spawnParticle(EnumParticleTypes.SNOWBALL, true, projectile.posX, projectile.posY, projectile.posZ, 0.0D, 0.0D, 0.0D);
		}
		for(int i = 0; i < 12; ++i)
		{
			float f = (float)i / 12;
			projectile.worldObj.spawnParticle(EnumParticleTypes.REDSTONE, true, projectile.posX + Stuff.Randomization.r(0.3 * f), projectile.posY + Stuff.Randomization.r(0.3 * f), projectile.posZ + Stuff.Randomization.r(0.3 * f), 1, 1, 1);
		}
		
		if(projectile.worldObj.canBlockFreeze(projectile.getPosition(), false))
		{
			MovingObjectPosition pos = new MovingObjectPosition(projectile.getPositionVector(), EnumFacing.DOWN);
			this.onImpact(projectile, pos);
		}
		
		if(!projectile.worldObj.isRemote && rand.nextDouble() > 0.6)
		{
			EntityFallingBlock fallingBlock = new EntityFallingBlock(projectile.worldObj, (int)Math.floor(projectile.posX) + 0.5 - projectile.motionX * rand.nextDouble(), (int)Math.floor(projectile.posY) - 0.5 - projectile.motionY * rand.nextDouble(), (int)Math.floor(projectile.posZ) + 0.5 - projectile.motionZ * rand.nextDouble(), M.blocks.snow_melting.getDefaultState());
			fallingBlock.fallTime = 1;
			fallingBlock.shouldDropItem = false;
			projectile.worldObj.spawnEntityInWorld(fallingBlock);
		}
	}
	
	@Override
	public float getGravityVelocity(EntityThrowable projectile)
	{
		return 0.01F;
	}
	
	@Override
	public boolean attackEntityFrom(EntityThrowable projectile, DamageSource src, float dmg)
	{
		if(src.getEntity() instanceof EntityLivingBase && (projectile.getThrower() == null || projectile.getThrower() != src.getEntity()))
		{
			freeze(projectile, (EntityLivingBase)src.getEntity());
		}
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
		return Element.winterBlast(projectile.getThrower());
	}
	
	public static void freeze(EntityLivingBase e)
	{
		EntityThrowable projectile = new EntityPlasmidProjectile(e.worldObj, e.posX, e.posY, e.posZ, M.items.plasmids.injectable.winter_blast.plasmid);
		PlasmidWinterBlast.freeze(projectile, e);
		e.setDead();
	}
	
	protected static void freeze(EntityThrowable projectile_, EntityLivingBase e)
	{
		EntityPlasmidProjectile projectile = (EntityPlasmidProjectile)projectile_;
		projectile.copyLocationAndAnglesFrom(e);
		MovingObjectPosition pos = new MovingObjectPosition(e);
		if(projectile.getPlasmid() instanceof IPlasmidProjectile)
		{
			((IPlasmidProjectile)projectile.getPlasmid()).onImpact(projectile, pos);
		}
	}
}
