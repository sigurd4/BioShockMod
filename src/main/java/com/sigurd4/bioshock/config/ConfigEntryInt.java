package com.sigurd4.bioshock.config;

import net.minecraftforge.common.config.Configuration;

import com.sigurd4.bioshock.config.Config.ConfigEntryCategory;

public class ConfigEntryInt extends ConfigEntry<Integer>
{
	public final int minValue;
	public final int maxValue;
	
	public ConfigEntryInt(int defaultValue, int minValue, int maxValue, String name, ConfigEntryCategory category, String description)
	{
		super(defaultValue, name, category, description);
		this.minValue = minValue;
		this.maxValue = maxValue;
	}
	
	@Override
	protected Integer load(Configuration config)
	{
		return config.getInt(this.name, this.category.toString(), this.defaultValue, this.minValue, this.maxValue, this.description);
	}
	
	@Override
	protected Integer valid(Integer value)
	{
		if(value > this.maxValue)
		{
			value = this.maxValue;
		}
		if(value < this.minValue)
		{
			value = this.minValue;
		}
		return value;
	}
}