package com.sigurd4.bioshock;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;

import com.sigurd4.bioshock.block.Block2;
import com.sigurd4.bioshock.block.BlockIceMelting;
import com.sigurd4.bioshock.block.BlockSeastone;
import com.sigurd4.bioshock.block.BlockSnowMelting;

public class MBlocks
{
	public static BlockIceMelting ice_melting = M.registerBlock("ice_melting", (BlockIceMelting)new BlockIceMelting().setHardness(Blocks.ice.getBlockHardness(null, null)).setLightOpacity(Blocks.ice.getLightOpacity()).setStepSound(Blocks.ice.stepSound), true, new String[]{});
	public static BlockSnowMelting snow_melting = M.registerBlock("snow_melting", (BlockSnowMelting)new BlockSnowMelting().setHardness(Blocks.snow_layer.getBlockHardness(null, null)).setLightOpacity(Blocks.snow_layer.getLightOpacity()).setStepSound(Blocks.snow_layer.stepSound), true, new String[]{});
	
	private static BlockSeastone b = (BlockSeastone)new BlockSeastone().setHardness(1.6F).setResistance(10F).setStepSound(Block.soundTypePiston).setUnlocalizedName("seastone").setCreativeTab(M.tabs.building_blocks);
	public static BlockSeastone seastone = M.registerBlock("seastone", b, false, new String[]{"stone"}, new ItemBlock(b));
	public static Block seastone_cobblestone = M.registerBlock("seastone_cobblestone", new Block2(Material.rock).setHardness(2.0F).setResistance(10.0F).setStepSound(Block.soundTypePiston).setUnlocalizedName("seastoneCobblestone").setCreativeTab(M.tabs.building_blocks), false, new String[]{"cobblestone"});
}
