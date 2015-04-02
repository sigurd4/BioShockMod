package com.sigurd4.bioshock.config;

import java.util.ArrayList;

import net.minecraftforge.common.config.Configuration;

import com.google.common.collect.Lists;
import com.sigurd4.bioshock.Stuff;

public class Config
{
	public static Configuration config = null;
	public static final ArrayList<ConfigEntry> entries = Lists.newArrayList();
	
	public enum ConfigEntryCategory
	{
		GENERAL
		{
			@Override
			public String toString()
			{
				return Configuration.CATEGORY_GENERAL;
			}
		};
		
		@Override
		public String toString()
		{
			return Stuff.Strings.UnderscoresToCamelSpaces(super.toString());
		}
	}
}
