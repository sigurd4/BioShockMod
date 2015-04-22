package com.sigurd4.bioshock.item;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.sigurd4.bioshock.reference.RefMod;

public class ItemMoney extends Item implements IItemPickupSound
{
	/**
	 * Cash!
	 */
	public ItemMoney()
	{
		super();
	}
	
	public static int consumeCash(ItemMoney currency, EntityPlayer player, int amount)
	{
		return addCash(currency, player, -amount);
	}
	
	public static int addCash(ItemMoney currency, EntityPlayer player, int amount)
	{
		return setCash(currency, player, getCash(currency, player) + amount);
	}
	
	public static int getCash(ItemMoney currency, EntityPlayer player)
	{
		int got = 0;
		for(int i = 0; i < player.inventory.getSizeInventory(); ++i)
		{
			if(player.inventory.getStackInSlot(i) != null && player.inventory.getStackInSlot(i).getItem() == currency)
			{
				got += player.inventory.getStackInSlot(i).stackSize;
			}
		}
		return got;
	}
	
	public static int setCash(ItemMoney currency, EntityPlayer player, int amount)
	{
		int i = 0;
		while(getCash(currency, player) + i < amount)
		{
			EntityItem entityitem = player.dropPlayerItemWithRandomChoice(new ItemStack(currency, 1), false);
			if(entityitem != null)
			{
				entityitem.setNoPickupDelay();
				entityitem.setOwner(player.getName());
			}
			++i;
		}
		while(getCash(currency, player) > amount && getCash(currency, player) > 0)
		{
			if(!player.inventory.consumeInventoryItem(currency))
			{
				break;
			}
		}
		return getCash(currency, player);
	}
	
	@Override
	public void playPickupSound(EntityPlayer player, ItemStack stack)
	{
		for(int i = -1; i < stack.stackSize; ++i)
		{
			player.worldObj.playSoundAtEntity(player, RefMod.MODID + ":" + "random.coin", 0.6F, 0.9F + player.worldObj.rand.nextFloat() * 0.4F);
		}
	}
	
	@Override
	public void playRightClickSound(ItemStack stack, World world, EntityPlayer player)
	{
		
	}
	
	@Override
	public void playSelectSound(EntityPlayer player, ItemStack stack)
	{
		
	}
}
