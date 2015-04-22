package com.sigurd4.bioshock.element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumParticleTypes;

import com.sigurd4.bioshock.M;
import com.sigurd4.bioshock.Stuff;
import com.sigurd4.bioshock.entity.projectile.EntityBullet;
import com.sigurd4.bioshock.particles.ParticleHandler.EnumParticleTypes2;
import com.sigurd4.bioshock.plasmids.IDamageSourcePlasmid.DamageSourcePlasmid;
import com.sigurd4.bioshock.plasmids.IDamageSourcePlasmid.EntityDamageSourcePlasmid;
import com.sigurd4.bioshock.plasmids.PlasmidWinterBlast;

public enum Element
{
	FIRE(
			DamageSource.inFire.damageType,
			DamageSource.onFire.damageType,
			DamageSource.lava.damageType)
	{
		@Override
		public boolean isElement(DamageSource ds)
		{
			return super.isElement(ds) || ds.isFireDamage();
		}
		
		@Override
		public void onUpdate(EntityLivingBase entity)
		{
			if(getEntityElement(entity, WATER) > 70)
			{
				setEntityElement(entity, FIRE, 0);
			}
			else if(entity.isBurning() && entity.getActivePotionEffect(Potion.fireResistance) == null)
			{
				addToEntityElement(entity, FIRE, entity.worldObj.rand.nextInt(10));
			}
			else if(getEntityElement(entity, FIRE) > 400 && getEntityElement(entity, WATER) < 10)
			{
				//entity.setFire(3);
			}
			else
			{
				addToEntityElement(entity, FIRE, -entity.worldObj.rand.nextInt(20));
			}
			
			if(getEntityElement(entity, FIRE) > 50 && entity.worldObj.isRemote)
			{
				if(entity != net.minecraft.client.Minecraft.getMinecraft().thePlayer || net.minecraft.client.Minecraft.getMinecraft().gameSettings.thirdPersonView > 0)
				{
					for(int i = 0; i < (int)((float)getEntityElement(entity, FIRE) / 200); ++i)
					{
						entity.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, true, entity.posX + Stuff.Randomization.r(entity.width / 2), entity.posY + entity.height / (entity instanceof EntityPlayer ? 2 : 1) * entity.worldObj.rand.nextDouble(), entity.posZ + Stuff.Randomization.r(entity.width / 2), 0, 0, 0);
					}
				}
			}
		}
	},
	WATER(DamageSource.drown.damageType)
	{
		@Override
		public void onUpdate(EntityLivingBase entity)
		{
			if(entity.isInWater() || entity.isInsideOfMaterial(Material.water))
			{
				addToEntityElement(entity, WATER, 600);
			}
			else if(entity.worldObj.isRaining() && entity.worldObj.canLightningStrike(new BlockPos((int)Math.floor(entity.posX), (int)Math.floor(entity.posY - (entity instanceof EntityPlayer ? 1 : 0)), (int)Math.floor(entity.posZ))) || entity.isWet())
			{
				addToEntityElement(entity, WATER, 5 + entity.worldObj.rand.nextInt(30));
			}
			if(getEntityElement(entity, WATER) > 200)
			{
				entity.extinguish();
			}
			
			if(getEntityElement(entity, WATER) > 50 && entity.worldObj.isRemote)
			{
				if(entity != net.minecraft.client.Minecraft.getMinecraft().thePlayer || net.minecraft.client.Minecraft.getMinecraft().gameSettings.thirdPersonView > 0)
				{
					for(int i = 0; i < (int)((float)getEntityElement(entity, WATER) / 100); ++i)
					{
						M.proxy.particle(EnumParticleTypes2.DRIP2, entity.worldObj, true, entity.posX + Stuff.Randomization.r(entity.width / 2), entity.posY + entity.height / (entity instanceof EntityPlayer ? 2 : 1) * entity.worldObj.rand.nextDouble(), entity.posZ + Stuff.Randomization.r(entity.width / 2), entity.motionX, entity.motionY, entity.motionZ);
					}
				}
			}
			return;
		}
	},
	ICE()
	{
		@Override
		public void onUpdate(EntityLivingBase entity)
		{
			if(Element.getEntityElement(entity, Element.ICE) > 30 && entity.onGround)
			{
				float f = 0.9F;
				f /= (float)Element.getEntityElement(entity, Element.ICE) / 70;
				if(f < 1)
				{
					entity.motionX *= f;
					entity.motionY *= f;
					entity.motionZ *= f;
				}
			}
			if(Element.getEntityElement(entity, Element.ICE) > 200 && entity.worldObj.rand.nextFloat() * (1 + (Element.getEntityElement(entity, Element.ICE) - 1) / 10) > Element.getEntityElement(entity, Element.WATER) / 500 && Element.getEntityElement(entity, Element.WATER) > 400)
			{
				PlasmidWinterBlast.freeze(entity);
			}
		}
	},
	ELECTRICITY()
	{
		@Override
		public void onUpdate(EntityLivingBase entity)
		{
			if(getEntityElement(entity, WATER) > 200 && getEntityElement(entity, ELECTRICITY) > 100)
			{
				addToEntityElement(entity, ELECTRICITY, 300 + entity.worldObj.rand.nextInt(100));
				addToEntityElement(entity, WATER, -20);
			}
			else
			{
				
			}
			if(getEntityElement(entity, ELECTRICITY) > 200)
			{
				float damage = entity.worldObj.rand.nextFloat() * getEntityElement(entity, ELECTRICITY) / 600 + entity.worldObj.rand.nextFloat() / 3;
				if(damage < 0.2)
				{
					damage = 0;
				}
				if(!entity.worldObj.isRemote && entity.attackEntityFrom(Element.electricity(null), damage))
				{
					int a = entity.worldObj.rand.nextInt(60 + 1);
					entity.addPotionEffect(new PotionEffect(Potion.weakness.id, a, 0, true, false));
					//entity.addPotionEffect(new PotionEffect(Potion.blindness.id, a-(int)(entity.worldObj.rand.nextFloat()*a/1.8), 0, true));
					entity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, a, 3, true, false));
				}
			}
			if(getEntityElement(entity, ELECTRICITY) > 200 && entity.worldObj.isRemote)
			{
				if(entity != net.minecraft.client.Minecraft.getMinecraft().thePlayer || net.minecraft.client.Minecraft.getMinecraft().gameSettings.thirdPersonView > 0)
				{
					for(int i = 0; i < (int)((float)getEntityElement(entity, ELECTRICITY) / 50); ++i)
					{
						M.proxy.particle(EnumParticleTypes2.REDSTONE2, entity.worldObj, true, entity.posX + Stuff.Randomization.r(entity.width / 2), entity.posY + entity.height / (entity instanceof EntityPlayer ? 2 : 1) * entity.worldObj.rand.nextDouble() - 0.05, entity.posZ + Stuff.Randomization.r(entity.width / 2), 0, 0, 0, new int[]{40, 90, 100});
					}
				}
			}
			if(getEntityElement(entity, ELECTRICITY) > 100)
			{
				entity.prevRotationYaw = entity.rotationYaw;
				entity.prevRotationPitch = entity.rotationPitch;
				while(true)
				{
					double w = getEntityElement(entity, ELECTRICITY) / 80;
					double x = Stuff.Randomization.r(w);
					double y = Stuff.Randomization.r(w);
					double y2 = Stuff.Randomization.r(w);
					if(Math.sqrt(x * x + y * y) <= w && Math.sqrt(x * x + y2 * y2) <= w)
					{
						entity.rotationPitch += x;
						entity.rotationYaw += y;
						entity.rotationYawHead += y2;
						break;
					}
				}
			}
			if(getEntityElement(entity, ELECTRICITY) > 400)
			{
				setEntityElement(entity, ELECTRICITY, (int)(400 + ((float)getEntityElement(entity, ELECTRICITY) - 400) / (entity.worldObj.rand.nextDouble() / 10 + 1)));
			}
		}
	},
	ANGER()
	{
		@Override
		public void onUpdate(EntityLivingBase entity)
		{
			if(getEntityElement(entity, ANGER) > 100)
			{
				entity.prevRotationYaw = entity.rotationYaw;
				entity.prevRotationPitch = entity.rotationPitch;
				while(true)
				{
					double w = getEntityElement(entity, ANGER) / 320;
					double x = Stuff.Randomization.r(w);
					double y = Stuff.Randomization.r(w);
					double y2 = Stuff.Randomization.r(w);
					if(Math.sqrt(x * x + y * y) <= w && Math.sqrt(x * x + y2 * y2) <= w)
					{
						entity.rotationPitch += x;
						entity.rotationYaw += y;
						entity.rotationYawHead += y2;
						break;
					}
				}
			}
		}
	};
	
	public static final HashMap<DamageSource, List<Element>> elements = new HashMap<DamageSource, List<Element>>();
	public static final HashMap<EntityLivingBase, HashMap<Element, Integer>> entityElements = new HashMap<EntityLivingBase, HashMap<Element, Integer>>();
	
	public final String[] dts;
	
	private Element(String... dts)
	{
		this.dts = dts;
	}
	
	public boolean isElement(DamageSource ds)
	{
		boolean b = false;
		for(int i = 0; i < this.dts.length; ++i)
		{
			if(this.dts[i] == ds.getDamageType() || this.dts[i] == ds.damageType)
			{
				b = true;
			}
		}
		return b;
	}
	
	public void onUpdate(EntityLivingBase entity)
	{
	}
	
	public static List<Element> getElements(DamageSource ds)
	{
		ArrayList<Element> es2 = new ArrayList<Element>();
		Element[] es = Element.values();
		for(int i = 0; i < es.length; ++i)
		{
			if(es[i].isElement(ds))
			{
				es2.add(es[i]);
			}
		}
		if(elements.containsKey(ds))
		{
			for(int i = 0; i < elements.get(ds).size(); ++i)
			{
				if(!es2.contains(elements.get(ds).get(i)))
				{
					es2.add(elements.get(ds).get(i));
				}
			}
		}
		return es2;
	}
	
	public static DamageSource addElements(DamageSource ds, Element... es)
	{
		if(!elements.containsKey(ds))
		{
			elements.put(ds, new ArrayList<Element>());
		}
		for(int i = 0; i < es.length; ++i)
		{
			elements.get(ds).add(es[i]);
		}
		return ds;
	}
	
	public static DamageSource winterBlast(Entity attacker)
	{
		DamageSource ds = new DamageSourcePlasmid("winterBlast", M.items.plasmids.injectable.winter_blast.plasmid);
		if(attacker != null)
		{
			ds = new EntityDamageSourcePlasmid("winterBlast", attacker, M.items.plasmids.injectable.winter_blast.plasmid);
		}
		addElements(ds, ICE, ICE);
		return ds;
	}
	
	public static DamageSource incinerate(Entity attacker)
	{
		DamageSource ds = new DamageSourcePlasmid("incinerate", M.items.plasmids.injectable.incinerate.plasmid);
		if(attacker != null)
		{
			ds = new EntityDamageSourcePlasmid("incinerate", attacker, M.items.plasmids.injectable.incinerate.plasmid);
		}
		addElements(ds, FIRE, FIRE, FIRE, FIRE);
		ds.setFireDamage();
		return ds;
	}
	
	public static DamageSource devilsKiss(Entity attacker)
	{
		DamageSource ds = new DamageSourcePlasmid("devilsKiss", M.items.plasmids.drinkable.devils_kiss.plasmid);
		if(attacker != null)
		{
			ds = new EntityDamageSourcePlasmid("devilsKiss", attacker, M.items.plasmids.drinkable.devils_kiss.plasmid);
		}
		addElements(ds, FIRE, FIRE, FIRE, FIRE);
		ds.setFireDamage();
		return ds;
	}
	
	public static DamageSource murderOfCrows(Entity attacker)
	{
		DamageSource ds = new DamageSourcePlasmid("murderOfCrows", M.items.plasmids.drinkable.murder_of_crows.plasmid);
		if(attacker != null)
		{
			ds = new EntityDamageSourcePlasmid("murderOfCrows", attacker, M.items.plasmids.drinkable.murder_of_crows.plasmid);
		}
		return ds;
	}
	
	public static DamageSource returnToSender(Entity attacker)
	{
		DamageSource ds = new DamageSourcePlasmid("returnToSender", M.items.plasmids.drinkable.return_to_sender.plasmid);
		if(attacker != null)
		{
			ds = new EntityDamageSourcePlasmid("returnToSender", attacker, M.items.plasmids.drinkable.return_to_sender.plasmid);
		}
		return ds;
	}
	
	public static DamageSource electricity(Entity attacker)
	{
		DamageSource ds = new DamageSource("electricity");
		if(attacker != null)
		{
			ds = new EntityDamageSource("electricity", attacker);
		}
		addElements(ds, ELECTRICITY, ELECTRICITY, ELECTRICITY, ELECTRICITY, ELECTRICITY);
		return ds;
	}
	
	public static DamageSource electroBolt(Entity attacker)
	{
		DamageSource ds = new DamageSourcePlasmid("electroBolt", M.items.plasmids.injectable.electro_bolt.plasmid);
		if(attacker != null)
		{
			ds = new EntityDamageSourcePlasmid("electroBolt", attacker, M.items.plasmids.injectable.electro_bolt.plasmid);
		}
		addElements(ds, ELECTRICITY, ELECTRICITY, ELECTRICITY, ELECTRICITY, ELECTRICITY);
		return ds;
	}
	
	public static DamageSource shockJockey(Entity attacker)
	{
		DamageSource ds = new DamageSourcePlasmid("shockJockey", M.items.plasmids.drinkable.shock_jockey.plasmid);
		if(attacker != null)
		{
			ds = new EntityDamageSourcePlasmid("shockJockey", attacker, M.items.plasmids.drinkable.shock_jockey.plasmid);
		}
		addElements(ds, ELECTRICITY, ELECTRICITY, ELECTRICITY, ELECTRICITY, ELECTRICITY);
		return ds;
	}
	
	public static DamageSource trapBolt(Entity attacker)
	{
		DamageSource ds = electricity(attacker);
		ds.damageType = "trapBolt";
		return ds;
	}
	
	public static DamageSource cycloneTrap(Entity attacker)
	{
		DamageSource ds = new DamageSourcePlasmid("cycloneTrap", M.items.plasmids.injectable.cyclone_trap.plasmid);
		if(attacker != null)
		{
			ds = new EntityDamageSourcePlasmid("cycloneTrap", attacker, M.items.plasmids.injectable.cyclone_trap.plasmid);
		}
		return ds;
	}
	
	public static DamageSource undertow(Entity attacker)
	{
		DamageSource ds = new DamageSourcePlasmid("undertow", M.items.plasmids.drinkable.undertow.plasmid);
		if(attacker != null)
		{
			ds = new EntityDamageSourcePlasmid("undertow", attacker, M.items.plasmids.drinkable.undertow.plasmid);
		}
		addElements(ds, WATER, WATER, WATER, WATER, WATER, WATER, WATER);
		return ds;
	}
	
	public static EntityDamageSource bullet(EntityCrossbowBolt bolt)
	{
		if(bolt.shootingEntity != null)
		{
			return new EntityDamageSourceIndirect(bolt.damageName != null ? "bullet" : "bullet", bolt, bolt.shootingEntity);
		}
		else
		{
			return new EntityDamageSource(bolt.damageName != null ? "bullet" : "bullet", bolt);
		}
	}
	
	public static EntityDamageSource bullet(EntityBullet bullet)
	{
		if(bullet.getThrower() != null)
		{
			return new EntityDamageSourceIndirect(bullet.damageName != null ? "bullet" : "bullet", bullet, bullet.getThrower());
		}
		else
		{
			return new EntityDamageSource(bullet.damageName != null ? "bullet" : "bullet", bullet);
		}
	}
	
	public static int getEntityElement(EntityLivingBase entity, Element element)
	{
		if(entityElements.get(entity) != null && entityElements.get(entity).get(element) != null)
		{
			return Math.max(0, entityElements.get(entity).get(element));
		}
		return 0;
	}
	
	public static void setEntityElement(EntityLivingBase entity, Element element, int value)
	{
		setEntityElement(entity, element, value, true);
	}
	
	public static void setEntityElement(EntityLivingBase entity, Element element, int value, boolean flag)
	{
		if(entityElements == null)
		{
			return;
		}
		if(entityElements.get(entity) == null)
		{
			entityElements.put(entity, new HashMap<Element, Integer>());
		}
		if(entityElements.get(entity).get(element) == null)
		{
			entityElements.get(entity).put(element, 0);
		}
		if(entityElements.get(entity).get(element) < value || flag)
		{
			entityElements.get(entity).put(element, value);
		}
		if(element == FIRE && value > 200 && getEntityElement(entity, WATER) <= 0 && entity.getActivePotionEffect(Potion.fireResistance) == null)
		{
			entity.setFire((int)((float)getEntityElement(entity, FIRE) / 80));
		}
		capEntityElement(entity, element);
	}
	
	public static void addToEntityElement(EntityLivingBase entity, Element element, int amount)
	{
		if(entityElements == null)
		{
			return;
		}
		if(entityElements.get(entity) == null)
		{
			entityElements.put(entity, new HashMap<Element, Integer>());
		}
		if(entityElements.get(entity).get(element) == null)
		{
			entityElements.get(entity).put(element, 0);
		}
		entityElements.get(entity).put(element, entityElements.get(entity).get(element) + amount);
		if(element == FIRE && amount > 50 && getEntityElement(entity, FIRE) > 200 && getEntityElement(entity, WATER) <= 0 && entity.getActivePotionEffect(Potion.fireResistance) == null)
		{
			entity.setFire((int)((float)getEntityElement(entity, FIRE) / 80));
		}
		capEntityElement(entity, element);
	}
	
	public static void addTowardsEntityElement(EntityLivingBase entity, Element element, int value, int amount)
	{
		if(getEntityElement(entity, element) < value)
		{
			addToEntityElement(entity, element, amount);
			if(getEntityElement(entity, element) > value)
			{
				setEntityElement(entity, element, value);
			}
		}
		capEntityElement(entity, element);
	}
	
	public static void capEntityElement(EntityLivingBase entity, Element element)
	{
		int max = 600;
		if(getEntityElement(entity, element) > max)
		{
			setEntityElement(entity, element, max);
		}
		if(entityElements != null)
		{
			if(entityElements.get(entity) != null && entityElements.get(entity).get(element) != null && entityElements.get(entity).get(element) <= 0)
			{
				entityElements.get(entity).remove(element);
			}
			if(entityElements.get(entity) != null && entityElements.get(entity).size() <= 0)
			{
				entityElements.remove(entity);
			}
		}
	}
	
	public static void update(EntityLivingBase entity)
	{
		if(entity == null || entityElements == null)
		{
			return;
		}
		Element[] es = Element.values();
		for(int i = 0; i < es.length; ++i)
		{
			if(es[i] != null)
			{
				try
				{
					if(entityElements.get(entity) != null)
					{
						addToEntityElement(entity, es[i], -1);
					}
					es[i].onUpdate(entity);
					if(entityElements.get(entity) != null)
					{
						capEntityElement(entity, es[i]);
					}
				}
				catch(Exception e)
				{
					//e.printStackTrace();
				}
			}
		}
		boolean b = false;
	}
}
