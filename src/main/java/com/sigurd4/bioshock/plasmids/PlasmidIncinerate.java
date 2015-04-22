package com.sigurd4.bioshock.plasmids;

import java.util.List;

import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockSnow;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
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
import com.sigurd4.bioshock.item.IItemAlcohol;

public class PlasmidIncinerate extends Plasmid implements IPlasmidProjectile
{
	public static float damage = 2;
	
	public PlasmidIncinerate(String id, int cost1, int cost2, boolean type, String... description)
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
		
		if(!world.isRemote)
		{
			EntityPlasmidProjectile projectile = new EntityPlasmidProjectile(world, player, this);
			world.spawnEntityInWorld(projectile);
			
			int a = rand.nextInt(4 + 1);
			player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, a * 20, 0));
			player.setFire(a);
		}
		player.swingItem();
		Element.addToEntityElement(player, Element.FIRE, 50);
		
		return stack;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected)
	{
		
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player)
	{
		for(int i = 0; i < 5; ++i)
		{
			world.spawnParticle(EnumParticleTypes.LAVA, true, player.posX, player.posY - 1.2, player.posZ, Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1));
		}
		for(int i = 0; i < 4; ++i)
		{
			world.spawnParticle(EnumParticleTypes.LAVA, true, player.posX, player.posY - 1.2, player.posZ, Stuff.Randomization.r(0.5), Stuff.Randomization.r(0.5), Stuff.Randomization.r(0.5));
		}
		
		if(!world.isRemote)
		{
			player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 80, 4, true, false));
			player.addPotionEffect(new PotionEffect(Potion.blindness.id, 20, 0, true, false));
			player.addPotionEffect(new PotionEffect(Potion.weakness.id, 160, 0, true, false));
			player.setFire(4);
		}
		Element.setEntityElement(player, Element.WATER, 0);
		Element.setEntityElement(player, Element.FIRE, 600, false);
		
		return stack;
	}
	
	@Override
	public double getInitialSpeed()
	{
		return 2;
	}
	
	@Override
	public void onImpact(EntityThrowable projectile_, MovingObjectPosition pos)
	{
		EntityPlasmidProjectile projectile = (EntityPlasmidProjectile)projectile_;
		Vec3 vec = projectile.getPositionVector();
		
		if(pos.entityHit != null)
		{
			vec = new Vec3(pos.entityHit.posX, pos.entityHit.posY, pos.entityHit.posZ);
			if(!pos.entityHit.equals(projectile.getThrower()))
			{
				pos.entityHit.setFire(80);
				
				if(pos.entityHit instanceof EntityCreeper && !projectile.worldObj.isRemote)
				{
					((EntityCreeper)pos.entityHit).func_146079_cb();
				}
				DamageSource damagesource = Element.incinerate(projectile.getThrower());
				damagesource.setFireDamage();
				pos.entityHit.attackEntityFrom(damagesource, this.damage + (this.rand.nextFloat() - 0.5F) * 4);
				if(pos.entityHit instanceof EntityLivingBase)
				{
					((EntityLivingBase)pos.entityHit).addPotionEffect(new PotionEffect(Potion.weakness.id, 80, 0, true, false));
					((EntityLivingBase)pos.entityHit).knockBack(projectile.getThrower(), 0.5F, -(projectile.getThrower().posX - pos.entityHit.posX), -(projectile.getThrower().posZ - pos.entityHit.posZ));
				}
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
			if(!projectile.worldObj.isRemote)
			{
				if(projectile.worldObj.getGameRules().getGameRuleBooleanValue("doFireTick"))
				{
					if(projectile.worldObj.isAirBlock(pos.getBlockPos()))
					{
						projectile.worldObj.setBlockState(pos.getBlockPos(), Blocks.fire.getDefaultState());
					}
					for(int x1 = -1; x1 <= 1; ++x1)
					{
						for(int y1 = -1; y1 <= 1; ++y1)
						{
							for(int z1 = -1; z1 <= 1; ++z1)
							{
								if(x1 == 0 && y1 == 0 || x1 == 0 && z1 == 0 || y1 == 0 && z1 == 0)
								{
									BlockPos pos1 = pos.getBlockPos().add(new BlockPos(x1, y1, z1));
									if(projectile.worldObj.isAirBlock(pos1) && this.rand.nextInt(5 + 1) == 0)
									{
										projectile.worldObj.setBlockState(pos.getBlockPos(), Blocks.fire.getDefaultState());
									}
								}
							}
						}
					}
				}
				spawnFlame(projectile, vec);
				spawnFlame(projectile, vec);
				spawnFlame(projectile, vec);
				EntityFlame flame = spawnFlame(projectile, vec);
				flame.setLocationAndAngles(vec.xCoord, vec.yCoord, vec.yCoord, projectile.rotationYaw, projectile.rotationPitch);
				flame.setPosition(vec.xCoord, vec.yCoord, vec.yCoord);
				flame.motionX = -projectile.motionX * 0.1;
				flame.motionY = -projectile.motionY * 0.1;
				flame.motionZ = -projectile.motionZ * 0.1;
				projectile.worldObj.spawnEntityInWorld(flame);
				meltIce(projectile.worldObj, pos.getBlockPos());
				meltSnow(projectile.worldObj, pos.getBlockPos());
				igniteAlcohol(projectile.worldObj, vec);
			}
		}
		setFireToNearbyEntities(projectile, this.damage, vec, 80);
		
		if(projectile.worldObj.getBlockState(pos.getBlockPos()).getBlock() == Blocks.water)
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
			if(!projectile.worldObj.isRemote)
			{
				projectile.setDead();
			}
		}
		else
		{
			for(int i = 0; i < 8; ++i)
			{
				projectile.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, true, vec.xCoord, vec.yCoord, vec.zCoord, Stuff.Randomization.r(0.05), Stuff.Randomization.r(0.05), Stuff.Randomization.r(0.05));
			}
			for(int i = 0; i < 6; ++i)
			{
				projectile.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, true, vec.xCoord, vec.yCoord, vec.zCoord, Stuff.Randomization.r(0.2 + 0.1 * i / 6), Stuff.Randomization.r(0.2 + 0.1 * i / 6), Stuff.Randomization.r(0.2 + 0.1 * i / 6));
			}
			for(int i = 0; i < 34; ++i)
			{
				projectile.worldObj.spawnParticle(EnumParticleTypes.LAVA, true, vec.xCoord, vec.yCoord, vec.zCoord, Stuff.Randomization.r(0.2 + 1.8 * i / 34), Stuff.Randomization.r(0.3 + 2.0 * i / 34), Stuff.Randomization.r(0.2 + 1.8 * i / 34));
			}
			for(int i = 0; i < 24; ++i)
			{
				projectile.worldObj.spawnParticle(EnumParticleTypes.FLAME, true, vec.xCoord, vec.yCoord, vec.zCoord, Stuff.Randomization.r(0.2 + 0.8 * i / 24), Stuff.Randomization.r(0.2 + 0.8 * i / 24), Stuff.Randomization.r(0.2 + 0.8 * i / 24));
			}
			for(int i = 0; i < 19; ++i)
			{
				projectile.worldObj.spawnParticle(EnumParticleTypes.FLAME, true, vec.xCoord + Stuff.Randomization.r(0.3), vec.yCoord, vec.zCoord + Stuff.Randomization.r(0.3), Stuff.Randomization.r(1.0), 0.1, Stuff.Randomization.r(1.0));
			}
			projectile.worldObj.playSoundEffect(vec.xCoord, vec.yCoord, vec.zCoord, "fire.fire", 1.0F, 0.6F + 0.3F);
		}
		
		if(!projectile.worldObj.isRemote)
		{
			if(pos.entityHit != null)
			{
				if(this.rand.nextInt(10 + 1) == 0)
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
	public void onEntityUpdate(EntityThrowable projectile)
	{
		if(projectile.worldObj.getBlockState(projectile.getPosition()).getBlock() == Blocks.water)
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
	}
	
	@Override
	public float getGravityVelocity(EntityThrowable projectile)
	{
		return 0;
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
		return Element.incinerate(projectile.getThrower());
	}
	
	public static EntityFlame spawnFlame(EntityThrowable projectile_, Vec3 vec)
	{
		EntityPlasmidProjectile projectile = (EntityPlasmidProjectile)projectile_;
		EntityFlame flame = null;
		if(projectile.getThrower() != null)
		{
			flame = new EntityFlame(projectile.worldObj, projectile.getThrower(), (PlasmidIncinerate)projectile.getPlasmid());
		}
		else
		{
			flame = new EntityFlame(projectile.worldObj, vec.xCoord, vec.yCoord, vec.zCoord, (PlasmidIncinerate)projectile.getPlasmid());
		}
		flame.setLocationAndAngles(vec.xCoord, vec.yCoord, vec.zCoord, Stuff.Randomization.r(1), Stuff.Randomization.r(1));
		flame.setPosition(vec.xCoord, vec.yCoord, vec.zCoord);
		flame.motionX *= Stuff.Randomization.r(1);
		flame.motionY *= Stuff.Randomization.r(1) + 1;
		flame.motionZ *= Stuff.Randomization.r(1);
		if(!projectile.worldObj.isRemote)
		{
			projectile.worldObj.spawnEntityInWorld(flame);
		}
		return flame;
	}
	
	public static void meltIce(World world, BlockPos pos)
	{
		if(!world.isRemote)
		{
			if(world.getBlockState(pos.up()).getBlock() instanceof BlockIce)
			{
				world.setBlockState(pos.up(), Blocks.flowing_water.getDefaultState());
			}
			if(world.getBlockState(pos.down()).getBlock() instanceof BlockIce)
			{
				world.setBlockState(pos.down(), Blocks.flowing_water.getDefaultState());
			}
			if(world.getBlockState(pos.north()).getBlock() instanceof BlockIce)
			{
				world.setBlockState(pos.north(), Blocks.flowing_water.getDefaultState());
			}
			if(world.getBlockState(pos.south()).getBlock() instanceof BlockIce)
			{
				world.setBlockState(pos.south(), Blocks.flowing_water.getDefaultState());
			}
			if(world.getBlockState(pos.east()).getBlock() instanceof BlockIce)
			{
				world.setBlockState(pos.east(), Blocks.flowing_water.getDefaultState());
			}
			if(world.getBlockState(pos.west()).getBlock() instanceof BlockIce)
			{
				world.setBlockState(pos.west(), Blocks.flowing_water.getDefaultState());
			}
		}
	}
	
	public static void meltSnow(World world, BlockPos pos)
	{
		if(!world.isRemote)
		{
			if(world.getBlockState(pos.up()).getBlock() instanceof BlockSnow && world.getBlockState(pos.up()).getValue(BlockSnow.LAYERS).equals(1) && world.rand.nextInt(2 + 1) == 0)
			{
				world.setBlockState(pos.up(), Blocks.air.getDefaultState());
			}
			if(world.getBlockState(pos.down()).getBlock() instanceof BlockSnow && world.getBlockState(pos.down()).getValue(BlockSnow.LAYERS).equals(1) && world.rand.nextInt(2 + 1) == 0)
			{
				world.setBlockState(pos.down(), Blocks.air.getDefaultState());
			}
			if(world.getBlockState(pos.north()).getBlock() instanceof BlockSnow && world.getBlockState(pos.north()).getValue(BlockSnow.LAYERS).equals(1) && world.rand.nextInt(2 + 1) == 0)
			{
				world.setBlockState(pos.north(), Blocks.air.getDefaultState());
			}
			if(world.getBlockState(pos.south()).getBlock() instanceof BlockSnow && world.getBlockState(pos.south()).getValue(BlockSnow.LAYERS).equals(1) && world.rand.nextInt(2 + 1) == 0)
			{
				world.setBlockState(pos.south(), Blocks.air.getDefaultState());
			}
			if(world.getBlockState(pos.east()).getBlock() instanceof BlockSnow && world.getBlockState(pos.east()).getValue(BlockSnow.LAYERS).equals(1) && world.rand.nextInt(2 + 1) == 0)
			{
				world.setBlockState(pos.east(), Blocks.air.getDefaultState());
			}
			if(world.getBlockState(pos.west()).getBlock() instanceof BlockSnow && world.getBlockState(pos.west()).getValue(BlockSnow.LAYERS).equals(1) && world.rand.nextInt(2 + 1) == 0)
			{
				world.setBlockState(pos.west(), Blocks.air.getDefaultState());
			}
		}
	}
	
	public static void setFireToNearbyEntities(EntityThrowable entity, float damage, Vec3 vec, int fire)
	{
		List<Entity> entities = Stuff.EntitiesInArea.getEntitiesWithinRadius(entity.getEntityWorld(), vec, 3.5);
		for(int i = 0; i < entities.size(); ++i)
		{
			if(!entities.get(i).equals(entity.getThrower()) && entities.get(i) != null)
			{
				entities.get(i).setFire(fire);
				DamageSource damagesource = Element.incinerate(entity.getThrower());
				damagesource.setFireDamage();
				entities.get(i).attackEntityFrom(damagesource, damage + (entity.worldObj.rand.nextFloat() - 0.5F) * 4);
				if(entities.get(i) instanceof EntityLivingBase)
				{
					((EntityLivingBase)entities.get(i)).addPotionEffect(new PotionEffect(Potion.weakness.id, 80, 0, true, false));
				}
				if(entity.worldObj.getGameRules().getGameRuleBooleanValue("doFireTick") && !entity.worldObj.isRemote)
				{
					if(entity.worldObj.isAirBlock(new BlockPos(entities.get(i).posX, entities.get(i).posY, entities.get(i).posZ)))
					{
						entity.worldObj.setBlockState(new BlockPos(entities.get(i).posX, entities.get(i).posY, entities.get(i).posZ), Blocks.fire.getDefaultState());
					}
				}
			}
		}
	}
	
	/**
	 * sets all nearby alcohol consumable items dropped on ground
	 * 
	 * @param world
	 *            The current world
	 * @param x
	 *            X Position
	 * @param y
	 *            Y Position
	 * @param z
	 *            Z Position
	 */
	public static void igniteAlcohol(World world, Vec3 vec)
	{
		List<Entity> entities = Stuff.EntitiesInArea.getEntitiesWithinRadius(world, vec, 6);
		for(int i = entities.size(); i > 0; i--)
		{
			Entity entity2 = entities.get(i - 1);
			if(entity2 instanceof EntityItem)
			{
				EntityItem entityItem = (EntityItem)entity2;
				ItemStack stack = entityItem.getEntityItem();
				if(stack != null && stack.getItem() instanceof IItemAlcohol)
				{
					int fuse = ((IItemAlcohol)stack.getItem()).fuse();
					IItemAlcohol.Variables.ALCOHOLBURNING.set(stack, (int)(fuse + Stuff.Randomization.r(fuse / 4)));
				}
			}
		}
	}
}
