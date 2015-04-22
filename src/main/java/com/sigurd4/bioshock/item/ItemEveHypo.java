package com.sigurd4.bioshock.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.sigurd4.bioshock.event.HandlerCommon;

public class ItemEveHypo extends ItemConsumable
{
	public ItemEveHypo(EnumConsumableType type, String subtype, int health, int eve, int food, float saturation, int speed, int drunkness, String rightClickSound)
	{
		super(type, subtype, health, eve, food, saturation, speed, drunkness, false, rightClickSound);
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player)
	{
		stack = super.onItemUseFinish(stack, world, player);
		
		if(HandlerCommon.eveHypo.containsKey(player) && HandlerCommon.eveHypo.get(player) != null)
		{
			if(HandlerCommon.eveHypo.get(player).isValid(player))
			{
				ItemStack stack2 = player.inventory.getStackInSlot(HandlerCommon.eveHypo.get(player).plasmidSlot);
				if(stack2 != null && stack2.getItem() instanceof ItemPlasmid)
				{
					if(world.isRemote)
					{
						HandlerCommon.eveHypo.put(player, new HandlerCommon.EveHypoLog(HandlerCommon.eveHypo.get(player).plasmidSlot, null, HandlerCommon.eveHypo.get(player).hypoSlot));
					}
					int i = 5;
					if(stack2.getTagCompound() != null && ItemPlasmid.COOLDOWN.get(stack2) < 5)
					{
						ItemPlasmid.COOLDOWN.set(stack2, i);
					}
				}
			}
		}
		return stack;
	}
}
