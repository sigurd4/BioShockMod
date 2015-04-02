package com.sigurd4.bioshock.config;

import net.minecraftforge.common.config.Configuration;

import com.sigurd4.bioshock.config.Config.ConfigEntryCategory;

public class ConfigEntryBoolean extends ConfigEntry<Boolean>
{
	public ConfigEntryBoolean(boolean defaultValue, String name, ConfigEntryCategory category, String description)
	{
		super(defaultValue, name, category, description);
	}
	
	@Override
	protected Boolean load(Configuration config)
	{
		return config.getBoolean(this.name, this.category.toString(), this.defaultValue, this.description);
	}
	
	@Override
	protected Boolean valid(Boolean value)
	{
		return value;
	}
}