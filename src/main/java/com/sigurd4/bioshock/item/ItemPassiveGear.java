package com.sigurd4.bioshock.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.sigurd4.bioshock.passives.Passive;

public class ItemPassiveGear extends ItemPassive
{
	public final EnumArmorType armorType;
	
	public ItemPassiveGear(Passive passive, EnumArmorType armorType, String... description)
	{
		super(passive, description);
		this.armorType = armorType;
		//this.setTextureName(RefMod.MODID + ":" + "gear");
		this.setUnlocalizedName("gear." + passive.name);
	}
	
	@Override
	public String getId()
	{
		return "gear_" + this.p.getID();
	}
	
	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		int i = 3 - this.armorType.ordinal();
		ItemStack stack1 = player.getCurrentArmor(i);
		
		if(stack1 == null && this.isValidArmor(stack, this.armorType.ordinal(), player))
		{
			player.setCurrentItemOrArmor(1 + i, stack.copy());
			stack.stackSize = 0;
		}
		
		return stack;
	}
	
	/**
	 * Determines if the specific ItemStack can be placed in the specified armor
	 * slot.
	 *
	 * @param stack
	 *            The ItemStack
	 * @param armorType
	 *            Armor slot ID: 0: Helmet, 1: Chest, 2: Legs, 3: Boots
	 * @param entity
	 *            The entity trying to equip the armor
	 * @return True if the given ItemStack can be inserted in the slot
	 */
	@Override
	public boolean isValidArmor(ItemStack stack, int armorType, Entity entity)
	{
		return armorType == this.armorType.ordinal() && entity instanceof EntityPlayer;
	}
}
