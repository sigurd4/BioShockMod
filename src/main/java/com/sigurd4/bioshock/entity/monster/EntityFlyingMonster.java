package com.sigurd4.bioshock.entity.monster;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public abstract class EntityFlyingMonster extends EntityMob implements IAnimals, IEntityAdditionalSpawnData
{
	private BlockPos spawnPosition;
	
	public EntityFlyingMonster(World par1World)
	{
		super(par1World);
	}
	
	@Override
	public void onEntityUpdate()
	{
		if(this.prevPosX == this.posX && this.prevPosY == this.posY && this.prevPosZ == this.posZ && this.ticksExisted > 1)
		{
			this.setHealth(this.getHealth() - 1);
			this.setDead();
		}
		else
		{
			this.setHealth(this.getHealth() + 1);
		}
		
		super.onEntityUpdate();
	}
	
	@Override
	public boolean allowLeashing()
	{
		return false;
	}
	
	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow,
	 * gets into the saddle on a pig.
	 */
	@Override
	protected boolean interact(EntityPlayer par1EntityPlayer)
	{
		return false;
	}
	
	/**
	 * Returns true if this entity should push and be pushed by other entities
	 * when colliding.
	 */
	@Override
	public boolean canBePushed()
	{
		return false;
	}
	
	@Override
	protected void updateAITasks()
	{
		super.updateAITasks();
		
		if(this.spawnPosition != null && (!this.worldObj.isAirBlock(this.spawnPosition) || this.spawnPosition.getY() < 1))
		{
			this.spawnPosition = null;
		}
		
		if(this.spawnPosition == null || this.rand.nextInt(30) == 0 || this.spawnPosition.distanceSq(this.posX, this.posY, this.posZ) < 4.0F)
		{
			this.spawnPosition = new BlockPos(this.posX + this.rand.nextInt(7) - this.rand.nextInt(7), this.posY + this.rand.nextInt(6) - 2, this.posZ + this.rand.nextInt(7) - this.rand.nextInt(7));
		}
		
		double d0 = this.spawnPosition.getX() + 0.5D - this.posX;
		double d1 = this.spawnPosition.getY() + 0.1D - this.posY;
		double d2 = this.spawnPosition.getZ() + 0.5D - this.posZ;
		
		this.motionX += (Math.signum(d0) * 0.5D - this.motionX) * 0.10000000149011612D;
		this.motionY += (Math.signum(d1) * 0.699999988079071D - this.motionY) * 0.10000000149011612D;
		this.motionZ += (Math.signum(d2) * 0.5D - this.motionZ) * 0.10000000149011612D;
		float f = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0D / Math.PI) - 90.0F;
		float f1 = MathHelper.wrapAngleTo180_float(f - this.rotationYaw);
		this.moveForward = 0.5F;
		this.rotationYaw += f1;
	}
	
	/**
	 * Called by the server when constructing the spawn packet.
	 * Data should be added to the provided stream.
	 *
	 * @param buffer
	 *            The packet data stream
	 */
	@Override
	public void writeSpawnData(ByteBuf buf)
	{
		NBTTagCompound compound = new NBTTagCompound();
		this.writeEntityToNBT(compound);
		ByteBufUtils.writeTag(buf, compound);
	}
	
	/**
	 * Called by the client when it receives a Entity spawn packet.
	 * Data should be read out of the stream in the same way as it was written.
	 *
	 * @param data
	 *            The packet data stream
	 */
	@Override
	public void readSpawnData(ByteBuf buf)
	{
		NBTTagCompound compound = ByteBufUtils.readTag(buf);
		if(compound != null)
		{
			this.readEntityFromNBT(compound);
		}
	}
}