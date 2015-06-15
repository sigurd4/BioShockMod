package com.sigurd4.bioshock.item;

import java.util.HashMap;
import java.util.UUID;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.google.common.collect.Multimap;
import com.sigurd4.bioshock.M;
import com.sigurd4.bioshock.Stuff;
import com.sigurd4.bioshock.extendedentity.ExtendedPlayer;
import com.sigurd4.bioshock.itemtags.ItemTagBlockPos;
import com.sigurd4.bioshock.itemtags.ItemTagBoolean;
import com.sigurd4.bioshock.itemtags.ItemTagFloat;
import com.sigurd4.bioshock.itemtags.ItemTagInteger;

public class ItemWeaponSkyHook extends ItemWeaponMelee
{
	public static int soundDelay = 0;
	
	public float damage;
	public HashMap<ItemStack, Float> damageHashMap = new HashMap();
	public HashMap<ItemStack, Float> damageHashMapLast = new HashMap();
	
	//nbt
	/** If set to true, the player will jump in the next tick **/
	public final static ItemTagBoolean JUMP = new ItemTagBoolean("Jump", false, false);
	/** The player can hook while HOOKED > 0. Goes down one every tick. **/
	public final static ItemTagInteger CAN_HOOK = new ItemTagInteger("CanHook", 0, 0, 5, false);
	/** If the player is sendt up in the air. **/
	public final static ItemTagBoolean IS_FLYING = new ItemTagBoolean("IsFlying", false, false);
	/** If the player dropped from a hook and IS_FLYING is true **/
	public final static ItemTagBoolean IS_FLYING_FROM_HOOK = new ItemTagBoolean("IsFlyingFromHook", false, false)
	{
		@Override
		public Boolean get(NBTTagCompound compound, boolean createNew)
		{
			return super.get(compound, createNew) && IS_FLYING.get(compound, false);
		}
	};
	public final static ItemTagBlockPos TARGET_COORDS = new ItemTagBlockPos("TargetCoords", null, new BlockPos(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE), new BlockPos(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE), false);
	public final static ItemTagBlockPos HOOK_COORDS = new ItemTagBlockPos("HookCoords", null, new BlockPos(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE), new BlockPos(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE), false);
	/** The player is hooked if HOOKED > 0. Goes down one every tick. **/
	public final static ItemTagInteger HOOKED = new ItemTagInteger("Hooked", 0, 0, 5, false);
	/** How long the player has been on the hook. **/
	public final static ItemTagInteger HOOK_TIME = new ItemTagInteger("HookTime", 0, 0, Integer.MAX_VALUE, false);
	public final static ItemTagFloat WEAPONDAMAGE = new ItemTagFloat("WeaponDamage", 0F, 0F, Float.MAX_VALUE, true);
	//for animation
	public final static ItemTagFloat ROTATION = new ItemTagFloat("Rotation", 0F, -180F, 180F, false);
	public final static ItemTagFloat ACCELERATION = new ItemTagFloat("Acceleration", 0F, 0F, 105.6F, false);
	public final static ItemTagFloat ROTATION_GEAR = new ItemTagFloat("RotationGear", 0F, -180F, 180F, false);
	public final static ItemTagFloat ACCELERATION_GEAR = new ItemTagFloat("AccelerationGear", 0F, 0F, 105.6F, false);
	public final static ItemTagFloat PROGRESS_OUT = new ItemTagFloat("ProgressOut", 0F, 0F, 1F, false);
	public final static ItemTagFloat ACCELERATION_OUT = new ItemTagFloat("AccelerationOut", 0F, 0F, 0.4F, false);
	
