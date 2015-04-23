package com.sigurd4.bioshock.passives;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemSmeltedEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.sigurd4.bioshock.entity.projectile.IEntityGunProjectile;
import com.sigurd4.bioshock.extendedentity.ExtendedLivingBase.LootEntry;
import com.sigurd4.bioshock.extendedentity.ExtendedPlayer;
import com.sigurd4.bioshock.item.ItemConsumable.ConsumableEffect;

public class Passive
{
	public final String name;
	public final Passive[] required;
	public final Passive[] incapatible;
	
	public final Type type;
	
	public static enum Type
	{
		COMBAT, ENGINEERING, PHYSICAL, GEAR
	};
	
	public Passive(String id, String name, Passive[] required, Passive[] incapatible, Type type)
	{
		Passives.passives.put(id, this);
		this.name = name;
		this.required = required;
		this.incapatible = incapatible;
		this.type = type;
	}
	
	public final String getID()
	{
		return Passives.getID(this);
	}
	
	public final String getName()
	{
		return this.name;
	}
	
	public final boolean isValid(Passive[] eps)
	{
		Passive[] rps = this.required;
		Passive[] ips = this.incapatible;
		
		ArrayList<Passive> a = new ArrayList<Passive>();
		for(int i = 0; i < eps.length; ++i)
		{
			Passive ep = eps[i];
			for(int i2 = 0; i2 < a.size(); ++i)
			{
				Passive rp = rps[i];
				if(ep == rp)
				{
					a.add(rp);
					break;
				}
			}
		}
		if(a.size() < eps.length)
		{
			return false;
		}
		
		for(int i = 0; i < eps.length; ++i)
		{
			Passive ep = eps[i];
			for(int i2 = 0; i2 < ips.length; ++i)
			{
				Passive ip = ips[i];
				if(ep == ip)
				{
					return false;
				}
			}
		}
		return true;
	}
	
	public void LivingHurtEvent(LivingHurtEvent event)
	{
	}
	
	public void LivingAttackEvent(LivingHurtEvent event)
	{
	}
	
	public void killEntity(LivingHurtEvent event)
	{
	}
	
	public void LivingUpdateEvent(LivingEvent.LivingUpdateEvent event)
	{
	}
	
	public void ItemCraftedEvent(ItemCraftedEvent event)
	{
	}
	
	public void ItemSmeltedEvent(ItemSmeltedEvent event)
	{
	}
	
	public ConsumableEffect consumableUsed(EntityPlayer player, ItemStack stack, ConsumableEffect results, boolean eaten)
	{
		return results;
	}
	
	public void LivingDropsEvent(LivingDropsEvent event)
	{
	}
	
	public float plasmidUse(ItemStack stack, EntityPlayer player, float eve)
	{
		return eve;
	}
	
	public float vendorPrice(EntityPlayer player, int x, int y, int z, float price)
	{
		return price;
	}
	
	public void listenToAudiolog(ItemStack stack, EntityPlayer player, String sound)
	{
	}
	
	/*public float securityCamTickAlarm(EntitySecurityCamera cam, EntityPlayer target, float amount)
	{
		return amount;
	}*/
	
	/*public float securityCamTickReaction(EntitySecurityCamera cam, EntityPlayer target, float amount)
	{
		return amount;
	}*/
	
	/*public float hackEvent(EntitySecurity hackable, EntityPlayer hacker, float time)
	{
		return time;
	}*/
	
	public void landFromSkyline(EntityPlayer player)
	{
	}
	
	public void hookOnSkyline(EntityPlayer player)
	{
	}
	
	public void shieldsBreak(LivingHurtEvent event)
	{
	}
	
	public void loot(EntityLivingBase victim, ArrayList<LootEntry> drops, ArrayList<LootEntry> confirmedDrops)
	{
	}
	
	public float fireWeaponAmmoModifier(ItemStack stack, EntityPlayer player, float amount)
	{
		return amount;
	}
	
	public <T extends EntityThrowable & IEntityGunProjectile> void bulletFire(EntityPlayer player, ItemStack stack, T bullet)
	{
	}
	
	public void loadNBTData(NBTTagCompound c)
	{
	}
	
	public void saveNBTData(NBTTagCompound c)
	{
	}
	
	@SideOnly(Side.CLIENT)
	public void PlaySoundEvent(PlaySoundEvent event)
	{
	}
	
	public final boolean playerHas(EntityPlayer player)
	{
		return Passives.playerHas(player, this);
	}
	
	/** Full passive upgrade list: **/
	public static final class Passives
	{
		private static final HashMap<String, Passive> passives = new HashMap<String, Passive>();
		
		public static boolean hasID(Passive p)
		{
			return getID(p) != null;
		}
		
		public static boolean hasComponent(String id)
		{
			return get(id) != null;
		}
		
		public static String getID(Passive p)
		{
			Iterator<String> keys = passives.keySet().iterator();
			while(keys.hasNext())
			{
				String id = keys.next();
				if(passives.get(id) == p)
				{
					return id;
				}
			}
			
			return null;
		}
		
		public static Passive get(String id)
		{
			return passives.get(id);
		}
		
		public static Passive[] get(String[] ids)
		{
			ArrayList<Passive> ps = new ArrayList<Passive>();
			
			for(int i = 0; i < ids.length; ++i)
			{
				Passive p = get(ids[i]);
				if(p != null)
				{
					ps.add(p);
				}
			}
			
			return (Passive[])ps.toArray();
		}
		
		public static boolean playerHas(EntityPlayer player, Passive p)
		{
			ExtendedPlayer props = ExtendedPlayer.get(player);
			return props.hasTonic(p);
		}
	}
}
