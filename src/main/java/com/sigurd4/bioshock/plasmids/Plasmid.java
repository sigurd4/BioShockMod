package com.sigurd4.bioshock.plasmids;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import com.google.common.collect.Lists;
import com.sigurd4.bioshock.entity.projectile.EntityPlasmidProjectile;

public abstract class Plasmid
{
	public static final HashMap<String, Plasmid> plasmids = new HashMap<String, Plasmid>();
	public static Random rand = new Random();
	
	public final String id;
	public final String[] description;
	public final boolean isFinished;
	public final int cost1;
	public final int cost2;
	public final boolean type;
	
	public Plasmid(String id, int cost1, int cost2, boolean type, String... description)
	{
		this.id = id;
		this.description = description;
		this.isFinished = !(this instanceof PlasmidUnfinished);
		this.cost1 = cost1;
		this.cost2 = cost2;
		this.type = type;
	}
	
	public abstract ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player);
	
	public final void onUpdate2(ItemStack stack, World world, Entity entity, int slot, boolean isSelected)
	{
		rand = world.rand;
	}
	
	public abstract void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected);
	
	public abstract ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player);
	
	public static Plasmid getPlasmid(String id)
	{
		for(Plasmid plasmid : plasmids.values())
		{
			if(plasmid.id.equals(id))
			{
				return plasmid;
			}
		}
		return null;
	}
	
	public static ArrayList<Plasmid> getPlasmids()
	{
		ArrayList<Plasmid> a = Lists.newArrayList();
		for(Plasmid plasmid : plasmids.values())
		{
			a.add(plasmid);
		}
		return a;
	}
	
	public static int getIndex(Plasmid p)
	{
		ArrayList a = getPlasmids();
		for(int i = 0; i < a.size(); ++i)
		{
			if(p == a.get(i))
			{
				return 0;
			}
		}
		return -1;
	}
	
	public static <Plsmd extends Plasmid & IPlasmidProjectile> DamageSource getDamageSource(EntityLivingBase thrower, Plasmid plasmid)
	{
		return ((Plsmd)plasmid).getDamageSource(new EntityPlasmidProjectile(thrower.worldObj, thrower, plasmid));
	}
}
