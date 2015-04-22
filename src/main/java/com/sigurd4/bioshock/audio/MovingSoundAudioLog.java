package com.sigurd4.bioshock.audio;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import com.sigurd4.bioshock.item.ItemAudioLog;
import com.sigurd4.bioshock.reference.RefMod;

public class MovingSoundAudioLog extends MovingSoundPublic
{
	public final boolean type;
	public String name;
	public String owner;
	
	public MovingSoundAudioLog(Entity entity, String name, String owner, boolean type)
	{
		super(new ResourceLocation(RefMod.MODID + ":" + "audiologs." + owner + "." + name));
		AudioHandler.createMovingEntitySound(entity, RefMod.MODID + ":" + "item." + (type ? "audiodiary" : "voxophone") + ".start", 1, 1, false);
		AudioHandler.createMovingEntitySound(entity, RefMod.MODID + ":" + "item." + (type ? "audiodiary" : "voxophone") + ".playing", 1, 1, true);
		this.entity = entity;
		this.volume = 1;
		this.repeat = false;
		this.pitch = 1;
		this.type = type;
		this.name = name;
		this.owner = owner;
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
			done();
		}
		else
		{
			this.xPosF = (float)this.entity.posX;
			this.yPosF = (float)this.entity.posY;
			this.zPosF = (float)this.entity.posZ;
			if(entity instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer)entity;
				ItemStack stack = getStack(player);
				if(stack == null)
				{
					done();
				}
			}
		}
	}
	
	public ItemStack getStack(EntityPlayer player)
	{
		for(int i = 0; i < player.inventory.getSizeInventory(); ++i)
		{
			ItemStack stack2 = player.inventory.getStackInSlot(i);
			if(stack2 != null)
			{
				if(stack2.getTagCompound() != null)
				{
					if(ItemAudioLog.LOG.get(stack2).equals(name) && ItemAudioLog.OWNER.get(stack2).equals(owner) && ItemAudioLog.ISRUNNING.get(stack2) > 0)
					{
						return stack2;
					}
				}
			}
		}
		return null;
	}
	
	@Override
	protected void setDone()
	{
		super.setDone();
		AudioHandler.stopSound(entity, RefMod.MODID + ":" + "item." + (type ? "audiodiary" : "voxophone") + ".start");
		AudioHandler.stopSound(entity, RefMod.MODID + ":" + "item." + (type ? "audiodiary" : "voxophone") + ".playing");
		AudioHandler.createMovingEntitySound(entity, RefMod.MODID + ":" + "item." + (type ? "audiodiary" : "voxophone") + ".end", 1, 1, false);
	}
}
