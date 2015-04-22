package com.sigurd4.bioshock.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.sigurd4.bioshock.M;
import com.sigurd4.bioshock.Stuff;
import com.sigurd4.bioshock.extendedentity.ExtendedPlayer;
import com.sigurd4.bioshock.plasmids.Plasmid;
import com.sigurd4.bioshock.reference.RefMod;

public class ItemPlasmidDrinkable extends ItemPlasmid implements IItemPickupSound
{
	/**
	 * Drinkable plasmids that give you mutant powers:
	 */
	public ItemPlasmidDrinkable(Plasmid plasmid)
	{
		super(plasmid);
		this.setUnlocalizedName(type(plasmid) + "." + plasmid.id);
		//this.setTextureName(RefMod.MODID + ":" + "flask_" + type(plasmid) + "_" + plasmid.id);
		this.setCreativeTab(M.tabs.plasmids);
		this.setMaxStackSize(1);
		this.setContainerItem(this);
		this.setMaxDamage(400);
	}
	
	/**
	 * How long it takes to use or consume an item
	 */
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		EntityPlayer player = this.getPlayer(stack);
		if(player != null)
		{
			ExtendedPlayer props = ExtendedPlayer.get(player);
			if(!props.hasPlasmid(this.plasmid) || this.canPlayerDrink(player))
			{
				return 40;
			}
		}
		return 0;
	}
	
	/**
	 * returns the action that specifies what animation to play when the items
	 * is being used
	 */
	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		EntityPlayer player = this.getPlayer(stack);
		if(player != null)
		{
			ExtendedPlayer props = ExtendedPlayer.get(player);
			if(!props.hasPlasmid(this.plasmid) || this.canPlayerDrink(player))
			{
				return EnumAction.DRINK;
			}
			else
			{
				if(props.getEve() > 0)
				{
					return EnumAction.BOW;
				}
			}
		}
		return EnumAction.NONE;
	}
	
	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		ExtendedPlayer props = ExtendedPlayer.get(player);
		if(!props.hasPlasmid(this.plasmid) || this.canPlayerDrink(player))
		{
			player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
		}
		else
		{
			super.usePlasmid(stack, world, player);
		}
		return stack;
	}
	
	@Override
	protected EnumCanUsePlasmidResult canUsePlasmid(ItemStack stack, World world, EntityPlayer player)
	{
		ExtendedPlayer props = ExtendedPlayer.get(player);
		if(!props.hasPlasmid(this.plasmid) || this.canPlayerDrink(player))
		{
			return EnumCanUsePlasmidResult.NO2;
		}
		return super.canUsePlasmid(stack, world, player);
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player)
	{
		ExtendedPlayer props = ExtendedPlayer.get(player);
		if(!props.hasPlasmid(this.plasmid))
		{
			Stuff.ItemStacks.adamEffect(stack, world, player);
		}
		props.setEve(props.eveMax);
		if(getItemCount(player.inventory, this) > 1)
		{
			for(int i = 0; i < player.inventory.getSizeInventory(); ++i)
			{
				ItemStack stack2 = player.inventory.getStackInSlot(i);
				if((stack2 != stack || stack.stackSize > 1) && stack2.getItem() == this)
				{
					--stack2.stackSize;
					if(stack2.stackSize <= 0)
					{
						player.inventory.setInventorySlotContents(i, null);
					}
					break;
				}
			}
		}
		if(!props.hasPlasmid(this.plasmid))
		{
			stack = this.plasmid.onItemUseFinish(stack, world, player);
			COOLDOWN.set(stack, 20);
		}
		props.givePlasmid(this.plasmid);
		return stack;
	}
	
	protected boolean canPlayerDrink(EntityPlayer player)
	{
		ExtendedPlayer props = ExtendedPlayer.get(player);
		return getItemCount(player.inventory, this) > 1 && props.getEve() < props.getMaxEve();
	}
	
	@Override
	public void playPickupSound(EntityPlayer player, ItemStack stack)
	{
		
	}
	
	@Override
	public void playRightClickSound(ItemStack stack, World world, EntityPlayer player)
	{
		player.playSound(RefMod.MODID + ":" + "item.consumables.right_click.cork", 1, 1);
	}
	
	@Override
	public void playSelectSound(EntityPlayer player, ItemStack stack)
	{
		
	}
}