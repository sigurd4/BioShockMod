package com.sigurd4.bioshock.itemtags;

import net.minecraft.nbt.NBTTagCompound;

public class ItemTagCompound extends ItemTagBase<NBTTagCompound, NBTTagCompound>
{
	public ItemTagCompound(String key, boolean local)
	{
		super(key, new NBTTagCompound(), local);
	}
	
	@Override
	protected NBTTagCompound rawToNBTTag(NBTTagCompound value)
	{
		return value;
	}
	
	@Override
	protected NBTTagCompound NBTTagToRaw(NBTTagCompound value)
	{
		return value;
	}
	
	@Override
	public NBTTagCompound getDefault2()
	{
		return new NBTTagCompound();
	}
}