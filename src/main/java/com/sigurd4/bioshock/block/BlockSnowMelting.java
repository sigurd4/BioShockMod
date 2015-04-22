package com.sigurd4.bioshock.block;

import java.util.Random;

import net.minecraft.block.BlockSnow;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockSnowMelting extends BlockSnow implements IBlockBreakRegardless
{
	public BlockSnowMelting()
	{
		super();
		this.setTickRandomly(true);
		this.setCreativeTab(null);
		this.setDefaultState(this.blockState.getBaseState().withProperty(BlockIceMelting.MELTING, BlockIceMelting.MELTING.getAllowedValues().size() - 1));
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		if(world.getClosestPlayer(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 40 + rand.nextDouble() * 50) == null)
		{
			int i = (Integer)state.getValue(BlockIceMelting.MELTING) / 2 - 1;
			world.setBlockState(pos, state.withProperty(BlockIceMelting.MELTING, i > 0 ? i : 0));
		}
		if((Integer)state.getValue(BlockIceMelting.MELTING) <= 0)
		{
			BlockIceMelting.particles(this, world, pos);
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
				world.setBlockState(pos, Blocks.air.getDefaultState());
			}
		}
		else if(rand.nextBoolean())
		{
			world.setBlockState(pos, state.withProperty(BlockIceMelting.MELTING, (Integer)state.getValue(BlockIceMelting.MELTING) + 1));
		}
	}
	
	@Override
	public String getUnlocalizedName()
	{
		return Blocks.snow_layer.getUnlocalizedName();
	}
	
	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		IBlockState state = super.getStateFromMeta(meta);
		state = state.withProperty(LAYERS, 1);
		state = state.withProperty(BlockIceMelting.MELTING, meta);
		return state;
	}
	
	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state)
	{
		int meta = this.getMetaFromState(state);
		meta = 0;
		meta += (Integer)state.getValue(BlockIceMelting.MELTING);
		return meta;
	}
	
	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[]{BlockIceMelting.MELTING});
	}
}
