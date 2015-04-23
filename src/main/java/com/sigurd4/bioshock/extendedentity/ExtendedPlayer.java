package com.sigurd4.bioshock.extendedentity;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemSmeltedEvent;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.sigurd4.bioshock.entity.projectile.IEntityGunProjectile;
import com.sigurd4.bioshock.extendedentity.ExtendedLivingBase.LootEntry;
import com.sigurd4.bioshock.item.ItemConsumable.ConsumableEffect;
import com.sigurd4.bioshock.item.ItemPassiveGear;
import com.sigurd4.bioshock.item.ItemWeaponRanged;
import com.sigurd4.bioshock.passives.Passive;
import com.sigurd4.bioshock.passives.Passive.Passives;
import com.sigurd4.bioshock.passives.PassiveElementalBoost;
import com.sigurd4.bioshock.plasmids.Plasmid;
import com.sigurd4.bioshock.reference.RefMod;

public class ExtendedPlayer implements IExtendedEntityProperties, IEntityAdditionalSpawnData
{
	private final EntityPlayer player;
	
	public boolean isRightClickHeldDown = false;
	
	public void setRightClick(boolean b)
	{
		this.isRightClickHeldDownLast = this.isRightClickHeldDown;
		this.isRightClickHeldDown = b;
	}
	
	public boolean isRightClickHeldDownLast = false;
	public boolean isZoomHeldDown = false;
	public boolean isJumpHeldDown = false;
	
	protected int drunkness;
	public int eveMax;
	protected int eve;
	public int shieldsMax;
	protected float shields;
	protected int shieldsTimer;
	public int extraHealth;
	public final static int maxCombatTonics = 6;
	public final static int maxEngineeringTonics = maxCombatTonics;
	public final static int maxPhysicalTonics = maxCombatTonics;
	
	public final static String PLASMIDS_NAME = "plasmids";
	public final static String TONICS_NAME = "tonics";
	public final static String AUDIOLOGS_NAME = "audiologs";
	public final static String DRUNKNESS = "drunkness";
	public final static String EVE_MAX = "eveMax";
	public final static String EVE = "eve";
	public final static String SHIELDS_MAX = "shieldsMax";
	public final static String SHIELDS = "shields";
	public final static String SHIELDS_TIMER = "shieldsTimer";
	public final static String EXTRA_HEALTH = "shieldsMax";
	
	public ArrayList<Plasmid> plasmids = new ArrayList();
	public ArrayList<Passive> tonicsCombat = new ArrayList();
	public ArrayList<Passive> tonicsEngineering = new ArrayList();
	public ArrayList<Passive> tonicsPhysical = new ArrayList();
	public ArrayList<Passive> tonicsObtained = new ArrayList();
	public ArrayList<String> audiologsObtained = new ArrayList();
	
	public final PassiveController passiveController = new PassiveController();
	
	public ExtendedPlayer(EntityPlayer player)
	{
		this.player = player;
		
		this.eveMax = 200;
		this.shieldsMax = 0;
		this.extraHealth = 0;
		this.setEve(this.eveMax / 2);
		this.setDrunkness(0);
	}
	
	/**
	 * Returns ExtendedPlayer properties for player
	 * This method is for convenience only; it will make your code look nicer
	 */
	public static final ExtendedPlayer get(EntityPlayer player)
	{
		return (ExtendedPlayer)player.getExtendedProperties(RefMod.MODID);
	}
	
	/**
	 * Used to register these extended properties for the player during
	 * EntityConstructing event
	 * This method is for convenience only; it makes my code look nicer.
	 */
	public static final void register(EntityPlayer player)
	{
		player.registerExtendedProperties(RefMod.MODID, new ExtendedPlayer(player));
	}
	
	@Override
	public void loadNBTData(NBTTagCompound compound)
	{
		this.loadNBTData(compound, true);
	}
	
