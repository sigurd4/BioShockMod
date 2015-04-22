package com.sigurd4.bioshock;

import net.minecraft.item.ItemStack;

import com.sigurd4.bioshock.item.ItemWeaponRanged;
import com.sigurd4.bioshock.tab.TabGeneric;

public class MTabs
{
	public TabGeneric core = new TabGeneric("core");
	public TabGeneric weapons = new TabGeneric("weapons")
	{
		@Override
		public ItemStack getIconItemStack()
		{
			ItemStack stack = super.getIconItemStack();
			if(stack.getItem() instanceof ItemWeaponRanged)
			{
				ItemWeaponRanged.AMMO.set(stack, ((ItemWeaponRanged)stack.getItem()).CAPACITY.get(stack));
			}
			return stack;
		}
	};
	public TabGeneric plasmids = new TabGeneric("plasmids");
	public TabGeneric passives = new TabGeneric("passives");
	public TabGeneric craftingItems = new TabGeneric("craftingItems");
	public TabGeneric consumables = new TabGeneric("consumables");
}
