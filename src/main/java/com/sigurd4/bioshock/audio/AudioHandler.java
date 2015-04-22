package com.sigurd4.bioshock.audio;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AudioHandler
{
	@SideOnly(Side.CLIENT)
	public static HashMap<Entity, ArrayList<ISound>> sounds = new HashMap<Entity, ArrayList<ISound>>();
	@SideOnly(Side.CLIENT)
	public static ArrayList<ISound> soundsToStop = new ArrayList<ISound>();
	@SideOnly(Side.CLIENT)
	public static ArrayList<ISound> soundsToPlay = new ArrayList<ISound>();
	
	public static ISound getSound(Entity entity, String string)
	{
		if(entity.worldObj.isRemote)
			return getSound_(entity, string);
		return null;
	}
	
	@SideOnly(Side.CLIENT)
	private static ISound getSound_(Entity entity, String string)
	{
		if(sounds.get(entity) != null)
		{
			for(int i = 0; i < sounds.get(entity).size(); ++i)
			{
				if(sounds.get(entity).get(i).getSoundLocation().toString().equals(string))
				{
					return sounds.get(entity).get(i);
				}
			}
		}
		return null;
	}
	
	public static void createMovingEntitySound(Entity entity, String string, float volume, float pitch, boolean repeat)
	{
		if(entity.worldObj.isRemote)
			createMovingEntitySound_(entity, string, volume, pitch, repeat);
	}
	
	@SideOnly(Side.CLIENT)
	private static void createMovingEntitySound_(Entity entity, String string, float volume, float pitch, boolean repeat)
	{
		ISound sound = new MovingSoundEntityGeneric(entity, string, volume, pitch, repeat);
		if(sounds.get(entity) == null)
		{
			sounds.put(entity, new ArrayList<ISound>());
		}
		sounds.get(entity).add(sound);
		soundsToPlay.add(sound);
	}
	
	public static void stopSound(Entity entity, String string)
	{
		if(entity.worldObj.isRemote)
			stopSound_(entity, string);
	}
	
	@SideOnly(Side.CLIENT)
	private static void stopSound_(Entity entity, String string)
	{
		if(sounds != null && sounds.get(entity) != null)
		{
			for(int i = 0; i < sounds.get(entity).size(); ++i)
			{
				if(sounds.get(entity).get(i).getSoundLocation().toString().equals(string))
				{
					ISound sound = sounds.get(entity).get(i);
					soundsToStop.add(sound);
				}
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public static void onUpdate()
	{
		for(int i = 0; i < soundsToStop.size(); ++i)
		{
			ISound sound = soundsToStop.get(i);
			if(sound != null)
				if(sound instanceof MovingSoundPublic)
				{
					((MovingSoundPublic)sound).done();
				}
				else
				{
					Minecraft.getMinecraft().getSoundHandler().stopSound(sound);
				}
		}
		soundsToStop.clear();
		for(int i = 0; i < soundsToPlay.size(); ++i)
		{
			ISound sound = soundsToPlay.get(i);
			if(sound != null)
				Minecraft.getMinecraft().getSoundHandler().playSound(sound);
		}
		soundsToPlay.clear();
		
		for(int i = 0; i < sounds.keySet().size(); ++i)
		{
			ArrayList array = sounds.get(sounds.keySet().toArray()[i]);
			for(int i2 = 0; i2 < array.size(); ++i2)
			{
				if(array.get(i2) instanceof MovingSoundPublic)
				{
					MovingSoundPublic sound = (MovingSoundPublic)array.get(i2);
					if(sound.isDonePlaying())
					{
						sound.done();
						sounds.get(sound.entity).remove(i2);
						if(sounds.get(sound.entity).size() <= 0)
						{
							sounds.remove(sound.entity);
						}
					}
				}
			}
		}
	}
}