	public void loadNBTData(NBTTagCompound compound, boolean b)
	{
		NBTTagCompound properties = (NBTTagCompound)compound.getTag(RefMod.MODID);
		if(properties == null)
		{
			return;
		}
		
		if(b)
		{
			this.eve = properties.getInteger(EVE);
			this.drunkness = properties.getInteger(DRUNKNESS);
			this.shields = properties.getFloat(SHIELDS);
			this.shieldsTimer = properties.getInteger(SHIELDS_TIMER);
		}
		
		this.eveMax = properties.getInteger(EVE_MAX);
		this.shieldsMax = properties.getInteger(SHIELDS_MAX);
		this.extraHealth = properties.getInteger(EXTRA_HEALTH);
		
		this.loadNBTDataArrays(compound);
	}
	
	public void loadNBTDataArrays(NBTTagCompound compound)
	{
		NBTTagCompound properties = (NBTTagCompound)compound.getTag(RefMod.MODID);
		if(properties == null)
		{
			return;
		}
		NBTTagList plasmids = (NBTTagList)properties.getTag(PLASMIDS_NAME);
		NBTTagList tonics = (NBTTagList)properties.getTag(TONICS_NAME);
		NBTTagList audiologs = (NBTTagList)properties.getTag(AUDIOLOGS_NAME);
		
		if(plasmids != null)
		{
			this.plasmids.clear();
			for(int i = 0; i < plasmids.tagCount(); ++i)
			{
				Plasmid p = Plasmid.plasmids.get(plasmids.getStringTagAt(i));
				if(p != null)
				{
					this.givePlasmid(p);
				}
			}
		}
		
		if(tonics != null)
		{
			this.tonicsCombat.clear();
			this.tonicsEngineering.clear();
			this.tonicsPhysical.clear();
			for(int i = 0; i < tonics.tagCount(); ++i)
			{
				Passive p = Passives.get(tonics.getStringTagAt(i));
				if(p != null)
				{
					this.giveTonic(p);
				}
			}
		}
		
		if(audiologs != null)
		{
			for(int i = 0; i < audiologs.tagCount(); ++i)
			{
				String s = audiologs.getStringTagAt(i);
				if(s != null && this.audiologsObtained.contains(s))
				{
					this.audiologsObtained.add(s);
				}
			}
		}
	}
	
	@Override
	public void saveNBTData(NBTTagCompound compound)
	{
		this.saveNBTData(compound, true);
	}
	
	public void saveNBTData(NBTTagCompound compound, boolean b)
	{
		NBTTagCompound properties = new NBTTagCompound();
		compound.setTag(RefMod.MODID, properties);
		
		if(b)
		{
			properties.setInteger(EVE, this.eve);
			properties.setInteger(DRUNKNESS, this.drunkness);
			properties.setFloat(SHIELDS, this.shields);
			properties.setInteger(SHIELDS_TIMER, this.shieldsTimer);
		}
		
		properties.setInteger(EVE_MAX, this.eveMax);
		properties.setInteger(SHIELDS_MAX, this.shieldsMax);
		properties.setInteger(EXTRA_HEALTH, this.extraHealth);
		
		this.saveNBTDataArrays(compound);
	}
	
	public void saveNBTDataArrays(NBTTagCompound compound)
	{
		NBTTagCompound properties = new NBTTagCompound();
		NBTTagList plasmids = new NBTTagList();
		NBTTagList tonics = new NBTTagList();
		NBTTagList audiologs = new NBTTagList();
		
		for(int i = 0; i < plasmids.tagCount();)
		{
			plasmids.removeTag(i);
		}
		for(int i = 0; i < this.plasmids.size(); ++i)
		{
			plasmids.appendTag(new NBTTagString(this.plasmids.get(i).id));
		}
		
		for(int i = 0; i < this.tonicsCombat.size(); ++i)
		{
			tonics.appendTag(new NBTTagString(this.tonicsCombat.get(i).getID()));
		}
		for(int i = 0; i < this.tonicsEngineering.size(); ++i)
		{
			tonics.appendTag(new NBTTagString(this.tonicsEngineering.get(i).getID()));
		}
		for(int i = 0; i < this.tonicsPhysical.size(); ++i)
		{
			tonics.appendTag(new NBTTagString(this.tonicsPhysical.get(i).getID()));
		}
		
		for(int i = 0; i < this.audiologsObtained.size(); ++i)
		{
			audiologs.appendTag(new NBTTagString(this.audiologsObtained.get(i)));
		}
		
		properties.setTag(AUDIOLOGS_NAME, audiologs);
		properties.setTag(PLASMIDS_NAME, plasmids);
		properties.setTag(TONICS_NAME, tonics);
	}
	
