package com.sigurd4.bioshock.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.BlockIce;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.sigurd4.bioshock.Stuff;

public class BlockIceMelting extends BlockIce implements IBlockBreakRegardless
{
	public static enum EnumMeltType implements IStringSerializable
	{
		AIR, WATER;
		
		@Override
		public String getName()
		{
			return this.toString().toLowerCase();
		}
	}
	
	public static final PropertyInteger MELTING = PropertyInteger.create("melting", 0, 7);
	public static final PropertyEnum MELT_TYPE = PropertyEnum.create("melt_type", EnumMeltType.class, EnumMeltType.values());
	
	public BlockIceMelting()
	{
		super();
		this.setTickRandomly(true);
		this.setCreativeTab(null);
		this.setDefaultState(this.blockState.getBaseState().withProperty(MELTING, MELTING.getAllowedValues().size() - 1).withProperty(MELT_TYPE, EnumMeltType.AIR));
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		if(world.getClosestPlayer(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 40 + rand.nextDouble() * 50) == null)
		{
			int i = (Integer)state.getValue(MELTING) / 2 - 1;
			world.setBlockState(pos, state.withProperty(MELTING, i > 0 ? i : 0));
		}
		if((Integer)state.getValue(MELTING) <= 0)
		{
			this.particles(this, world, pos);
			if(world.provider.doesWaterVaporize())
			{
				if(!world.isRemote)
				{
					world.setBlockToAir(pos);
				}
				return;
			}
			if(!world.isRemote)
			{
				if((EnumMeltType)state.getValue(MELT_TYPE) == EnumMeltType.WATER || (world.getBlockState(pos.down()).getBlock().getMaterial() == Material.water || world.getBlockState(pos.down()).getBlock().getMaterial().blocksMovement()) && world.getBlockState(pos.north()).getBlock().getMaterial() == Material.water && world.getBlockState(pos.south()).getBlock().getMaterial() == Material.water && world.getBlockState(pos.east()).getBlock().getMaterial() == Material.water && world.getBlockState(pos.west()).getBlock().getMaterial() == Material.water)
				{
					world.setBlockState(pos, Blocks.water.getDefaultState());
				}
				else if(world.getBlockState(pos.down()).getBlock().getMaterial().blocksMovement() && world.getBlockState(pos.down()).getBlock().getMaterial() != Material.ice)
				{
					world.setBlockState(pos, Blocks.flowing_water.getDefaultState().withProperty(BlockDynamicLiquid.LEVEL, 1));
				}
				else
				{
					//world.setBlock(x, y, z, Blocks.air);
					world.setBlockState(pos, Blocks.flowing_water.getDefaultState().withProperty(BlockDynamicLiquid.LEVEL, 5));
				}
			}
		}
		else if(rand.nextBoolean())
		{
			world.setBlockState(pos, state.withProperty(MELTING, (Integer)state.getValue(MELTING) + 1));
		}
	}
	
	public static void particles(Block block, World world, BlockPos pos)
	{
		Vec3 vec = Stuff.Coordinates3D.middle(pos);
		for(int i = 0; i < 20; ++i)
		{
			float f = 1.4F;
			world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, true, vec.xCoord, vec.yCoord, vec.zCoord, Stuff.Randomization.r(f), Stuff.Randomization.r(f), Stuff.Randomization.r(f), new int[]{Block.getStateId(block.getDefaultState())});
		}
		for(int i = 0; i < 20; ++i)
		{
			float f = 4.8F + Stuff.Randomization.r(4);
			world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, true, vec.xCoord, vec.yCoord, vec.zCoord, Stuff.Randomization.r(f), Stuff.Randomization.r(f), Stuff.Randomization.r(f), new int[]{Block.getStateId(block.getDefaultState())});
		}
		for(int i = 0; i < 30; ++i)
		{
			world.spawnParticle(EnumParticleTypes.REDSTONE, true, vec.xCoord + Stuff.Randomization.r(1), vec.yCoord + Stuff.Randomization.r(1), vec.zCoord + Stuff.Randomization.r(1), 1, 1, 1);
		}
		
		for(int i = 0; i < 16; ++i)
		{
			world.spawnParticle(EnumParticleTypes.SNOWBALL, true, vec.xCoord + Stuff.Randomization.r(1), vec.yCoord + Stuff.Randomization.r(1), vec.zCoord + Stuff.Randomization.r(1), 0, 0, 0);
		}
	}
	
	@Override
	public String getUnlocalizedName()
	{
		return Blocks.ice.getUnlocalizedName();
	}
	
	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		IBlockState state = super.getStateFromMeta(meta);
		if(meta > MELT_TYPE.getAllowedValues().size() - 1)
		{
			state = state.withProperty(MELT_TYPE, EnumMeltType.WATER);
		}
		state = state.withProperty(MELTING, meta % MELT_TYPE.getAllowedValues().size());
		return state;
	}
	
	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state)
	{
		int meta = 0;
		meta += (Integer)state.getValue(MELTING);
		if((EnumMeltType)state.getValue(MELT_TYPE) == EnumMeltType.WATER)
		{
			meta += MELT_TYPE.getAllowedValues().size();
		}
		return meta;
	}
	
	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[]{MELTING, MELT_TYPE});
	}
}
