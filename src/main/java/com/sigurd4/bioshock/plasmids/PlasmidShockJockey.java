package com.sigurd4.bioshock.plasmids;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

import com.sigurd4.bioshock.Stuff;
import com.sigurd4.bioshock.element.Element;
import com.sigurd4.bioshock.entity.projectile.EntityPlasmidProjectile;

public class PlasmidShockJockey extends PlasmidElectroBolt implements IPlasmidProjectile
{
	public PlasmidShockJockey(String id, int cost1, int cost2, boolean type, String... description)
	{
		super(id, cost1, cost2, type, description);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		world.playSoundEffect(player.posX, player.posY, player.posZ, "mob.silverfish.say", 4.0F, 1.2F);
		for(int i = 0; i < 6; ++i)
		{
			world.spawnParticle(EnumParticleTypes.REDSTONE, true, player.posX + Stuff.Randomization.r(2), player.posY + player.height / 2 + Stuff.Randomization.r(2), player.posZ + Stuff.Randomization.r(2), 0.4, 0.9, 1.0);
		}
		for(int i = 0; i < 8; ++i)
		{
			world.spawnParticle(EnumParticleTypes.REDSTONE, true, player.posX + Stuff.Randomization.r(0.5), player.posY + player.height / 2 + Stuff.Randomization.r(2), player.posZ + Stuff.Randomization.r(0.5), 0.4, 0.9, 1.0);
		}
		
		if(!world.isRemote)
		{
			world.spawnEntityInWorld(new EntityPlasmidProjectile(world, player, this));
		}
		
		player.swingItem();
		Element.setEntityElement(player, Element.ELECTRICITY, 100, false);
		
		return stack;
	}
}