	@SuppressWarnings("unused")
	public static boolean findTagInList(NBTTagList list, String s)
	{
		for(int i = 0; i < list.tagCount(); ++i)
		{
			if(list.getStringTagAt(i).equals(s))
			{
				;
			}
			{
				return true;
			}
		}
		return false;
	}
	
	public void update()
	{
		this.updateHealth();
		this.updateShields();
		this.updateShieldsKnockback();
		this.updateDrunkness();
	}
	
	public void updateDrunkness()
	{
		if(this.getDrunkness() > 0 && this.player.worldObj.rand.nextFloat() > 0.8)
		{
			this.setDrunkness(this.getDrunkness() - 1);
		}
	}
	
	public void updateHealth()
	{
		IAttributeInstance maxHealthAttribute = this.player.getEntityAttribute(SharedMonsterAttributes.maxHealth);
		if(maxHealthAttribute.getModifier(UUID.fromString("5891a1ce-c0fc-40b1-b3f2-6a3377a54a82")) != null)
		{
			maxHealthAttribute.removeModifier(maxHealthAttribute.getModifier(UUID.fromString("5891a1ce-c0fc-40b1-b3f2-6a3377a54a82")));
		}
		maxHealthAttribute.applyModifier(new AttributeModifier(UUID.fromString("5891a1ce-c0fc-40b1-b3f2-6a3377a54a82"), "bioshockExtraMaxHealth", -this.extraHealth, 0));
		if(maxHealthAttribute.getModifier(UUID.fromString("5891a1ce-c0fc-40b1-b3f2-6a3377a54a82")) != null)
		{
			maxHealthAttribute.removeModifier(maxHealthAttribute.getModifier(UUID.fromString("5891a1ce-c0fc-40b1-b3f2-6a3377a54a82")));
		}
		if(maxHealthAttribute.getModifier(UUID.fromString("5891a1ce-c0fc-40b1-b3f2-6a3377a54a81")) != null)
		{
			maxHealthAttribute.removeModifier(maxHealthAttribute.getModifier(UUID.fromString("5891a1ce-c0fc-40b1-b3f2-6a3377a54a81")));
		}
		maxHealthAttribute.applyModifier(new AttributeModifier(UUID.fromString("5891a1ce-c0fc-40b1-b3f2-6a3377a54a81"), "bioshockExtraMaxHealth", this.extraHealth, 0));
	}
	
	public void updateShields()
	{
		if(this.getMaxShields() > 0)
		{
			if(this.getShieldsTimer() > 0)
			{
				this.setShieldsTimer(this.getShieldsTimer() - 1);
				if(Math.sqrt(this.player.motionX * this.player.motionX + this.player.motionY * this.player.motionY + this.player.motionZ * this.player.motionZ) < 0.1)
				{
					this.setShieldsTimer(this.getShieldsTimer() - 2);
				}
			}
			else
			{
				this.setShields(this.getShields() + 0.1F);
			}
		}
	}
	
	public void updateShieldsKnockback()
	{
		IAttributeInstance attribute = this.player.getEntityAttribute(SharedMonsterAttributes.knockbackResistance);
		if(attribute.getModifier(UUID.fromString("5891a1ce-c0fc-40b1-b3f2-6a3377a54a84")) != null)
		{
			attribute.removeModifier(attribute.getModifier(UUID.fromString("5891a1ce-c0fc-40b1-b3f2-6a3377a54a84")));
		}
		attribute.applyModifier(new AttributeModifier(UUID.fromString("5891a1ce-c0fc-40b1-b3f2-6a3377a54a84"), "bioshockShieldKnockback", -1, 0));
		if(attribute.getModifier(UUID.fromString("5891a1ce-c0fc-40b1-b3f2-6a3377a54a84")) != null)
		{
			attribute.removeModifier(attribute.getModifier(UUID.fromString("5891a1ce-c0fc-40b1-b3f2-6a3377a54a84")));
		}
		if(attribute.getModifier(UUID.fromString("5891a1ce-c0fc-40b1-b3f2-6a3377a54a83")) != null)
		{
			attribute.removeModifier(attribute.getModifier(UUID.fromString("5891a1ce-c0fc-40b1-b3f2-6a3377a54a83")));
		}
	}
	
