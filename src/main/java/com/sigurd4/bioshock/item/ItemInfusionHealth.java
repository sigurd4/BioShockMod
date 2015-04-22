package com.sigurd4.bioshock.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.sigurd4.bioshock.extendedentity.ExtendedPlayer;

public class ItemInfusionHealth extends ItemInfusion
{
	/**
	 * Health upgrade:
	 */
	public ItemInfusionHealth()
	{
		super();
		this.setUnlocalizedName("infusionHealth");
		//this.setTextureName(RefMod.MODID + ":" + "flask_infusion_health");
	}
	
	@Override
	protected boolean getIfIsUsable(ItemStack stack, World world, EntityPlayer player)
	{
		ExtendedPlayer props = ExtendedPlayer.get(player);
		
		if(props.extraHealth + 1 <= 20)
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
		props.extraHealth += 2;
		if(props.extraHealth > 20)
		{
			props.extraHealth = 20;
		}
		props.updateHealth();
		player.setHealth(player.getMaxHealth());
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
		if(player.getHealth() > 0)
		{
			c = EnumChatFormatting.RED;
		}
		if(stack != null)
		{
			list.add(c + "Health: " + (int)player.getHealth() + " / " + (int)player.getMaxHealth() + EnumChatFormatting.RESET);
		}
		list.add(c + "Health upgrades: " + (float)props.extraHealth / 2 + " / 10" + EnumChatFormatting.RESET);
	}
}
