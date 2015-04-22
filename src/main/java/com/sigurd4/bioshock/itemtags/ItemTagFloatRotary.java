package com.sigurd4.bioshock.itemtags;


public class ItemTagFloatRotary extends ItemTagFloat
{
	public final float maxRot;
	
	public ItemTagFloatRotary(String key, Float defaultValue, Float max, boolean local)
	{
		super(key, defaultValue, -Float.MAX_VALUE, Float.MAX_VALUE, local);
		this.maxRot = max;
	}
	
	@Override
	protected Float isValid(Float original)
	{
		if(original > this.max)
		{
			return this.max;
		}
		if(original < this.min)
		{
			return this.min;
		}
		
		while(original > this.maxRot / 2)
		{
			original -= this.maxRot;
		}
		while(original < -this.maxRot / 2)
		{
			original += this.maxRot;
		}
		return original;
	}
}