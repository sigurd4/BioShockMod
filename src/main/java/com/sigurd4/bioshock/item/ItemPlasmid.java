package com.sigurd4.bioshock.item;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.sigurd4.bioshock.M;
import com.sigurd4.bioshock.extendedentity.ExtendedPlayer;
import com.sigurd4.bioshock.itemtags.ItemTagInteger;
import com.sigurd4.bioshock.itemtags.ItemTagLong;
import com.sigurd4.bioshock.itemtags.ItemTagString;
import com.sigurd4.bioshock.plasmids.Plasmid;
import com.sigurd4.bioshock.plasmids.PlasmidHold;

public abstract class ItemPlasmid extends Item implements IItemIdFrom
{
	public final Plasmid plasmid;
	
	public static final HashMap<EntityPlayer, String> skins = new HashMap();
	
	//nbt
	public static final ItemTagInteger SLOT = new ItemTagInteger("Slot", 0, 0, InventoryPlayer.getHotbarSize(), false);
	public static final ItemTagInteger COOLDOWN = new ItemTagInteger("Cooldown", 0, 0, Integer.MAX_VALUE, true);
	public static final ItemTagLong PLAYER_UUID_MOST = new ItemTagLong("PlayerUUIDMost", 0L, 0L, Long.MAX_VALUE, false);
	public static final ItemTagLong PLAYER_UUID_LEAST = new ItemTagLong("PlayerUUIDLeast", 0L, 0L, Long.MAX_VALUE, false);
	public static final ItemTagString PLAYER_NAME = new ItemTagString("PlayerName", "", false);
	
	public ItemPlasmid(Plasmid plasmid)
	{
		super();
		this.plasmid = plasmid;
		this.setCreativeTab(M.tabs.plasmids);
		this.setMaxStackSize(1);
		this.setMaxDamage(400);
	}
	
	@Override
	public String getId()
	{
		return type(this.plasmid) + "_" + this.plasmid.id;
	}
	
