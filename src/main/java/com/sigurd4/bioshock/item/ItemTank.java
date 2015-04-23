package com.sigurd4.bioshock.item;

import java.util.List;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.sigurd4.bioshock.M;
import com.sigurd4.bioshock.itemtags.ItemTagInteger;
import com.sigurd4.bioshock.reference.RefMod;

public class ItemTank extends Item
{
	public final ItemTagInteger FILLED = new ItemTagInteger("Filled", 0, 0, Integer.MAX_VALUE, true)
	{
		@Override
		protected Integer isValid(NBTTagCompound compound, Integer original)
		{
			original = super.isValid(compound, original);
			int max = ItemTank.this.CAPACITY.get(compound, false);
			if(original > max)
			{
				return max;
			}
			return original;
		}
	};
	public final ItemTagInteger CAPACITY = new ItemTagInteger("Capacity", this.getMaxDamage(), 1, Integer.MAX_VALUE, false)
	{
		@Override
		public void set(NBTTagCompound compound, Integer value)
		{
			if(ItemTank.this.FILLED.has(compound) && value < ItemTank.this.FILLED.get(compound, false))
			{
				ItemTank.this.FILLED.set(compound, value);
			}
		}
		
		@Override
		protected Integer isValid(NBTTagCompound compound, Integer original)
		{
			original = super.isValid(compound, original);
			return original;
		}
	};
	
	/**
	 * Just a generic item. Used for crafting recipes, ammo and more!
	 */
	public ItemTank(int capacity)
	{
		this.setMaxDamage(capacity);
		this.setCreativeTab(M.tabs.core);
	}
	
	@Override
	public int getMaxDamage(ItemStack stack)
	{
		return this.CAPACITY.get(stack);
	}
	
	@Override
	public double getDurabilityForDisplay(ItemStack stack)
	{
		return 1 - (double)this.FILLED.get(stack) / (double)this.CAPACITY.get(stack);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
	{
		list.add("Oxygen Level: " + Integer.toString(this.FILLED.get(stack) / 10) + " / " + Integer.toString(this.CAPACITY.get(stack) / 10));
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		String s = ("" + StatCollector.translateToLocal(super.getUnlocalizedName(stack) + ".name")).trim();
		String s1 = null;
		
		if(this.FILLED.get(stack) > 0)
		{
			s1 = Blocks.air.getLocalizedName();
		}
		
		if(s1 != null)
		{
			s = s1 + " " + s + " (" + MathHelper.floor_double(100 * (double)this.FILLED.get(stack) / (double)this.CAPACITY.get(stack)) + "%)";
		}
		
		return s;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public ModelResourceLocation getModel(ItemStack stack, EntityPlayer player, int useRemaining)
	{
		if(M.otherIconsIndex >= 0 && M.otherIconsIndex < M.otherIcons.length)
		{
			return new ModelResourceLocation(RefMod.MODID, M.otherIcons[M.otherIconsIndex]);
		}
		else
		{
			return super.getModel(stack, player, useRemaining);
		}
	}
}
