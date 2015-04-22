package com.sigurd4.bioshock.item;

import net.minecraft.item.Item;

public class ItemAmmo extends Item
{
	/**
	 * Ammunition for ranged weapons
	 */
	public ItemAmmo(int maxDamage)
	{
		this.setMaxDamage(maxDamage);
	}
}
