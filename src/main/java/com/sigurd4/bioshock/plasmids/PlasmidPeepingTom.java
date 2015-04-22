package com.sigurd4.bioshock.plasmids;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

import com.sigurd4.bioshock.Stuff;
import com.sigurd4.bioshock.extendedentity.ExtendedPlayer;
import com.sigurd4.bioshock.item.ItemPlasmid;

public class PlasmidPeepingTom extends PlasmidHold
{
	public PlasmidPeepingTom(String id, int cost1, int cost2, boolean type, int consumeRate, String... description)
	{
		super(id, cost1, cost2, type, consumeRate, description);
	}
	
	@Override
	public void clicked(ItemStack stack, World world, EntityPlayer player)
	{
		ItemPlasmid item = (ItemPlasmid)stack.getItem();
		ExtendedPlayer props = ExtendedPlayer.get(player);
		for(int i = 0; i < 15; ++i)
		{
			world.spawnParticle(EnumParticleTypes.REDSTONE, true, player.posX + Stuff.Randomization.r(1 * ((float)(i + 1) / 16)), player.posY - 0.5 + Stuff.Randomization.r(2 * ((float)(i + 1) / 16)), player.posZ + Stuff.Randomization.r(1 * ((float)(i + 1) / 16)), -0.1, 3, 0.5);
		}
		
		for(int i = 0; i < 128; ++i)
		{
			world.spawnParticle(EnumParticleTypes.SPELL_MOB, true, player.posX + Stuff.Randomization.r(1.4 * ((float)(i + 1) / 129)), player.posY - 0.5 + Stuff.Randomization.r(2 * 1.4 * ((float)(i + 1) / 129)), player.posZ + Stuff.Randomization.r(1.4 * ((float)(i + 1) / 129)), 0.5, 3, 0.5);
		}
		player.setInvisible(true);
	}
	
	@Override
	public void held(ItemStack stack, World world, Entity entity, int slot, boolean isSelected)
	{
		if(entity instanceof EntityPlayer)
		{
			ItemPlasmid item = (ItemPlasmid)stack.getItem();
			EntityPlayer player = (EntityPlayer)entity;
			player.setInvisible(true);
			if(rand.nextInt(3 + 1) == 0)
			{
				float f = -0.3F + rand.nextFloat() * 0.6F;
				world.spawnParticle(EnumParticleTypes.SPELL_MOB, true, player.posX + Stuff.Randomization.r(player.width + f), player.posY + player.height / 4 + Stuff.Randomization.r(player.height / 2 + f), player.posZ + Stuff.Randomization.r(player.width + f), 0.5, 3, 0.5);
			}
		}
	}
	
	@Override
	public void notHeld(ItemStack stack, World world, Entity entity, int slot, boolean isSelected)
	{
		if(entity instanceof EntityPlayer && ((EntityPlayer)entity).getActivePotionEffect(Potion.invisibility) == null)
		{
			entity.setInvisible(false);
		}
	}
	
	@Override
	public void unClicked(ItemStack stack, World world, Entity entity, int slot, boolean isSelected)
	{
		for(int i = 0; i < 15; ++i)
		{
			world.spawnParticle(EnumParticleTypes.REDSTONE, true, entity.posX + Stuff.Randomization.r(1 * ((float)(i + 1) / 16)), entity.posY - 0.5 + Stuff.Randomization.r(2 * ((float)(i + 1) / 16)), entity.posZ + Stuff.Randomization.r(1 * ((float)(i + 1) / 16)), -0.1, 3, 0.5);
		}
		
		for(int i = 0; i < 128; ++i)
		{
			world.spawnParticle(EnumParticleTypes.SPELL_MOB, true, entity.posX + Stuff.Randomization.r(1.4 * ((float)(i + 1) / 129)), entity.posY - 0.5 + Stuff.Randomization.r(2 * 1.4 * ((float)(i + 1) / 129)), entity.posZ + Stuff.Randomization.r(1.4 * ((float)(i + 1) / 129)), 0.5, 3, 0.5);
		}
		
		entity.setInvisible(false);
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
			world.spawnParticle(EnumParticleTypes.SPELL_MOB, true, player.posX + Stuff.Randomization.r(1.4 * ((float)(i + 1) / 129)), player.posY - 0.5 + Stuff.Randomization.r(2 * 1.4 * ((float)(i + 1) / 129)), player.posZ + Stuff.Randomization.r(1.4 * ((float)(i + 1) / 129)), 0.5, 3, 0.5);
		}
		
		if(!world.isRemote)
		{
			player.addPotionEffect(new PotionEffect(Potion.blindness.id, 120, 0, true, false));
			player.addPotionEffect(new PotionEffect(Potion.confusion.id, 160, 0, true, false));
			player.addPotionEffect(new PotionEffect(Potion.weakness.id, 320, 1, true, false));
			player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 80, 3, true, false));
		}
		this.clicked(stack, world, player);
		
		return stack;
	}
	
}
