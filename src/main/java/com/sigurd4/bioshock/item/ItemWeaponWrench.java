package com.sigurd4.bioshock.item;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.sigurd4.bioshock.Stuff;

public class ItemWeaponWrench extends ItemWeaponMelee
{
	/**
	 * Wrench
	 */
	public ItemWeaponWrench(float damage, int maxDamage, String weaponPickupSound)
	{
		super(damage, maxDamage, weaponPickupSound);
	}
	
	/**
	 * Callback for item usage. If the item does something special on right
	 * clicking, he will have one of those. Return
	 * True if something happen and false if it don't. This is for ITEMS, not
	 * BLOCKS
	 */
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float xF, float yF, float zF)
	{
		if(player.capabilities.allowEdit)
		{
			IBlockState state = world.getBlockState(pos);
			EnumFacing[] directions = state.getBlock().getValidRotations(world, pos);
			EnumFacing preferredDirection = null;
			if(directions.length > 0)
			{
				boolean b = state.getBlock().rotateBlock(world, pos, directions[Item.itemRand.nextInt(directions.length - 1 + 1)]);
				if(b || !world.getBlockState(pos).equals(state))
				{
					Vec3 vec = Stuff.Coordinates3D.middle(pos);
					world.playSound(vec.xCoord, vec.yCoord, vec.zCoord, "step.stone", 0.1F + 1.0F / 8, 2F, true);
					world.playSound(vec.xCoord, vec.yCoord, vec.zCoord, "random.anvil_land", world.rand.nextFloat() * 0.05F + 1.0F / 32, 1 + world.rand.nextFloat(), true);
				}
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This is called when the item is used, before the block is activated.
	 * 
	 * @param stack
	 *            The Item Stack
	 * @param player
	 *            The Player that used the item
	 * @param world
	 *            The Current World
	 * @param x
	 *            Target X Position
	 * @param y
	 *            Target Y Position
	 * @param z
	 *            Target Z Position
	 * @param side
	 *            The side of the target hit
	 * @return Return true to prevent any further processing.
	 */
	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		world.playSound(hitX, hitY, hitZ, "step.stone", 0.2F + 1.0F / 8, 2F, true);
		world.playSound(hitX, hitY, hitZ, "random.anvil_land", world.rand.nextFloat() * 0.1F + 1.0F / 8, 1 + world.rand.nextFloat(), true);
		return false;
	}
	
	/**
	 * Called before a block is broken. Return true to prevent default block
	 * harvesting.
	 *
	 * Note: In SMP, this is called on both client and server sides!
	 *
	 * @param stack
	 *            The current ItemStack
	 * @param X
	 *            The X Position
	 * @param Y
	 *            The X Position
	 * @param Z
	 *            The X Position
	 * @param player
	 *            The Player that is wielding the item
	 * @return True to prevent harvesting, false to continue as normal
	 */
	@Override
	public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, EntityPlayer player)
	{
		boolean b = super.onBlockStartBreak(stack, pos, player);
		if(!player.worldObj.getBlockState(pos).getBlock().canPlaceBlockAt(player.worldObj, pos))
		{
			Vec3 vec = Stuff.Coordinates3D.middle(pos);
			player.worldObj.playSound(vec.xCoord, vec.yCoord, vec.zCoord, "step.stone", 0.2F + 1.0F / 16, 2F, true);
			player.worldObj.playSound(vec.xCoord, vec.yCoord, vec.zCoord, "random.anvil_land", player.worldObj.rand.nextFloat() * 0.1F + 1.0F / 16, 1 + player.worldObj.rand.nextFloat(), true);
		}
		return b;
	}
}
