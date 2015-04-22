package com.sigurd4.bioshock.inventory;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.sigurd4.bioshock.M;
import com.sigurd4.bioshock.reference.RefMod;

public class ContainerUInvent extends Container implements IContainerAddPlayerSlots
{
	/** The crafting matrix inventory (3x3). */
	public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
	public IInventory craftResult = new InventoryCraftResult();
	protected World worldObj;
	public BlockPos pos;
	public final static ArrayList<EntityPlayer> playerList2 = new ArrayList<EntityPlayer>();
	
	public ContainerUInvent(InventoryPlayer inventory, World world, BlockPos pos)
	{
		this.worldObj = world;
		this.pos = pos;
		this.addSlotToContainer(new SlotCrafting(inventory.player, this.craftMatrix, this.craftResult, 0, 124, 35));
		int a;
		int b;
		
		for(a = 0; a < 3; ++a)
		{
			for(b = 0; b < 3; ++b)
			{
				this.addSlotToContainer(new Slot(this.craftMatrix, b + a * 3, 30 + b * 18, 17 + a * 18));
			}
		}
		
		for(a = 0; a < 3; ++a)
		{
			for(b = 0; b < 9; ++b)
			{
				this.addSlotToContainer(new Slot(inventory, b + a * 9 + 9, 8 + b * 18, 84 + a * 18));
			}
		}
		
		for(a = 0; a < 9; ++a)
		{
			this.addSlotToContainer(new Slot(inventory, a, 8 + a * 18, 142));
		}
		
		this.onCraftMatrixChanged(this.craftMatrix);
		onContainerOpened(inventory.player);
	}
	
	/**
	 * Callback for when the crafting matrix is changed.
	 */
	@Override
	public void onCraftMatrixChanged(IInventory inv)
	{
		this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.worldObj));
	}
	
	/**
	 * Called when the container is closed.
	 */
	@Override
	public void onContainerClosed(EntityPlayer player)
	{
		super.onContainerClosed(player);
		playerList2.remove(player);
		
		if(this.worldObj.isRemote)
		{
			if(player == Minecraft.getMinecraft().thePlayer || playerList2.size() <= 0)
			{
				worldObj.playSoundEffect(pos.getX() + 0.5, pos.getY() + 0.7, pos.getZ() + 0.5, RefMod.MODID + ":" + "block.uinvent.close", 1, 1);
			}
		}
		
		if(!this.worldObj.isRemote)
		{
			for(int i = 0; i < 9; ++i)
			{
				ItemStack stack = this.craftMatrix.getStackInSlotOnClosing(i);
				
				if(stack != null)
				{
					player.dropPlayerItemWithRandomChoice(stack, false);
				}
			}
		}
	}
	
	public void onContainerOpened(EntityPlayer player)
	{
		if(worldObj.isRemote)
		{
			if(Minecraft.getMinecraft().thePlayer == player || playerList2.size() <= 0)
			{
				worldObj.playSoundEffect(pos.getX() + 0.5, pos.getY() + 0.7, pos.getZ() + 0.5, RefMod.MODID + ":" + "block.uinvent.open", 1, 1);
			}
		}
		playerList2.add(player);
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return this.worldObj.getBlockState(new BlockPos(this.pos.getX(), this.pos.getY(), this.pos.getZ())).getBlock() != M.VendorUInvent ? false : player.getDistanceSq(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 64.0D;
	}
	
	/**
	 * Called when a player shift-clicks on a slot. You must override this or
	 * you will crash when someone does that.
	 */
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotI)
	{
		ItemStack stack = null;
		Slot slot = (Slot)this.inventorySlots.get(slotI);
		
		if(slot != null && slot.getHasStack())
		{
			ItemStack stack1 = slot.getStack();
			stack = stack1.copy();
			
			if(slotI == 0)
			{
				if(!this.mergeItemStack(stack1, 10, 46, true))
				{
					return null;
				}
				
				slot.onSlotChange(stack1, stack);
			}
			else if(slotI >= 10 && slotI < 37)
			{
				if(!this.mergeItemStack(stack1, 37, 46, false))
				{
					return null;
				}
			}
			else if(slotI >= 37 && slotI < 46)
			{
				if(!this.mergeItemStack(stack1, 10, 37, false))
				{
					return null;
				}
			}
			else if(!this.mergeItemStack(stack1, 10, 46, false))
			{
				return null;
			}
			
			if(stack1.stackSize == 0)
			{
				slot.putStack((ItemStack)null);
			}
			else
			{
				slot.onSlotChanged();
			}
			
			if(stack1.stackSize == stack.stackSize)
			{
				return null;
			}
			
			slot.onPickupFromSlot(player, stack1);
		}
		
		return stack;
	}
	
	@Override
	public ItemStack slotClick(int slot, int p_75144_2_, int operation, EntityPlayer player)
	{
		//return super.slotClick(slot-1, p_75144_2_, operation, player); nb: this did not work
		return super.slotClick(slot, p_75144_2_, operation, player);
	}
	
	@Override
	public boolean canMergeSlot(ItemStack stack, Slot slot)
	{
		return slot.inventory != this.craftResult && super.canMergeSlot(stack, slot);
	}
	
	@Override
	public Slot addSlotToContainer2(Slot slot)
	{
		return this.addSlotToContainer(slot);
	}
}