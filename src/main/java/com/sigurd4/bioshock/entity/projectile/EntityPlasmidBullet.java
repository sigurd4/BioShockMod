package com.sigurd4.bioshock.entity.projectile;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

import com.sigurd4.bioshock.M;
import com.sigurd4.bioshock.entity.IEntityPlasmid;
import com.sigurd4.bioshock.plasmids.IPlasmidProjectile;
import com.sigurd4.bioshock.plasmids.Plasmid;

public class EntityPlasmidBullet<Plsmd extends Plasmid & IPlasmidProjectile> extends EntityBullet implements IEntityAdditionalSpawnData, IEntityPlasmid
{
	private NBTTagCompound compound = new NBTTagCompound();
	private Plsmd plasmid;
	public EntityLivingBase thrower;
	public String throwerName;
	
	public <Plsmd2 extends Plasmid & IPlasmidProjectile> EntityPlasmidBullet(World world, EntityLivingBase thrower, Plsmd2 plasmid, float speed, float mass, float damage)
	{
		super(world, thrower, speed, mass, damage);
		this.setSize(0, 0);
		this.renderDistanceWeight = 6.0D;
		this.plasmid = (Plsmd)plasmid;
		this.thrower = thrower;
		this.addVelocityBullet(this.plasmid.getInitialSpeed());
		
	}
	
	public EntityPlasmidBullet(World world)
	{
		super(world);
		this.setSize(0, 0);
		this.renderDistanceWeight = 6.0D;
		this.plasmid = (Plsmd)M.items.plasmids.injectable.incinerate.plasmid;
		this.addVelocityBullet(this.plasmid.getInitialSpeed());
	}
	
	public <Plsmd2 extends Plasmid & IPlasmidProjectile> EntityPlasmidBullet(World world, EntityLivingBase thrower, Plsmd2 plasmid)
	{
		super(world, thrower);
		this.setSize(0, 0);
		this.renderDistanceWeight = 6.0D;
		this.plasmid = (Plsmd)plasmid;
		this.thrower = thrower;
		this.addVelocityBullet(this.plasmid.getInitialSpeed());
	}
	
	public EntityPlasmidBullet(World world, double x, double y, double z, Plsmd plasmid)
	{
		super(world, x, y, z);
		this.setSize(0, 0);
		this.renderDistanceWeight = 6.0D;
		this.plasmid = plasmid;
		this.addVelocityBullet(this.plasmid.getInitialSpeed());
	}
	
	/**
	 * Adds to the current velocity of the entity. Args: x, y, z
	 */
	private void addVelocityBullet(double speed)
	{
		this.motionX *= speed;
		this.motionY *= speed;
		this.motionZ *= speed;
		this.isAirBorne = true;
	}
	
	@Override
	public void onEntityUpdate()
	{
		if(this.getThrower() == null && this.worldObj.getClosestPlayerToEntity(this, 10) != null)
		{
			this.setThrower(this.worldObj.getClosestPlayerToEntity(this, 10));
		}
		if(this.ticksExisted > 10)
		{
			if(this.getThrower() != null)
			{
				if(Math.sqrt((this.posX - this.getThrower().posX) * (this.posX - this.getThrower().posX) + (this.posZ - this.getThrower().posZ) * (this.posZ - this.getThrower().posZ) + 10 * (this.posY - this.getThrower().posY) * (this.posY - this.getThrower().posY)) > 32)
				{
					this.setDead();
				}
			}
			else
			{
				this.setDead();
			}
		}
		this.getPlasmid().onEntityUpdate(this);
		super.onEntityUpdate();
	}
	
	@Override
	protected void onImpact(MovingObjectPosition pos)
	{
		this.getPlasmid().onImpact(this, pos);
	}
	
	/**
	 * Gets the amount of gravity to apply to the thrown entity with each tick.
	 */
	@Override
	protected float getGravityVelocity()
	{
		return this.getPlasmid().getGravityVelocity(this);
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource src, float dmg)
	{
		return super.attackEntityFrom(src, dmg) && this.getPlasmid().attackEntityFrom(this, src, dmg);
	}
	
	@Override
	public boolean interactFirst(EntityPlayer player)
	{
		return super.interactFirst(player) && this.getPlasmid().interactFirst(this, player);
	}
	
	/** STUFF **/
	
	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);
		
		EntityPlasmidProjectile.PLASMID.set(compound, Plasmid.getIndex(this.plasmid));
		EntityPlasmidProjectile.COMPOUND.set(compound, this.compound);
		
		this.throwerName = compound.getString("ownerName");
		
		if(this.throwerName != null && this.throwerName.length() == 0)
		{
			this.throwerName = null;
		}
	}
	
	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);
		
		this.setPlasmidFromIndex(EntityPlasmidProjectile.PLASMID.get(compound, true));
		this.compound = EntityPlasmidProjectile.COMPOUND.get(compound, true);
		
		this.throwerName = compound.getString("ownerName");
		
		if(this.throwerName != null && this.throwerName.length() == 0)
		{
			this.throwerName = null;
		}
	}
	
	@Override
	public void writeSpawnData(ByteBuf buf)
	{
		buf.writeInt(Plasmid.getIndex(this.plasmid));
		ByteBufUtils.writeTag(buf, this.compound);
	}
	
	@Override
	public void readSpawnData(ByteBuf buf)
	{
		this.setPlasmidFromIndex(buf.readInt());
		this.compound = ByteBufUtils.readTag(buf);
	}
	
	public NBTTagCompound getCompound()
	{
		return this.compound;
	}
	
	@Override
	public Plsmd getPlasmid()
	{
		return this.plasmid;
	}
	
	@Override
	public EntityLivingBase getThrower()
	{
		if(this.thrower == null && this.throwerName != null && this.throwerName.length() > 0)
		{
			this.thrower = this.worldObj.getPlayerEntityByName(this.throwerName);
		}
		
		return this.thrower;
	}
	
	public void setThrower(EntityLivingBase entity)
	{
		this.thrower = entity;
		this.throwerName = entity.getName();
	}
	
	private void setPlasmidFromIndex(int index)
	{
		if(index >= 0 && index < Plasmid.getPlasmids().size())
		{
			Plasmid plasmid2 = Plasmid.getPlasmids().get(index);
			if(plasmid2 != null && plasmid2 instanceof IPlasmidProjectile)
			{
				this.plasmid = (Plsmd)plasmid2;
			}
		}
	}
	
	public void setEntitySize(float width, float height)
	{
		this.setEntitySize(width, height);
	}
	
	@Override
	protected DamageSource damageSource()
	{
		return this.getPlasmid().getDamageSource(this);
	}
}
