package com.sigurd4.bioshock.item;

import java.util.List;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.sigurd4.bioshock.M;
import com.sigurd4.bioshock.audio.AudioHandler;
import com.sigurd4.bioshock.itemtags.ItemTagInteger;
import com.sigurd4.bioshock.itemtags.ItemTagString;
import com.sigurd4.bioshock.reference.RefMod;

public class ItemAudioLog extends Item implements IItemTextureVariants
{
	public final boolean b;
	
	/**
	 * An audio diary / voxophone
	 */
	public ItemAudioLog(boolean b)
	{
		super();
		this.b = b;
		this.setMaxStackSize(1);
	}
	
	//nbt
	public static final ItemTagInteger ISRUNNING = new ItemTagInteger("IsRunning", 0, 0, Integer.MAX_VALUE, true);
	public static final ItemTagInteger TIME = new ItemTagInteger("Time", 30 * 20, 0, Integer.MAX_VALUE, false);
	public static final ItemTagString OWNER = new ItemTagString("Owner", "sigurd4", false);
	public static final ItemTagString OWNERFANCY = new ItemTagString("OwnerFancy", "sigurd4", false);
	public static final ItemTagString LOG = new ItemTagString("Log", "test", false);
	public static final ItemTagString LOGFANCY = new ItemTagString("LogFancy", "Test Log", false);
	
	/**
	 * allows items to add custom lines of information to the mouseover
	 * description
	 */
	@Override
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
	{
		list.add("Log Name: " + LOGFANCY.get(stack));
		list.add("Log Owner: " + OWNERFANCY.get(stack));
		if(ISRUNNING.get(stack) > 0)
		{
			list.add("Time Left: " + Float.toString((float)Math.floor((double)ISRUNNING.get(stack) / 2F) / 10F));
		}
		list.add("Lenght: " + Float.toString((float)Math.floor((double)TIME.get(stack) / 2F) / 10F));
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
		ItemStack stack = entityItem.getEntityItem();
		Entity entity = entityItem.worldObj.getClosestPlayerToEntity(entityItem, 10);
		if(ISRUNNING.get(stack) > 0 && entity != null)
		{
			AudioHandler.stopSound(entity, RefMod.MODID + ":" + "audiologs." + OWNER.get(stack) + "." + LOG.get(stack));
		}
		ISRUNNING.set(stack, 0);
		return false;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
	{
		if(ISRUNNING.get(stack) > 0)
		{
			ISRUNNING.add(stack, -1);
		}
		if(ISRUNNING.get(stack) == 9)
		{
			AudioHandler.stopSound(entity, RefMod.MODID + ":" + "audiologs." + OWNER.get(stack) + "." + LOG.get(stack));
		}
		
		if(world.isRemote && AudioHandler.getSound(entity, RefMod.MODID + ":" + "audiologs." + OWNER.get(stack) + "." + LOG.get(stack)) == null)
		{
			AudioHandler.stopSound(entity, RefMod.MODID + ":" + "audiologs." + OWNER.get(stack) + "." + LOG.get(stack));
			ISRUNNING.set(stack, 0);
		}
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		for(int i = 0; i < player.inventory.getSizeInventory(); ++i)
		{
			ItemStack stack2 = player.inventory.getStackInSlot(i);
			if(stack2 != null)
			{
				if(stack2.getTagCompound() != null)
				{
					if(stack2 != stack && ISRUNNING.get(stack2) > 0)
					{
						if(ISRUNNING.get(stack2) > 0 && player != null)
						{
							AudioHandler.stopSound(player, RefMod.MODID + ":" + "audiologs." + OWNER.get(stack2) + "." + LOG.get(stack2));
						}
						ISRUNNING.set(stack2, 2);
					}
				}
			}
		}
		if(ISRUNNING.get(stack) <= 0)
		{
			ItemStack stack1 = null;
			for(int i = 0; i < player.inventory.getSizeInventory(); ++i)
			{
				ItemStack stack2 = player.inventory.getStackInSlot(i);
				if(stack2 != null)
				{
					if(stack2.getTagCompound() != null)
					{
						if(stack2 != stack && ItemAudioLog.ISRUNNING.get(stack2) > 0)
						{
							stack1 = stack2;
						}
					}
				}
			}
			if(stack1 == null)
			{
				M.proxy.createAudioLogSound(player, stack, this.b);
				ISRUNNING.set(stack, TIME.get(stack));
			}
		}
		else
		{
			AudioHandler.stopSound(player, RefMod.MODID + ":" + "audiologs." + OWNER.get(stack) + "." + LOG.get(stack));
			ISRUNNING.set(stack, 2);
		}
		return stack;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public ModelResourceLocation getModel(ItemStack stack, EntityPlayer player, int useRemaining)
	{
		if(ISRUNNING.has(stack) && ISRUNNING.get(stack) > 0)
		{
			return new ModelResourceLocation(this.getTextureVariants(stack.getItemDamage())[1], "inventory");
		}
		else
		{
			return new ModelResourceLocation(this.getTextureVariants(stack.getItemDamage())[0], "inventory");
		}
	}
	
	@Override
	public String[] getTextureVariants(int meta)
	{
		return new String[]{M.getId(this).mod + ":" + M.getId(this).id, M.getId(this).mod + ":" + M.getId(this).id + "_playing"};
	}
}
