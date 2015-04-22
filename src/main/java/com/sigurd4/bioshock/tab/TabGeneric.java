package com.sigurd4.bioshock.tab;

import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.google.common.collect.Lists;
import com.sigurd4.bioshock.M;
import com.sigurd4.bioshock.Stuff;
import com.sigurd4.bioshock.reference.RefMod;

public class TabGeneric extends CreativeTabs
{
	public TabGeneric(String tabLabel)
	{
		super(RefMod.MODID + "." + tabLabel);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem()
	{
		World world = M.proxy.world(0);
		long time = world.getTotalWorldTime();
		ArrayList<Item> items = getItems();
		if(items.size() > 0)
		{
			return items.get((int)(time % items.size()));
		}
		else
		{
			return Item.getItemFromBlock(Blocks.stone);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getIconItemStack()
	{
		World world = M.proxy.world(0);
		long time = world.getTotalWorldTime();
		ArrayList<Item> items = getItems();
		ArrayList<ItemStack> stacks = Lists.newArrayList();
		for(int i = 0; i < items.size(); ++i)
		{
			ArrayList<ItemStack> variants = Lists.newArrayList();
			items.get(i).getSubItems(items.get(i), this, variants);
			//stacks.addAll(variants);
			if(variants.size() > 0)
			{
				stacks.add(variants.get(Stuff.Randomization.randSeed(world.getSeed(), world.getTotalWorldTime() / 10).nextInt(variants.size())));
			}
		}
		if(stacks.size() > 0)
		{
			return stacks.get((int)(time / 10 % stacks.size()));
		}
		else
		{
			return new ItemStack(Item.getItemFromBlock(Blocks.stone));
		}
	}
	
	public ArrayList<Item> getItems()
	{
		Iterator iterator = Item.itemRegistry.iterator();
		ArrayList<Item> items = Lists.newArrayList();
		while(iterator.hasNext())
		{
			Item item = (Item)iterator.next();
			if(item == null)
			{
				continue;
			}
			boolean contains = false;
			CreativeTabs[] itemTabs = item.getCreativeTabs();
			for(int i = 0; i < itemTabs.length; ++i)
			{
				if(this == itemTabs[i])
				{
					items.add(item);
				}
			}
		}
		return items;
	}
}