package com.sigurd4.bioshock.itemtags;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemTagItemStack extends ItemTagBase<ItemStack, NBTTagCompound>
{
	public ItemTagItemStack(String key, ItemStack defaultValue, boolean local)
	{
		super(key, defaultValue, local);
	}
	
	@Override
	protected NBTTagCompound rawToNBTTag(ItemStack value)
	{
		if(value == null || value.stackSize <= 0)
		{
			return null;
		}
		return value.writeToNBT(new NBTTagCompound());
	}
	
	@Override
	protected ItemStack NBTTagToRaw(NBTTagCompound value)
	{
		return ItemStack.loadItemStackFromNBT(value);
	}
	
	@Override
	protected ItemStack isValid(NBTTagCompound compound, ItemStack value)
	{
		if(value == null || value.stackSize <= 0)
		{
			return null;
		}
		return value;
	}
	
	@Override
	public NBTTagCompound getDefault2()
	{
		return null;
	}
}