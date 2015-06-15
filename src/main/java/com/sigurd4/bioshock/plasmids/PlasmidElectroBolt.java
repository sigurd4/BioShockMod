package com.sigurd4.bioshock.plasmids;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
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
import com.sigurd4.bioshock.entity.EntityWaterElectro;
import com.sigurd4.bioshock.entity.projectile.EntityPlasmidProjectile;

public class PlasmidElectroBolt extends Plasmid implements IPlasmidProjectile
{
	public static float damage = 1;
	
	public PlasmidElectroBolt(String id, int cost1, int cost2, boolean type, String... description)
	{
		super(id, cost1, cost2, type, description);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		world.playSoundEffect(player.posX, player.posY, player.posZ, "mob.silverfish.say", 4.0F, 1.2F);
		for(int i = 0; i < 6; ++i)
		{
			world.spawnParticle(EnumParticleTypes.REDSTONE, true, player.posX + Stuff.Randomization.r(2), player.posY + player.height / 2 + Stuff.Randomization.r(2), player.posZ + Stuff.Randomization.r(2), 0.4, 0.9, 1.0);
		}
		for(int i = 0; i < 8; ++i)
		{
			world.spawnParticle(EnumParticleTypes.REDSTONE, true, player.posX + Stuff.Randomization.r(0.5), player.posY + player.height / 2 + Stuff.Randomization.r(2), player.posZ + Stuff.Randomization.r(0.5), 0.4, 0.9, 1.0);
		}
		
		if(!world.isRemote)
		{
			world.spawnEntityInWorld(new EntityPlasmidProjectile(world, player, this));
		}
		player.swingItem();
		Element.setEntityElement(player, Element.ELECTRICITY, 50, false);
		
		return stack;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected)
	{
		
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player)
	{
		for(int i = 0; i < 6; ++i)
		{
			world.spawnParticle(EnumParticleTypes.REDSTONE, true, player.posX + Stuff.Randomization.r(2), player.posY + player.height / 2 + Stuff.Randomization.r(2), player.posZ + Stuff.Randomization.r(2), 0.4, 0.9, 1.0);
		}
		for(int i = 0; i < 12; ++i)
		{
			world.spawnParticle(EnumParticleTypes.REDSTONE, true, player.posX + Stuff.Randomization.r(0.5), player.posY + player.height / 2 + Stuff.Randomization.r(2), player.posZ + Stuff.Randomization.r(0.5), 0.4, 0.9, 1.0);
		}
		
		if(!world.isRemote)
		{
			player.addPotionEffect(new PotionEffect(Potion.weakness.id, 160, 0, true, false));
			player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 80, 4));
			player.addPotionEffect(new PotionEffect(Potion.blindness.id, 10, 0, true, false));
		}
		Element.setEntityElement(player, Element.ELECTRICITY, 400, false);
		
		return stack;
	}
	
	@Override
	public double getInitialSpeed()
	{
		return 2.0;
	}
	
	@Override
	public void onImpact(EntityThrowable projectile_, MovingObjectPosition pos)
	{
		EntityPlasmidProjectile projectile = (EntityPlasmidProjectile)projectile_;
		Vec3 vec = projectile.getPositionVector();
		
		if(pos.entityHit != null)
		{
			int a = this.rand.nextInt(40 + 1) + 30;
			vec = pos.entityHit.getPositionVector();
			DamageSource damagesource = ((IPlasmidProjectile)projectile.getPlasmid()).getDamageSource(projectile);
			if(pos.entityHit.isInWater() || projectile.isInsideOfMaterial(Material.water))
			{
				pos.entityHit.attackEntityFrom(damagesource, this.damage * 4 + this.rand.nextFloat() * 4);
				if(pos.entityHit instanceof EntityLivingBase)
				{
					Element.setEntityElement((EntityLivingBase)pos.entityHit, Element.ELECTRICITY, 500, false);
				}
			}
			else if(projectile.worldObj.isRaining() && projectile.worldObj.canLightningStrike(new BlockPos(vec)) || pos.entityHit.isWet())
			{
				pos.entityHit.attackEntityFrom(damagesource, this.damage * 2 + this.rand.nextFloat() * 3);
			}
			else
			{
				pos.entityHit.attackEntityFrom(damagesource, this.damage + this.rand.nextFloat() * 2);
			}
			if(pos.entityHit instanceof EntityLivingBase)
			{
				Element.setEntityElement((EntityLivingBase)pos.entityHit, Element.ELECTRICITY, 300, false);
				
				((EntityLivingBase)pos.entityHit).addPotionEffect(new PotionEffect(Potion.weakness.id, a, 2));
				((EntityLivingBase)pos.entityHit).addPotionEffect(new PotionEffect(Potion.blindness.id, a - (int)(this.rand.nextFloat() * a / 1.8), 0, true, false));
				((EntityLivingBase)pos.entityHit).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, a, 8, true, false));
				
				if(pos.entityHit instanceof EntityCreeper)
				{
					((EntityLivingBase)pos.entityHit).onStruckByLightning(new EntityLightningBolt(projectile.worldObj, projectile.posX, projectile.posZ, projectile.posY));
				}
			}
			if(!projectile.worldObj.isRemote)
			{
				for(int i = 0; i < Math.ceil(pos.entityHit.height); ++i)
				{
					electrifyWater(projectile, new Vec3(0, i, 0));
				}
			}
		}
		else
		{
			vec = Stuff.Coordinates3D.middle(pos.getBlockPos());
			
			switch(pos.sideHit.getAxis())
			{
			case Y:
				vec = vec.addVector(0, 0.5 * (pos.sideHit.getAxisDirection() == AxisDirection.POSITIVE ? 1 : -1), 0);
				break;
			case Z:
				vec = vec.addVector(0, 0, 0.5 * (pos.sideHit.getAxisDirection() == AxisDirection.POSITIVE ? 1 : -1));
				break;
			case X:
				vec = vec.addVector(0.5 * (pos.sideHit.getAxisDirection() == AxisDirection.POSITIVE ? 1 : -1), 0, 0);
				break;
			}
			
			if(!projectile.worldObj.isRemote)
			{
				this.electrifyWater(projectile, new Vec3(0, 0, 0));
			}
		}
		
		if(projectile.worldObj.getBlockState(new BlockPos(vec)).getBlock().getMaterial() == Material.water)
		{
			for(int i = 0; i < 12; ++i)
			{
				projectile.worldObj.spawnParticle(EnumParticleTypes.REDSTONE, true, vec.xCoord + Stuff.Randomization.r(this.rand.nextDouble() * 2), vec.yCoord + Stuff.Randomization.r(this.rand.nextDouble() * 2), vec.zCoord + Stuff.Randomization.r(this.rand.nextDouble() * 2), 0.4, 0.9, 1.0);
			}
			projectile.worldObj.playSoundEffect(vec.xCoord, vec.yCoord, vec.zCoord, "random.fizz", 1.0F, 0.6F + 0.3F);
			projectile.worldObj.playSoundEffect(vec.xCoord, vec.yCoord, vec.zCoord, "mob.enderman.idle", 4.0F, 0.6F);
			projectile.worldObj.playSoundEffect(vec.xCoord, vec.yCoord, vec.zCoord, "mob.silverfish.say", 4.0F, 0.6F);
		}
		else
		{
			for(int i = 0; i < 7; ++i)
			{
				projectile.worldObj.spawnParticle(EnumParticleTypes.REDSTONE, true, vec.xCoord + Stuff.Randomization.r(2), vec.yCoord + Stuff.Randomization.r(2), vec.zCoord + Stuff.Randomization.r(2), 0.4, 0.9, 1.0);
			}
			projectile.worldObj.playSoundEffect(vec.xCoord, vec.yCoord, vec.zCoord, "mob.enderman.idle", 4.0F, 0.6F);
			projectile.worldObj.playSoundEffect(vec.xCoord, vec.yCoord, vec.zCoord, "mob.silverfish.say", 4.0F, 0.6F);
			if(!projectile.worldObj.isRemote)
			{
				projectile.setDead();
			}
		}
		for(int i = 0; i < 7; ++i)
		{
			projectile.worldObj.spawnParticle(EnumParticleTypes.REDSTONE, true, vec.xCoord + Stuff.Randomization.r(0.1), vec.yCoord + Stuff.Randomization.r(0.1), vec.zCoord + Stuff.Randomization.r(0.01), 0.5, 0.97, 1.0);
		}
		
		for(int i = 0; i < 7; ++i)
		{
			projectile.worldObj.spawnParticle(EnumParticleTypes.REDSTONE, true, vec.xCoord, vec.yCoord, vec.zCoord, 0.5, 0.97, 1.0);
		}
	}
	
	@Override
	public void onEntityUpdate(EntityThrowable projectile_)
	{
		EntityPlasmidProjectile projectile = (EntityPlasmidProjectile)projectile_;
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
		if(projectile.worldObj.getBlockState(new BlockPos(projectile.getPositionVector())) == Blocks.water)
		{
			for(int i = 0; i < 5; ++i)
			{
				projectile.worldObj.spawnParticle(EnumParticleTypes.REDSTONE, true, projectile.posX + Stuff.Randomization.r(2), projectile.posY + Stuff.Randomization.r(2), projectile.posZ + Stuff.Randomization.r(2), 0.4, 0.9, 1.0);
			}
			
			if(!projectile.worldObj.isRemote)
			{
				electrifyWater(projectile, new Vec3(0, 0, 0));
				projectile.setDead();
			}
		}
		projectile.worldObj.spawnParticle(EnumParticleTypes.REDSTONE, true, projectile.posX, projectile.posY, projectile.posZ, 0.4, 0.9, 1.0);
		for(int i = 0; i < 4; ++i)
		{
			projectile.worldObj.spawnParticle(EnumParticleTypes.REDSTONE, true, projectile.posX + Stuff.Randomization.r(0.3), projectile.posY + Stuff.Randomization.r(0.3), projectile.posZ + Stuff.Randomization.r(0.3), 0.4, 0.9, 1.0);
		}
		for(int i = 0; i < 7; ++i)
		{
			projectile.worldObj.spawnParticle(EnumParticleTypes.REDSTONE, true, projectile.posX + Stuff.Randomization.r(0.01), projectile.posY + Stuff.Randomization.r(0.01), projectile.posZ + Stuff.Randomization.r(0.01), 0.5, 0.97, 1.0);
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
		return Element.electroBolt(projectile.getThrower());
	}
	
	protected static void electrifyWater(EntityThrowable projectile_, Vec3 vec)
	{
		EntityPlasmidProjectile projectile = (EntityPlasmidProjectile)projectile_;
		BlockPos pos = null;
		if(projectile.worldObj.getBlockState(projectile.getPosition()).getBlock().getMaterial() == Material.water)
		{
			pos = projectile.getPosition();
		}
		for(int x1 = -1; x1 <= 1 && pos == null; ++x1)
		{
			for(int y1 = -1; y1 <= 1 && pos == null; ++y1)
			{
				for(int z1 = -1; z1 <= 1 && pos == null; ++z1)
				{
					if(x1 == 0 && y1 == 0 || x1 == 0 && z1 == 0 || y1 == 0 && z1 == 0)
					{
						BlockPos pos1 = projectile.getPosition().add(new BlockPos(x1, y1, z1));
						if(projectile.worldObj.getBlockState(pos1).getBlock().getMaterial() == Material.water)
						{
							pos = pos1;
						}
					}
				}
			}
		}
		if(pos != null)
		{
			EntityWaterElectro electro = new EntityWaterElectro(projectile.worldObj, 3, projectile.getThrower(), damage, projectile.getPlasmid());
			electro.maxTime = 16;
			electro.spawnLimiter = 3;
			electro.damage = damage;
			electro.thrower = projectile.getThrower();
			
			if(projectile.worldObj.getBlockState(pos).getBlock().getMaterial() == Material.water)
			{
				electro.setLocationAndAngles(pos.getX() + 0.5 + vec.xCoord, pos.getY() + vec.yCoord, pos.getZ() + 0.5 + vec.zCoord, 0, 0.0F);
				projectile.worldObj.spawnEntityInWorld(electro);
			}
		}
	}
	
	@Override
	public boolean isInRangeToRenderDist(EntityThrowable projectile, double distance)
	{
		return false;
	}
}
