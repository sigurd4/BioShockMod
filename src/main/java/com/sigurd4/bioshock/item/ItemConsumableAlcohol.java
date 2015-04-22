package com.sigurd4.bioshock.item;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemConsumableAlcohol extends ItemConsumable implements IItemAlcohol
{
	public final int fuse;
	
	/**
	 * Alcoholic beverages.
	 */
	public ItemConsumableAlcohol(EnumConsumableType type, String subtype, int health, int eve, int food, float saturation, int speed, int drunkness, boolean sweet, String rightClickSound)
	{
		super(type, subtype, health, eve, food, saturation, speed, drunkness, sweet, rightClickSound);
		this.fuse = (int)(400 * 200F / drunkness);
		this.setMaxDamage(this.fuse);
	}
	
	/**
	 * Queries the percentage of the 'Durability' bar that should be drawn.
	 * 
	 * @param stack
	 *            The current ItemStack
	 * @return 1.0 for 100% 0 for 0%
	 */
	@Override
	public double getDurabilityForDisplay(ItemStack stack)
	{
		Double action = IItemAlcohol.Variables.getDurabilityForDisplay(stack, this.fuse());
		if(action != null)
		{
			return action;
		}
		return 1;
	}
	
	/**
	 * Called by the default implemetation of EntityItem's onUpdate method,
	 * allowing for cleaner
	 * control over the update of the item without having to write a subclass.
	 * 
	 * @param entityItem
	 *            The entity Item
	 * @return Return true to skip any further update code.
	 */
	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem)
	{
		Boolean result = IItemAlcohol.Variables.onEntityItemUpdate(entityItem, this.fuse());
		if(result != null)
		{
			return result;
		}
		return super.onEntityItemUpdate(entityItem);
	}
	
	/**
	 * returns the action that specifies what animation to play when the items
	 * is being used
	 */
	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		EnumAction action = IItemAlcohol.Variables.getItemUseAction(stack);
		if(action != null)
		{
			return action;
		}
		return super.getItemUseAction(stack);
	}
	
	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		ItemStack stack2 = IItemAlcohol.Variables.onItemRightClick(stack, world, player, this.fuse());
		if(stack2 != null)
		{
			return stack2;
		}
		return super.onItemRightClick(stack, world, player);
	}
	
	@Override
	public int fuse()
	{
		return this.fuse;
	}
}
