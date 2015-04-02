package com.sigurd4.bioshock.config;

import net.minecraftforge.common.config.Configuration;

import com.sigurd4.bioshock.config.Config.ConfigEntryCategory;

public abstract class ConfigEntry<T>
{
	public final T defaultValue;
	public final String name;
	public final ConfigEntryCategory category;
	public final String description;
	
	protected T value;
	
	public ConfigEntry(T defaultValue, String name, ConfigEntryCategory category, String description)
	{
		this.defaultValue = this.value = defaultValue;
		this.name = name;
		this.category = category;
		this.description = description;
		Config.entries.add(this);
	}
	
	public final void set(Configuration config)
	{
		T newValue = this.load(config);
		if(newValue != null)
		{
			this.value = newValue;
		}
	}
	
	public final T get()
	{
		return this.value != null ? this.valid(this.value) : this.valid(this.defaultValue);
	}
	
	protected abstract T load(Configuration config);
	
	protected abstract T valid(T value);
}