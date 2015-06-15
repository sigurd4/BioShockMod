package com.sigurd4.bioshock.itemtags;

import java.util.HashMap;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ItemTagBase<W, T extends NBTBase>
{
	private HashMap<NBTTagCompound, NBTTagCompound> localMap = new HashMap();
	private T defaultValue;
	public final String key;
	public final boolean local;
	
	public ItemTagBase(String key, W defaultValue, boolean local)
	{
		this.key = key;
		this.local = false;
		if(defaultValue != null)
		{
			this.defaultValue = this.rawToNBTTag(defaultValue);
		}
	}
	
	public W get(ItemStack stack)
	{
		return this.get(stack, true);
	}
	
	public W get(ItemStack stack, boolean createNew)
	{
		NBTTagCompound compound = this.getCompound(stack, createNew);
		if(compound == null)
		{
			return this.getDefault();
		}
		return this.get(compound, createNew);
	}
	
	public W get(NBTTagCompound compound, boolean createNew)
	{
		if(createNew)
		{
			this.set(compound, this.get(compound, false));
		}
		if(this.has(compound))
		{
			return this.NBTTagToRaw(this.isValid2(compound, (T)compound.getTag(this.key)));
		}
		else
		{
			T d = this.getDefault2();
			if(d != null)
			{
				return this.NBTTagToRaw(this.isValid2(compound, (T)this.getDefault2().copy()));
			}
			return null;
		}
	}
	
	public void set(ItemStack stack, W value)
	{
		NBTTagCompound compound = this.getCompound(stack, true);
		this.set(compound, value);
	}
	
	public void set(NBTTagCompound compound, W value)
	{
		if(value != null)
		{
			T tag = this.rawToNBTTag(value);
			compound.setTag(this.key, tag);
		}
		else
		{
			this.remove(compound);
		}
	}
	
	public boolean has(ItemStack stack)
	{
		NBTTagCompound compound = this.getCompound(stack, false);
		return compound != null ? this.has(compound) && this.get(compound, false) != null : false;
	}
	
	public boolean has(NBTTagCompound compound)
	{
		NBTBase tag = compound.getTag(this.key);
		T d = this.getDefault2();
		return tag != null && (d == null || d.getClass().isInstance(tag) && tag.getId() == d.getId());
	}
	
	public boolean remove(ItemStack stack)
	{
		NBTTagCompound compound = this.getCompound(stack, false);
		boolean b = compound != null ? this.has(compound) : false;
		if(compound.hasNoTags())
		{
			stack.setTagCompound(null);
		}
		return b;
	}
	
	public boolean remove(NBTTagCompound compound)
	{
		boolean b = this.has(compound);
		compound.removeTag(this.key);
		return b;
	}
	
	public W getDefault()
	{
		T d = this.getDefault2();
		if(d != null)
		{
			return this.NBTTagToRaw(d);
		}
		return null;
	}
	
	public T getDefault2()
	{
		return this.defaultValue;
	}
	
	protected abstract T rawToNBTTag(W value);
	
	protected abstract W NBTTagToRaw(T value);
	
	protected T isValid2(NBTTagCompound compound, T original)
	{
		return this.rawToNBTTag(this.isValid(compound, this.NBTTagToRaw(original)));
	}
	
	protected W isValid(NBTTagCompound compound, W original)
	{
		return original;
	}
	
	@Override
	public String toString()
	{
		return this.key;
	}
	
	protected final NBTTagCompound getCompound(ItemStack stack, boolean createNew)
	{
		NBTTagCompound compound = stack.getTagCompound();
		if(this.local)
		{
			if(!this.localMap.containsKey(compound))
			{
				if(compound == null)
				{
					stack.setTagCompound(new NBTTagCompound());
					compound = stack.getTagCompound();
				}
				this.localMap.put(compound, new NBTTagCompound());
			}
			compound = this.localMap.get(compound);
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
