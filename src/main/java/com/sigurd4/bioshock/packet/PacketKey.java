package com.sigurd4.bioshock.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.sigurd4.bioshock.event.HandlerCommon;
import com.sigurd4.bioshock.extendedentity.ExtendedPlayer;
import com.sigurd4.bioshock.item.ItemArmorDivingSuit;
import com.sigurd4.bioshock.item.ItemEveHypo;
import com.sigurd4.bioshock.item.ItemPlasmid;
import com.sigurd4.bioshock.item.ItemWeaponRanged;
import com.sigurd4.bioshock.item.ItemWeaponSkyHook;

public class PacketKey implements IMessage
{
	public static enum Key
	{
		RELOAD,
		SWITCH_AMMO_TYPE,
		RIGHT_CLICK,
		NOT_RIGHT_CLICK,
		JUMP,
		NOT_JUMP,
		ZOOM,
		NOT_ZOOM;
		
		public static int get(Key key)
		{
			return key.ordinal();
		}
		
		public static Key get(int id)
		{
			if(id < Key.values().length && id >= 0)
			{
				return Key.values()[id];
			}
			return null;
		}
	}
	
	private int i;
	
	public PacketKey()
	{
		this.i = -1;
	}
	
	public PacketKey(Key k)
	{
		this.i = Key.get(k);
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		if(buf.array().length != 1)
		{
			this.i = -1;
			return;
		}
		this.i = buf.readInt();
	}
	
	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.i);
	}
	
	public static class Handler implements IMessageHandler<PacketKey, IMessage>
	{
		public Handler()
		{
		}
		
		@Override
		public IMessage onMessage(PacketKey message, MessageContext context)
		{
			EntityPlayer player = context.getServerHandler().playerEntity;
			return onMessage(message, player);
		}
		
		public static IMessage onMessage(PacketKey message, EntityPlayer player)
		{
			if(player == null)
			{
				return null;
			}
			ExtendedPlayer props = ExtendedPlayer.get(player);
			if(props == null)
			{
				props = new ExtendedPlayer(player);
			}
			Key k = Key.get(message.i);
			if(k == null)
			{
				return null;
			}
			switch(k)
			{
			case RIGHT_CLICK:
			{
				props.setRightClick(true);
				break;
			}
			case NOT_RIGHT_CLICK:
			{
				props.setRightClick(false);
				break;
			}
			case JUMP:
			{
				props.isJumpHeldDown = true;
				if(player.getEquipmentInSlot(1) != null && player.getEquipmentInSlot(1).getItem() instanceof ItemArmorDivingSuit)
				{
					if(player.isInWater() && player.isCollidedVertically)
					{
						player.setJumping(true);
						player.jump();
					}
				}
				
				if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemWeaponSkyHook)
				{
					ItemStack stack = player.getHeldItem();
					if(ItemWeaponSkyHook.CAN_HOOK.get(stack) > 0 && ItemWeaponSkyHook.HOOK_COORDS.has(stack) && props.isRightClickHeldDown && props.isRightClickHeldDownLast)
					{
						ItemWeaponSkyHook.JUMP.set(stack, true);
					}
					props.isJumpHeldDown = true;
				}
				break;
			}
			case NOT_JUMP:
			{
				props.isJumpHeldDown = false;
				break;
			}
			case NOT_ZOOM:
			{
				props.isZoomHeldDown = false;
				break;
			}
			case RELOAD:
			{
				if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemPlasmid)
				{
					eveHypo(message, player);
				}
				if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemWeaponRanged)
				{
					reloadWeapon(message, player);
				}
				break;
			}
			case SWITCH_AMMO_TYPE:
			{
				if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemWeaponRanged)
				{
					switchAmmoType(message, player);
				}
				break;
			}
			case ZOOM:
			{
				props.isZoomHeldDown = true;
				if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemWeaponRanged)
				{
					if(ItemWeaponRanged.heldUp(player.getHeldItem()))
					{
						player.setSprinting(false);
					}
				}
				break;
			}
			}
			return null;
		}
		
		private static void switchAmmoType(PacketKey message, EntityPlayer player)
		{
			ItemStack stack = player.getHeldItem();
			ItemWeaponRanged gun = (ItemWeaponRanged)player.getHeldItem().getItem();
			if(ItemWeaponRanged.AMMO.get(stack) > 0)
			{
				player.swingItem();
			}
			for(int i2 = 0; i2 <= gun.allAmmoTypes().length; ++i2)
			{
				gun.selectNextAmmoType(player.getHeldItem(), player);
				if(ItemWeaponRanged.AMMO.get(stack) + 1 <= gun.CAPACITY.get(stack))
				{
					if(gun.reload(player.getHeldItem(), player, 1))
					{
						player.swingItem();
						break;
					}
				}
				else
				{
					player.worldObj.playSoundAtEntity(player, "random.click", 0.3F, 1.6F);
					break;
				}
			}
		}
		
		private static void reloadWeapon(PacketKey message, EntityPlayer player)
		{
			ItemStack stack = player.getHeldItem();
			ItemWeaponRanged gun = (ItemWeaponRanged)stack.getItem();
			if(ItemWeaponRanged.AMMO.get(stack) + 1 <= gun.CAPACITY.get(stack))
			{
				if(!gun.reload(stack, player, 1))
				{
					player.swingItem();
				}
			}
			else
			{
				player.worldObj.playSoundAtEntity(player, "random.click", 0.3F, 1.6F);
			}
		}
		
		private static void eveHypo(PacketKey message, EntityPlayer player)
		{
			ItemStack itemstack = null;
			int slot = player.inventory.currentItem;
			for(int i2 = 0; i2 < player.inventory.getHotbarSize(); ++i2)
			{
				itemstack = ItemStack.copyItemStack(player.inventory.getStackInSlot(i2));
				if(itemstack != null)
				{
					if(itemstack.getItem() instanceof ItemEveHypo)
					{
						slot = i2;
						break;
					}
				}
			}
			if(slot == player.inventory.currentItem)
			{
				itemstack = null;
				for(int i2 = 0; i2 < player.inventory.getHotbarSize(); ++i2)
				{
					if(player.inventory.getStackInSlot(i2) == null)
					{
						slot = i2;
						break;
					}
				}
				if(slot != player.inventory.currentItem)
				{
					for(int i2 = 0; i2 < player.inventory.getSizeInventory(); ++i2)
					{
						itemstack = ItemStack.copyItemStack(player.inventory.getStackInSlot(i2));
						if(itemstack != null)
						{
							if(itemstack.getItem() instanceof ItemEveHypo)
							{
								player.inventory.setInventorySlotContents(i2, null);
								break;
							}
						}
					}
					if(itemstack != null && itemstack.getItem() instanceof ItemEveHypo && player.inventory.getStackInSlot(slot) == null)
					{
						player.inventory.setInventorySlotContents(slot, itemstack);
					}
				}
			}
			if(itemstack != null && itemstack.getItem() instanceof ItemEveHypo)
			{
				HandlerCommon.eveHypo.put(player, new HandlerCommon.EveHypoLog(player.inventory.currentItem, player.getHeldItem(), slot));
				player.inventory.currentItem = slot;
			}
		}
	}
}
