package com.sigurd4.bioshock.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.sigurd4.bioshock.M;
import com.sigurd4.bioshock.Stuff;
import com.sigurd4.bioshock.extendedentity.ExtendedPlayer;
import com.sigurd4.bioshock.item.ItemWeaponRanged.IEnumAmmoType;
import com.sigurd4.bioshock.item.ItemWeaponRanged.IEnumUpgrade;
import com.sigurd4.bioshock.itemtags.ItemTagBoolean;
import com.sigurd4.bioshock.itemtags.ItemTagDouble;
import com.sigurd4.bioshock.itemtags.ItemTagEnum;
import com.sigurd4.bioshock.itemtags.ItemTagEnumMapBoolean;
import com.sigurd4.bioshock.itemtags.ItemTagInteger;
import com.sigurd4.bioshock.reference.RefMod;

public abstract class ItemWeaponRanged<EnumAmmoType extends Enum & IEnumAmmoType, EnumUpgrades extends Enum & IEnumUpgrade> extends Item implements IItemWeapon, IItemTextureVariants
{
	protected static interface IEnumAmmoType
	{
		public ItemAmmo getItem();
		
		@SideOnly(Side.CLIENT)
		public void addInformation(ItemStack stack, EntityPlayer player, ArrayList<String> list, boolean bool);
	}
	
	protected static interface IEnumUpgrade
	{
		@SideOnly(Side.CLIENT)
		public void addInformation(ItemStack stack, EntityPlayer player, ArrayList<String> list, boolean bool);
		
		public void setupNBT(ItemStack stack);
	}
	
	@Deprecated
	private int fireRate;
	
	public int fireRate(ItemStack stack)
	{
		return this.fireRate;
	}
	
	@Deprecated
	private int capacity;
	
	public int capacity(ItemStack stack)
	{
		return this.capacity;
	}
	
	@Deprecated
	private float spread;
	
	public float spread(ItemStack stack)
	{
		return this.spread;
	}
	
	@Deprecated
	private double recoil;
	
	public double recoil(ItemStack stack)
	{
		return this.recoil;
	}
	
	@Deprecated
	private int reloadTime;
	
	public int reloadTime(ItemStack stack)
	{
		return this.reloadTime;
	}
	
	@Deprecated
	private boolean isRapidFire;
	
	public boolean isRapidFire(ItemStack stack)
	{
		return this.isRapidFire;
	}
	
	@Deprecated
	private float zoom;
	
	public float zoom(ItemStack stack)
	{
		return this.zoom;
	}
	
	protected boolean differentTextureIfEmpty;
	
	public final EnumAmmoType[] allAmmoTypes;
	public final EnumUpgrades[] allUpgrades;
	
	public int reloads = 10;
	
