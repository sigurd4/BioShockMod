package com.sigurd4.bioshock.particles;

import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EntityDropParticleFX;
import net.minecraft.world.World;

public class EntityDrop2FX extends EntityDropParticleFX
{
	public EntityDrop2FX(World world, double x, double y, double z, double mx, double my, double mz, Material material)
	{
		super(world, x, y, z, material);
		this.motionX = mx;
		this.motionY = my;
		this.motionZ = mz;
	}
}
