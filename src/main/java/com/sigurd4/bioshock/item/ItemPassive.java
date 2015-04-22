package com.sigurd4.bioshock.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.sigurd4.bioshock.M;
import com.sigurd4.bioshock.extendedentity.ExtendedPlayer;
import com.sigurd4.bioshock.passives.Passive;

public abstract class ItemPassive extends Item implements IItemIdFrom
{
	public final String[] description;
	public final Passive p;
	
	/**
	 * Just a generic item. Used for crafting recipes, ammo and more!
	 */
	public ItemPassive(Passive p, String[] description)
	{
		super();
		this.p = p;
		this.setMaxStackSize(1);
		this.setCreativeTab(M.tabs.passives);
		this.description = description;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack)
	{
		boolean b = this.p.playerHas(net.minecraft.client.Minecraft.getMinecraft().thePlayer);
		return b;
	}
	
	/**
	 * allows items to add custom lines of information to the mouseover
	 * description
	 */
	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
	{
		for(int i = 0; i < this.description.length; ++i)
		{
			list.add(EnumChatFormatting.WHITE + "" + EnumChatFormatting.ITALIC + this.description[i]);
		}
		ExtendedPlayer props = ExtendedPlayer.get(player);
		ArrayList<Passive> p = props.tonicsCombat;
		list.add(EnumChatFormatting.GREEN + "(" + p.size() + "/" + props.maxCombatTonics + ") Combat Tonics:");
		for(int i = 0; i < p.size(); ++i)
		{
			list.add(EnumChatFormatting.GREEN + " - " + StatCollector.translateToLocal("item.tonic." + p.get(i).name) + ".name");
		}
		
		p = props.tonicsEngineering;
		list.add(EnumChatFormatting.GOLD + "(" + p.size() + "/" + props.maxEngineeringTonics + ") Engineering Tonics:");
		
		for(int i = 0; i < p.size(); ++i)
		{
			list.add(EnumChatFormatting.GOLD + " - " + StatCollector.translateToLocal("item.tonic." + p.get(i).name) + ".name");
			
		}
		
		p = props.tonicsPhysical;
		list.add(EnumChatFormatting.AQUA + "(" + p.size() + "/" + props.maxPhysicalTonics + ") Physical Tonics:");
		
		for(int i = 0; i < p.size(); ++i)
		{
			list.add(EnumChatFormatting.AQUA + " - " + StatCollector.translateToLocal("item.tonic." + p.get(i).name) + ".name");
			
		}
		
		p = props.getAllGear();
		list.add(EnumChatFormatting.BLUE + "Gear:");
		
		ItemStack[] armor = player.inventory.armorInventory;
		for(int i = 0; i < armor.length; ++i)
		{
			if(armor[i] != null && armor[i].getItem() instanceof ItemPassiveGear)
			{
				String s = "   -  ";
				switch(i)
				{
				case 3:
					s = "Hat:   ";
					break;
				case 2:
					s = "Shirt: ";
					break;
				case 1:
					s = "Pants: ";
					break;
				case 0:
					s = "Boots: ";
					break;
				}
				list.add(EnumChatFormatting.BLUE + " " + s + StatCollector.translateToLocal(armor[i].getUnlocalizedName() + ".name"));
			}
		}
		//list.set(0, EnumChatFormatting.RESET + "" + list.get(0));
	}
}
