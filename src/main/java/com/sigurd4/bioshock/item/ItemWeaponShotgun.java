package com.sigurd4.bioshock.item;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.sigurd4.bioshock.M;
import com.sigurd4.bioshock.Stuff;
import com.sigurd4.bioshock.entity.projectile.EntityGunBullet;
import com.sigurd4.bioshock.extendedentity.ExtendedPlayer;
import com.sigurd4.bioshock.item.ItemWeaponShotgun.EnumAmmoTypeShotgun;
import com.sigurd4.bioshock.item.ItemWeaponShotgun.EnumUpgradesShotgun;
import com.sigurd4.bioshock.itemtags.ItemTagBoolean;
import com.sigurd4.bioshock.reference.RefMass;
import com.sigurd4.bioshock.reference.RefMod;

public class ItemWeaponShotgun extends ItemWeaponRanged<EnumAmmoTypeShotgun, EnumUpgradesShotgun>
{
	public static enum EnumUpgradesShotgun
			implements
			ItemWeaponRanged.IEnumUpgrade
	{
		FIRE_RATE, DAMAGE;
		
		@Override
		public void addInformation(ItemStack stack, EntityPlayer player, ArrayList<String> list, boolean bool)
		{
			
		}
		
		@Override
		public void setupNBT(ItemStack stack)
		{
			((ItemWeaponRanged)stack.getItem()).FIRE_RATE.set(stack, ((ItemWeaponRanged)stack.getItem()).fireRate(stack));
		}
	}
	
	public static enum EnumAmmoTypeShotgun
			implements
			ItemWeaponRanged.IEnumAmmoType
	{
		STANDARD_00(M.items.weapons.shotgun.ammo.standard_buck), ELECTRIC(
				M.items.weapons.shotgun.ammo.electric_buck), EXPLODING(
				M.items.weapons.shotgun.ammo.exploding_buck), SOLID_SLUG(
				M.items.weapons.shotgun.ammo.solid_slug);
		
		private ItemAmmo item;
		
		private EnumAmmoTypeShotgun(ItemAmmo item)
		{
			this.item = item;
		}
		
		@Override
		public ItemAmmo getItem()
		{
			return this.item;
		}
		
		@Override
		public void addInformation(ItemStack stack, EntityPlayer player, ArrayList<String> list, boolean bool)
		{
			
		}
	}
	
	private int fireRate2;
	
	@Override
	public int fireRate(ItemStack stack)
	{
		if(this.UPGRADES.get(stack, EnumUpgradesShotgun.FIRE_RATE, true))
		{
			return this.fireRate2;
		}
		else
		{
			return super.fireRate(stack);
		}
	}
	
	@Override
	public float spread(ItemStack stack)
	{
		if(this.AMMO_TYPE.get(stack) == EnumAmmoTypeShotgun.SOLID_SLUG)
		{
			return 0;
		}
		return super.spread(stack);
	}
	
	//nbt
	public final static ItemTagBoolean WILLEJECTSHELLCASING = new ItemTagBoolean("WillEjectShellCasing", false, true);
	
