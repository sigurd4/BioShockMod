package com.sigurd4.bioshock.item;

import com.sigurd4.bioshock.passives.Passive;

public class ItemPassiveTonic extends ItemPassive
{
	public final ItemPassiveTonicSyringe syringe;
	
	public ItemPassiveTonic(Passive passive, String... description)
	{
		super(passive, description);
		String unlocalizedName = "tonic." + passive.name;
		this.setUnlocalizedName(unlocalizedName);
		this.setContainerItem(this);
		//this.setTextureName(RefMod.MODID + ":" + "bottle_tonic_" + passive.type.toString().toLowerCase());
		this.syringe = new ItemPassiveTonicSyringe(passive, unlocalizedName);
	}
	
	@Override
	public String getId()
	{
		return "tonic_" + this.p.type.toString().toLowerCase() + "_" + this.p.getID();
	}
}
