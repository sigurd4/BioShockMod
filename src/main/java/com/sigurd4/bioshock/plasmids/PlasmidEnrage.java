package com.sigurd4.bioshock.plasmids;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.sigurd4.bioshock.M;
import com.sigurd4.bioshock.Stuff;
import com.sigurd4.bioshock.element.Element;
import com.sigurd4.bioshock.entity.projectile.EntityPlasmidProjectile;

public class PlasmidEnrage extends Plasmid implements IPlasmidProjectile
{
	public PlasmidEnrage(String id, int cost1, int cost2, boolean type, String... description)
	{
		super(id, cost1, cost2, type, description);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		world.playSoundAtEntity(player, "mob.slime.small", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F) / 0.8F);
		world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (rand.nextFloat() * 0.4F + 0.8F));
		world.playSoundAtEntity(player, "mob.wolf.growl", 0.2F, 0.4F / (rand.nextFloat() * 0.4F + 1.6F));
		
		if(!world.isRemote)
		{
			world.spawnEntityInWorld(new EntityPlasmidProjectile(world, player, this));
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
		for(int i = 0; i < 4; ++i)
		{
			world.spawnParticle(EnumParticleTypes.REDSTONE, true, player.posX + Stuff.Randomization.r(2), player.posY + player.height / 2 + Stuff.Randomization.r(2), player.posZ + Stuff.Randomization.r(2), -0.4, 0.0, 0.0);
		}
		for(int i = 0; i < 6; ++i)
		{
			world.spawnParticle(EnumParticleTypes.REDSTONE, true, player.posX + Stuff.Randomization.r(0.5), player.posY + player.height / 2 + Stuff.Randomization.r(2), player.posZ + Stuff.Randomization.r(0.5), -0.4, 0.0, 0.0);
		}
		
		for(int i = 0; i < 3; ++i)
		{
			world.playSoundAtEntity(player, "mob.slime.small", 0.4F, ((rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F) / 0.8F);
			world.playSoundAtEntity(player, "mob.wolf.growl", 0.4F, 0.4F / (rand.nextFloat() * 0.4F + 1.6F));
		}
		
		if(!world.isRemote)
		{
			player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 160, 0));
			player.addPotionEffect(new PotionEffect(Potion.blindness.id, 80, 0, true, false));
			player.addPotionEffect(new PotionEffect(Potion.poison.id, 300, 0, true, false));
		}
		Element.setEntityElement(player, Element.FIRE, 60, false);
		
		return stack;
	}
	
	@Override
	public double getInitialSpeed()
	{
		return 1.5;
	}
	
	@Override
	public void onImpact(EntityThrowable projectile_, MovingObjectPosition pos)
	{
		EntityPlasmidProjectile projectile = (EntityPlasmidProjectile)projectile_;
		if(pos.entityHit != null && pos.entityHit instanceof EntityLivingBase)
		{
			if(pos.entityHit instanceof EntityPlayer)
			{
				pos.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(projectile, projectile.getThrower()), 0.0F);
				if(!projectile.worldObj.isRemote)
				{
					((EntityLiving)pos.entityHit).addPotionEffect(new PotionEffect(Potion.confusion.id, this.rand.nextInt(20 + 1) + 60, 0, true, false));
				}
			}
			else
			{
				EntityLivingBase blamedEntity = blameEntity(projectile, 1, (EntityLivingBase)pos.entityHit, 3, 50, 0);
				if(blamedEntity == null)
				{
					blamedEntity = blameEntity(projectile, 1, (EntityLivingBase)pos.entityHit, 20, 200, 0);
				}
				if(blamedEntity == null)
				{
					blamedEntity = projectile.getThrower();
				}
				pos.entityHit.attackEntityFrom(((IPlasmidProjectile)projectile.getPlasmid()).getDamageSource(projectile), 0.0F);
				if(!(blamedEntity instanceof EntityPlayer) || !((EntityPlayer)blamedEntity).capabilities.isCreativeMode)
				{
					((EntityLiving)pos.entityHit).setAttackTarget(blamedEntity);
				}
				if(pos.entityHit instanceof EntityWolf)
				{
					((EntityWolf)pos.entityHit).setAngry(true);
				}
				if(pos.entityHit instanceof EntityMob)
				{
					if(!(blamedEntity instanceof EntityPlayer) || !((EntityPlayer)blamedEntity).capabilities.isCreativeMode)
					{
						((EntityMob)pos.entityHit).setRevengeTarget(blamedEntity);
						if(((EntityMob)pos.entityHit).getEntityAttribute(SharedMonsterAttributes.followRange) != null && ((EntityMob)pos.entityHit).getEntityAttribute(SharedMonsterAttributes.followRange).getBaseValue() < pos.entityHit.getDistanceToEntity(((EntityMob)pos.entityHit).getAITarget()))
						{
							((EntityMob)pos.entityHit).getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(pos.entityHit.getDistanceToEntity(((EntityMob)pos.entityHit).getAITarget()));
						}
					}
				}
				
				if(!projectile.worldObj.isRemote)
				{
					((EntityLivingBase)pos.entityHit).addPotionEffect(new PotionEffect(Potion.damageBoost.id, this.rand.nextInt(60 + 1) + 360, 0));
				}
			}
			
			for(int i = 0; i < 6; ++i)
			{
				projectile.worldObj.spawnParticle(EnumParticleTypes.REDSTONE, true, projectile.posX + Stuff.Randomization.r(1.0), projectile.posY + Stuff.Randomization.r(2.5), projectile.posZ + Stuff.Randomization.r(1.0), 0.0D, 0.0D, 0.0D);
			}
		}
		
		M.otherIconsIndex = 0;
		Item item = M.items.crafting.gear_steel;
		for(int i = 0; i < 24; ++i)
		{
			projectile.worldObj.spawnParticle(EnumParticleTypes.ITEM_CRACK, true, projectile.posX, projectile.posY, projectile.posZ, 0.0D, 5.0D, 0.0D, Item.getIdFromItem(item));
			projectile.worldObj.spawnParticle(EnumParticleTypes.REDSTONE, true, projectile.posX + Stuff.Randomization.r(0.1), projectile.posY + Stuff.Randomization.r(0.1), projectile.posZ + Stuff.Randomization.r(0.1), 0.0D, 0.0D, 0.0D);
		}
		M.otherIconsIndex = -1;
		
		projectile.worldObj.playSoundAtEntity(projectile, "mob.slime.small", 0.4F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) / 0.8F);
		
		if(!projectile.worldObj.isRemote)
		{
			projectile.setDead();
		}
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
		return new EntityDamageSourceIndirect(this.id, projectile, projectile.getThrower());
	}
	
	protected static EntityLivingBase blameEntity(EntityThrowable projectile, int range, EntityLivingBase target, int limitFar, int limitClose, int limiter)
	{
		if(range > limitFar || limiter > limitFar)
		{
			return null;
		}
		List<Entity> entities = target.worldObj.getEntitiesWithinAABBExcludingEntity(projectile, projectile.getBoundingBox().expand(range, range, range));
		if(entities != null && entities.size() > 1)
		{
			Entity entity = entities.get(rand.nextInt(entities.size()));
			if(entity instanceof EntityLivingBase)
			{
				if(entity instanceof EntityPlayer || entity.isInvisible() || !(entity instanceof EntityLivingBase))
				{
					return blameEntity(projectile, range + 1, target, limitFar, limitClose, limiter + 1);
				}
				else
				{
					if(entity == target)
					{
						return blameEntity(projectile, range + 1, target, limitFar, limitClose, limiter + 1);
					}
					else
					{
						if(range > limitClose)
						{
							if(target.canEntityBeSeen(entity))
							{
								return (EntityLivingBase)entity;
							}
							else
							{
								return blameEntity(projectile, range + 1, target, limitFar, limitClose, limiter + 1);
							}
						}
						else
						{
							return (EntityLivingBase)entity;
						}
					}
				}
			}
			else
			{
				return blameEntity(projectile, range + 1, target, limitFar, limitClose, limiter + 1);
			}
		}
		else
		{
			return blameEntity(projectile, range + 1, target, limitFar, limitClose, limiter + 1);
		}
	}
	
	@Override
	public boolean isInRangeToRenderDist(EntityThrowable projectile, double distance)
	{
		return false;
	}
}