	public void setShieldsKnockbackResistance()
	{
		this.updateShieldsKnockback();
		IAttributeInstance attribute = this.player.getEntityAttribute(SharedMonsterAttributes.knockbackResistance);
		attribute.applyModifier(new AttributeModifier(UUID.fromString("5891a1ce-c0fc-40b1-b3f2-6a3377a54a83"), "bioshockShieldKnockback", 1, 0));
	}
	
	public boolean hasAnyPlasmids()
	{
		return !this.plasmids.isEmpty();
	}
	
	public final boolean consumeEve(int amount)
	{
		int eve = this.getEve();
		
		if(eve > 0)
		{
			this.setEve(eve - amount);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public final boolean consumeEve2(int amount)
	{
		if(this.getEve() - amount >= 0)
		{
			return this.consumeEve(amount);
		}
		else
		{
			return false;
		}
	}
	
	public final float consumeShields(float amount)
	{
		float shields = this.getShields();
		shields -= amount;
		if(shields < 0)
		{
			this.setShields(0);
			return -shields;
		}
		else
		{
			this.setShields(shields);
			return 0;
		}
	}
	
	public final int getMaxEve()
	{
		return this.eveMax;
	}
	
	public final void setMaxEve(int i)
	{
		this.eveMax = i;
	}
	
	public final int getMaxShields()
	{
		return this.shieldsMax;
	}
	
	public final void setMaxShields(int i)
	{
		this.shieldsMax = i;
	}
	
	public final int getEve()
	{
		return this.eve;
	}
	
	public final float getShields()
	{
		return this.shields;
	}
	
	public final int getDrunkness()
	{
		return this.drunkness;
	}
	
	public final int getShieldsTimer()
	{
		return this.shieldsTimer;
	}
	
	public final void setEve(int amount)
	{
		this.eve = amount > 0 ? amount : 0;
	}
	
	public final void setShields(float amount)
	{
		this.shields = amount > 0 ? amount : 0;
	}
	
	public final void setDrunkness(int amount)
	{
		this.drunkness = amount > 0 ? amount : 0;
	}
	
	public final void setShieldsTimer(int amount)
	{
		this.shieldsTimer = amount > 0 ? amount : 0;
	}
	
	public boolean hasPlasmid(Plasmid s)
	{
		return this.plasmids.contains(s);
	}
	
	public void givePlasmid(Plasmid s)
	{
		if(!this.hasPlasmid(s))
		{
			this.plasmids.add(s);
		}
	}
	
	public boolean hasTonic(Passive p)
	{
		ArrayList<Passive> a = this.getAllPassives();
		return a.contains(p);
	}
	
	public boolean removeTonic(Passive p)
	{
		ArrayList<Passive> a = null;
		switch(p.type)
		{
		case COMBAT:
			a = this.tonicsCombat;
			break;
		case ENGINEERING:
			a = this.tonicsEngineering;
			break;
		case PHYSICAL:
			a = this.tonicsPhysical;
			break;
		default:
			break;
		}
		boolean b = false;
		for(int i = 0; i < a.size(); ++i)
		{
			if(a.get(i) == p)
			{
				a.remove(i);
				--i;
				b = true;
			}
		}
		return b;
	}
	
	public boolean hasHadTonic(Passive p)
	{
		return this.tonicsObtained.contains(p) || this.hasTonic(p);
	}
	
	public void giveTonic(Passive p)
	{
		if(!this.tonicsObtained.contains(p))
		{
			this.tonicsObtained.add(p);
		}
		if(this.hasTonic(p))
		{
			return;
		}
		switch(p.type)
		{
		case COMBAT:
			this.tonicsCombat.add(p);
			break;
		case ENGINEERING:
			this.tonicsEngineering.add(p);
			break;
		case PHYSICAL:
			this.tonicsPhysical.add(p);
			break;
		default:
			break;
		}
		this.capTonics();
	}
	
	public void capTonics()
	{
		while(this.tonicsCombat.size() > maxCombatTonics)
		{
			this.tonicsCombat.remove(0);
		}
		while(this.tonicsEngineering.size() > maxEngineeringTonics)
		{
			this.tonicsEngineering.remove(0);
		}
		while(this.tonicsPhysical.size() > maxPhysicalTonics)
		{
			this.tonicsPhysical.remove(0);
		}
		ArrayList<Passive> tonics = this.getAllTonics();
		HashMap<Passive, Boolean> m = new HashMap<Passive, Boolean>();
		for(int i = 0; i < tonics.size(); ++i)
		{
			if(m.get(tonics.get(i)) != null)
			{
				this.removeTonic(tonics.get(i));
			}
			else
			{
				m.put(tonics.get(i), true);
			}
		}
	}
	
	public ArrayList<Passive> getAllPassives()
	{
		ArrayList<Passive> a = this.getAllTonics();
		a.addAll(this.getAllGear());
		return a;
	}
	
	public ArrayList<Passive> getAllGear()
	{
		ArrayList<Passive> a = new ArrayList<Passive>();
		ItemStack[] armor = this.player.inventory.armorInventory;
		for(int i = 0; i < armor.length; ++i)
		{
			if(armor[i] != null && armor[i].getItem() instanceof ItemPassiveGear)
			{
				a.add(((ItemPassiveGear)armor[i].getItem()).p);
			}
		}
		return a;
	}
	
	public ArrayList<Passive> getAllTonics()
	{
		ArrayList<Passive> a = new ArrayList<Passive>();
		a.addAll(this.tonicsCombat);
		a.addAll(this.tonicsEngineering);
		a.addAll(this.tonicsPhysical);
		return a;
	}
	
	public void reloadAllWeapons()
	{
		for(int i = 0; i < this.player.inventory.getSizeInventory(); ++i)
		{
			ItemStack stack = this.player.inventory.getStackInSlot(i);
			if(stack != null && stack.getItem() instanceof ItemWeaponRanged)
			{
				int i2 = 0;
				while(ItemWeaponRanged.AMMO.get(stack) + 1 <= ((ItemWeaponRanged)stack.getItem()).CAPACITY.get(stack))
				{
					ItemWeaponRanged.FIRE_RATE_TIMER.set(stack, ((ItemWeaponRanged)stack.getItem()).FIRE_RATE.get(stack));
					if(!((ItemWeaponRanged)stack.getItem()).reload(stack, this.player, 1) || i2 > 100)
					{
						break;
					}
					else
					{
						++i2;
					}
				}
			}
		}
	}
	
	@Override
	public void init(Entity entity, World world)
	{
	}
	
	public class PassiveController
	{
		public void hurt(LivingHurtEvent event)
		{
			if(event.entity != ExtendedPlayer.this.player)
			{
				return;
			}
			ArrayList<Passive> p = ExtendedPlayer.this.getAllPassives();
			for(int i = 0; i < p.size(); ++i)
			{
				p.get(i).LivingHurtEvent(event);
			}
		}
		
		public void attack(LivingHurtEvent event)
		{
			if(event.source.getEntity() != ExtendedPlayer.this.player)
			{
				return;
			}
			ArrayList<Passive> p = ExtendedPlayer.this.getAllPassives();
			for(int i = 0; i < p.size(); ++i)
			{
				p.get(i).LivingAttackEvent(event);
				if(event.entityLiving.getHealth() - event.ammount <= 0)
				{
					p.get(i).killEntity(event);
				}
			}
		}
		
		public void update(LivingUpdateEvent event)
		{
			if(event.entity != ExtendedPlayer.this.player)
			{
				return;
			}
			ArrayList<Passive> p = ExtendedPlayer.this.getAllPassives();
			for(int i = 0; i < p.size(); ++i)
			{
				p.get(i).LivingUpdateEvent(event);
			}
		}
		
		public void itemCrafted(ItemCraftedEvent event)
		{
			if(event.player != ExtendedPlayer.this.player)
			{
				return;
			}
			ArrayList<Passive> p = ExtendedPlayer.this.getAllPassives();
			for(int i = 0; i < p.size(); ++i)
			{
				p.get(i).ItemCraftedEvent(event);
			}
		}
		
		public void itemCrafted(ItemSmeltedEvent event)
		{
			if(event.player != ExtendedPlayer.this.player)
			{
				return;
			}
			ArrayList<Passive> p = ExtendedPlayer.this.getAllPassives();
			for(int i = 0; i < p.size(); ++i)
			{
				p.get(i).ItemSmeltedEvent(event);
			}
		}
		
		public ConsumableEffect itemEaten(EntityPlayer player_, ItemStack stack, ConsumableEffect results, boolean eaten)
		{
			if(player_ != ExtendedPlayer.this.player)
			{
				return results;
			}
			ArrayList<Passive> p = ExtendedPlayer.this.getAllPassives();
			for(int i = 0; i < p.size(); ++i)
			{
				results = p.get(i).consumableUsed(player_, stack, results, eaten);
			}
			return results;
		}
		
		public int plasmidUse(EntityPlayer player_, ItemStack stack, int eve_)
		{
			float eve = eve_;
			if(player_ != ExtendedPlayer.this.player)
			{
				return (int)Math.floor(eve);
			}
			ArrayList<Passive> p = ExtendedPlayer.this.getAllPassives();
			for(int i = 0; i < p.size(); ++i)
			{
				eve = p.get(i).plasmidUse(stack, player_, eve);
			}
			return (int)Math.floor(eve);
		}
		
		public int gunUse(EntityPlayer player_, ItemStack stack, int usage_)
		{
			float usage = usage_;
			if(player_ != ExtendedPlayer.this.player)
			{
				return (int)Math.floor(usage);
			}
			ArrayList<Passive> p = ExtendedPlayer.this.getAllPassives();
			for(int i = 0; i < p.size(); ++i)
			{
				usage = p.get(i).fireWeaponAmmoModifier(stack, player_, usage);
			}
			return (int)Math.floor(usage);
		}
		
		public void killedDrops(LivingDropsEvent event)
		{
			if(event.entityLiving.getLastAttacker() != ExtendedPlayer.this.player)
			{
				return;
			}
			ArrayList<Passive> p = ExtendedPlayer.this.getAllPassives();
			for(int i = 0; i < p.size(); ++i)
			{
				p.get(i).LivingDropsEvent(event);
			}
		}
		
		public void fireDamage(LivingHurtEvent event)
		{
			if(event.entityLiving.func_94060_bK() != ExtendedPlayer.this.player)
			{
				return;
			}
			ArrayList<Passive> p = ExtendedPlayer.this.getAllPassives();
			for(int i = 0; i < p.size(); ++i)
			{
				if(p.get(i) instanceof PassiveElementalBoost && ((PassiveElementalBoost)p.get(i)).isCorrectDamageType(DamageSource.inFire))
				{
					event.ammount *= ((PassiveElementalBoost)p.get(i)).damageMultiplier;
				}
			}
		}
		
		@SideOnly(Side.CLIENT)
		public void playSound(PlaySoundEvent event)
		{
			EntityPlayer player2 = Minecraft.getMinecraft().theWorld.getClosestPlayer(event.sound.getXPosF(), event.sound.getYPosF(), event.sound.getZPosF(), 2);
			if(player2 != ExtendedPlayer.this.player)
			{
				return;
			}
			ArrayList<Passive> p = ExtendedPlayer.this.getAllPassives();
			for(int i = 0; i < p.size(); ++i)
			{
				p.get(i).PlaySoundEvent(event);
			}
		}
		
		public void listenToAudiolog(ItemStack stack, String sound)
		{
			ArrayList<Passive> p = ExtendedPlayer.this.getAllPassives();
			for(int i = 0; i < p.size(); ++i)
			{
				p.get(i).listenToAudiolog(stack, ExtendedPlayer.this.player, sound);
			}
		}
		
		public void shieldsBreak(LivingHurtEvent event)
		{
			if(event.entity != ExtendedPlayer.this.player)
			{
				return;
			}
			ArrayList<Passive> p = ExtendedPlayer.this.getAllPassives();
			for(int i = 0; i < p.size(); ++i)
			{
				p.get(i).shieldsBreak(event);
			}
		}
		
		public void landFromSkyline(EntityPlayer player_)
		{
			if(player_ != ExtendedPlayer.this.player)
			{
				return;
			}
			ArrayList<Passive> p = ExtendedPlayer.this.getAllPassives();
			for(int i = 0; i < p.size(); ++i)
			{
				p.get(i).landFromSkyline(player_);
			}
		}
		
		public void hookOnSkyline(EntityPlayer player_)
		{
			if(player_ != ExtendedPlayer.this.player)
			{
				return;
			}
			ArrayList<Passive> p = ExtendedPlayer.this.getAllPassives();
			for(int i = 0; i < p.size(); ++i)
			{
				p.get(i).hookOnSkyline(player_);
			}
		}
		
		public void loot(EntityLivingBase victim, ArrayList<LootEntry> drops, ArrayList<LootEntry> confirmedDrops)
		{
			ArrayList<Passive> p = ExtendedPlayer.this.getAllPassives();
			for(int i = 0; i < p.size(); ++i)
			{
				p.get(i).loot(victim, drops, confirmedDrops);
			}
		}
		
		public <T extends EntityThrowable & IEntityGunProjectile> void bulletFire(ItemStack stack, T bullet)
		{
			ArrayList<Passive> p = ExtendedPlayer.this.getAllPassives();
			for(int i = 0; i < p.size(); ++i)
			{
				p.get(i).bulletFire(ExtendedPlayer.this.player, stack, bullet);
			}
		}
	}
	
	/**
	 * Called by the server when constructing the spawn packet.
	 * Data should be added to the provided stream.
	 *
	 * @param buffer
	 *            The packet data stream
	 */
	@Override
	public void writeSpawnData(ByteBuf buf)
	{
		buf.writeBoolean(this.isRightClickHeldDown);
		buf.writeBoolean(this.isRightClickHeldDownLast);
		buf.writeBoolean(this.isZoomHeldDown);
		buf.writeBoolean(this.isJumpHeldDown);
		
		buf.writeInt(this.drunkness);
		buf.writeInt(this.eveMax);
		buf.writeInt(this.eve);
		buf.writeInt(this.shieldsMax);
		buf.writeFloat(this.shields);
		buf.writeInt(this.shieldsTimer);
		buf.writeInt(this.extraHealth);
		
		NBTTagCompound compound = new NBTTagCompound();
		this.saveNBTDataArrays(compound);
		ByteBufUtils.writeTag(buf, compound);
	}
	
	/**
	 * Called by the client when it receives a Entity spawn packet.
	 * Data should be read out of the stream in the same way as it was written.
	 *
	 * @param data
	 *            The packet data stream
	 */
	@Override
	public void readSpawnData(ByteBuf buf)
	{
		this.isRightClickHeldDown = buf.readBoolean();
		this.isRightClickHeldDownLast = buf.readBoolean();
		this.isZoomHeldDown = buf.readBoolean();
		this.isJumpHeldDown = buf.readBoolean();
		
		this.drunkness = buf.readInt();
		this.eveMax = buf.readInt();
		this.eve = buf.readInt();
		this.shieldsMax = buf.readInt();
		this.shields = buf.readFloat();
		this.shieldsTimer = buf.readInt();
		this.extraHealth = buf.readInt();
		
		NBTTagCompound compound = ByteBufUtils.readTag(buf);
		this.loadNBTDataArrays(compound);
	}
}