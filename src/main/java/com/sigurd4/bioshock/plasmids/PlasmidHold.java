package com.sigurd4.bioshock.plasmids;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.sigurd4.bioshock.extendedentity.ExtendedPlayer;
import com.sigurd4.bioshock.item.ItemPlasmid;
import com.sigurd4.bioshock.itemtags.ItemTagInteger;

public abstract class PlasmidHold extends Plasmid
{
	public int consumeRate;
	//TODO ^final
	public static final ItemTagInteger HELD = new ItemTagInteger("Held", 0, 0, 9, true);
	public static final ItemTagInteger HELDFOR = new ItemTagInteger("HeldFor", 0, 0, Integer.MAX_VALUE, true);
	
	public PlasmidHold(String id, int cost1, int cost2, boolean type, int consumeRate, String... description)
	{
		super(id, cost1, cost2, type, description);
		this.consumeRate = consumeRate;
	}
	
	@Override
	public final ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if(stack.getTagCompound() == null)
		{
			stack.setTagCompound(new NBTTagCompound());
		}
		if(HELDFOR.get(stack) <= 0)
		{
			this.clicked(stack, world, player);
		}
		HELD.set(stack, 9);
		return stack;
	}
	
	@Override
	public final void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected)
	{
		if(stack.getTagCompound() == null)
		{
			stack.setTagCompound(new NBTTagCompound());
		}
		if(HELD.get(stack) > 0)
		{
			ItemPlasmid item = (ItemPlasmid)stack.getItem();
			HELD.add(stack, -1);
			HELDFOR.add(stack, 1);
			this.held(stack, world, entity, slot, isSelected);
			if(entity instanceof EntityPlayer && (this.consumeRate <= 1 || HELDFOR.get(stack) % this.consumeRate == this.consumeRate))
			{
				ExtendedPlayer props = ExtendedPlayer.get((EntityPlayer)entity);
				if(!item.consumeEve(stack, (EntityPlayer)entity, 1))
				{
					HELD.set(stack, 0);
				}
			}
		}
		if(HELD.get(stack) <= 0)
		{
			this.notHeld(stack, world, entity, slot, isSelected);
			if(HELDFOR.get(stack) > 0)
			{
				this.unClicked(stack, world, entity, slot, isSelected);
			}
			HELDFOR.set(stack, 0);
			HELD.set(stack, 0);
		}
	}
	
	public abstract void clicked(ItemStack stack, World world, EntityPlayer entity);
	
	public abstract void held(ItemStack stack, World world, Entity entity, int slot, boolean isSelected);
	
	public abstract void notHeld(ItemStack stack, World world, Entity entity, int slot, boolean isSelected);
	
	public abstract void unClicked(ItemStack stack, World world, Entity entity, int slot, boolean isSelected);
	
	@Override
	public abstract ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player);
}
