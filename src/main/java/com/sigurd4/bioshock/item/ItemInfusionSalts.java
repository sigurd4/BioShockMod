package com.sigurd4.bioshock.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.sigurd4.bioshock.extendedentity.ExtendedPlayer;

public class ItemInfusionSalts extends ItemInfusion
{
	/**
	 * Health upgrade:
	 */
	public ItemInfusionSalts()
	{
		super();
		this.setUnlocalizedName("infusionSalts");
		//this;
	}
	
	@Override
	protected boolean getIfIsUsable(ItemStack stack, World world, EntityPlayer player)
	{
		ExtendedPlayer props = ExtendedPlayer.get(player);
		if(props.getMaxEve() + 20 <= 400)
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
		props.setMaxEve(props.getMaxEve() + 20);
		props.setEve(props.getMaxEve());
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
		if(props.getEve() > 0)
		{
			c = EnumChatFormatting.BLUE;
		}
		if(stack != null)
		{
			list.add(c + "Eve: " + props.getEve() + " / " + props.getMaxEve() + EnumChatFormatting.RESET);
		}
		list.add(c + "Eve upgrades: " + (props.getMaxEve() - 200) / 20 + " / 10" + EnumChatFormatting.RESET);
	}
}
