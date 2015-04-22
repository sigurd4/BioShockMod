package com.sigurd4.bioshock.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.sigurd4.bioshock.extendedentity.ExtendedPlayer;
import com.sigurd4.bioshock.plasmids.Plasmid;

public class ItemPlasmidInjectable extends ItemPlasmid
{
	public final ItemPlasmidInjectableSyringe syringe;
	
	//nbt
	
	/**
	 * Containers with genetic liquid to be injected into the body that give you
	 * mutant powers:
	 */
	public ItemPlasmidInjectable(Plasmid plasmid)
	{
		super(plasmid);
		this.setUnlocalizedName(type(plasmid) + "." + plasmid.id);
		//this.setTextureName(RefMod.MODID + ":" + "bottle_" + type(plasmid));
		this.setContainerItem(this);
		this.syringe = new ItemPlasmidInjectableSyringe(plasmid);
	}
	
	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		ExtendedPlayer props = ExtendedPlayer.get(player);
		if(stack.getTagCompound() == null)
		{
			stack.setTagCompound(new NBTTagCompound());
		}
		if(props.hasPlasmid(this.plasmid))
		{
			super.usePlasmid(stack, world, player);
		}
		return stack;
	}
}
