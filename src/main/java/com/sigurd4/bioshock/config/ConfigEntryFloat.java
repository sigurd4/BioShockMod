package com.sigurd4.bioshock.config;

import net.minecraftforge.common.config.Configuration;

import com.sigurd4.bioshock.config.Config.ConfigEntryCategory;

public class ConfigEntryFloat extends ConfigEntry<Float>
{
	public final float minValue;
	public final float maxValue;
	
	public ConfigEntryFloat(float defaultValue, float minValue, float maxValue, String name, ConfigEntryCategory category, String description)
	{
		super(defaultValue, name, category, description);
		this.minValue = minValue;
		this.maxValue = maxValue;
	}
	
	@Override
	protected Float load(Configuration config)
	{
		return config.getFloat(this.name, this.category.toString(), this.defaultValue, this.minValue, this.maxValue, this.description);
	}
	
	@Override
	protected Float valid(Float value)
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