	/**
	 * Ride rails or use it as a brutal melee weapon!
	 */
	public ItemWeaponSkyHook(float damage, boolean clickSounds, String weaponPickupSound)
	{
		super(damage, 0, weaponPickupSound);
		this.weaponDamage = 0;
		this.damage = damage;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected)
	{
		if(entity instanceof EntityPlayer)
		{
			UUID uuid = ((EntityPlayer)entity).getUUID(((EntityPlayer)entity).getGameProfile());
			ItemPlasmid.PLAYER_UUID_LEAST.set(stack, uuid.getLeastSignificantBits());
			ItemPlasmid.PLAYER_UUID_MOST.set(stack, uuid.getMostSignificantBits());
		}
		if(entity instanceof EntityLivingBase)
		{
			this.calculateDamage(stack, world, (EntityLivingBase)entity, slot, isSelected);
		}
		if(this.rightClicked(entity, stack))
		{
			
		}
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		return stack;
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase user)
	{
		return super.hitEntity(stack, target, user);
	}
	
	/**
	 * Metadata-sensitive version of getStrVsBlock
	 * 
	 * @param stack
	 *            The Item Stack
	 * @param block
	 *            The block the item is trying to break
	 * @param metadata
	 *            The items current metadata
	 * @return The damage strength
	 */
	@Override
	public float getDigSpeed(ItemStack stack, IBlockState state)
	{
		return super.getDigSpeed(stack, state) + 1.3F * this.acceleration(stack);
	}
	
	@Override
	public Multimap getAttributeModifiers(ItemStack stack)
	{
		if(this.damageHashMap.get(stack) != null)
		{
			this.weaponDamage = this.damageHashMap.get(stack);
		}
		return super.getAttributeModifiers(stack);
	}
	
	protected void flyTowards(ItemStack stack, EntityLivingBase entity)
	{
		
	}
	
	protected void jumpTo(ItemStack stack, EntityLivingBase entity)
	{
		
	}
	
	protected float acceleration(ItemStack stack)
	{
		float f = ACCELERATION.get(stack) / ACCELERATION.max;
		EntityPlayer player = ItemPlasmid.getPlayer(stack);
		if(this.rightClicked(stack))
		{
			f /= 3 / 4;
		}
		if(f > 1)
		{
			return 1;
		}
		return f;
	}
	
	protected void updateSpinningAnimation1(EntityPlayer player, ItemStack stack)
	{
		float accelerateOut = 0.2F;
		float accelerate = 15.8F;
		
		//apply motion to position
		if(!this.isHooked(stack))
		{
			ROTATION.add(stack, ACCELERATION.get(stack));
		}
		ROTATION_GEAR.add(stack, -ACCELERATION_GEAR.get(stack));
		PROGRESS_OUT.add(stack, ACCELERATION_OUT.get(stack));
		
		//accelerate
		if(this.rightClicked(player, stack))
		{
			ACCELERATION_GEAR.add(stack, accelerate);
		}
		else
		{
			ACCELERATION_GEAR.add(stack, accelerate / 2);
		}
		if(this.isHooked(stack))
		{
			ACCELERATION_OUT.set(stack, 0.6F);
			ACCELERATION.set(stack, accelerate * 4);
		}
		else
		{
			
			if(ACCELERATION.get(stack) > ACCELERATION.max * 0.8 || PROGRESS_OUT.get(stack) > 0.2)
			{
				ACCELERATION_OUT.add(stack, accelerateOut * (ACCELERATION.get(stack) - ACCELERATION.max * 0.4F) / 10);
			}
			
			if(ACCELERATION.get(stack) < ACCELERATION.max * 0.8 && this.rightClicked(player, stack))
			{
				ACCELERATION_OUT.add(stack, -accelerateOut * 2);
			}
			else
			{
				ACCELERATION_OUT.add(stack, -accelerateOut / 4);
			}
		}
		
		if(PROGRESS_OUT.get(stack) < 0.3)
		{
			float f = (ACCELERATION_GEAR.get(stack) + ACCELERATION.get(stack) * 4) / 5;
			ACCELERATION.set(stack, f);
			ACCELERATION_GEAR.set(stack, f);
		}
		else
		{
			ACCELERATION.add(stack, -accelerate / 18);
		}
	}
	
