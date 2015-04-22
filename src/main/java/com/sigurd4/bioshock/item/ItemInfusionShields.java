package com.sigurd4.bioshock.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.sigurd4.bioshock.extendedentity.ExtendedPlayer;

public class ItemInfusionShields extends ItemInfusion
{
	/**
	 * Health upgrade:
	 */
	public ItemInfusionShields()
	{
		super();
		this.setUnlocalizedName("infusionShields");
		//this.setTextureName(RefMod.MODID + ":" + "flask_infusion_shields");
	}
	
	@Override
	protected boolean getIfIsUsable(ItemStack stack, World world, EntityPlayer player)
	{
		ExtendedPlayer props = ExtendedPlayer.get(player);
		if(props.getMaxShields() + 1 <= 10)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player)
	{
		ExtendedPlayer props = ExtendedPlayer.get(player);
		props.setMaxShields(props.getMaxShields() + 1);;
		props.setShields(props.getMaxEve());
		--stack.stackSize;
		return stack;
	}
	
	/**
	 * allows items to add custom lines of information to the mouseover
	 * description
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
	{
		ExtendedPlayer props = ExtendedPlayer.get(player);
		EnumChatFormatting c = EnumChatFormatting.DARK_GRAY;
		if(props.getShields() > 0)
		{
			c = EnumChatFormatting.GOLD;
		}
		if(stack != null)
		{
			list.add(c + "Shields: " + Math.ceil(props.getShields() * 10) / 10 + " / " + props.getMaxShields() + EnumChatFormatting.RESET);
		}
		list.add(c + "Shields upgrades: " + props.getMaxShields() + " / 10" + EnumChatFormatting.RESET);
	}
}
