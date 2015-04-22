package com.sigurd4.bioshock.item;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemValuable extends Item
{
	public final int valueMin;
	public final int valueMax;
	public final ItemMoney currency;
	
	public ItemValuable(int valueMin, int valueMax, ItemMoney currency)
	{
		super();
		this.valueMin = valueMin;
		this.valueMax = valueMax;
		this.currency = currency;
		this.setCreativeTab(null);
		this.setMaxStackSize(1);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected)
	{
		if(entity instanceof EntityPlayer && !((EntityPlayer)entity).capabilities.isCreativeMode)
		{
			int amount = this.valueMin;
			if(this.valueMin < this.valueMax)
			{
				amount = Item.itemRand.nextInt(this.valueMax - this.valueMin + 1) + this.valueMin;
			}
			if(amount > this.valueMax)
			{
				amount = this.valueMax;
			}
			if(amount < this.valueMin)
			{
				amount = this.valueMin;
			}
			--stack.stackSize;
			if(stack.stackSize <= 0)
			{
				((EntityPlayer)entity).inventory.setInventorySlotContents(slot, null);
				if(world.isRemote && entity.equals(Minecraft.getMinecraft().thePlayer))
				{
					this.currency.playPickupSound((EntityPlayer)entity, new ItemStack(this.currency));
				}
			}
			this.currency.setCash(this.currency, (EntityPlayer)entity, amount);
		}
		if(stack != null && (stack.stackSize > this.maxStackSize || stack.stackSize < 0))
		{
			((EntityPlayer)entity).inventory.setInventorySlotContents(slot, null);
			if(world.isRemote && entity.equals(Minecraft.getMinecraft().thePlayer))
			{
				this.currency.playPickupSound((EntityPlayer)entity, new ItemStack(this.currency));
			}
		}
	}
	
	/**
	 * allows items to add custom lines of information to the mouseover
	 * description
	 */
	@Override
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
	{
		list.add("Currency: " + I18n.format(this.currency.getUnlocalizedName() + ".name"));
		if(this.valueMin < this.valueMax)
		{
			list.add("Worth: " + this.valueMin + " to " + this.valueMax);
			list.add("(Random)");
		}
		else
		{
			list.add("Worth: " + this.valueMin);
		}
	}
}
