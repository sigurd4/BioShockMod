package com.sigurd4.bioshock.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.sigurd4.bioshock.M;

public class BlockSeastone extends Block
{
	public static final PropertyEnum VARIANT = PropertyEnum.create("variant", BlockSeastone.EnumType.class);
	
	public BlockSeastone()
	{
		super(Material.rock);
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockSeastone.EnumType.LIGHT));
	}
	
	/**
	 * Get the Item that this Block should drop when harvested.
	 * 
	 * @param fortune
	 *            the level of the Fortune enchantment on the player's tool
	 */
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(M.blocks.seastone_cobblestone);
	}
	
	/**
	 * Get the damage value that this Block should drop
	 */
	@Override
	public int damageDropped(IBlockState state)
	{
		return ((BlockSeastone.EnumType)state.getValue(VARIANT)).getMetadata();
	}
	
	@Override
	public float getBlockHardness(World worldIn, BlockPos pos)
	{
		IBlockState state = worldIn.getBlockState(pos);
		if(state != null && state.getProperties().containsKey(VARIANT))
		{
			return ((BlockSeastone.EnumType)state.getValue(VARIANT)).hardness;
		}
		return this.blockHardness;
	}
	
	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood
	 * returns 4 blocks)
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list)
	{
		BlockSeastone.EnumType[] aenumtype = BlockSeastone.EnumType.values();
		int i = aenumtype.length;
		
		for(int j = 0; j < i; ++j)
		{
			BlockSeastone.EnumType enumtype = aenumtype[j];
			list.add(new ItemStack(itemIn, 1, enumtype.getMetadata()));
		}
	}
	
	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(VARIANT, BlockSeastone.EnumType.byMetadata(meta));
	}
	
	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((BlockSeastone.EnumType)state.getValue(VARIANT)).getMetadata();
	}
	
	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[]{VARIANT});
	}
	
	public static enum EnumType implements IStringSerializable
	{
		LIGHT(0, "light", "seastoneLight", 2.6F),
		DARK(1, "dark", "seastoneDark", 8.2F),
		CRACKED(2, "cracked", "seastoneCracked", 2.2F),
		POROUS(3, "porous", "seastonePorous", 0.9F),
		ALGAE(4, "algae", "seastoneAlgae", 3.2F),
		CRUSHED(5, "crushed", "seastoneCrushed", 0.5F),
		OLD(6, "old", "seastoneOld", 6.9F);
		/** Array of the Block's BlockStates */
		private static final BlockSeastone.EnumType[] META_LOOKUP = new BlockSeastone.EnumType[values().length];
		/** The BlockState's metadata. */
		private final int meta;
		/** The EnumType's name. */
		private final String name;
		private final String unlocalizedName;
		public final float hardness;
		
		private EnumType(int meta, String name, String unlocalizedName, float hardness)
		{
			this.meta = meta;
			this.name = name;
			this.unlocalizedName = unlocalizedName;
			this.hardness = hardness;
		}
		
		/**
		 * Returns the EnumType's metadata value.
		 */
		public int getMetadata()
		{
			return this.meta;
		}
		
		@Override
		public String toString()
		{
			return this.name;
		}
		
		/**
		 * Returns an EnumType for the BlockState from a metadata value.
		 */
		public static BlockSeastone.EnumType byMetadata(int meta)
		{
			if(meta < 0 || meta >= META_LOOKUP.length)
			{
				meta = 0;
			}
			
			return META_LOOKUP[meta];
		}
		
		@Override
		public String getName()
		{
			return this.name;
		}
		
		public String getUnlocalizedName()
		{
			return this.unlocalizedName;
		}
		
		static
		{
			BlockSeastone.EnumType[] var0 = values();
			int var1 = var0.length;
			
			for(int var2 = 0; var2 < var1; ++var2)
			{
				BlockSeastone.EnumType var3 = var0[var2];
				META_LOOKUP[var3.getMetadata()] = var3;
			}
		}
	}
}