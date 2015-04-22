package com.sigurd4.bioshock.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract interface IItemPickupSound
{
	public void playPickupSound(EntityPlayer player, ItemStack stack);
	
	public void playSelectSound(EntityPlayer player, ItemStack stack);
	
	public void playRightClickSound(ItemStack stack, World world, EntityPlayer player);
	
	public static final class Variables
	{
		public static boolean playRightClickSound = true;
		public static int lastSlot = 0;
	}
}
