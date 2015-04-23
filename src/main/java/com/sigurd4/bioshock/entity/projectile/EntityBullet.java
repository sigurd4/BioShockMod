package com.sigurd4.bioshock.entity.projectile;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

import com.sigurd4.bioshock.Stuff;

public abstract class EntityBullet extends EntityThrowable implements IEntityAdditionalSpawnData
{
	public float mass;
	public float damage;
	public EntityLivingBase thrower;
	public String throwerName;
	
	public EntityBullet(World world, EntityLivingBase thrower, float speed, float mass, float damage, float size)
	{
		this(world, thrower);
		this.setSize(size, size);
		this.speed(speed);
		this.mass = mass;
		this.damage = damage;
	}
	
	public EntityBullet(World world)
	{
		super(world);
		this.setSize(0, 0);
		this.renderDistanceWeight = 6.0D;
	}
	
	public EntityBullet(World world, EntityLivingBase thrower)
	{
		super(world, thrower);
		this.setSize(0, 0);
		this.renderDistanceWeight = 6.0D;
	}
	
	public EntityBullet(World world, double x, double y, double z)
	{
		super(world, x, y, z);
		this.setSize(0, 0);
		this.renderDistanceWeight = 6.0D;
	}
	
	@Override
	protected void onImpact(MovingObjectPosition p_70184_1_)
	{
		
	}
	
	@Override
	public void writeSpawnData(ByteBuf buf)
	{
		buf.writeFloat(this.mass);
		buf.writeFloat(this.damage);
	}
	
	@Override
	public void readSpawnData(ByteBuf buf)
	{
		this.mass = buf.readFloat();
		this.damage = buf.readFloat();
	}
	
	public float speed()
	{
		return (float)Stuff.Coordinates3D.distance(new Vec3(this.motionX, this.motionY, this.motionZ));
	}
	
	public void speed(float speed)
	{
		float f = speed / (float)Stuff.Coordinates3D.distance(new Vec3(this.motionX, this.motionY, this.motionZ));
		this.motionX *= f;
		this.motionY *= f;
		this.motionZ *= f;
	}
	
	public float force()
	{
		return this.speed() * this.mass;
	}
	
	protected abstract DamageSource damageSource();
}
