package com.sigurd4.bioshock.plasmids;

import com.sigurd4.bioshock.extendedentity.ExtendedPlayer;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;

public interface IDamageSourcePlasmid
{
	public abstract boolean isValid();
	public abstract Plasmid getPlasmid();
	
	public static class EntityDamageSourcePlasmid extends EntityDamageSource
	{
		public Plasmid plasmid;

		public EntityDamageSourcePlasmid(String damageType, Entity entity, Plasmid plasmid)
		{
			super(damageType, entity);
			this.plasmid = plasmid;
		}

		public boolean isValid()
		{
			if(this.getEntity() instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer)this.getEntity();
				ExtendedPlayer props = ExtendedPlayer.get(player);
				return props.hasPlasmid(plasmid);
			}
			return true;
		}

		public Plasmid getPlasmid()
		{
			return plasmid;
		}
	}
	
	public static class DamageSourcePlasmid extends DamageSource
	{
		public Plasmid plasmid;

		public DamageSourcePlasmid(String damageType, Plasmid plasmid)
		{
			super(damageType);
			this.plasmid = plasmid;
		}

		public boolean isValid()
		{
			return true;
		}

		public Plasmid getPlasmid()
		{
			return plasmid;
		}
	}
}
