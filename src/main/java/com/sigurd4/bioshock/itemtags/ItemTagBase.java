package com.sigurd4.bioshock.itemtags;

import java.util.HashMap;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ItemTagBase<W, T extends NBTBase>
{
	private HashMap<NBTTagCompound, NBTTagCompound> localMap = new HashMap();
	protected T defaultValue;
	public final String key;
	public final boolean local;
	
	public ItemTagBase(String key, W defaultValue, boolean local)
	{
		this.key = key;
		this.local = local;
	}
	
	public W get(ItemStack stack)
	{
		return get(stack, true);
	}
	
	public W get(ItemStack stack, boolean createNew)
	{
		NBTTagCompound compound = getCompound(stack, createNew);
		return get(compound, createNew);
	}
	
	public W get(NBTTagCompound compound, boolean createNew)
	{
		if(createNew)
		{
			set(compound, get(compound, false));
		}
		if(compound.hasKey(key) && defaultValue.getClass().isInstance(compound.getTag(key)) && compound.getTag(key).getId() == defaultValue.getId())
		{
			return NBTTagToRaw(isValid((T)compound.getTag(key)));
		}
		else
		{
			return NBTTagToRaw(isValid((T)defaultValue.copy()));
		}
	}
	
	public void set(ItemStack stack, W value)
	{
		NBTTagCompound compound = getCompound(stack, true);
		set(compound, value);
	}
	
	public void set(NBTTagCompound compound, W value)
	{
		T tag = rawToNBTTag(value);
		compound.setTag(key, tag);
	}
	
	protected abstract T rawToNBTTag(W value);
	
	protected abstract W NBTTagToRaw(T value);
	
	protected T isValid(T original)
	{
		return rawToNBTTag(isValid(NBTTagToRaw(original)));
	}
	
	protected W isValid(W original)
	{
		return original;
	}
	
	public String toString()
	{
		return key;
	}
	
	protected final NBTTagCompound getCompound(ItemStack stack, boolean createNew)
	{
		NBTTagCompound compound = stack.getTagCompound();
		if(local)
		{
			if(!localMap.containsKey(compound))
			{
				if(compound == null)
				{
					stack.setTagCompound(new NBTTagCompound());
					compound = stack.getTagCompound();
				}
				localMap.put(compound, new NBTTagCompound());
			}
			compound = localMap.get(compound);
		}
		else
		{
			if(compound == null)
			{
				compound = new NBTTagCompound();
				if(createNew)
				{
					stack.setTagCompound(compound);
				}
			}
		}
		return compound;
	}
}
