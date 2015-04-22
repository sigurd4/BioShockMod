package com.sigurd4.bioshock.plasmids;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.sigurd4.bioshock.M;
import com.sigurd4.bioshock.element.Element;
import com.sigurd4.bioshock.entity.projectile.EntityPlasmidProjectile;

public class PlasmidOldManWinter extends PlasmidWinterBlast implements IPlasmidProjectile
{
	public PlasmidOldManWinter(String id, int cost1, int cost2, boolean type, String... description)
	{
		super(id, cost1, cost2, type, description);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		for(int i = 0; i < 12; ++i)
		{
			world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, true, player.posX - player.width * rand.nextDouble() + player.width, player.posY - player.height * rand.nextDouble(), player.posZ - player.width * rand.nextDouble() + player.width, 0.0D, 0.0D, 0.0D, new int[]{Block.getIdFromBlock(Blocks.ice)});
		}
		for(int i = 0; i < 12; ++i)
		{
			world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, true, player.posX - player.width * rand.nextDouble() + player.width, player.posY - player.height * rand.nextDouble(), player.posZ - player.width * rand.nextDouble() + player.width, 0.0D, 0.0D, 0.0D, new int[]{Block.getIdFromBlock(Blocks.snow)});
		}
		
		if(!world.isRemote)
		{
			world.spawnEntityInWorld(new EntityPlasmidProjectile(world, player, this));
		}
		player.swingItem();
		Element.addToEntityElement(player, Element.ICE, 30);
		
		return stack;
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player)
	{
		for(int i = 0; i < 24; ++i)
		{
			world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, true, player.posX - player.width * rand.nextDouble() + player.width, player.posY - player.height * rand.nextDouble(), player.posZ - player.width * rand.nextDouble() + player.width, 0.0D, 0.0D, 0.0D, new int[]{Block.getIdFromBlock(Blocks.snow)});
		}
		return super.onItemUseFinish(stack, world, player);
	}
	
	@Override
	public void onImpact(EntityThrowable projectile, MovingObjectPosition pos)
	{
		super.onImpact(projectile, pos);
		
		float f = 1.5F;
		for(int i = 0; i < 8; ++i)
		{
			projectile.worldObj.spawnParticle(EnumParticleTypes.SNOWBALL, true, projectile.posX - projectile.motionX * rand.nextDouble(), projectile.posY - projectile.motionY * rand.nextDouble(), projectile.posZ - projectile.motionZ * rand.nextDouble(), -projectile.motionX + rand.nextDouble() * f, -projectile.motionY + rand.nextDouble() * f, -projectile.motionZ + rand.nextDouble() * f);
		}
		for(int i = 0; i < 12; ++i)
		{
			projectile.worldObj.spawnParticle(EnumParticleTypes.BLOCK_CRACK, projectile.posX - projectile.motionX * rand.nextDouble(), projectile.posY - projectile.motionY * rand.nextDouble(), projectile.posZ - projectile.motionZ * rand.nextDouble(), rand.nextDouble() * f, rand.nextDouble() * f, rand.nextDouble() * f, new int[]{Block.getStateId(M.blocks.snow_melting.getDefaultState())});
		}
	}
	
	@Override
	public double getInitialSpeed()
	{
		return 1.0;
	}
	
	@Override
	public void onEntityUpdate(EntityThrowable projectile)
	{
		super.onEntityUpdate(projectile);
		
		float f = 1.5F;
		for(int i = 0; i < 8; ++i)
		{
			projectile.worldObj.spawnParticle(EnumParticleTypes.SNOWBALL, true, projectile.posX - projectile.motionX * rand.nextDouble(), projectile.posY - projectile.motionY * rand.nextDouble(), projectile.posZ - projectile.motionZ * rand.nextDouble(), -projectile.motionX + rand.nextDouble() * f, -projectile.motionY + rand.nextDouble() * f, -projectile.motionZ + rand.nextDouble() * f);
		}
		for(int i = 0; i < 12; ++i)
		{
			projectile.worldObj.spawnParticle(EnumParticleTypes.BLOCK_CRACK, projectile.posX - projectile.motionX * rand.nextDouble(), projectile.posY - projectile.motionY * rand.nextDouble(), projectile.posZ - projectile.motionZ * rand.nextDouble(), rand.nextDouble() * f, rand.nextDouble() * f, rand.nextDouble() * f, new int[]{Block.getStateId(M.blocks.snow_melting.getDefaultState())});
		}
		
		for(int i = 0; i < 3; ++i)
		{
			if(!projectile.worldObj.isRemote)
			{
				EntityFallingBlock fallingBlock = new EntityFallingBlock(projectile.worldObj, (int)Math.floor(projectile.posX) + 0.5 - projectile.motionX * rand.nextDouble(), (int)Math.floor(projectile.posY) - 0.5 - projectile.motionY * rand.nextDouble(), (int)Math.floor(projectile.posZ) + 0.5 - projectile.motionZ * rand.nextDouble(), M.blocks.snow_melting.getDefaultState());
				fallingBlock.fallTime = 1;
				fallingBlock.shouldDropItem = false;
				fallingBlock.addVelocity(rand.nextDouble() * 0.2 - 0.1, rand.nextDouble() * 0.2 - 0.1, rand.nextDouble() * 0.2 - 0.1);
				projectile.worldObj.spawnEntityInWorld(fallingBlock);
			}
		}
	}
	
	@Override
	public float getGravityVelocity(EntityThrowable projectile)
	{
		return 0.02F;
	}
}
