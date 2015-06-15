package com.sigurd4.bioshock.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.sigurd4.bioshock.M;
import com.sigurd4.bioshock.Stuff;
import com.sigurd4.bioshock.itemtags.ItemTagItemStack;

public class ItemArmorDivingSuitTank extends ItemArmorDivingSuit
{
	public ItemArmorDivingSuit item1;
	public ItemArmorDivingSuit item2;
	public ItemArmorDivingSuit item3;
	
	//nbt
	public static final ItemTagItemStack TANK = new ItemTagItemStack("Tank", null, false)
	{
		@Override
		public ItemStack isValid(NBTTagCompound compound, ItemStack value)
		{
			if(value != null && !(value.getItem() instanceof ItemTank))
			{
				return null;
			}
			if(value != null)
			{
				value.stackSize = 1;
			}
			return super.isValid(compound, value);
		}
	};
	
	public ItemArmorDivingSuitTank(ArmorMaterial material, EnumArmorType armorType, String texture, boolean isMetal, ItemArmorDivingSuit item1, ItemArmorDivingSuit item2, ItemArmorDivingSuit item3)
	{
		super(material, armorType, texture, isMetal);
		this.item3 = item3;
		this.item2 = item2;
		this.item1 = item1;
		this.setHasSubtypes(true);
	}
	
	/**
	 * returns a list of items with the same ID, but different meta (eg: dye
	 * returns 16 items)
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTab, List list)
	{
		ItemStack stack = new ItemStack(item, 1, 0);
		ItemStack tank = new ItemStack(M.items.utility.tank, 1, 0);
		((ItemTank)tank.getItem()).FILLED.set(tank, ((ItemTank)tank.getItem()).CAPACITY.get(tank));
		TANK.set(stack, tank);
		list.add(stack);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack)
	{
		if(TANK.get(stack) != null)
		{
			TANK.get(stack).getItem().onArmorTick(world, player, TANK.get(stack));
			if(this.isSealed(player))
			{
				if((float)player.ticksExisted / 10 == Math.ceil((float)player.ticksExisted / 10) && (float)player.ticksExisted / 10 == Math.floor((float)player.ticksExisted / 10) && ((ItemTank)TANK.get(stack).getItem()).FILLED.get(TANK.get(stack)) > 0)
				{
					((ItemTank)TANK.get(stack).getItem()).FILLED.add(TANK.get(stack), -1);
					int air = (int)(300 * (float)((ItemTank)TANK.get(stack).getItem()).FILLED.get(TANK.get(stack)) / ((ItemTank)TANK.get(stack).getItem()).CAPACITY.get(TANK.get(stack)) * 10) / 10;
					if(air < 30)
					{
						air = 30;
					}
					if(air > player.getAir() || true)
					{
						player.setAir(air);
					}
				}
			}
		}
	}
	
	/**
	 * Queries the percentage of the 'Durability' bar that should be drawn.
	 * 
	 * @param stack
	 *            The current ItemStack
	 * @return 1.0 for 100% 0 for 0%
	 */
	@Override
	public double getDurabilityForDisplay(ItemStack stack)
	{
		if(TANK.get(stack) != null)
		{
			return TANK.get(stack).getItem().getDurabilityForDisplay(TANK.get(stack));
		}
		return super.getDurabilityForDisplay(stack);
	}
	
	/**
	 * allows items to add custom lines of information to the mouseover
	 * description
	 */
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
	{
		if(TANK.get(stack) != null)
		{
			String s = TANK.get(stack).getDisplayName();
			list.add("With " + Stuff.Strings.indefiniteArticle(s) + " " + s + " attached.");
			TANK.get(stack).getItem().addInformation(TANK.get(stack), player, list, bool);
			if(((ItemTank)TANK.get(stack).getItem()).FILLED.get(TANK.get(stack)) <= 0)
			{
				list.add("(EMPTY) Right click to remove gas tank.");
			}
			
			if(bool && ((ItemTank)TANK.get(stack).getItem()).FILLED.get(TANK.get(stack)) > 0)
			{
				if(this.isSealed(player))
				{
					list.add("[SEALED]");
				}
				else
				{
					list.add("[NOT SEALED]:");
					ArrayList<ItemArmorDivingSuit>[] map = new ArrayList[]{new ArrayList(), new ArrayList(), new ArrayList(), new ArrayList()};
					
					map[this.armorType].add(this);
					map[this.item1.armorType].add(this.item1);
					map[this.item2.armorType].add(this.item2);
					map[this.item3.armorType].add(this.item3);
					
					for(int i = 0; i < map.length; ++i)
					{
						for(ItemArmorDivingSuit item : map[i])
						{
							list.add("" + this.a(item, player));
						}
					}
				}
			}
		}
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if(TANK.get(stack) != null && (((ItemTank)TANK.get(stack).getItem()).FILLED.get(TANK.get(stack)) <= 0 || player.isSneaking()))
		{
			if(true)
			{
				EntityItem entityitem = player.dropPlayerItemWithRandomChoice(TANK.get(stack), false);
				if(entityitem != null)
				{
					entityitem.setNoPickupDelay();
					entityitem.setOwner(player.getName());
				}
			}
			if(stack.stackSize <= 1)
			{
				TANK.set(stack, null);
			}
			else
			{
				ItemStack stack2 = stack.copy();
				stack2.stackSize = 1;
				TANK.set(stack2, null);
				
				EntityItem entityitem = player.dropPlayerItemWithRandomChoice(stack2, false);
				if(entityitem != null)
				{
					entityitem.setNoPickupDelay();
					entityitem.setOwner(player.getName());
				}
				
				--stack.stackSize;
			}
			
			return stack;
		}
		else
		{
			return super.onItemRightClick(stack, world, player);
		}
	}
	
	public boolean isSealed(EntityPlayer player)
	{
		return this.a(this.item1, player) && this.a(this.item2, player) && this.a(this.item3, player) && this.a(this, player);
	}
	
	public boolean a(ItemArmorDivingSuit item, EntityPlayer player)
	{
		int index = item.armorType + 1;
		if(player.getEquipmentInSlot(index) != null)
		{
			return Item.getIdFromItem(player.getEquipmentInSlot(index).getItem()) == Item.getIdFromItem(item) || Item.getIdFromItem(player.getEquipmentInSlot(index).getItem()) == Item.getIdFromItem(this);
		}
		else
		{
			return false;
		}
	}
}
