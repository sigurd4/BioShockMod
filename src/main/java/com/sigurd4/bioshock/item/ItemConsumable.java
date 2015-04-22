package com.sigurd4.bioshock.item;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

import com.sigurd4.bioshock.M;
import com.sigurd4.bioshock.Stuff;
import com.sigurd4.bioshock.extendedentity.ExtendedPlayer;
import com.sigurd4.bioshock.reference.RefMod;

public class ItemConsumable extends Item
{
	public static boolean playRightClickSound = true;
	public static int lastSlot = 0;
	
	public static enum EnumConsumableType
	{
		DRINK, ALCOHOL, FOOD, TOBACCO, MEDICAL, INJECTABLE
	}
	
	public final EnumConsumableType type;
	public final String subtype;
	public final int speed;
	public final ConsumableEffect effect;
	protected String rightClickSound;
	
	public static int nauseaTrigger = 300;
	
	/**
	 * Everything thats left of food, drink, medicinal and tobacco.
	 */
	public ItemConsumable(EnumConsumableType type, String subtype, int health, int eve, int food, float saturation, int speed, int drunkness, boolean sweet, String rightClickSound)
	{
		super();
		this.type = type;
		this.subtype = subtype;
		this.effect = new ConsumableEffect(health, eve, food, saturation, drunkness, sweet);
		this.speed = speed;
		this.rightClickSound = rightClickSound;
		this.setMaxStackSize(16);
		this.setCreativeTab(M.tabs.consumables);
	}
	
