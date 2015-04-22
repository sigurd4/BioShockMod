package com.sigurd4.bioshock.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.google.common.collect.Lists;
import com.sigurd4.bioshock.M;
import com.sigurd4.bioshock.M.Id;
import com.sigurd4.bioshock.extendedentity.ExtendedPlayer;
import com.sigurd4.bioshock.itemtags.ItemTagEnum;
import com.sigurd4.bioshock.itemtags.ItemTagInteger;

public class ItemInfusionQuantumSuperposition extends ItemInfusion implements IItemTextureVariants
{
	protected static enum EnumTypes
	{
		HEALTH, SALTS, SHIELDS
	}
	
	protected HashMap<EnumTypes, ItemInfusion> map = new HashMap();
	
	//nbt
	public static final ItemTagEnum<EnumTypes> TYPE = new ItemTagEnum("Type", EnumTypes.HEALTH, true);
	public static final ItemTagInteger TIMER = new ItemTagInteger("Timer", 0, 0, 40, true);
	
	/**
	 * Upgrade in quantum superposition. Alternates between all three upgrades:
	 */
	public ItemInfusionQuantumSuperposition(ItemInfusion i1, ItemInfusion i2, ItemInfusion i3)
	{
		super();
		this.setUnlocalizedName("infusionQuantumSuperposition");
		this.map.put(EnumTypes.HEALTH, i1);
		this.map.put(EnumTypes.SALTS, i2);
		this.map.put(EnumTypes.SHIELDS, i3);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public ModelResourceLocation getModel(ItemStack stack, EntityPlayer player, int useRemaining)
	{
		if(!TYPE.has(stack) || !player.inventory.hasItemStack(stack))
		{
			return new ModelResourceLocation(this.getTextureVariants(stack.getItemDamage())[0], "inventory");
		}
		return new ModelResourceLocation(this.getTextureVariants(stack.getItemDamage())[TYPE.get(stack).ordinal() + (TIMER.get(stack) > TIMER.max - 8 ? EnumTypes.values().length : 0)], "inventory");
	}
	
	@Override
	public String[] getTextureVariants(int meta)
	{
		ArrayList<String> ids = Lists.newArrayList();
		ArrayList<String> ids2 = Lists.newArrayList();
		for(EnumTypes e : this.map.keySet())
		{
			ItemInfusion item = this.map.get(e);
			Id id = M.getId(item);
			ids.add(id.mod + ":" + id.id);
			ids2.add(id.mod + ":" + id.id + "_animation");
		}
		return ids.toArray(new String[]{});
	}
	
	/**
	 * Called by the default implemetation of EntityItem's onUpdate method,
	 * allowing for cleaner
	 * control over the update of the item without having to write a subclass.
	 * 
	 * @param entityItem
	 *            The entity Item
	 * @return Return true to skip any further update code.
	 */
	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem)
	{
		if(entityItem.ticksExisted % 2 == 1)
		{
			this.onUpdate(entityItem.getEntityItem(), entityItem.worldObj, null, 0, false);
		}
		return false;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected)
	{
		if(entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)entity;
			ExtendedPlayer props = ExtendedPlayer.get(player);
			if(player.getItemInUse() != stack && !props.isRightClickHeldDown && TIMER.get(stack) > 0 || !this.getIfIsUsable(stack, world, (EntityPlayer)entity))
			{
				TIMER.add(stack, -1);
			}
		}
		else
		{
			if(TIMER.get(stack) > 0)
			{
				TIMER.add(stack, -1);
			}
		}
		if(TIMER.get(stack) <= 0)
		{
			TIMER.set(stack, TIMER.max);
			int i = TYPE.get(stack).ordinal() + 1;
			if(i >= EnumTypes.values().length)
			{
				i -= EnumTypes.values().length;
			}
			TYPE.set(stack, EnumTypes.values()[i]);
		}
	}
	
	/**
	 * Returns the unlocalized name of this item. This version accepts an
	 * ItemStack so different stacks can have
	 * different names based on their damage or NBT.
	 */
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		if(!TYPE.has(stack) || M.proxy.world(0).isRemote && !Minecraft.getMinecraft().thePlayer.inventory.hasItemStack(stack))
		{
			return super.getUnlocalizedName(stack);
		}
		ItemInfusion item = this.map.get(TYPE.get(stack));
		return item.getUnlocalizedName(stack);
	}
	
	@Override
	protected boolean getIfIsUsable(ItemStack stack, World world, EntityPlayer player)
	{
		if(!TYPE.has(stack) || !player.inventory.hasItemStack(stack))
		{
			return false;
		}
		ItemInfusion item = this.map.get(TYPE.get(stack));
		return item.getIfIsUsable(stack, world, player);
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player)
	{
		if(!TYPE.has(stack) || !player.inventory.hasItemStack(stack))
		{
			return stack;
		}
		ItemInfusion item = this.map.get(TYPE.get(stack));
		return item.onItemUseFinish(stack, world, player);
	}
	
	/**
	 * allows items to add custom lines of information to the mouseover
	 * description
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
	{
		if(!TYPE.has(stack) || !player.inventory.hasItemStack(stack))
		{
			list.add("An infusion in quantum superposition.");
			list.add("Alternates between a health upgrade,");
			list.add("a salts upgrade and a shields upgrade.");
			list.add("Choose wisely!");
			return;
		}
		ItemInfusion item = this.map.get(TYPE.get(stack));
		item.addInformation(stack, player, list, bool);
	}
}