	protected void hookStuff(ItemStack stack, World world, EntityLivingBase entity, int slot, boolean isSelected)
	{
		BlockPos entityPos = entity.getPosition();
		TARGET_COORDS.set(stack, entity.getPosition());
		if(IS_FLYING_FROM_HOOK.get(stack))
		{
			IS_FLYING.set(stack, true);
		}
		if(this.isFlying(stack))
		{
			this.flyTowards(stack, entity);
			if(Math.sqrt(entity.getDistanceSqToCenter(TARGET_COORDS.get(stack))) < 20 && entity.posY > TARGET_COORDS.get(stack).getY() - 20 || this.isHooked(stack))
			{
				entity.fallDistance -= 0.6;
			}
			else
			{
				entity.fallDistance -= 0.18;
			}
		}
		if(entity.onGround)
		{
			if(this.isFlying(stack))
			{
				if(entity instanceof EntityPlayer)
				{
					ExtendedPlayer props = ExtendedPlayer.get((EntityPlayer)entity);
					props.passiveController.landFromSkyline((EntityPlayer)entity);
				}
				entity.motionX *= 0.3F;
				entity.motionY *= 0.8F;
				entity.motionZ *= 0.3F;
			}
			IS_FLYING.set(stack, true);
			IS_FLYING_FROM_HOOK.set(stack, true);
		}
		if(!entity.isSneaking() && (IS_FLYING.get(stack) || this.isHooked(stack)) && (this.checkIfBlockValid(entityPos.up(), world.getBlockState(entityPos.up()), world) || this.checkIfBlockValid(entityPos.up(2), world.getBlockState(entityPos.up(2)), world) || this.checkIfBlockValid(entityPos.up(3), world.getBlockState(entityPos.up(3)), world)) && Stuff.Coordinates3D.distance(entity.getPositionVector(), Stuff.Coordinates3D.middle(TARGET_COORDS.get(stack)).addVector(0, -0.5, 0)) < 2)
		{
			HOOKED.set(stack, HOOKED.max);
		}
		if(entity.isSneaking())
		{
			if(this.isHooked(stack))
			{
				entity.worldObj.playSoundAtEntity(entity, "step.stone", 0.2F + 0.4F / 8, 2);
				entity.worldObj.playSoundAtEntity(entity, "random.anvil_land", itemRand.nextFloat() * 0.03F + 0.4F / 8, 0.8F + itemRand.nextFloat() * 0.6F);
			}
			HOOKED.set(stack, 0);
		}
		if(this.isHooked(stack))
		{
			HOOK_TIME.add(stack, 1);
			
			entity.fallDistance = 0;
			if(HOOKED.get(stack) == 1 || HOOK_TIME.get(stack) == 1)
			{
				entity.worldObj.playSoundAtEntity(entity, "step.stone", 0.2F + 0.4F / 8, 2);
				entity.worldObj.playSoundAtEntity(entity, "random.anvil_land", itemRand.nextFloat() * 0.03F + 0.4F / 8, 1.4F + itemRand.nextFloat());
				if(entity instanceof EntityPlayer)
				{
					ExtendedPlayer props = ExtendedPlayer.get((EntityPlayer)entity);
					props.passiveController.hookOnSkyline((EntityPlayer)entity);
				}
			}
			entity.posY = entity.posY < entity.lastTickPosY ? entity.lastTickPosY : entity.posY + 0.4;
			if(HOOKED.get(stack) == 1 && (TARGET_COORDS.get(stack).getX() != entity.getPosition().getX() || TARGET_COORDS.get(stack).getZ() != entity.getPosition().getZ()))
			{
				entity.motionY += 0.3;
				if(this.rightClicked(entity, stack))
				{
					this.jumpTo(stack, entity);
					entity.motionY += 0.3;
				}
				if(entity instanceof EntityPlayer)
				{
					if(ExtendedPlayer.get((EntityPlayer)entity).isJumpHeldDown)
					{
						entity.motionY += 0.5;
					}
					if(entity instanceof EntityPlayer)
					{
						((EntityPlayer)entity).moveForward *= 1.2;
						((EntityPlayer)entity).moveStrafing *= 1.2;
					}
				}
				IS_FLYING.set(stack, true);
			}
			else
			{
				entity.motionY += 0.1;
			}
			if(HOOK_TIME.get(stack) > 5)
			{
				if(entity instanceof EntityPlayer)
				{
					((EntityPlayer)entity).moveForward *= 1.2;
					((EntityPlayer)entity).moveStrafing *= 1.2;
					//IS_FLYING.set(stack, (((EntityPlayer)entity).moveForward != 0 || ((EntityPlayer)entity).moveStrafing != 0) && entity.isSprinting());
				}
			}
			else
			{
				entity.posX = Math.floor(entity.posX) + 0.5;
				entity.motionX = 0;
				entity.posZ = Math.floor(entity.posZ) + 0.5;
				entity.motionZ = 0;
				if(entity instanceof EntityPlayer)
				{
					((EntityPlayer)entity).moveForward = 0;
					((EntityPlayer)entity).moveStrafing = 0;
				}
				IS_FLYING.set(stack, false);
			}
			if((entity.motionX != 0 || entity.motionZ != 0) && entity.isSprinting())
			{
				if(entity.posX > Math.floor(entity.posX) + 0.5)
				{
					entity.motionX += -0.1;
				}
				else if(entity.posX < Math.floor(entity.posX) + 0.5)
				{
					entity.motionX += 0.1;
				}
				if(entity.posZ > Math.floor(entity.posZ) + 0.5)
				{
					entity.motionZ += -0.1;
				}
				else if(entity.posZ < Math.floor(entity.posZ) + 0.5)
				{
					entity.motionZ += 0.1;
				}
			}
			if(HOOKED.get(stack) == 1)
			{
				if(TARGET_COORDS.get(stack).getX() != entity.getPosition().getX() || TARGET_COORDS.get(stack).getZ() != entity.getPosition().getZ())
				{
					this.jumpTo(stack, entity);
					IS_FLYING.set(stack, true);
				}
				IS_FLYING_FROM_HOOK.set(stack, true);
			}
			
			HOOKED.add(stack, -1);
		}
		if(entity.getHeldItem() == stack)
		{
			boolean isFirst = false;
			boolean deleteCoordsIfInvalid = true;
			boolean replaceCoordsIfValid = true;
			if((IS_FLYING_FROM_HOOK.get(stack) || HOOKED.get(stack) > 0 || !IS_FLYING.get(stack) || true) && this.rightClicked(entity, stack))
			{
				if(this.checkIfBlockValid(TARGET_COORDS.get(stack), world.getBlockState(TARGET_COORDS.get(stack)), world))
				{
					isFirst = CAN_HOOK.get(stack) <= 0;
				}
				if(this.isFlying(stack))
				{
					deleteCoordsIfInvalid = false;
					isFirst = false;
				}
				if(IS_FLYING.get(stack))
				{
					replaceCoordsIfValid = false;
				}
				int range = 25;
				int horizontalRange = 15 + (entity.isAirBorne && !entity.onGround ? 10 : 0);
				int verticalRange = 20 + (entity.isAirBorne && !entity.onGround ? 15 : 0);
				Vec3 vec = Stuff.Coordinates3D.multiply(entity.getLookVec(), range);
				if(Math.sqrt(vec.yCoord * vec.yCoord) > verticalRange)
				{
					vec = Stuff.Coordinates3D.divide(vec, Math.sqrt(vec.yCoord * vec.yCoord) / verticalRange);
				}
				if(Math.sqrt(vec.xCoord * vec.xCoord + vec.zCoord * vec.zCoord) > horizontalRange)
				{
					vec = Stuff.Coordinates3D.divide(vec, Math.sqrt(vec.yCoord * vec.yCoord) / horizontalRange);
				}
				Vec3 eyeVec = entity.getPositionVector().addVector(0, entity.getEyeHeight() + entity.getYOffset(), 0);
				MovingObjectPosition mpos = world.rayTraceBlocks(eyeVec, eyeVec.add(vec), true, false, false);
				if(entity instanceof EntityPlayer)
				{
					this.getTarget(stack, (EntityPlayer)entity, mpos, isFirst, deleteCoordsIfInvalid, replaceCoordsIfValid);
				}
			}
			if(CAN_HOOK.get(stack) <= 0 && !IS_FLYING_FROM_HOOK.get(stack))
			{
				TARGET_COORDS.remove(stack);
			}
			if(CAN_HOOK.get(stack) > -1)
			{
				CAN_HOOK.add(stack, -1);
			}
			if(JUMP.get(stack))
			{
				this.jumpTo(stack, entity);
				CAN_HOOK.set(stack, -2);
			}
		}
		if(TARGET_COORDS.has(stack) && this.isHooked(stack) && TARGET_COORDS.get(stack).getX() == entity.getPosition().getX() && TARGET_COORDS.get(stack).getZ() == entity.getPosition().getZ())
		{
			HOOK_COORDS.set(stack, TARGET_COORDS.get(stack));
		}
		if(world.isRemote && entity.equals(Minecraft.getMinecraft().thePlayer))
		{
			if(TARGET_COORDS.has(stack) && (this.isHooked(stack) ? TARGET_COORDS.get(stack).getX() != entity.getPosition().getX() || TARGET_COORDS.get(stack).getY() - 2 != entity.getPosition().getY() && TARGET_COORDS.get(stack).getY() - 1 != entity.getPosition().getY() && TARGET_COORDS.get(stack).getY() != entity.getPosition().getY() || TARGET_COORDS.get(stack).getZ() != entity.getPosition().getZ() : true) && CAN_HOOK.get(stack) > 0)
			{
				for(int i = 4; i > 0; --i)
				{
					Vec3 vec = Stuff.Coordinates3D.middle(TARGET_COORDS.get(stack)).addVector(itemRand.nextFloat() - 0.5F, itemRand.nextFloat() - 0.5F, itemRand.nextFloat() - 0.5F);
					entity.worldObj.spawnParticle(EnumParticleTypes.REDSTONE, true, vec.xCoord, vec.yCoord, vec.zCoord, 0.5, 1.4, 0.5);
				}
			}
		}
		JUMP.set(stack, false);
	}
	
