package com.sigurd4.bioshock.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.sigurd4.bioshock.M;
import com.sigurd4.bioshock.Stuff;
import com.sigurd4.bioshock.extendedentity.ExtendedPlayer;
import com.sigurd4.bioshock.passives.Passive;

public class ItemPassiveTonicSyringe extends Item implements IItemIdFrom, IItemInit
{
	public Passive p;
	
	/**
	 * Drinkable plasmids that give you mutant powers:
	 */
	public ItemPassiveTonicSyringe(Passive passive, String unlocalizedName)
	{
		super();
		this.setUnlocalizedName(unlocalizedName);
		//this.setTextureName(RefMod.MODID + ":" + "syringe_tonic_" + passive.type.toString().toLowerCase());
		this.p = passive;
		this.setCreativeTab(M.tabs.plasmids);
		this.setMaxStackSize(1);
	}
	
	@Override
	public void init()
	{
		this.setContainerItem(M.items.crafting.empty_hypo);
	}
	
	@Override
	public String getId()
	{
		return "syringe_tonic_" + this.p.type.toString().toLowerCase() + "_" + this.p.getID();
	}
	
	/**
	 * How long it takes to use or consume an item
	 */
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		return 22;
	}
	
	/**
	 * returns the action that specifies what animation to play when the items
	 * is being used
	 */
	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.BOW;
	}
	
	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		ExtendedPlayer props = ExtendedPlayer.get(player);
		if(!props.hasTonic(this.p))
		{
			player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
		}
		else
		{
			props.giveTonic(this.p);
		}
		return stack;
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player)
	{
		ExtendedPlayer props = ExtendedPlayer.get(player);
		props.giveTonic(this.p);
		if(!props.hasHadTonic(this.p))
		{
			Stuff.ItemStacks.adamEffect(stack, world, player);
		}
		Stuff.ItemStacks.syringeUse(stack, world, player);
		if(!props.hasTonic(this.p))
		{
			//effects
		}
		return stack;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack)
	{
		boolean b = this.p.playerHas(net.minecraft.client.Minecraft.getMinecraft().thePlayer);
		return b;
	}
}
