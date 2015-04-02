package com.sigurd4.bioshock.itemtags;

import net.minecraft.nbt.NBTTagInt;

public class ItemTagInteger extends ItemTagComparable<Integer, NBTTagInt>
{
	public ItemTagInteger(String key, Integer defaultValue, Integer min, Integer max, boolean local)
	{
		super(key, defaultValue, min, max, local);
	}
	
	protected Integer isValid(Integer original)
	{
		if(original > max)
		{
			return max;
		}
		if(original < min)
		{
			return min;
		}
		return original;
	}
	
	@Override
	protected NBTTagInt rawToNBTTag(Integer value)
	{
		return new NBTTagInt(value);
	}
	
	@Override
	protected Integer NBTTagToRaw(NBTTagInt value)
	{
		if(value != null)
		{
			return value.getInt();
		}
		return 0;
	}
	
	protected Integer add(Integer value1, Integer value2)
	{
		return value1 + value2;
	}
}