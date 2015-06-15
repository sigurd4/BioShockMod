package com.sigurd4.bioshock.item;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.sigurd4.bioshock.M;
import com.sigurd4.bioshock.reference.RefMod;

public class ItemCrafting extends Item
{
	/**
	 * Just a generic item. Used for crafting recipes, ammo and more!
	 */
	public ItemCrafting()
	{
		this.setMaxStackSize(64);
		this.setCreativeTab(M.tabs.crafting_items);
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
