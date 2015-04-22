package com.sigurd4.bioshock.plasmids;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

import com.sigurd4.bioshock.Stuff;

public class PlasmidIronsides extends PlasmidHold
{
	public PlasmidIronsides(String id, int cost1, int cost2, boolean type, int consumeRate, String... description)
	{
		super(id, cost1, cost2, type, consumeRate, description);
	}
	
	@Override
	public void clicked(ItemStack stack, World world, EntityPlayer player)
	{
		for(int i = 0; i < 25; ++i)
		{
			world.spawnParticle(EnumParticleTypes.REDSTONE, true, player.posX + Stuff.Randomization.r(1), player.posY - 1.0 + Stuff.Randomization.r(1), player.posZ + Stuff.Randomization.r(1), -1, 0, 2);
		}
		for(int i = 0; i < 14; ++i)
		{
			world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, true, player.posX + Stuff.Randomization.r(1), player.posY - 1.0 + Stuff.Randomization.r(1), player.posZ + Stuff.Randomization.r(1), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1), new int[]{Block.getIdFromBlock(Blocks.iron_block)});
		}
	}
	
	@Override
	public void notHeld(ItemStack stack, World world, Entity entity, int slot, boolean isSelected)
	{
		
	}
	
	@Override
	public void held(ItemStack stack, World world, Entity entity, int slot, boolean isSelected)
	{
		for(int i = 0; i < 3; ++i)
		{
			world.spawnParticle(EnumParticleTypes.REDSTONE, true, entity.posX + Stuff.Randomization.r(1), entity.posY - 1.0 + Stuff.Randomization.r(1), entity.posZ + Stuff.Randomization.r(1), -1, 0, 2);
		}
		for(int i = 0; i < 3; ++i)
		{
			world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, true, entity.posX + Stuff.Randomization.r(1), entity.posY - 1.0 + Stuff.Randomization.r(1), entity.posZ + Stuff.Randomization.r(1), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1), new int[]{Block.getIdFromBlock(Blocks.iron_block)});
		}
	}
	
	@Override
	public void unClicked(ItemStack stack, World world, Entity entity, int slot, boolean isSelected)
	{
		
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player)
	{
		for(int i = 0; i < 5; ++i)
		{
			world.spawnParticle(EnumParticleTypes.REDSTONE, true, player.posX + Stuff.Randomization.r(1), player.posY - 1.0 + Stuff.Randomization.r(1), player.posZ + Stuff.Randomization.r(1), 0, 0.9, 2);
		}
		for(int i = 0; i < 40; ++i)
		{
			world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, true, player.posX + Stuff.Randomization.r(1), player.posY - 1.0 + Stuff.Randomization.r(1), player.posZ + Stuff.Randomization.r(1), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1), Stuff.Randomization.r(0.1), new int[]{Block.getIdFromBlock(Blocks.iron_block)});
		}
		
		if(!world.isRemote)
		{
			player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 80, 2));
			player.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 80, 10, true, false));
			player.addPotionEffect(new PotionEffect(Potion.resistance.id, 80, 1, true, false));
		}
		
		return stack;
	}
	
}
