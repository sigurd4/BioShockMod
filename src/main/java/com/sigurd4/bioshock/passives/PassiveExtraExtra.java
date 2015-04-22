package com.sigurd4.bioshock.passives;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import com.sigurd4.bioshock.M;
import com.sigurd4.bioshock.extendedentity.ExtendedPlayer;
import com.sigurd4.bioshock.item.ItemAudioLog;
import com.sigurd4.bioshock.item.ItemMoney;

public class PassiveExtraExtra extends Passive
{
	public final boolean currency;
	
	public PassiveExtraExtra(String id, String name, Passive[] required, Passive[] incapatible, Type type, boolean currency)
	{
		super(id, name, required, incapatible, type);
		this.currency = currency;
	}
	
	@Override
	public void listenToAudiolog(ItemStack stack, EntityPlayer player, String sound)
	{
		ExtendedPlayer props = ExtendedPlayer.get(player);
		if(stack.getItem() instanceof ItemAudioLog && !props.audiologsObtained.contains(sound))
		{
			boolean b = false;
			try
			{
				if(!((ItemAudioLog)stack.getItem()).b)
				{
					b = true;
				}
			}
			catch(Throwable e)
			{
			}
			ItemMoney.addCash(b ? M.items.money.silver_eagles : M.items.money.dollars, player, 1);
		}
	}
}
