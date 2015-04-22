package com.sigurd4.bioshock.plasmids;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PlasmidUnfinished extends Plasmid
{
	public PlasmidUnfinished(String id, int cost1, int cost2, boolean type, String... description)
	{
		super(id, 0, 0, type, description);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		return stack;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected)
	{
		
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player)
	{
		return stack;
	}
}
