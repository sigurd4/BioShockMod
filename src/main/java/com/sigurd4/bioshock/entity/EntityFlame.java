package com.sigurd4.bioshock.entity;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing.AxisDirection;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.sigurd4.bioshock.Stuff;
import com.sigurd4.bioshock.itemtags.ItemTagFloat;
import com.sigurd4.bioshock.itemtags.ItemTagInteger;
import com.sigurd4.bioshock.plasmids.IPlasmidProjectile;
import com.sigurd4.bioshock.plasmids.Plasmid;
import com.sigurd4.bioshock.plasmids.PlasmidIncinerate;

public class EntityFlame extends EntityThrowable implements IEntityPlasmid
{
	public static final ItemTagInteger PLASMID = new ItemTagInteger("Plasmid", -1, -1, Plasmid.getPlasmids().size() - 1, false);
	public static final ItemTagFloat DAMAGE = new ItemTagFloat("Damage", 1F, 0F, Float.MAX_VALUE, false);
	
	public float damage = DAMAGE.getDefault();
	public PlasmidIncinerate plasmid;
	
	public EntityFlame(World world)
	{
		super(world);
		this.setSize(0, 0);
		this.addVelocityBullet(0.5F);
	}
	
	public EntityFlame(World world, EntityLivingBase thrower, PlasmidIncinerate plasmid)
	{
		super(world, thrower);
		this.setSize(0, 0);
		this.addVelocityBullet(0.5F);
		this.plasmid = plasmid;
	}
	
	public EntityFlame(World world, double x, double y, double z, PlasmidIncinerate plasmid)
	{
		super(world, x, y, z);
		this.setSize(0, 0);
		this.addVelocityBullet(0.5F);
		this.plasmid = plasmid;
	}
	
	/**
	 * Adds to the current velocity of the entity. Args: x, y, z
	 */
	public void addVelocityBullet(float speed)
	{
		this.motionX = this.motionX * speed;
		this.motionY = this.motionY * speed;
		this.motionZ = this.motionZ * speed;
		if(this.motionY < 0)
		{
			this.motionY = -this.motionY;
		}
		this.isAirBorne = true;
	}
	
	@Override
	public void onEntityUpdate()
	{
		if(this.ticksExisted < 4)
		{
			for(int i = 0; i < 4; ++i)
			{
				this.worldObj.spawnParticle(EnumParticleTypes.FLAME, true, this.posX + Stuff.Randomization.r(1), this.posY + Stuff.Randomization.r(1), this.posZ + Stuff.Randomization.r(1), Stuff.Randomization.r(0.03), Stuff.Randomization.r(0.03), Stuff.Randomization.r(0.03));
			}
		}
		
		if(this.ticksExisted > 80 || this.worldObj.getBlockState(this.getPosition()).getBlock().getMaterial() == Material.water)
		{
			this.setDead();
		}
		
		super.onEntityUpdate();
	}
	
	@Override
	protected void onImpact(MovingObjectPosition pos)
	{
		Vec3 vec = this.getPositionVector();
		
		if(pos.entityHit != null)
		{
			if(pos.entityHit == this.getThrower())
			{
				pos.entityHit.setFire(80);
				vec = new Vec3(pos.entityHit.posX, pos.entityHit.posY, pos.entityHit.posZ);
				DamageSource damagesource = DamageSource.onFire;
				if(this.getPlasmid() != null && this.getPlasmid() instanceof IPlasmidProjectile)
				{
					damagesource = Plasmid.getDamageSource(this.getThrower(), this.getPlasmid()).setFireDamage();
				}
				pos.entityHit.attackEntityFrom(damagesource, this.damage + (this.rand.nextFloat() - 0.5F) * 4);
				((EntityLivingBase)pos.entityHit).addPotionEffect(new PotionEffect(Potion.weakness.id, 80, 0, true, false));
			}
		}
		else
		{
			vec = new Vec3(pos.getBlockPos().getX() + 0.5, pos.getBlockPos().getY() + 0.5, pos.getBlockPos().getZ() + 0.5);
			
			switch(pos.sideHit.getAxis())
			{
			case Y:
				vec = vec.addVector(0, 0.5 * (pos.sideHit.getAxisDirection() == AxisDirection.POSITIVE ? 1 : -1), 0);
				this.motionY = -this.motionY / 10;
				break;
			case Z:
				vec = vec.addVector(0, 0, 0.5 * (pos.sideHit.getAxisDirection() == AxisDirection.POSITIVE ? 1 : -1));
				this.motionZ = -this.motionZ / 10;
				break;
			case X:
				vec = vec.addVector(0.5 * (pos.sideHit.getAxisDirection() == AxisDirection.POSITIVE ? 1 : -1), 0, 0);
				this.motionX = -this.motionX / 10;
				break;
			}
			PlasmidIncinerate.meltIce(this.worldObj, new BlockPos(vec));
			PlasmidIncinerate.meltSnow(this.worldObj, new BlockPos(vec));
			PlasmidIncinerate.igniteAlcohol(this.worldObj, vec);
			if(!this.worldObj.isRemote)
			{
				this.setDead();
			}
		}
		if(this.worldObj.getBlockState(this.getPosition()) == Blocks.water)
		{
			this.setDead();
		}
		else
		{
			for(int i = 0; i < 24; ++i)
			{
				this.worldObj.spawnParticle(EnumParticleTypes.FLAME, true, vec.xCoord, vec.yCoord, vec.zCoord, Stuff.Randomization.r(0.2 + 0.8 * i / 24), Stuff.Randomization.r(0.2 + 0.8 * i / 24), Stuff.Randomization.r(0.2 + 0.8 * i / 24));
			}
			for(int i = 0; i < 19; ++i)
			{
				this.worldObj.spawnParticle(EnumParticleTypes.FLAME, true, vec.xCoord + Stuff.Randomization.r(0.3), vec.yCoord, vec.zCoord + Stuff.Randomization.r(0.3), Stuff.Randomization.r(1.0), 0.1, Stuff.Randomization.r(1.0));
			}
			this.worldObj.playSoundEffect(vec.xCoord, vec.yCoord, vec.zCoord, "fire.fire", 1.0F, 0.6F + 0.3F);
		}
	}
	
	/**
	 * Gets the amount of gravity to apply to the thrown entity with each tick.
	 */
	@Override
	protected float getGravityVelocity()
	{
		return 0.2F;
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
		return false;
	}
	
	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);
		DAMAGE.set(compound, this.damage);
	}
	
	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);
		this.damage = DAMAGE.get(compound, true);
	}
	
	@Override
	public Plasmid getPlasmid()
	{
		return this.plasmid;
	}
}
