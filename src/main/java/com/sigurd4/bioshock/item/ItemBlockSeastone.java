package com.sigurd4.bioshock.item;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import com.sigurd4.bioshock.block.BlockSeastone;

public class ItemBlockSeastone extends ItemBlock
{
	public ItemBlockSeastone(BlockSeastone block)
	{
		super(block);
		this.setMaxDamage(BlockSeastone.EnumType.values().length);
		this.setHasSubtypes(true);
	}
	
	@Override
	public int getMetadata(int metadata)
	{
		return metadata;
	}
	
	/**
	 * Returns the unlocalized name of this item. This version accepts an
	 * ItemStack so different stacks can have
	 * different names based on their damage or NBT.
	 */
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return BlockSeastone.EnumType.byMetadata(stack.getItemDamage()).getUnlocalizedName();
	}
}