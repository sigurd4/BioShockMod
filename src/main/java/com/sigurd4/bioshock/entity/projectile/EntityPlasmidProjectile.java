package com.sigurd4.bioshock.entity.projectile;

import io.netty.buffer.ByteBuf;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.sigurd4.bioshock.M;
import com.sigurd4.bioshock.entity.IEntityPlasmid;
import com.sigurd4.bioshock.itemtags.ItemTagCompound;
import com.sigurd4.bioshock.itemtags.ItemTagInteger;
import com.sigurd4.bioshock.plasmids.IPlasmidProjectile;
import com.sigurd4.bioshock.plasmids.Plasmid;

public class EntityPlasmidProjectile<Plsmd extends Plasmid & IPlasmidProjectile> extends EntityThrowable implements IEntityAdditionalSpawnData, IEntityPlasmid
{
	public static final ItemTagCompound COMPOUND = new ItemTagCompound("PlasmidProperties", false);
	public static final ItemTagInteger PLASMID = new ItemTagInteger("Plasmid", Plasmid.getIndex(M.items.plasmids.injectable.incinerate.plasmid), 0, Plasmid.getPlasmids().size() - 1, false);
	
	private NBTTagCompound compound = new NBTTagCompound();
	private Plsmd plasmid;
	public EntityLivingBase thrower;
	public String throwerName;
	
	public EntityPlasmidProjectile(World world)
	{
		super(world);
		this.setSize(0, 0);
		this.renderDistanceWeight = 6.0D;
		this.plasmid = (Plsmd)M.items.plasmids.injectable.incinerate.plasmid;
		this.addVelocityBullet(this.plasmid.getInitialSpeed());
	}
	
	public <Plsmd2 extends Plasmid & IPlasmidProjectile> EntityPlasmidProjectile(World world, EntityLivingBase thrower, Plsmd2 plasmid)
	{
		super(world, thrower);
		this.setSize(0, 0);
		this.renderDistanceWeight = 6.0D;
		this.plasmid = (Plsmd)plasmid;
		this.thrower = thrower;
		this.addVelocityBullet(this.plasmid.getInitialSpeed());
	}
	
	public EntityPlasmidProjectile(World world, double x, double y, double z, Plsmd plasmid)
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
		
		PLASMID.set(compound, Plasmid.getIndex(this.plasmid));
		COMPOUND.set(compound, this.compound);
		
		if((this.throwerName == null || this.throwerName.length() == 0) && this.thrower instanceof EntityPlayer)
		{
			this.throwerName = this.thrower.getName();
		}
		
		compound.setString("ownerName", this.throwerName == null ? "" : this.throwerName);
	}
	
	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);
		
		this.setPlasmidFromIndex(PLASMID.get(compound, true));
		this.compound = COMPOUND.get(compound, true);
		
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
		this.setSize(width, height);
	}
	
	public int getNearbyInstances(BlockPos offset)
	{
		BlockPos pos = this.getPosition();
		List<IEntityPlasmid> a = this.worldObj.getEntitiesWithinAABB(IEntityPlasmid.class, new AxisAlignedBB(pos.getX() + offset.getX(), pos.getY() + offset.getY(), pos.getZ() + offset.getZ(), pos.getX() + 1 + offset.getX(), pos.getY() + 1 + offset.getY(), pos.getZ() + 1 + offset.getZ()));
		for(int i = 0; i < a.size(); ++i)
		{
			if(a.get(i).getPlasmid() != this.getPlasmid())
			{
				a.remove(i);
				--i;
			}
		}
		return a.size();
	}
	
	/**
	 * Checks if the entity is in range to render by using the past in distance
	 * and comparing it to its average edge
	 * length * 64 * renderDistanceWeight Args: distance
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance)
	{
		if(this.plasmid.isInRangeToRenderDist(this, distance))
		{
			double d1 = this.getBoundingBox().getAverageEdgeLength() * 4.0D;
			d1 *= 64.0D * 6;
			return distance < d1 * d1;
		}
		return false;
	}
}
