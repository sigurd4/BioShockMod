package com.sigurd4.bioshock.particles;

import java.util.ArrayList;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityDropParticleFX;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ParticleHandler
{
	private static ArrayList<P> particles = new ArrayList<P>();
	
	private static class P
	{
		public final EntityFX particle;
		public final EnumParticleTypes2 type;
		public int[] par;
		
		public P(EntityFX particle, EnumParticleTypes2 type, int[] par)
		{
			this.particle = particle;
			this.type = type;
			this.par = par;
		}
	}
	
	public static enum EnumParticleTypes2
	{
		REDSTONE2()
		{
			@Override
			public EntityFX get(World world, double x, double y, double z, double mx, double my, double mz, int... par)
			{
				float r = 1;
				float g = 0;
				float b = 0;
				float scale = 1;
				
				if(par.length >= 3)
				{
					r = (float)par[0] / 100;
					g = (float)par[1] / 100;
					b = (float)par[2] / 100;
					scale = (float)par[3] / 100;
				}
				else
				{
					par = new int[]{100, 0, 0, 100};
				}
				if(scale > 4)
				{
					scale = 4;
				}
				else if(scale < 0.1)
				{
					scale = 0.1F;
				}
				
				EntityReddust2FX p = new EntityReddust2FX(world, x, y, z, scale, r - 1, g, b);
				p.motionX = mx;
				p.motionY = my;
				p.motionZ = mz;
				
				RenderManager rm = Minecraft.getMinecraft().getRenderManager();
				if(p.getDistance(rm.viewerPosX, rm.viewerPosY, rm.viewerPosZ) > 64)
				{
					return null;
				}
				
				return p;
			}
			
			@Override
			public void update(P p)
			{
				
			}
		},
		DRIP2()
		{
			@Override
			public EntityFX get(World world, double x, double y, double z, double mx, double my, double mz, int... par)
			{
				EntityDropParticleFX p = new EntityDrop2FX(world, x, y, z, mx, my, mz, Material.water);
				for(int i2 = 0; p != null && i2 < 40; ++i2)
				{
					p.onUpdate();
				}
				return p;
			}
			
			@Override
			public void update(P p)
			{
				
			}
		};
		
		private EnumParticleTypes2()
		{
			
		}
		
		public abstract EntityFX get(World world, double x, double y, double z, double mx, double my, double mz, int... par);
		
		public abstract void update(P p);
	}
	
	@SideOnly(Side.CLIENT)
	public static EntityFX particle(EnumParticleTypes2 particleEnum, World world, boolean ignoreDistance, double x, double y, double z, double mx, double my, double mz, int... par)
	{
		EntityFX particle = particleEnum.get(world, x, y, z, mx, my, mz, par);
		if(particle != null)
		{
			particle.prevPosX = particle.posX;
			particle.prevPosY = particle.posY;
			particle.prevPosZ = particle.posZ;
			particles.add(new P(particle, particleEnum, par));
			return spawnEntityFX(particle, ignoreDistance, par);
		}
		return null;
	}
	
	@SideOnly(Side.CLIENT)
	public static EntityFX spawnEntityFX(EntityFX particle, boolean ignoreDistance, int... par)
	{
		Minecraft mc = Minecraft.getMinecraft();
		if(mc != null && mc.getRenderViewEntity() != null && mc.effectRenderer != null)
		{
			int k = mc.gameSettings.particleSetting;
			
			if(k == 1 && mc.theWorld.rand.nextInt(3) == 0)
			{
				k = 2;
			}
			
			double d6 = mc.getRenderViewEntity().posX - particle.posX;
			double d7 = mc.getRenderViewEntity().posY - particle.posY;
			double d8 = mc.getRenderViewEntity().posZ - particle.posZ;
			
			double r = 32;
			if(!(ignoreDistance || d6 * d6 + d7 * d7 + d8 * d8 <= r * r && k <= 1))
			{
				particle = null;
			}
			if(particle != null)
			{
				mc.effectRenderer.addEffect(particle);
				return particle;
			}
		}
		return null;
	}
	
	public static void update()
	{
		for(int i = 0; i < particles.size(); ++i)
		{
			P p = particles.get(i);
			if(p.particle == null || p.particle.isDead)
			{
				particles.remove(i);
				--i;
			}
			else
			{
				p.type.update(p);
			}
		}
	}
}
