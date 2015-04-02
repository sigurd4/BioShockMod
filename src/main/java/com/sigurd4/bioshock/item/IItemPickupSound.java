package com.sigurd4.bioshock.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public abstract interface IItemPickupSound
{
	public void setPickupSound(String sound);
	
	public void playPickupSound(EntityPlayer player, ItemStack itemstack);
	
	public static final class Variables
	{
		public static boolean playRightClickSound = true;
		public static int lastSlot = 0;
	}
}
