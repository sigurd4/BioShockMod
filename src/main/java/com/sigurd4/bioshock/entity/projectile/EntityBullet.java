package com.sigurd4.bioshock.entity.projectile;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

import com.sigurd4.bioshock.Stuff;
import com.sigurd4.bioshock.element.Element;

public class EntityBullet extends EntityThrowable implements IEntityAdditionalSpawnData
{
	public float mass;
	public float damage;
	public float onTickDamageModifier;
	public EntityLivingBase thrower;
	public String throwerName;
	public ItemStack gun;
	
	public EntityBullet(World world, EntityLivingBase thrower, ItemStack gun, float speed, float mass, float size, float damage)
	{
		this(world, thrower);
		this.setSize(size, size);
		float f = speed / (float)Stuff.Coordinates3D.distance(new Vec3(this.motionX, this.motionY, this.motionZ));
		this.motionX *= f;
		this.motionY *= f;
		this.motionZ *= f;
		this.mass = mass;
		this.damage = damage;
		this.gun = gun.copy();
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
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void writeSpawnData(ByteBuf buffer)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void readSpawnData(ByteBuf additionalData)
	{
		// TODO Auto-generated method stub
		
	}
	
	public float speed()
	{
		return (float)Stuff.Coordinates3D.distance(new Vec3(this.motionX, this.motionY, this.motionZ));
	}
	
	public float force()
	{
		return this.speed() * this.mass;
	}
	
	protected DamageSource damageSource()
	{
		return Element.bullet(this);
	}
}
