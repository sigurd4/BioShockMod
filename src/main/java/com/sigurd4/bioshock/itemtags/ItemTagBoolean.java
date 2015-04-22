package com.sigurd4.bioshock.itemtags;

import net.minecraft.nbt.NBTTagByte;

public class ItemTagBoolean extends ItemTagBase<Boolean, NBTTagByte>
{
	public ItemTagBoolean(String key, Boolean defaultValue, boolean local)
	{
		super(key, defaultValue, local);
	}
	
	@Override
	protected NBTTagByte rawToNBTTag(Boolean value)
	{
		return new NBTTagByte(value ? (byte)1 : (byte)0);
	}
	
	@Override
	protected Boolean NBTTagToRaw(NBTTagByte value)
	{
		return value.getByte() > 0;
	}
}