package com.sigurd4.bioshock.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.sigurd4.bioshock.M;
import com.sigurd4.bioshock.reference.RefMod;

public class ItemInfusion extends Item implements IItemPickupSound
{
	/**
	 * Upgrades from bioshock infinite:
	 */
	public ItemInfusion()
	{
		super();
		this.setCreativeTab(M.tabs.core);
		this.setMaxStackSize(1);
		this.setContainerItem(Items.glass_bottle);
	}
	
	/**
	 * How long it takes to use or consume an item
	 */
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 20;
	}
	
	/**
	 * returns the action that specifies what animation to play when the items
	 * is being used
	 */
	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.DRINK;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
	{
		this.staticAddInformation(stack, player, list, bool);
	}
	
	/**
	 * allows items to add custom lines of information to the mouseover
	 * description
	 */
	@SideOnly(Side.CLIENT)
	public void staticAddInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
	{
	}
	
	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if(stack.getTagCompound() == null)
		{
			stack.setTagCompound(new NBTTagCompound());
		}
		if(this.getIfIsUsable(stack, world, player))
		{
			player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
		}
		return stack;
	}
	
	protected boolean getIfIsUsable(ItemStack stack, World world, EntityPlayer player)
	{
		return true;
	}
	
	@Override
	public void playPickupSound(EntityPlayer player, ItemStack stack)
	{
		
	}
	
	@Override
	public void playRightClickSound(ItemStack stack, World world, EntityPlayer player)
	{
		player.playSound(RefMod.MODID + ":" + "item.consumables.right_click.cork", 1, 1);
	}
	
	@Override
	public void playSelectSound(EntityPlayer player, ItemStack stack)
	{
		
	}
}
