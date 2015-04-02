package com.sigurd4.bioshock.itemtags;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ItemTagComparable<W extends Comparable, T extends NBTBase> extends ItemTagBase<W, T>
{
	public final W min;
	public final W max;
	
	public ItemTagComparable(String key, W defaultValue, W min, W max, boolean local)
	{
		super(key, defaultValue, local);
		this.min = min;
		this.max = max;
		this.defaultValue = rawToNBTTag(isValid(NBTTagToRaw(this.defaultValue)));
	}
	
	protected W isValid(W original)
	{
		return original;
	}
	
	public void add(ItemStack stack, W amount)
	{
		NBTTagCompound compound = getCompound(stack, true);
		add(compound, amount);
	}
	
	public void add(NBTTagCompound compound, W amount)
	{
		W value = get(compound, true);
		set(compound, add(value, amount));
	}
	
	protected abstract W add(W value1, W value2);
}