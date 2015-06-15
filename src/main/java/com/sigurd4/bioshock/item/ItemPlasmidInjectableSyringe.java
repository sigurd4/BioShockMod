package com.sigurd4.bioshock.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.sigurd4.bioshock.M;
import com.sigurd4.bioshock.Stuff;
import com.sigurd4.bioshock.extendedentity.ExtendedPlayer;
import com.sigurd4.bioshock.plasmids.Plasmid;

public class ItemPlasmidInjectableSyringe extends Item implements IItemIdFrom, IItemInit
{
	public Plasmid plasmid;
	
	/**
	 * Drinkable plasmids that give you mutant powers:
	 */
	public ItemPlasmidInjectableSyringe(Plasmid plasmid)
	{
		super();
		this.setUnlocalizedName(ItemPlasmid.type(plasmid) + "." + plasmid.id);
		this.plasmid = plasmid;
		this.setCreativeTab(M.tabs.plasmids);
		this.setMaxStackSize(1);
	}
	
	@Override
	public void init()
	{
		this.setContainerItem(M.items.crafting.empty_hypo);
	}
	
	@Override
	public String getId()
	{
		return "syringe_" + ItemPlasmid.type(this.plasmid) + "_" + this.plasmid.id;
	}
	
	/**
	 * How long it takes to use or consume an item
	 */
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		return 22;
	}
	
	/**
	 * returns the action that specifies what animation to play when the items
	 * is being used
	 */
	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.BOW;
	}
	
	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		ExtendedPlayer props = ExtendedPlayer.get(player);
		if(!props.hasPlasmid(this.plasmid))
		{
			player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
		}
		else
		{
			props.givePlasmid(this.plasmid);
		}
		return stack;
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player)
	{
		ExtendedPlayer props = ExtendedPlayer.get(player);
		if(!props.hasPlasmid(this.plasmid))
		{
			Stuff.ItemStacks.adamEffect(stack, world, player);
		}
		Stuff.ItemStacks.syringeUse(stack, world, player);
		if(!props.hasPlasmid(this.plasmid))
		{
			stack = this.plasmid.onItemUseFinish(stack, world, player);
		}
		props.givePlasmid(this.plasmid);
		return stack;
	}
}
