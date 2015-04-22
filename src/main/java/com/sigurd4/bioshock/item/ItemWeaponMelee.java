package com.sigurd4.bioshock.item;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.google.common.collect.Multimap;
import com.sigurd4.bioshock.M;
import com.sigurd4.bioshock.block.IBlockBreakRegardless;
import com.sigurd4.bioshock.reference.RefMod;

public class ItemWeaponMelee extends Item implements IItemWeapon
{
	public float weaponDamage;
	
	public String weaponPickupSound;
	
	/**
	 * Template for generic close combat weapons
	 */
	public ItemWeaponMelee(float damage, int maxDamage, String weaponPickupSound)
	{
		super();
		this.setCreativeTab(M.tabs.weapons);
		if(maxDamage > 0)
		{
			this.setMaxDamage(maxDamage);
		}
		this.setMaxStackSize(1);
		this.weaponDamage = damage;
		this.weaponPickupSound = weaponPickupSound;
	}
	
	/**
	 * Gets a map of item attribute modifiers, used by ItemSword to increase hit
	 * damage.
	 */
	@Override
	@SuppressWarnings({"rawtypes", "unchecked", "deprecation"})
	public Multimap getAttributeModifiers(ItemStack stack)
	{
		Multimap multimap = super.getAttributeModifiers(stack);
		if(this.weaponDamage > 0)
		{
			multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(itemModifierUUID, "Weapon modifier", this.weaponDamage, 0));
		}
		return multimap;
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
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase user)
	{
		stack.damageItem(1, user);
		return super.hitEntity(stack, target, user);
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, Block block, BlockPos pos, EntityLivingBase player)
	{
		if(block.getBlockHardness(world, pos) != 0.0D)
		{
			stack.damageItem(2, player);
		}
		
		return true;
	}
	
	@Override
	public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, EntityPlayer player)
	{
		IBlockState state = player.worldObj.getBlockState(pos);
		if(player.worldObj.getBlockState(pos) == null || !(player.worldObj.getBlockState(pos).getBlock() instanceof IBlockBreakRegardless))
		{
			if(player.capabilities.isCreativeMode && !player.worldObj.getBlockState(pos).getBlock().canPlaceBlockAt(player.worldObj, pos))
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void playRightClickSound(ItemStack stack, World world, EntityPlayer player)
	{
		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean showCrosshair()
	{
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean onEntitySwing(EntityLivingBase entity, ItemStack stack)
	{
		boolean b = super.onEntitySwing(entity, stack);
		if(!b && entity.worldObj.isRemote && entity == Minecraft.getMinecraft().thePlayer)
		{
			entity.playSound(RefMod.MODID + ":" + "item.weapon.wrench.swing", 0.5F, 0.9F + Minecraft.getMinecraft().theWorld.rand.nextFloat() * 0.2F);
		}
		return b;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void playPickupSound(EntityPlayer player, ItemStack stack)
	{
		if(this.weaponPickupSound != null)
		{
			player.playSound(this.weaponPickupSound, 1.0F, 0.9F + Item.itemRand.nextFloat() * 0.2F);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void playSelectSound(EntityPlayer player, ItemStack stack)
	{
		this.playPickupSound(player, stack);
	}
}