	/**
	 * @param int Ammunition maximum capacity
	 * @param int Fire rate without upgrade (in ticks)
	 * @param int Fire rate with upgrade (in ticks)
	 * @param float Spread
	 * @param float Recoil
	 * @param int How much time reloading takes (in ticks)
	 * @param float Zoom multiplier
	 */
	public ItemWeaponShotgun(int capacity, int fireRate, int fireRate2, float spread, float recoil, int reloadTime, float zoom)
	{
		super(capacity, fireRate, spread, recoil, reloadTime, zoom, EnumAmmoTypeShotgun.values(), EnumUpgradesShotgun.values(), false);
		this.fireRate2 = fireRate2;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
	{
		if(entity instanceof EntityLivingBase)
		{
			if(((EntityLivingBase)entity).getHeldItem() == stack)
			{
				if(!this.UPGRADES.get(stack, EnumUpgradesShotgun.FIRE_RATE))
				{
					if(FIRE_RATE_TIMER.get(stack) == this.FIRE_RATE.get(stack) - 8)
					{
						world.playSoundAtEntity(entity, RefMod.MODID + ":" + "item.weapon.shotgun.pump", 0.8F, 1.0F);
					}
				}
				else
				{
					if(FIRE_RATE_TIMER.get(stack) == this.FIRE_RATE.get(stack) - 2)
					{
						world.playSoundAtEntity(entity, RefMod.MODID + ":" + "item.weapon.shotgun.pump.auto", 0.5F, 1.0F);
					}
				}
			}
		}
		if(FIRE_RATE_TIMER.get(stack) == 3)
		{
			if(WILLEJECTSHELLCASING.get(stack) && !world.isRemote)
			{
				WILLEJECTSHELLCASING.set(stack, false);
				EntityItem entityitem = entity.entityDropItem(new ItemStack(M.items.crafting.empty_shell_casing, 1, 0), Item.itemRand.nextFloat());
				entityitem.setPickupDelay(20);
				if(Item.itemRand.nextInt(15 + 1) < 12)
				{
					entityitem.setInfinitePickupDelay();
				}
				entityitem.lifespan = 120 + (int)Stuff.Randomization.r(30) + 10;
			}
		}
		super.onUpdate(stack, world, entity, par4, par5);
	}
	
	@Override
	protected void playReloadSound(EntityPlayer player)
	{
		player.worldObj.playSoundAtEntity(player, RefMod.MODID + ":" + "item.weapon.shotgun.reload", 0.8F, 1.0F);
	}
	
	public float instability(EntityPlayer player, ItemStack stack, float recoil)
	{
		return 0.6F;
	}
	
	@Override
	public void fireBullet(ItemStack stack, World world, EntityPlayer player)
	{
		ExtendedPlayer props = ExtendedPlayer.get(player);
		
		WILLEJECTSHELLCASING.set(stack, true);
		
		world.playSoundAtEntity(player, "fireworks.blast", 1.8F, 1.5F / (itemRand.nextFloat() * 0.4F + 0.8F));
		world.playSoundAtEntity(player, "random.explode", 0.6F, (1.0F + (Item.itemRand.nextFloat() - Item.itemRand.nextFloat()) * 0.2F) * 2.7F);
		world.playSoundAtEntity(player, "mob.blaze.hit", 0.015F, 0.08F / (itemRand.nextFloat() * 0.4F + 0.8F));
		
		double d = 16;
		
		Random rYaw = new Random();
		Random rPitch = new Random();
		Random r = new Random();
		float rotationPitch = player.rotationPitch;
		float rotationYaw = player.rotationYaw;
		
		this.applySpread(player, stack, rYaw, rPitch, r, rotationYaw, rotationPitch);
		
		if(this.AMMO_TYPE.get(stack) != EnumAmmoTypeShotgun.SOLID_SLUG)
		{
			for(int a = 16; a > 0; --a)
			{
				float damage = 35F / 2;
				float onTickDamageModifier = props.isZoomHeldDown ? Item.itemRand.nextFloat() * 0.1F + 0.7F : Item.itemRand.nextFloat() * 0.1F + 0.6F;
				
				switch(this.AMMO_TYPE.get(stack))
				{
				case EXPLODING:
				{
					damage = damage / 0.7F;
				}
				default:
					break;
				}
				
				EntityGunBullet bullet = new EntityGunBullet(world, player, stack, 8, RefMass.SHOTGUN_SHOT, damage, 0.1F / 16, onTickDamageModifier, false);
				if(a < 4)
				{
					bullet.setSilent(true);
				}
				
				switch(this.AMMO_TYPE.get(stack))
				{
				case ELECTRIC:
				{
					bullet.electric = true;
				}
				case EXPLODING:
				{
					bullet.burning = true;
				}
				default:
					break;
				}
				
				if(this.UPGRADES.get(stack, EnumUpgradesShotgun.DAMAGE))
				{
					bullet.damage = damage + damage / 4;
					bullet.speed(bullet.speed() * 1.5F);
				}
				else
				{
					bullet.damage = damage;
				}
				bullet.knockback = 0.4F;
				if(!world.isRemote)
				{
					world.spawnEntityInWorld(bullet);
				}
				
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, true, bullet.posX + bullet.motionX / 8, bullet.posY + bullet.motionY / 8, bullet.posZ + bullet.motionZ / 8, bullet.motionX / d + player.motionX, bullet.motionY / d + player.motionY, bullet.motionZ / d + player.motionZ);
				
				this.applySpread(player, stack, rYaw, rPitch, r, rotationYaw, rotationPitch);
			}
		}
		else
		{
			float power = 2.4F;
			float damage = 68F;
			float onTickDamageModifier = 0.98F;
			
			EntityGunBullet bullet = new EntityGunBullet(world, player, stack, 8, RefMass.SOLID_SLUG, damage, 0.1F / 16, onTickDamageModifier, true);
			bullet.ignoresArmour = true;
			bullet.piercing = true;
			if(this.UPGRADES.get(stack, EnumUpgradesShotgun.DAMAGE))
			{
				bullet.damage = damage + damage / 4;
				bullet.speed(bullet.speed() * 1.5F);
			}
			else
			{
				bullet.damage = damage;
			}
			if(!world.isRemote)
			{
				world.spawnEntityInWorld(bullet);
			}
			
			for(int a = 16; a > 0; --a)
			{
				float f = 0.04F;
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, true, bullet.posX + bullet.motionX / 8, bullet.posY + bullet.motionY / 8, bullet.posZ + bullet.motionZ / 8, bullet.motionX / d, bullet.motionY / d, bullet.motionZ / d);
			}
		}
		
		player.rotationPitch = rotationPitch;
		player.rotationYaw = rotationYaw;
	}
	
	@Override
	public float getZoom(World world, EntityPlayer player, ItemStack stack)
	{
		if(this.AMMO_TYPE.get(stack) == EnumAmmoTypeShotgun.SOLID_SLUG)
		{
			return super.getZoom(world, player, stack) * 4;
		}
		return super.getZoom(world, player, stack);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void playPickupSound(EntityPlayer player, ItemStack stack)
	{
		if(FIRE_RATE_TIMER.get(stack) <= this.FIRE_RATE.get(stack))
		{
			player.playSound(RefMod.MODID + ":" + "item.weapon.shotgun.pickup", 1.0F, 0.9F + Item.itemRand.nextFloat() * 0.2F);
		}
	}
	
	@Override
	public boolean isHeldUp(ItemStack stack)
	{
		if(FIRE_RATE_TIMER.get(stack) <= 0 || FIRE_RATE_TIMER.get(stack) > 19 || this.UPGRADES.get(stack, EnumUpgradesShotgun.FIRE_RATE))
		{
			return super.isHeldUp(stack);
		}
		else
		{
			return false;
		}
	}
	
	@Override
	public float kickBack(EntityPlayer player, ItemStack stack)
	{
		return super.kickBack(player, stack) * 3;
	}
}
