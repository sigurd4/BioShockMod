package com.sigurd4.bioshock.passives;

import java.util.ArrayList;

import net.minecraft.entity.EntityLivingBase;

import com.sigurd4.bioshock.extendedentity.ExtendedLivingBase.LootEntry;
import com.sigurd4.bioshock.item.ItemAmmo;

public class PassiveScavengersVest extends Passive
{
	public final float chance;
	
	public PassiveScavengersVest(String id, String name, Passive[] required, Passive[] incapatible, Type type, float chance)
	{
		super(id, name, required, incapatible, type);
		this.chance = chance;
	}
	
	@Override
	public void loot(EntityLivingBase victim, ArrayList<LootEntry> drops, ArrayList<LootEntry> confirmedDrops)
	{
		if(victim.worldObj.rand.nextFloat() <= this.chance)
		{
			ArrayList<LootEntry> possibleAmmo = new ArrayList<LootEntry>();
			for(int i = 0; i < drops.size(); ++i)
			{
				if(drops.get(i) != null && drops.get(i).item instanceof ItemAmmo && drops.get(i).stacksize > 0)
				{
					possibleAmmo.add(drops.get(i));
				}
			}
			if(possibleAmmo.size() > 0)
			{
				LootEntry ammo = possibleAmmo.get(victim.worldObj.rand.nextInt(possibleAmmo.size()));
				confirmedDrops.add(ammo.getProcessedLoot(victim.worldObj.rand));
			}
		}
	}
}
