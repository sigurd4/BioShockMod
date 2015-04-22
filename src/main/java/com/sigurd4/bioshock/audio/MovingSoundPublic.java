package com.sigurd4.bioshock.audio;

import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public abstract class MovingSoundPublic extends MovingSound
{
	private boolean hasBeenSetToDone = false;
	
	protected MovingSoundPublic(ResourceLocation resource)
	{
		super(resource);
	}
	
	public Entity entity;
	
	public void setVolume(float volume)
	{
		this.volume = volume;
	}
	
	public void setPitch(float pitch)
	{
		this.pitch = pitch;
	}
	
	public void setRepeat(boolean repeat)
	{
		this.repeat = repeat;
	}
	
	public final void done()
	{
		if(!hasBeenSetToDone)
		{
			setDone();
			hasBeenSetToDone = true;
		}
	}
	
	protected void setDone()
	{
		this.donePlaying = true;
	}
}