	protected void calculateDamage(ItemStack stack, World world, EntityLivingBase entity, int slot, boolean isSelected)
	{
		float baseDamage = 0;
		if(ACCELERATION.get(stack) > 0)
		{
			baseDamage = this.acceleration(stack) * 4;
			this.weaponDamage = baseDamage;
		}
		else
		{
			this.weaponDamage = 0;
		}
		if(entity.motionY > 0)
		{
			this.weaponDamage *= 1 + ((float)entity.motionY + 2) / 2;
		}
		else
		{
			this.weaponDamage *= 1 + (-(float)entity.motionY + 2) / 2;
		}
		this.weaponDamage *= 4;
		this.weaponDamage *= Math.sqrt(entity.motionX * entity.motionX + entity.motionY * entity.motionY + entity.motionZ * entity.motionZ) / 4;
		if(entity.rotationPitch > 0)
		{
			this.weaponDamage += baseDamage * entity.rotationPitch / 60 * this.acceleration(stack);
		}
		if(entity.fallDistance < 10)
		{
			this.weaponDamage += baseDamage * (entity.fallDistance > 0 ? entity.fallDistance : 0) / 2 * this.acceleration(stack);
		}
		else
		{
			this.weaponDamage += baseDamage * 10 * this.acceleration(stack);
		}
		if(this.weaponDamage < 0)
		{
			this.weaponDamage *= -1;
		}
		if(entity.isSprinting())
		{
			++this.weaponDamage;
		}
		if(!this.rightClicked(entity, stack))
		{
			this.weaponDamage = this.weaponDamage / 2;
		}
		if(IS_FLYING.get(stack))
		{
			this.weaponDamage *= 2;
		}
		
		this.weaponDamage = (this.weaponDamage + WEAPONDAMAGE.get(stack) * this.acceleration(stack) * (this.rightClicked(entity, stack) ? 3 : 0.2F)) / 3;
		WEAPONDAMAGE.set(stack, this.weaponDamage);
		this.weaponDamage *= 0.2F;
		this.weaponDamage += this.damage * this.acceleration(stack) * 2 / 3;
		this.weaponDamage *= this.acceleration(stack);
		if(this.weaponDamage < 0)
		{
			this.weaponDamage = 0;
		}
		WEAPONDAMAGE.set(stack, this.weaponDamage);
	}
	
