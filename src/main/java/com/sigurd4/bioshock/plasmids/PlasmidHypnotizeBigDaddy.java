package com.sigurd4.bioshock.plasmids;

import net.minecraft.entity.Entity;
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

public class PlasmidHypnotizeBigDaddy extends Plasmid implements IPlasmidProjectile
{
	public PlasmidHypnotizeBigDaddy(String id, int cost1, int cost2, boolean type, String... description)
	{
		super(id, cost1, cost2, type, description);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		world.playSoundAtEntity(player, "mob.slime.small", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F) / 0.8F);
		world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (rand.nextFloat() * 0.4F + 0.8F));
		
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
			world.spawnParticle(EnumParticleTypes.REDSTONE, true, player.posX + Stuff.Randomization.r(2), player.posY + player.height / 2 + Stuff.Randomization.r(2), player.posZ + Stuff.Randomization.r(2), -1.0, 1.6, 0.0);
		}
		for(int i = 0; i < 6; ++i)
		{
			world.spawnParticle(EnumParticleTypes.REDSTONE, true, player.posX + Stuff.Randomization.r(0.5), player.posY + player.height / 2 + Stuff.Randomization.r(2), player.posZ + Stuff.Randomization.r(0.5), -1.0, 1.6, 0.0);
		}
		
		for(int i = 0; i < 4; ++i)
		{
			world.playSoundAtEntity(player, "mob.slime.small", 0.4F, ((rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F) / 0.8F);
		}
		
		if(!world.isRemote)
		{
			player.addPotionEffect(new PotionEffect(Potion.weakness.id, 320, 2, true, false));
			player.addPotionEffect(new PotionEffect(Potion.blindness.id, 160, 0, true, false));
			player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 320, 0, true, false));
			player.addPotionEffect(new PotionEffect(Potion.jump.id, 320, 0));
		}
		Element.setEntityElement(player, Element.ICE, 60, false);
		
		return stack;
	}
	
	@Override
	public double getInitialSpeed()
	{
		return 1.5;
	}
	
	@Override
	public void onImpact(EntityThrowable projectile, MovingObjectPosition pos)
	{
		if(pos.entityHit != null)
		{
			if(pos.entityHit instanceof EntityBigDaddyBouncer)
			{
				((EntityBigDaddyBouncer)pos.entityHit).setAngry(0);
				((EntityBigDaddyBouncer)pos.entityHit).setHypnotized(((EntityBigDaddyBouncer)pos.entityHit).hypnotizedMax);
				((EntityBigDaddyBouncer)pos.entityHit).hypnotizedBy = projectile.getThrower();
				
				for(int i = 0; i < 6; ++i)
				{
					projectile.worldObj.spawnParticle(EnumParticleTypes.REDSTONE, true, pos.entityHit.posX + Stuff.Randomization.r(1.0), pos.entityHit.posY + Stuff.Randomization.r(2.5), pos.entityHit.posZ + Stuff.Randomization.r(1.0), -1.0D, 1.0D, 0.3D);
				}
			}
		}
		
		if(!projectile.worldObj.isRemote)
		{
			projectile.setDead();
		}
		
		M.otherIconsIndex = 1;
		Item item = M.items.crafting.gear_steel;
		for(int i = 0; i < 24; ++i)
		{
			projectile.worldObj.spawnParticle(EnumParticleTypes.ITEM_CRACK, true, projectile.posX, projectile.posY, projectile.posZ, 0.0D, 5.0D, 0.0D, Item.getIdFromItem(item));
			projectile.worldObj.spawnParticle(EnumParticleTypes.REDSTONE, true, projectile.posX + Stuff.Randomization.r(0.1), projectile.posY + Stuff.Randomization.r(0.1), projectile.posZ + Stuff.Randomization.r(0.1), -1.0D, 1.0D, 0.3D);
		}
		M.otherIconsIndex = -1;
		
		projectile.worldObj.playSoundAtEntity(projectile, "mob.slime.small", 0.4F, ((rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F) / 0.8F);
	}
	
	@Override
	public void onEntityUpdate(EntityThrowable projectile)
	{
		projectile.worldObj.spawnParticle(EnumParticleTypes.REDSTONE, true, projectile.posX + Stuff.Randomization.r(0.1), projectile.posY + Stuff.Randomization.r(0.1), projectile.posZ + Stuff.Randomization.r(0.1), -1.0D, 1.0D, 0.3D);
		projectile.worldObj.spawnParticle(EnumParticleTypes.REDSTONE, true, projectile.posX + Stuff.Randomization.r(0.1), projectile.posY + Stuff.Randomization.r(0.1), projectile.posZ + Stuff.Randomization.r(0.1), -1.0D, 1.0D, 0.3D);
		projectile.worldObj.spawnParticle(EnumParticleTypes.REDSTONE, true, projectile.posX + Stuff.Randomization.r(0.1), projectile.posY + Stuff.Randomization.r(0.1), projectile.posZ + Stuff.Randomization.r(0.1), -1.0D, 1.0D, 0.3D);
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
}
