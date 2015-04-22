package com.sigurd4.bioshock.audio;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import com.sigurd4.bioshock.item.ItemWeaponSkyHook;
import com.sigurd4.bioshock.reference.RefMod;

public class MovingSoundSkyHook extends MovingSoundPublic
{
	public final boolean type;
	
	public MovingSoundSkyHook(Entity entity, boolean type)
	{
		super(new ResourceLocation(RefMod.MODID + ":" + "item.weapon.skyhook.motor"));
		AudioHandler.createMovingEntitySound(this.entity, RefMod.MODID + ":" + "item.weapon.skyhook.motor.rough", 0.1F + (type ? 2.4F : 0.1F), 1.4F, false);
		AudioHandler.createMovingEntitySound(this.entity, RefMod.MODID + ":" + "item.weapon.skyhook.motor.start", 1.0F - (type ? 0.9F : 0), 1.0F, false);
		AudioHandler.createMovingEntitySound(this.entity, RefMod.MODID + ":" + "item.weapon.skyhook.motor.rough.start", 0.3F + (type ? 0.4F : 0.1F), 1.4F, false);
		this.entity = entity;
		this.volume = 2.5F - (type ? 1.4F : 0);
		this.repeat = false;
		this.pitch = 1.05F;
		this.type = type;
		IResource res;
		try
		{
			res = Minecraft.getMinecraft().getResourceManager().getResource(this.getSoundLocation());
		}
		catch(IOException e)
		{
			res = null;
		}
	}
	
	/**
	 * Updates the JList with a new model.
	 */
	@Override
	public void update()
	{
		if(this.entity.isDead)
		{
			this.done();
		}
		else
		{
			this.xPosF = (float)this.entity.posX;
			this.yPosF = (float)this.entity.posY;
			this.zPosF = (float)this.entity.posZ;
			if(this.entity instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer)this.entity;
				ItemStack stack = this.getStack(player);
				if(stack == null)
				{
					this.done();
				}
			}
			this.entity.playSound("random.click", 0.02F + this.entity.worldObj.rand.nextFloat() * 0.02F, 0.9F + this.entity.worldObj.rand.nextFloat() * 1.8F);
		}
	}
	
	public ItemStack getStack(EntityPlayer player)
	{
		if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemWeaponSkyHook)
		{
			return player.getHeldItem();
		}
		return null;
	}
	
	@Override
	protected void setDone()
	{
		super.setDone();
		AudioHandler.stopSound(this.entity, RefMod.MODID + ":" + "item.weapon.skyhook.motor.rough");
		AudioHandler.stopSound(this.entity, RefMod.MODID + ":" + "item.weapon.skyhook.motor.start");
		AudioHandler.stopSound(this.entity, RefMod.MODID + ":" + "item.weapon.skyhook.motor.rough.start");
		AudioHandler.createMovingEntitySound(this.entity, RefMod.MODID + ":" + "item.weapon.skyhook.motor.end", 1.0F + (this.type ? 0.4F : 0), 1.0F, false);
	}
}
