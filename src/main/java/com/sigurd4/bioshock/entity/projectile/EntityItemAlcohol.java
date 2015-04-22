package com.sigurd4.bioshock.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityItemAlcohol extends EntityItem
{
	public static float defaultExplosionRadius = 1.6F;
	public float explosionRadius;
	
	public EntityItemAlcohol(World world, EntityLivingBase thrower)
	{
		super(world);
		this.explosionRadius = defaultExplosionRadius;
		this.setOwner(thrower.getName());
		this.setThrower(thrower.getName());
		this.setLocationAndAngles(thrower.posX, thrower.posY + thrower.getEyeHeight(), thrower.posZ, thrower.rotationYaw, thrower.rotationPitch);
		this.posX -= MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F;
		this.posY -= 0.10000000149011612D;
		this.posZ -= MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F;
		this.setPosition(this.posX, this.posY, this.posZ);
		float f = 0.4F;
		this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * f;
		this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * f;
		this.motionY = -MathHelper.sin((this.rotationPitch + this.func_70183_g()) / 180.0F * (float)Math.PI) * f;
		this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, this.func_70182_d(), 1.0F);
	}
	
	@Override
	public void onEntityUpdate()
	{
		super.onEntityUpdate();
		this.isImmuneToFire = true;
	}
	
	protected float func_70182_d()
	{
		return 1.5F;
	}
	
	protected float func_70183_g()
	{
		return 0.03F;
	}
	
	/**
	 * Similar to setArrowHeading, it's point the throwable entity to a x, y, z
	 * direction.
	 */
	public void setThrowableHeading(double x, double y, double z, float power, float inaccuracy)
	{
		float f2 = MathHelper.sqrt_double(x * x + y * y + z * z);
		x /= f2;
		y /= f2;
		z /= f2;
		x += this.rand.nextGaussian() * 0.007499999832361937D * inaccuracy;
		y += this.rand.nextGaussian() * 0.007499999832361937D * inaccuracy;
		z += this.rand.nextGaussian() * 0.007499999832361937D * inaccuracy;
		x *= power;
		y *= power;
		z *= power;
		this.motionX = x;
		this.motionY = y;
		this.motionZ = z;
		float f3 = MathHelper.sqrt_double(x * x + z * z);
		this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(x, z) * 180.0D / Math.PI);
		this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(y, f3) * 180.0D / Math.PI);
	}
	
	public void explode()
	{
		if(!this.worldObj.isRemote)
		{
			boolean flag = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
			
			this.worldObj.newExplosion(this.worldObj.getPlayerEntityByName(this.getOwner()), this.posX, this.posY, this.posZ, this.explosionRadius, true, false);
			
			this.setDead();
		}
	}
	
	/**
	 * Checks if the entity is in range to render by using the past in distance
	 * and comparing it to its average edge
	 * length * 64 * renderDistanceWeight Args: distance
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double p_70112_1_)
	{
		double d1 = this.getBoundingBox().getAverageEdgeLength() * 4.0D;
		d1 *= 64.0D * 6;
		return p_70112_1_ < d1 * d1;
	}
}
