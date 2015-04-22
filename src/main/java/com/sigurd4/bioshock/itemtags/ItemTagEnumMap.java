package com.sigurd4.bioshock.itemtags;

public abstract class ItemTagEnumMap<W, T extends ItemTagBase<W, ?>, K extends Enum> extends ItemTagMap<W, T, K>
{
	public ItemTagEnumMap(String key, T defaultEntry, boolean local)
	{
		super(key, defaultEntry, local);
	}
	
	@Override
	protected String getKey(K key)
	{
		return key.name().toLowerCase();
	}
}