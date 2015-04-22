package com.sigurd4.bioshock.passives;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class PassiveNewtonsLaw extends PassiveFireBird
{
	public PassiveNewtonsLaw(String id, String name, Passive[] required, Passive[] incapatible, Type type, float range)
	{
		super(id, name, required, incapatible, type, 0, range);
	}
	
	@Override
	protected void action(EntityPlayer player, EntityLivingBase victim)
	{
		double x = player.posX - victim.posX;
		double y = player.posY - victim.posY;
		double z = player.posZ - victim.posZ;
		double w = Math.sqrt(x * x + y * y + z * z);
		x /= w;
		y /= w;
		z /= w;
		x *= (this.range - w) / this.range;
		y *= (this.range - w) / this.range;
		z *= (this.range - w) / this.range;
		if(player.motionY < 0)
		{
			x *= -player.motionY;
			y *= -player.motionY;
			z *= -player.motionY;
			if(victim.onGround)
			{
				y += -player.motionY / 8;
			}
		}
		victim.addVelocity(x, y, z);
	}
}