	protected boolean getTarget(ItemStack stack, EntityPlayer player, MovingObjectPosition pos, boolean isFirst, boolean deleteCoordsIfInvalid, boolean replaceCoordsIfValid)
	{
		if(player != null)
		{
			if(player.getDistance(pos.hitVec.xCoord, pos.hitVec.yCoord, pos.hitVec.zCoord) > 1.5 && pos.entityHit == null)
			{
				IBlockState state = player.worldObj.getBlockState(pos.getBlockPos());
				if(state.getBlock().getBlockHardness(player.worldObj, pos.getBlockPos()) > 0 && state.getBlock().isCollidable() && (state.getBlock().isOpaqueCube() || this.checkIfBlockValid(pos.getBlockPos(), state, player.worldObj)))
				{
					if(this.losCheck(player, pos.hitVec))
					{
						if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemWeaponSkyHook)
						{
							if(this.checkIfBlockValid(pos.getBlockPos(), state, player.worldObj))
							{
								if(!replaceCoordsIfValid || !TARGET_COORDS.has(stack))
								{
									if(TARGET_COORDS.has(stack) || isFirst)
									{
										if(player.worldObj.isRemote)
										{
											M.proxy.playTargetSound(TARGET_COORDS.get(stack), pos, player);
										}
									}
									CAN_HOOK.set(stack, 4);
								}
							}
							else if(deleteCoordsIfInvalid)
							{
								TARGET_COORDS.set(stack, null);
							}
						}
					}
					return true;
				}
			}
		}
		else
		{
			return true;
		}
		return false;
	}
	
	protected boolean losCheck(Entity entity, Vec3 vec)
	{
		boolean b = true;
		for(double i = entity.getEyeHeight(); b && i >= 0; i -= entity.getEyeHeight() / 8)
		{
			b = entity.worldObj.rayTraceBlocks(vec, new Vec3(entity.posX, entity.posY + entity.getEyeHeight() + entity.getYOffset(), entity.posZ), true, false, false) == null;
		}
		return b && entity.worldObj.rayTraceBlocks(vec, new Vec3(entity.posX, entity.posY, entity.posZ), true, false, false) == null;
	}
	
	protected boolean checkIfBlockValid(BlockPos pos, IBlockState state, World world)
	{
		//vanilla iron bars
		if(state.getBlock() == Blocks.iron_bars)
		{
			if(world.isAirBlock(pos.north()) && world.isAirBlock(pos.south()) && world.isAirBlock(pos.east()) && world.isAirBlock(pos.west()))
			{
				if(world.isAirBlock(pos.down()) && world.isAirBlock(pos.down(2)) && !world.isBlockNormalCube(pos.up(), false))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	protected boolean rightClicked(ItemStack stack)
	{
		return this.rightClicked(ItemPlasmid.getPlayer(stack), stack);
	}
	
	protected boolean rightClicked(Entity entity, ItemStack stack)
	{
		if(entity instanceof EntityPlayer && ExtendedPlayer.get((EntityPlayer)entity).isRightClickHeldDown && ((EntityPlayer)entity).getHeldItem() == stack)
		{
			return true;
		}
		return false;
	}
	
	public boolean isFlying(ItemStack stack)
	{
		return IS_FLYING.get(stack) || IS_FLYING_FROM_HOOK.get(stack);
	}
	
	public boolean isHooked(ItemStack stack)
	{
		return HOOKED.get(stack) > 0 || HOOK_TIME.get(stack) > 0;
	}
}