	//nbt
	public final ItemTagEnumMapBoolean<EnumUpgrades> UPGRADES = new ItemTagEnumMapBoolean<EnumUpgrades>("Upgrades", false, false);
	public final ItemTagInteger CAPACITY = new ItemTagInteger("Capacity", Integer.MAX_VALUE, 1, Integer.MAX_VALUE, false)
	{
		@Override
		public Integer get(NBTTagCompound compound, boolean createNew)
		{
			if(!this.has(compound))
			{
				return ItemWeaponRanged.this.capacity(ItemWeaponRanged.this.stack(compound));
			}
			return super.get(compound, createNew);
		}
		
		@Override
		protected Integer isValid(NBTTagCompound compound, Integer original)
		{
			original = super.isValid(compound, original);
			int max = ItemWeaponRanged.this.capacity(ItemWeaponRanged.this.stack(compound));
			if(original > max)
			{
				return original;
			}
			return original;
		}
	};
	public final static ItemTagInteger AMMO = new ItemTagInteger("Ammo", 0, 0, Integer.MAX_VALUE, true);
	public final ItemTagEnum<EnumAmmoType> AMMO_TYPE = new ItemTagEnum<EnumAmmoType>("AmmoType", this.allAmmoTypes[0], false);
	public final ItemTagInteger FIRE_RATE = new ItemTagInteger("FireRate", Integer.MAX_VALUE, 1, Integer.MAX_VALUE, false)
	{
		@Override
		public Integer get(NBTTagCompound compound, boolean createNew)
		{
			if(!this.has(compound))
			{
				return ItemWeaponRanged.this.fireRate(ItemWeaponRanged.this.stack(compound));
			}
			return super.get(compound, createNew);
		}
		
		@Override
		protected Integer isValid(NBTTagCompound compound, Integer original)
		{
			original = super.isValid(compound, original);
			int max = ItemWeaponRanged.this.fireRate(ItemWeaponRanged.this.stack(compound));
			if(!this.has(compound))
			{
				return max;
			}
			if(original > max)
			{
				return original;
			}
			return original;
		}
	};
	public final static ItemTagInteger FIRE_RATE_TIMER = new ItemTagInteger("FireRateTimer", 0, 0, Integer.MAX_VALUE, true);
	public final ItemTagInteger RELOAD_TIME = new ItemTagInteger("ReloadTime", Integer.MAX_VALUE, 1, Integer.MAX_VALUE, false)
	{
		@Override
		public Integer get(NBTTagCompound compound, boolean createNew)
		{
			if(!this.has(compound))
			{
				return ItemWeaponRanged.this.reloadTime(ItemWeaponRanged.this.stack(compound));
			}
			return super.get(compound, createNew);
		}
		
		@Override
		protected Integer isValid(NBTTagCompound compound, Integer original)
		{
			original = super.isValid(compound, original);
			int max = ItemWeaponRanged.this.reloadTime(ItemWeaponRanged.this.stack(compound));
			if(!this.has(compound))
			{
				return max;
			}
			if(original > max)
			{
				return original;
			}
			return original;
		}
	};
	public final static ItemTagInteger INSTABILITY = new ItemTagInteger("Instability", 0, 0, 40, false);
	public final static ItemTagBoolean IS_PICKED_UP = new ItemTagBoolean("IsPickedUp", false, false);
	public final ItemTagDouble RECOIL = new ItemTagDouble("AppliedRecoil", 0D, 0D, Double.MAX_VALUE, false)
	{
		@Override
		public Double get(NBTTagCompound compound, boolean createNew)
		{
			if(!this.has(compound))
			{
				return ItemWeaponRanged.this.recoil(ItemWeaponRanged.this.stack(compound));
			}
			return super.get(compound, createNew);
		}
		
		@Override
		protected Double isValid(NBTTagCompound compound, Double original)
		{
			original = super.isValid(compound, original);
			double max = ItemWeaponRanged.this.recoil(ItemWeaponRanged.this.stack(compound));
			if(!this.has(compound))
			{
				return max;
			}
			if(original > max)
			{
				return original;
			}
			return original;
		}
	};
	
	protected ItemStack stack(NBTTagCompound compound)
	{
		ItemStack stack = new ItemStack(this, 1, 0);
		stack.setTagCompound(compound);
		return stack;
	}
	
