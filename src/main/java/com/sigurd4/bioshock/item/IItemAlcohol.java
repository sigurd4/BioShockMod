package com.sigurd4.bioshock.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

import com.sigurd4.bioshock.Stuff;
import com.sigurd4.bioshock.entity.projectile.EntityItemAlcohol;
import com.sigurd4.bioshock.itemtags.ItemTagInteger;

public interface IItemAlcohol
{
	public int fuse();
	
	public static final class Variables
	{
		//nbt
		public static final ItemTagInteger ALCOHOLBURNING = new ItemTagInteger("AlcoholBurning", 0, 0, Integer.MAX_VALUE, true);
		
		/**
		 * Queries the percentage of the 'Durability' bar that should be drawn.
		 * 
		 * @param stack
		 *            The current ItemStack
		 * @return 1.0 for 100% 0 for 0%
		 */
		public static Double getDurabilityForDisplay(ItemStack stack, int fuse)
		{
			if(ALCOHOLBURNING.has(stack))
			{
				return 1 - (double)ALCOHOLBURNING.get(stack) / (double)fuse;
			}
			return null;
		}
		
		/**
		 * Called by the default implemetation of EntityItem's onUpdate method,
		 * allowing for cleaner
		 * control over the update of the item without having to write a
		 * subclass.
		 * 
		 * @param entityItem
		 *            The entity Item
		 * @return Return true to skip any further update code.
		 */
		public static Boolean onEntityItemUpdate(EntityItem entityItem, int fuse)
		{
			ItemStack stack = entityItem.getEntityItem();
			
			if(ALCOHOLBURNING.has(stack))
			{
				entityItem.worldObj.spawnParticle(EnumParticleTypes.FLAME, true, entityItem.posX, entityItem.posY + 0.5, entityItem.posZ, 0, 0, 0);
				entityItem.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, true, entityItem.posX, entityItem.posY + 0.6 + Stuff.Randomization.r(0.1), entityItem.posZ, Stuff.Randomization.r(0.01), Stuff.Randomization.r(0.01), Stuff.Randomization.r(0.01));
				if(ALCOHOLBURNING.get(stack) == 1 || entityItem.ticksExisted > fuse)
				{
					if(!entityItem.worldObj.isRemote)
					{
						boolean flag = entityItem.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
						
						entityItem.worldObj.newExplosion(entityItem.worldObj.getPlayerEntityByName(entityItem.getOwner()), entityItem.posX, entityItem.posY, entityItem.posZ, entityItem instanceof EntityItemAlcohol ? ((EntityItemAlcohol)entityItem).explosionRadius : (float)EntityItemAlcohol.defaultExplosionRadius, true, false);
						
						entityItem.setDead();
					}
				}
				if(ALCOHOLBURNING.get(stack) > 0)
				{
					ALCOHOLBURNING.add(stack, -1);
				}
			}
			else
			{
				if(entityItem.isBurning())
				{
					ALCOHOLBURNING.add(stack, fuse);
				}
			}
			Variables.onUpdate(stack, entityItem.worldObj, null, 0, false, fuse);
			return null;
		}
		
		public static Boolean isDamaged(ItemStack stack)
		{
			if(ALCOHOLBURNING.has(stack))
			{
				return true;
			}
			return null;
		}
		
		public static void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected, int fuse)
		{
			if(entity != null)
			{
				if(ALCOHOLBURNING.has(stack))
				{
					if(entity.isInWater())
					{
						ALCOHOLBURNING.remove(stack);
					}
					if(ALCOHOLBURNING.get(stack) == 1)
					{
						if(!world.isRemote)
						{
							boolean flag = world.getGameRules().getGameRuleBooleanValue("mobGriefing");
							
							world.newExplosion(entity, entity.posX, entity.posY, entity.posZ, EntityItemAlcohol.defaultExplosionRadius, true, false);
						}
						stack.stackSize = 0;
					}
					if(ALCOHOLBURNING.get(stack) > 0)
					{
						ALCOHOLBURNING.add(stack, -1);
					}
				}
				else
				{
					if(entity.isBurning())
					{
						if(entity instanceof EntityLivingBase)
						{
							if(((EntityLivingBase)entity).getHeldItem() == stack)
							{
								ALCOHOLBURNING.set(stack, fuse);
							}
						}
						else
						{
							ALCOHOLBURNING.set(stack, fuse);
						}
					}
				}
			}
		}
		
		/**
		 * returns the action that specifies what animation to play when the
		 * items is being used
		 */
		public static EnumAction getItemUseAction(ItemStack stack)
		{
			if(ALCOHOLBURNING.has(stack))
			{
				return EnumAction.NONE;
			}
			return null;
		}
		
		/**
		 * Called whenever this item is equipped and the right mouse button is
		 * pressed. Args: itemStack, world, entityPlayer
		 */
		public static ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player, int fuse)
		{
			if(ALCOHOLBURNING.has(stack))
			{
				--stack.stackSize;
				
				world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (world.rand.nextFloat() * 0.4F + 0.8F));
				
				if(!world.isRemote)
				{
					EntityItemAlcohol entityItem = new EntityItemAlcohol(world, player);
					ItemStack stack2 = ItemStack.copyItemStack(stack);
					stack2.stackSize = 1;
					if(ALCOHOLBURNING.get(stack2) > fuse / 4)
					{
						ALCOHOLBURNING.set(stack2, fuse / 4);
					}
					entityItem.setEntityItemStack(stack2);
					entityItem.setPickupDelay(20);
					world.spawnEntityInWorld(entityItem);
				}
				return stack;
			}
			return null;
		}
	}
}