	public static int tickd = -1;
	
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
		EntityPlayer player = this.getPlayer(stack);
		if(player != null)
		{
			ExtendedPlayer props = ExtendedPlayer.get(player);
			if(props.hasPlasmid(this.plasmid))
			{
				float min = 0.1F;
				double d = 1 - (double)props.getEve() / (double)props.getMaxEve();
				float c = this.plasmid.cost1;
				if(c < min)
				{
					c = min;
				}
				if(stack == Minecraft.getMinecraft().thePlayer.getHeldItem() && tickd > 0 && (props.getEve() - this.plasmid.cost1 >= 0 || this instanceof ItemPlasmidInjectable))
				{
					d = 1 - ((double)props.getEve() - c) / props.getMaxEve();
				}
				if(d < 0)
				{
					d = 0;
				}
				if(d > 1 - min && d < 1)
				{
					d = 1 - min;
				}
				if(d > 1)
				{
					d = 1;
				}
				if(props.getMaxEve() - this.plasmid.cost1 < 0 && !(this instanceof ItemPlasmidInjectable))
				{
					d = 1;
				}
				return d;
			}
		}
		return 1;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected)
	{
		SLOT.set(stack, slot);
		if(COOLDOWN.get(stack) > 0)
		{
			COOLDOWN.add(stack, -1);
		}
		if(entity instanceof EntityPlayer)
		{
			ExtendedPlayer props = ExtendedPlayer.get((EntityPlayer)entity);
			if(entity instanceof EntityPlayer)
			{
				UUID uuid = ((EntityPlayer)entity).getUUID(((EntityPlayer)entity).getGameProfile());
				PLAYER_UUID_MOST.set(stack, uuid.getMostSignificantBits());
				PLAYER_UUID_LEAST.set(stack, uuid.getLeastSignificantBits());
				PLAYER_NAME.set(stack, ((EntityPlayer)entity).getName());
				if(world.isRemote)
				{
					M.proxy.sendPlayerSkinData((EntityPlayer)entity);
				}
				else if(!skins.containsKey(entity))
				{
					skins.put((EntityPlayer)entity, "textures/entity/steve.png");
				}
			}
		}
		this.plasmid.onUpdate2(stack, world, entity, slot, isSelected);
		this.plasmid.onUpdate(stack, world, entity, slot, isSelected);
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
		if(!this.plasmid.isFinished)
		{
			list.add(EnumChatFormatting.DARK_RED + "(This " + type(this.plasmid) + " does not work yet)");
		}
		for(int i = 0; i < this.plasmid.description.length; ++i)
		{
			list.add(" " + EnumChatFormatting.WHITE + EnumChatFormatting.ITALIC + this.plasmid.description[i]);
		}
		if(player != null)
		{
			ExtendedPlayer props = ExtendedPlayer.get(player);
			if(props.hasPlasmid(this.plasmid))
			{
				list.add(EnumChatFormatting.BLUE + this.fuel().toUpperCase() + ": " + props.getEve() + "/" + props.getMaxEve() + EnumChatFormatting.RESET);
			}
			else
			{
				if(this.getItemUseAction(stack) == EnumAction.DRINK)
				{
					list.add("Drink to obtain powers.");
				}
				else
				{
					list.add("Combine with an empty hypo");
					list.add("and inject yourself to");
					list.add("obtain powers.");
				}
				if(props.getEve() < 1)
				{
					list.add("(also make sure you have enough " + this.fuel() + "!)");
				}
			}
		}
	}
	
	protected static int getItemCount(InventoryPlayer inventory, Item item)
	{
		int count = 0;
		for(int i = 0; i < inventory.getSizeInventory(); ++i)
		{
			if(inventory.getStackInSlot(i) != null && inventory.getStackInSlot(i).getItem() == item)
			{
				count += inventory.getStackInSlot(i).stackSize;
			}
		}
		return count;
	}
	
	/**
	 * Returns True is the item is renderer in full 3D when hold.
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isFull3D()
	{
		return true;
	}
	
	@Override
	public boolean isDamaged(ItemStack stack)
	{
		if(stack.getTagCompound() != null && this.plasmid.isFinished)
		{
			EntityPlayer player = this.getPlayer(stack);
			if(player != null)
			{
				ExtendedPlayer props = ExtendedPlayer.get(player);
				if(props.hasPlasmid(this.plasmid))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean hasEve(ItemStack stack, EntityPlayer player, int amount)
	{
		int eve = ExtendedPlayer.get(player).getEve();
		boolean b = this.consumeEve(stack, player, amount);
		ExtendedPlayer.get(player).setEve(eve);
		return b;
	}
	
	public boolean consumeEve(ItemStack stack, EntityPlayer player, int amount)
	{
		ExtendedPlayer props = ExtendedPlayer.get(player);
		amount = props.passiveController.plasmidUse(player, stack, amount);
		if(this.getItemUseAction(stack) == EnumAction.DRINK)
		{
			return props.getEve() - amount >= 0 && props.consumeEve2(amount);
		}
		else
		{
			return props.getEve() > 0 && props.consumeEve(amount);
		}
	}
	
	protected boolean usePlasmid(ItemStack stack, World world, EntityPlayer player)
	{
		ExtendedPlayer props = ExtendedPlayer.get(player);
		EnumCanUsePlasmidResult canUsePlasmid = this.canUsePlasmid(stack, world, player);
		if(canUsePlasmid == EnumCanUsePlasmidResult.YES && this.consumeEve(stack, player, this.plasmid.cost1))
		{
			this.plasmid.onItemRightClick(stack, world, player);
			COOLDOWN.set(stack, 7);
		}
		return canUsePlasmid == EnumCanUsePlasmidResult.YES || canUsePlasmid == EnumCanUsePlasmidResult.NO2;
	}
	
	protected enum EnumCanUsePlasmidResult
	{
		YES, NO1, NO2
	}
	
	protected EnumCanUsePlasmidResult canUsePlasmid(ItemStack stack, World world, EntityPlayer player)
	{
		ExtendedPlayer props = ExtendedPlayer.get(player);
		if(props.hasPlasmid(this.plasmid))
		{
			props.givePlasmid(this.plasmid);
			if(COOLDOWN.get(stack) <= 0)
			{
				if((!props.isRightClickHeldDownLast || this.plasmid instanceof PlasmidHold) && !player.isInsideOfMaterial(Material.water))
				{
					if(this.hasEve(stack, player, this.plasmid.cost1))
					{
						return EnumCanUsePlasmidResult.YES;
					}
				}
			}
			return EnumCanUsePlasmidResult.NO2;
		}
		return EnumCanUsePlasmidResult.NO1;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack)
	{
		EntityPlayer player = net.minecraft.client.Minecraft.getMinecraft().thePlayer;
		return this.canUsePlasmid(stack, player.worldObj, player) == EnumCanUsePlasmidResult.YES;
	}
	
	public static final EntityPlayer getPlayer(ItemStack stack)
	{
		UUID uuid = new UUID(PLAYER_UUID_MOST.get(stack), PLAYER_UUID_LEAST.get(stack));
		Entity entity = MinecraftServer.getServer().getEntityFromUuid(uuid);
		if(entity == null)
		{
			loop:
			for(World world : MinecraftServer.getServer().worldServers)
			{
				for(Object player : world.playerEntities)
				{
					if(player instanceof EntityPlayer && (PLAYER_NAME.get(stack).equals(((EntityPlayer)player).getName()) || ((EntityPlayer)player).getUniqueID().getMostSignificantBits() == uuid.getMostSignificantBits() && ((EntityPlayer)player).getUniqueID().getLeastSignificantBits() == uuid.getLeastSignificantBits()))
					{
						entity = (EntityPlayer)player;
						break loop;
					}
				}
			}
		}
		if(entity != null && entity instanceof EntityPlayer)
		{
			return (EntityPlayer)entity;
		}
		return null;
	}
	
	public final String type()
	{
		return type(this.plasmid);
	}
	
	public final static String type(Plasmid plasmid)
	{
		return plasmid.type ? "vigor" : "plasmid";
	}
	
	public final String fuel()
	{
		return fuel(this.plasmid);
	}
	
	public final static String fuel(Plasmid plasmid)
	{
		return plasmid.type ? "salts" : "eve";
	}
}