	/**
	 * Base class for ranged weapons and other similar things that use
	 * ammunition
	 * 
	 * @param int Ammunition maximum capacity
	 * @param int Fire rate
	 * @param float Spread
	 * @param float Recoil
	 * @param String
	 *            name for first upgrade
	 * @param String
	 *            name for second upgrade
	 * @param String
	 *            name for first type of ammunition
	 * @param String
	 *            name for second type of ammunition
	 * @param String
	 *            name for third type of ammunition
	 */
	public ItemWeaponRanged(int capacity, int fireRate, float spread, float recoil, int reloadTime, float zoom, EnumAmmoType[] allAmmoTypes, EnumUpgrades[] allUpgrades, boolean differentTextureIfEmpty)
	{
		super();
		this.isRapidFire = false;
		this.capacity = capacity;
		this.fireRate = fireRate;
		this.spread = spread;
		this.recoil = recoil;
		this.reloadTime = reloadTime;
		this.zoom = zoom;
		this.allAmmoTypes = allAmmoTypes;
		this.allUpgrades = allUpgrades;
		this.setMaxStackSize(1);
		this.setCreativeTab(M.tabs.weapons);
		this.setUnlocalizedName("weapon");
		this.setMaxDamage(100);
		this.setNoRepair();
		this.setHasSubtypes(true);
		this.differentTextureIfEmpty = differentTextureIfEmpty;
	}
	
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
		return 1 - (double)AMMO.get(stack) / (double)this.CAPACITY.get(stack);
	}
	
	@Override
	public boolean isDamaged(ItemStack stack)
	{
		return stack.getTagCompound() != null && IS_PICKED_UP.get(stack);
	}
	
	private void forCombination(Item item, ItemStack[] stacks, int i2, int stack)
	{
		for(int i = 0; i < 1; ++i)
		{
			this.UPGRADES.set(stacks[stack + i], this.allUpgrades[i2], i > 0);
			if(i2 < this.allUpgrades.length)
			{
				this.forCombination(item, stacks, i2 + 1, stack + i);
			}
		}
	}
	
	/**
	 * returns a list of items with the same ID, but different meta (eg: dye
	 * returns 16 items)
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTab, List list)
	{
		this.getSubItems2(item, creativeTab, list);
		
		long time = 34325 + M.proxy.world(0).getTotalWorldTime();
		int ammoType = (int)(time % this.allAmmoTypes.length);
		for(int i = 0; i < list.size(); ++i)
		{
			if(list.get(i) instanceof ItemStack)
			{
				this.AMMO_TYPE.set((ItemStack)list.get(i), this.allAmmoTypes[ammoType]);
			}
		}
	}
	
	public void getSubItems2(Item item, CreativeTabs creativeTab, List list)
	{
		ItemStack[] stacks = new ItemStack[(int)Math.pow(this.allUpgrades.length, this.allUpgrades.length)];
		for(int i = 0; i < stacks.length; ++i)
		{
			stacks[i] = new ItemStack(item, 1, 0);
		}
		this.forCombination(item, stacks, 0, 0);
		for(int i = 0; i < stacks.length; ++i)
		{
			for(EnumUpgrades upgrade : this.getUpgrades(stacks[i]))
			{
				upgrade.setupNBT(stacks[i]);
			}
			AMMO.set(stacks[i], this.CAPACITY.get(stacks[i]));
			list.add(stacks[i]);
		}
	}
	
	/**
	 * allows items to add custom lines of information to the mouseover
	 * description
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
	{
		list.add("Ammo: ");
		list.add(AMMO.get(stack) + "/" + this.CAPACITY.get(stack));
		
		if(true)
		{
			ArrayList<String> list2 = new ArrayList<String>();
			this.AMMO_TYPE.get(stack).addInformation(stack, player, list2, bool);
			list.add("(" + this.AMMO_TYPE.get(stack).toString() + ")" + (list2.size() > 0 ? ":" : ""));
			for(String string : list2)
			{
				list.add("   " + string);
			}
		}
		
		list.add("Fire Rate: ");
		list.add(this.FIRE_RATE.get(stack) / 20F);
		if(((ItemWeaponRanged)stack.getItem()).isRapidFire)
		{
			list.add("(Rapid Fire)");
		}
		
		if(this.getUpgrades(stack).size() > 0)
		{
			list.add("Upgrades: ");
			
			for(EnumUpgrades upgrade : this.getUpgrades(stack))
			{
				String name = upgrade.toString().toLowerCase();
				name = Stuff.Strings.UnderscoresToCamelSpaces(name);
				char[] cs = name.toCharArray();
				cs[0] = Character.toLowerCase(cs[0]);
				name = String.copyValueOf(cs);
				ArrayList<String> list2 = new ArrayList<String>();
				upgrade.addInformation(stack, player, list2, bool);
				list.add("-" + name + " Upgrade" + (list2.size() > 0 ? ":" : ""));
				for(String string : list2)
				{
					list.add("   " + string);
				}
			}
		}
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
	{
		IS_PICKED_UP.set(stack, true);
		
		if(FIRE_RATE_TIMER.get(stack) > 0 && ((EntityLivingBase)entity).getHeldItem() == stack)
		{
			if(AMMO.get(stack) > 0)
			{
				FIRE_RATE_TIMER.add(stack, -1);
			}
			else
			{
				FIRE_RATE_TIMER.set(stack, 0);
			}
		}
		
		if(entity instanceof EntityPlayer)
		{
			ExtendedPlayer props = ExtendedPlayer.get((EntityPlayer)entity);
			if(props.isZoomHeldDown && ((EntityPlayer)entity).onGround)
			{
				entity.setSprinting(false);
				entity.motionX *= 0.01F;
				entity.motionZ *= 0.01F;
			}
			
			if(this.isRapidFire)
			{
				if(props.isRightClickHeldDown && props.isRightClickHeldDownLast)
				{
					if(INSTABILITY.get(stack) < 40)
					{
						INSTABILITY.add(stack, +itemRand.nextInt(2 + 1));
					}
				}
				else
				{
					if(INSTABILITY.get(stack) > 0)
					{
						INSTABILITY.add(stack, -10 + itemRand.nextInt(4 + 1));
					}
				}
			}
			if(this.RECOIL.get(stack) > 0.01)
			{
				this.applyRecoilPerTick(stack, (EntityPlayer)entity);
			}
		}
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		ExtendedPlayer props = ExtendedPlayer.get(player);
		if(!props.isRightClickHeldDownLast)
		{
			if(!((ItemWeaponRanged)stack.getItem()).isRapidFire)
			{
				if(FIRE_RATE_TIMER.get(stack) <= 0)
				{
					if(AMMO.get(stack) > 0)
					{
						this.fireBullet(stack, world, player);
						AMMO.add(stack, -props.passiveController.gunUse(player, stack, 1));
						if(AMMO.get(stack) <= 0)
						{
							FIRE_RATE_TIMER.set(stack, 0);
							AMMO.set(stack, 0);
							world.playSoundAtEntity(player, RefMod.MODID + ":" + "item.weapon.empty", 3.0F, 0.9F + itemRand.nextFloat() * 0.3F);
						}
						else
						{
							this.setFireRate(stack);
						}
						this.applyRecoil(player, stack);
					}
					else
					{
						world.playSoundAtEntity(player, RefMod.MODID + ":" + "item.weapon.empty", 3.0F, 0.9F + itemRand.nextFloat() * 0.3F);
					}
				}
			}
			props.isRightClickHeldDownLast = true;
			props.isRightClickHeldDown = true;
		}
		return stack;
	}
	
	public ItemStack rapidFire(ItemStack stack, World world, EntityPlayer player)
	{
		if(this.isRapidFire)
		{
			ExtendedPlayer props = ExtendedPlayer.get(player);
			if(FIRE_RATE_TIMER.get(stack) <= 0)
			{
				if(AMMO.get(stack) > 0)
				{
					this.fireBullet(stack, world, player);
					AMMO.add(stack, -props.passiveController.gunUse(player, stack, 1));
					if(AMMO.get(stack) <= 0)
					{
						FIRE_RATE_TIMER.set(stack, 0);
						AMMO.set(stack, 0);
						world.playSoundAtEntity(player, RefMod.MODID + ":" + "item.weapon.empty", 3.0F, 0.9F + itemRand.nextFloat() * 0.3F);
					}
					else
					{
						this.setFireRate(stack);
					}
					this.applyRecoil(player, stack);
				}
			}
		}
		return stack;
	}
	
	public void applyRecoil(EntityPlayer player, ItemStack stack)
	{
		this.applyRecoil(player, stack, this.recoil(stack));
	}
	
	public void applyRecoilPerTick(ItemStack stack, EntityPlayer player)
	{
		this.applyRecoilPerTick(stack, player, this.recoil(stack));
	}
	
	public void applyRecoil(EntityPlayer player, ItemStack stack, double recoil)
	{
		ExtendedPlayer props = ExtendedPlayer.get(player);
		if(props.isZoomHeldDown)
		{
			recoil *= 2F / 3F;
		}
		double f = 1.1F * (1 + (recoil - 1) / 8F);
		if(props.isZoomHeldDown)
		{
			f *= 0.4;
		}
		f *= this.instability(player, stack, recoil);
		if(player.worldObj.isRemote)
		{
			if(f > 0)
			{
				player.rotationYaw += Stuff.Randomization.r(f);
				player.prevRotationPitch += Stuff.Randomization.r(f);
			}
			player.rotationPitch -= recoil / 2;
		}
		this.RECOIL.add(stack, recoil);
		player.rotationYaw += Stuff.Randomization.r(Math.sqrt(recoil) * this.instability(player, stack, recoil) * 2);
		player.rotationPitch -= recoil;
	}
	
	public float instability(EntityPlayer player, ItemStack stack, double recoil)
	{
		return 1;
	}
	
	public void applyRecoilPerTick(ItemStack stack, EntityPlayer player, double recoil)
	{
		ExtendedPlayer props = ExtendedPlayer.get(player);
		if(props.isZoomHeldDown)
		{
			recoil *= 2F / 3F;
		}
		int i = 3;
		player.rotationPitch += recoil - recoil * (i - 1) / i;
		
		this.RECOIL.set(stack, recoil * (i - 1) / i);
	}
	
	public void setFireRate(ItemStack stack)
	{
		FIRE_RATE_TIMER.set(stack, this.FIRE_RATE.get(stack));
	}
	
	public abstract void fireBullet(ItemStack stack, World world, EntityPlayer player);
	
	public void selectNextAmmoType(ItemStack stack, EntityPlayer player)
	{
		if(FIRE_RATE_TIMER.get(stack) <= 0)
		{
			this.selectAmmoType(stack, player, this.getAmmoNameIndex(stack, 1));
		}
	}
	
	public void selectAmmoType(ItemStack stack, EntityPlayer player, int ammoNameNumber)
	{
		if(AMMO.get(stack) > 0)
		{
			for(int i = AMMO.get(stack); i > 0; i -= Math.max(1, this.AMMO_TYPE.get(stack).getItem().getMaxDamage()))
			{
				player.entityDropItem(new ItemStack(this.AMMO_TYPE.get(stack).getItem(), 1, this.AMMO_TYPE.get(stack).getItem().getMaxDamage() - i), 0);
				AMMO.add(stack, -Math.max(1, this.AMMO_TYPE.get(stack).getItem().getMaxDamage()));
			}
		}
		AMMO.set(stack, 0);
		this.AMMO_TYPE.set(stack, this.allAmmoTypes[ammoNameNumber]);
	}
	
	protected int getAmmoNameIndex(ItemStack stack, int modifier)
	{
		int a = this.AMMO_TYPE.get(stack).ordinal() + modifier;
		if(a > this.allAmmoTypes.length - 1)
		{
			a -= this.allAmmoTypes.length;
		}
		
		if(a < 0)
		{
			a = 0;
		}
		return a;
	}
	
	public boolean reload(ItemStack stack, EntityPlayer player, int requestedAmount)
	{
		if(stack.getItem() instanceof ItemWeaponRanged && FIRE_RATE_TIMER.get(stack) <= this.FIRE_RATE.get(stack) + 4)
		{
			int i = this.lookForItemInInventory(this.AMMO_TYPE.get(stack).getItem(), player, requestedAmount);
			if(i > 0)
			{
				AMMO.set(stack, i > this.CAPACITY.get(stack) ? this.CAPACITY.get(stack) : i + AMMO.get(stack));
				FIRE_RATE_TIMER.set(stack, this.RELOAD_TIME.get(stack) + this.FIRE_RATE.get(stack) + 4);
				this.playReloadSound(player);
				return true;
			}
			else
			{
				player.worldObj.playSoundAtEntity(player, RefMod.MODID + ":" + "item.weapon.empty", 3.0F, 0.9F + itemRand.nextFloat() * 0.3F);
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	
	protected abstract void playReloadSound(EntityPlayer player);
	
	protected int lookForItemInInventory(Item item, EntityPlayer player, int requestedAmount)
	{
		int amount = 0;
		for(int slot = player.inventory.getSizeInventory() - 1; slot >= 0 && amount < requestedAmount; --slot)
		{
			if(player.inventory.getStackInSlot(slot) != null)
			{
				if(Item.getIdFromItem(player.inventory.getStackInSlot(slot).getItem()) == Item.getIdFromItem(item))
				{
					++amount;
					if(player.inventory.getStackInSlot(slot).stackSize > 1)
					{
						--player.inventory.getStackInSlot(slot).stackSize;
					}
					else
					{
						player.inventory.setInventorySlotContents(slot, null);
					}
					return amount;
				}
			}
		}
		return amount;
	}
	
	public float getZoom(World world, EntityPlayer player, ItemStack stack)
	{
		return this.zoom(stack);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void playPickupSound(EntityPlayer player, ItemStack stack)
	{
		player.playSound(RefMod.MODID + ":" + "item.weapon.shotgun.pump.auto", 0.2F, 1.1F + Item.itemRand.nextFloat() * 0.3F);
	}
	
	public static boolean heldUp(ItemStack stack)
	{
		ItemWeaponRanged item = (ItemWeaponRanged)stack.getItem();
		return item.isHeldUp(stack);
	}
	
	public boolean isHeldUp(ItemStack stack)
	{
		if((AMMO.get(stack) > 0 || FIRE_RATE_TIMER.get(stack) > 0) && FIRE_RATE_TIMER.get(stack) <= this.FIRE_RATE.get(stack))
		{
			return true;
		}
		return false;
	}
	
	@Override
	public boolean showCrosshair()
	{
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public ModelResourceLocation getModel(ItemStack stack, EntityPlayer player, int useRemaining)
	{
		String tex = M.getId(this).mod + ":" + M.getId(this).id + this.getUpgradeSuffix(stack);
		if(this.differentTextureIfEmpty && AMMO.get(stack) <= 0 && FIRE_RATE_TIMER.get(stack) <= 0)
		{
			tex += "_empty";
		}
		return new ModelResourceLocation(tex, "inventory");
	}
	
	@Override
	public String[] getTextureVariants(int meta)
	{
		ArrayList<String> textures = new ArrayList<String>();
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		this.getSubItems2(this, this.getCreativeTab(), stacks);
		for(ItemStack stack : stacks)
		{
			String tex = M.getId(this).mod + ":" + M.getId(this).id + this.getUpgradeSuffix(stack);
			textures.add(tex);
			if(this.differentTextureIfEmpty)
			{
				textures.add(tex + "_empty");
			}
		}
		return textures.toArray(new String[textures.size()]);
	}
	
	public ArrayList<EnumUpgrades> getUpgrades(ItemStack stack)
	{
		ArrayList<EnumUpgrades> upgrades = new ArrayList<EnumUpgrades>();
		for(EnumUpgrades upgrade : this.allUpgrades)
		{
			if(this.UPGRADES.get(stack, upgrade))
			{
				upgrades.add(upgrade);
			}
		}
		return upgrades;
	}
	
	public String getUpgradeSuffix(ItemStack stack)
	{
		String tex = "";
		ArrayList<EnumUpgrades> upgrades = this.getUpgrades(stack);
		if(upgrades.size() > 0)
		{
			for(EnumUpgrades key : upgrades)
			{
				tex += "_" + key.toString().toLowerCase();
			}
			if(upgrades.size() > 1)
			{
				tex += "_upgrades";
			}
			else
			{
				tex += "_upgrade";
			}
		}
		return tex;
	}
}