package com.sigurd4.bioshock.entity.projectile;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.sigurd4.bioshock.M;
import com.sigurd4.bioshock.element.Element;

public class EntityGunBullet extends EntityBullet implements IEntityGunProjectile
{
	public final static float damageReducer = 6;
	
	public ItemStack gun;
	public float onTickDamageModifier;
	public boolean canBeCaught = true;
	public boolean ignoresArmour = false;
	public boolean antipersonnel = false;
	public boolean electric = false;
	public boolean burning = false;
	public boolean piercing = false;
	public float knockback = 0.2F;
	
	public EntityGunBullet(World world, EntityLivingBase thrower, ItemStack gun, float speed, float mass, float damage, float size, float onTickDamageModifier, boolean canBeCaught)
	{
		super(world, thrower, speed, mass, damage, size);
		this.setSize(size, size);
		this.renderDistanceWeight = 6.0D;
		this.gun = gun.copy();
		this.onTickDamageModifier = onTickDamageModifier;
		this.canBeCaught = canBeCaught;
	}
	
	public EntityGunBullet(World world)
	{
		super(world);
		this.setSize(0, 0);
		this.renderDistanceWeight = 6.0D;
	}
	
	@Override
	public void onEntityUpdate()
	{
		super.onEntityUpdate();
	}
	
	@Override
	protected void onImpact(MovingObjectPosition pos)
	{
		super.onImpact(pos);
	}
	
	/**
	 * Gets the amount of gravity to apply to the thrown entity with each tick.
	 */
	@Override
	protected float getGravityVelocity()
	{
		return 0.05F;
	}
	
	/** STUFF **/
	
	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);
	}
	
	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);
	}
	
	@Override
	public void writeSpawnData(ByteBuf buf)
	{
		super.writeSpawnData(buf);
		buf.writeFloat(this.onTickDamageModifier);
		buf.writeBoolean(this.canBeCaught);
		buf.writeBoolean(this.ignoresArmour);
		buf.writeBoolean(this.antipersonnel);
		buf.writeBoolean(this.electric);
		buf.writeBoolean(this.burning);
		buf.writeBoolean(this.piercing);
		buf.writeFloat(this.knockback);
	}
	
	@Override
	public void readSpawnData(ByteBuf buf)
	{
		super.readSpawnData(buf);
		this.onTickDamageModifier = buf.readFloat();
		this.canBeCaught = buf.readBoolean();
		this.ignoresArmour = buf.readBoolean();
		this.antipersonnel = buf.readBoolean();
		this.electric = buf.readBoolean();
		this.burning = buf.readBoolean();
		this.piercing = buf.readBoolean();
		this.knockback = buf.readFloat();
	}
	
	public void setEntitySize(float width, float height)
	{
		this.setSize(width, height);
	}
	
	@Override
	protected DamageSource damageSource()
	{
		return Element.bullet(this);
	}
	
	@Override
	public String getDamageName()
	{
		return this.gun != null ? M.getId(this.gun.getItem()).id : null;
	}
	
	@Override
	public float getDamage()
	{
		return this.damage;
	}
	
	@Override
	public void setDamage(float damage)
	{
		this.damage = damage;
	}
}
