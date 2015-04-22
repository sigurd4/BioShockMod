package com.sigurd4.bioshock;

import net.minecraft.init.Blocks;

import com.sigurd4.bioshock.block.BlockIceMelting;
import com.sigurd4.bioshock.block.BlockSnowMelting;

public class MBlocks
{
	public static BlockIceMelting ice_melting = M.registerBlock("ice_melting", (BlockIceMelting)new BlockIceMelting().setHardness(Blocks.ice.getBlockHardness(null, null)).setLightOpacity(Blocks.ice.getLightOpacity()).setStepSound(Blocks.ice.stepSound), true, new String[]{});
	public static BlockSnowMelting snow_melting = M.registerBlock("snow_melting", (BlockSnowMelting)new BlockSnowMelting().setHardness(Blocks.snow_layer.getBlockHardness(null, null)).setLightOpacity(Blocks.snow_layer.getLightOpacity()).setStepSound(Blocks.snow_layer.stepSound), true, new String[]{});
}