	/**
	 * How long it takes to use or consume an item
	 */
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		return this.speed;
	}
	
	/**
	 * returns the action that specifies what animation to play when the items
	 * is being used
	 */
	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		if(this.type == EnumConsumableType.ALCOHOL || this.type == EnumConsumableType.DRINK || this.subtype == "cureAll" || this.subtype == "pickles" || this.subtype == "finktonsTomatoSoup")
		{
			return EnumAction.DRINK;
		}
		if(this.type == EnumConsumableType.FOOD || this.subtype == "vitamins" || this.subtype == "aspirin")
		{
			return EnumAction.EAT;
		}
		if(this.type == EnumConsumableType.TOBACCO)
		{
			return EnumAction.BOW;
		}
		if(this.type == EnumConsumableType.INJECTABLE)
		{
			return EnumAction.BOW;
		}
		return EnumAction.BOW;
	}
	
	/**
	 * Called each tick as long the item is on a player inventory. Uses by maps
	 * to check if is on a player hand and
	 * update it's contents.
	 */
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected)
	{
		
	}
	
	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		boolean flag = false;
		ExtendedPlayer props = ExtendedPlayer.get(player);
		ConsumableEffect effect = props.passiveController.itemEaten(player, stack, this.effect.copy(), false);
		if(effect.health > 0)
		{
			if(player.getHealth() < player.getMaxHealth())
			{
				flag = true;
			}
		}
		else if(effect.eve > 0)
		{
			if(props.getEve() < props.getMaxEve())
			{
				flag = true;
			}
		}
		else if(effect.food > 0)
		{
			if(player.getFoodStats().getFoodLevel() < 20)
			{
				flag = true;
			}
		}
		else if(this.subtype == "adam")
		{
			flag = true;
		}
		if(flag || player.isSneaking())
		{
			player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
			if(world.isRemote)
			{
				if(playRightClickSound)
				{
					String soundName = RefMod.MODID + ":" + "item.consumables.right_click." + this.rightClickSound;
					if(this.rightClickSound != null)
					{
						player.playSound(soundName, 1, 1);
					}
					playRightClickSound = false;
				}
			}
		}
		return stack;
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player)
	{
		ExtendedPlayer props = ExtendedPlayer.get(player);
		if(!(this.subtype == "eve" && player.capabilities.isCreativeMode && stack.stackSize == 1))
		{
			--stack.stackSize;
		}
		ConsumableEffect effect = props.passiveController.itemEaten(player, stack, this.effect.copy(), true);
		if(effect.drunkness != 0)
		{
			props.setDrunkness(props.getDrunkness() + effect.drunkness);
			if(props.getDrunkness() >= ItemConsumable.nauseaTrigger)
			{
				player.addPotionEffect(new PotionEffect(Potion.confusion.id, props.getDrunkness(), 0, true, false));
			}
		}
		if(effect.health != 0)
		{
			if(effect.health >= 1000)
			{
				player.setHealth(player.getMaxHealth());
			}
			else
			{
				if(!player.capabilities.isCreativeMode)
				{
					if(effect.health <= -1000)
					{
						int oldHealth = (int)player.getHealth();
						player.setHealth(player.getHealth() / 2);
						if(oldHealth == player.getHealth())
						{
							player.setHealth(player.getHealth() - 1);
						}
					}
					else if(player.getHealth() + effect.health <= 0)
					{
						player.addPotionEffect(new PotionEffect(Potion.weakness.id, 240, 0, true, false));
						player.setHealth(1);
					}
					else
					{
						player.setHealth(player.getHealth() + effect.health);
					}
				}
			}
		}
		if(effect.eve != 0)
		{
			if(effect.eve >= 100)
			{
				props.setEve(props.getMaxEve());
			}
			else if(effect.eve <= -100)
			{
				int oldEve = props.getEve();
				props.setEve(props.getEve() / 2);
				if(oldEve == props.getEve())
				{
					props.setEve(props.getEve() - 1);
				}
			}
			else
			{
				props.setEve(props.getEve() + (int)((float)props.getMaxEve() * effect.eve * 0.01F));
			}
		}
		if(effect.food != 0 || effect.saturation != 0)
		{
			player.getFoodStats().addStats((int)Math.floor(effect.food), effect.saturation);
		}
		if(this.type == EnumConsumableType.TOBACCO)
		{
			for(int i = 0; i < 7; ++i)
			{
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, true, player.posX, player.posY, player.posZ, Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1));
			}
			world.playSoundAtEntity(player, "random.fizz", 0.03F, 0.08F / (itemRand.nextFloat() * 0.4F + 0.8F));
		}
		if(this.type == EnumConsumableType.FOOD || this.type == EnumConsumableType.ALCOHOL || this.type == EnumConsumableType.DRINK)
		{
			world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
		}
		if(this.subtype == "adam")
		{
			player.clearActivePotions();
			player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 600, 0));
			player.addPotionEffect(new PotionEffect(Potion.resistance.id, 300, 1, true, false));
			player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 300, 5, true, false));
			player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 2, 0, true, false));
			player.addPotionEffect(new PotionEffect(Potion.blindness.id, 10, 1, true, false));
			player.addPotionEffect(new PotionEffect(Potion.confusion.id, 120, 0, true, false));
			props.setDrunkness(0);
		}
		if(this.subtype == "milkBottle")
		{
			player.curePotionEffects(stack);
			player.clearActivePotions();
		}
		if(this.type == EnumConsumableType.INJECTABLE)
		{
			player.addPotionEffect(new PotionEffect(Potion.confusion.id, 80, 0, true, false));
			
			world.playSoundAtEntity(player, "game.player.hurt", 0.3F, 0.9F);
			if(!player.capabilities.isCreativeMode)
			{
				player.inventory.addItemStackToInventory(new ItemStack(M.items.crafting.empty_hypo));
			}
			
			return stack;
		}
		if(this.type == EnumConsumableType.MEDICAL)
		{
			if(!world.isRemote)
			{
				Iterator<PotionEffect> potions = player.getActivePotionEffects().iterator();
				
				while(potions.hasNext())
				{
					PotionEffect potion = potions.next();
					if(potion.isCurativeItem(stack) && Potion.potionTypes[potion.getPotionID()].isBadEffect())
					{
						potions.remove();
						player.removePotionEffect(potion.getPotionID());
					}
				}
			}
		}
		
		return stack;
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
	{
		list.add(EnumChatFormatting.GRAY + this.type.toString().toLowerCase());
		EnumChatFormatting c = null;
		ExtendedPlayer props = ExtendedPlayer.get(player);
		ConsumableEffect effect = props.passiveController.itemEaten(player, stack, this.effect.copy(), false);
		if(effect.health != 0)
		{
			if(effect.health > 0)
			{
				c = EnumChatFormatting.BLUE;
				if(effect.health >= 1000)
				{
					list.add(c + "+100% Health");
				}
				else
				{
					list.add(c + "+" + (int)Math.floor(effect.health) + " Health");
				}
			}
			if(effect.health < 0)
			{
				c = EnumChatFormatting.RED;
				if(effect.health <= -1000)
				{
					list.add(c + "-50% Health");
				}
				else
				{
					list.add(c + "" + (int)Math.floor(effect.health) + " Health");
				}
			}
		}
		if(effect.eve != 0)
		{
			if(effect.eve > 0)
			{
				c = EnumChatFormatting.BLUE;
				list.add(c + "+" + effect.eve + "% Eve");
			}
			if(effect.eve < 0)
			{
				c = EnumChatFormatting.RED;
				list.add(c + "" + effect.eve + "% Eve");
			}
		}
		if(effect.food != 0)
		{
			if(effect.food > 0)
			{
				c = EnumChatFormatting.BLUE;
				if(effect.food >= 1000)
				{
					list.add(c + "+100% Food");
				}
				else
				{
					list.add(c + "+" + (int)Math.floor(effect.food) + " Food");
				}
			}
			if(effect.food < 0)
			{
				c = EnumChatFormatting.RED;
				if(effect.food <= -1000)
				{
					list.add(c + "-100% Food");
				}
				else
				{
					list.add(c + "" + (int)Math.floor(effect.food) + " Food");
				}
			}
		}
		if(effect.drunkness != 0)
		{
			if(effect.drunkness > 0)
			{
				c = EnumChatFormatting.RED;
				if(effect.drunkness >= 1000)
				{
					list.add(c + "-100% Drunkness");
				}
				else
				{
					list.add(c + "+" + "Drunkness");
				}
			}
		}
		if(this.subtype == "adam")
		{
			list.add(EnumChatFormatting.BLUE + "Strenght (0:30)");
			list.add(EnumChatFormatting.BLUE + "Resistance (0:15)");
			list.add(EnumChatFormatting.BLUE + "Regeneration (0:15)");
		}
	}
	
	public static class ConsumableEffect
	{
		public float health;
		public int eve;
		public float food;
		public float saturation;
		public int drunkness;
		public boolean sweet;
		
		public ConsumableEffect(float health, int eve, float food, float saturation, int drunkness, boolean sweet)
		{
			this.health = health;
			this.eve = eve;
			this.food = food;
			this.saturation = saturation;
			this.drunkness = drunkness;
			this.sweet = sweet;
		}
		
		public ConsumableEffect copy()
		{
			return new ConsumableEffect(this.health, this.eve, this.food, this.saturation, this.drunkness, this.sweet);
		}
	}
}
