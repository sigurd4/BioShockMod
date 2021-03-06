package com.sigurd4.bioshock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumFacing.AxisDirection;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkProviderSettings;

import com.google.common.collect.Lists;
import com.sigurd4.bioshock.extendedentity.ExtendedPlayer;
import com.sigurd4.bioshock.inventory.IContainerAddPlayerSlots;

public final class Stuff
{
	
	/** Random stuff **/
	// - sigurd4
	public static final class Randomization
	{
		public final static Random rand = new Random();
		public final static Random randSeed = new Random();
		
		public static double r(double i)
		{
			return r(i, rand);
		}
		
		public static double r(double i, Random rand)
		{
			return rand.nextDouble() * i * 2 - i;
		}
		
		public static float r(float i)
		{
			return (float)r(i, rand);
		}
		
		public static double rG(double i)
		{
			return rG(i, rand);
		}
		
		public static double rG(double i, Random rand)
		{
			return rand.nextGaussian() * i * 2 - i;
		}
		
		public static <T> T getRandom(List<T> es)
		{
			if(es.size() > 0)
			{
				return es.get(es.size() > 1 ? rand.nextInt(es.size() - 1) : 0);
			}
			return null;
		}
		
		public static <T> T getRandom(T[] es)
		{
			return getRandom(ArraysAndSuch.arrayToArrayList(es));
		}
		
		public static Random randSeed(long seed, long... seeds)
		{
			for(int i = 0; i < seeds.length; ++i)
			{
				seed += randSeed(seeds[i]).nextLong();
			}
			randSeed.setSeed(seed);
			return randSeed;
		}
	}
	
	/** Stuff with coordinates in a 3D room. */
	// - sigurd4
	public static final class Coordinates3D
	{
		public static Vec3 mix(ArrayList<Vec3> a)
		{
			Vec3 pos = new Vec3(0, 0, 0);
			for(int i = 0; i < a.size(); ++i)
			{
				pos = add(pos, a.get(i));
			}
			pos = divide(pos, a.size());
			return pos;
		}
		
		public static Vec3 stabilize(Vec3 pos, double w)
		{
			double d = w / distance(pos);
			return new Vec3(pos.xCoord * d, pos.yCoord * d, pos.zCoord * d);
		}
		
		public static double distance(Vec3 pos1, Vec3 pos2)
		{
			return distance(subtract(pos1, pos2));
		}
		
		public static Vec3 subtract(Vec3 pos1, Vec3 pos2)
		{
			return new Vec3(pos1.xCoord - pos2.xCoord, pos1.yCoord - pos2.yCoord, pos1.zCoord - pos2.zCoord);
		}
		
		public static Vec3 add(Vec3 pos1, Vec3 pos2)
		{
			return new Vec3(pos1.xCoord + pos2.xCoord, pos1.yCoord + pos2.yCoord, pos1.zCoord + pos2.zCoord);
		}
		
		public static Vec3 divide(Vec3 pos, double d)
		{
			return multiply(pos, 1 / d);
		}
		
		public static Vec3 multiply(Vec3 pos, double d)
		{
			return new Vec3(pos.xCoord * d, pos.yCoord * d, pos.zCoord * d);
		}
		
		public static double distance(Vec3 pos)
		{
			return Math.sqrt(pos.xCoord * pos.xCoord + pos.yCoord * pos.yCoord + pos.zCoord * pos.zCoord);
		}
		
		public static void throwThing(Entity source, Entity object, double f)
		{
			object.setLocationAndAngles(source.posX, source.posY + source.getEyeHeight(), source.posZ, source.rotationYaw, source.rotationPitch);
			object.posX -= MathHelper.cos(object.rotationYaw / 180.0F * (float)Math.PI) * 0.16F;
			object.posY -= 0.10000000149011612D;
			object.posZ -= MathHelper.sin(object.rotationYaw / 180.0F * (float)Math.PI) * 0.16F;
			object.setPosition(object.posX, object.posY, object.posZ);
			
			object.motionX = -MathHelper.sin(object.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(object.rotationPitch / 180.0F * (float)Math.PI) * f;
			object.motionZ = MathHelper.cos(object.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(object.rotationPitch / 180.0F * (float)Math.PI) * f;
			
			object.motionY = -MathHelper.sin(object.rotationPitch / 180.0F * (float)Math.PI) * f;
		}
		
		public static Vec3 velocity(Entity entity)
		{
			if(entity == null)
			{
				return new Vec3(0, 0, 0);
			}
			return new Vec3(entity.motionX, entity.motionY, entity.motionZ);
		}
		
		public static void velocity(Entity entity, Vec3 vec)
		{
			if(entity == null || vec == null)
			{
				return;
			}
			entity.motionX = vec.xCoord;
			entity.motionY = vec.yCoord;
			entity.motionZ = vec.zCoord;
		}
		
		public static void bounce(Entity entity, EnumFacing sideHit, double bouncyness)
		{
			velocity(entity, bounce(velocity(entity), sideHit, bouncyness));
		}
		
		public static Vec3 bounce(Vec3 m, EnumFacing sideHit, double bouncyness)
		{
			if(sideHit == null)
			{
				return m;
			}
			double xCoord = m.xCoord;
			double yCoord = m.yCoord;
			double zCoord = m.zCoord;
			Axis a = sideHit.getAxis();
			switch(a)
			{
			case X:
			{
				xCoord *= -bouncyness;
				break;
			}
			case Y:
			{
				yCoord *= -bouncyness;
				break;
			}
			case Z:
			{
				zCoord *= -bouncyness;
				break;
			}
			}
			return new Vec3(xCoord, yCoord, zCoord);
		}
		
		public static BlockPos getVecFromAxis(Axis axis, AxisDirection direction)
		{
			BlockPos pos = new BlockPos(0, 0, 0);
			
			switch(axis)
			{
			case X:
			{
				pos = new BlockPos(direction.getOffset(), 0, 0);
				break;
			}
			case Y:
			{
				pos = new BlockPos(0, direction.getOffset(), 0);
				break;
			}
			case Z:
			{
				pos = new BlockPos(0, 0, direction.getOffset());
				break;
			}
			}
			
			return pos;
		}
		
		public static Vec3 middle(BlockPos pos)
		{
			return new Vec3(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
		}
	}
	
	/** Compare entities. **/
	// - sigurd4
	public static class EntityComparison
	{
		public static boolean isEntitySmaller(Entity e1, Entity e2, float offset)
		{
			return isEntityBigger(e2, e1, offset);
		}
		
		public static boolean isEntityBigger(Entity e1, Entity e2, float offset)
		{
			if(e1 != null && e2 != null)
			{
				float s1 = e1.height;
				if(e1.width > s1)
				{
					s1 = e1.width;
				}
				float s2 = e2.height;
				if(e2.width > s2)
				{
					s2 = e2.width;
				}
				return s1 + offset > s2;
			}
			return false;
		}
	}
	
	/** Things with arrays and lists etc. **/
	// - sigurd4
	public static final class ArraysAndSuch
	{
		public static <T> ArrayList<T> hashMapToArrayList(HashMap<?, T> map)
		{
			ArrayList<T> a = new ArrayList<T>();
			Iterator<T> values = map.values().iterator();
			while(values.hasNext())
			{
				a.add(values.next());
			}
			return a;
		}
		
		public static <T> ArrayList<T> hashMapKeysToArrayList(HashMap<T, ?> map)
		{
			ArrayList<T> a = new ArrayList<T>();
			T[] keys = (T[])map.keySet().toArray();
			for(T key : keys)
			{
				a.add(key);
			}
			return a;
		}
		
		public static <T> boolean has(T[] a, T o)
		{
			return has(arrayToArrayList(a), o);
		}
		
		public static <T> boolean has(ArrayList<T> a, T o)
		{
			for(int i = 0; i < a.size(); ++i)
			{
				if(a.get(i) == o)
				{
					return true;
				}
			}
			return false;
		}
		
		public static Object[] arrayListToArray(ArrayList<Object> al)
		{
			return arrayListToArray2(al, new Object[al.size()]);
		}
		
		public static <T> T[] arrayListToArray2(ArrayList<T> al, T[] a)
		{
			if(al.size() == a.length)
			{
				for(int i = 0; i < al.size(); ++i)
				{
					a[i] = al.get(i);
				}
			}
			return a;
		}
		
		public static <T> ArrayList<T> arrayToArrayList(T[] a)
		{
			ArrayList<T> al = new ArrayList<T>();
			for(int i = 0; i < a.length; ++i)
			{
				al.add(a[i]);
			}
			return al;
		}
		
		public static <T> T[] mixArrays(T[] a1, T[] a2)
		{
			ArrayList<T> al = new ArrayList<T>();
			al.addAll(arrayToArrayList(a1));
			al.addAll(arrayToArrayList(a2));
			return (T[])al.toArray();
		}
		
		public static <T> T[] addToArray(T[] a, T o)
		{
			ArrayList<T> al = new ArrayList<T>();
			al.addAll(arrayToArrayList(a));
			al.add(o);
			return (T[])al.toArray();
		}
		
		public static <T> boolean removeFromArrayList(ArrayList<T> a, T o)
		{
			for(int i = 0; i < a.size(); ++i)
			{
				if(a.get(i) == o)
				{
					a.remove(i);
					return true;
				}
			}
			return false;
		}
		
		public static <T> ArrayList<T> allExtending(ArrayList a, Class<T> c)
		{
			ArrayList<T> at = new ArrayList<T>();
			for(int i = 0; i < a.size(); ++i)
			{
				if(c.isInstance(a.get(i)))
				{
					at.add((T)a.get(i));
				}
			}
			return at;
		}
	}
	
	/** Get all entities within the area **/
	// - sigurd4
	public static final class EntitiesInArea
	{
		public static HashMap<Entity, Vec3> hit = new HashMap<Entity, Vec3>();
		
		public static List<Entity> getEntitiesWithinEllipse(World w, Vec3 v, Vec3 size1)
		{
			return getEntitiesWithinEllipse(w, v, size1, size1);
		}
		
		public static List<Entity> getEntitiesWithinEllipse(World w, Vec3 v, Vec3 size1, Vec3 size2)
		{
			double r = MathWithMultiple.biggest(new double[]{size1.xCoord, size1.yCoord, size1.zCoord, size2.xCoord, size2.yCoord, size2.zCoord});
			List<Entity> es = getEntitiesWithinCube(w, v, r);
			for(int i = 0; i > es.size(); ++i)
			{
				Entity e2 = es.get(i);
				if(e2.getDistance(v.xCoord / (v.xCoord < e2.posX ? size1.xCoord : size2.xCoord), v.yCoord / (v.yCoord < e2.posY ? size1.yCoord : size2.yCoord), v.zCoord / (v.zCoord < e2.posZ ? size1.zCoord : size2.zCoord)) > 1)
				{
					es.remove(i);
					--i;
				}
			}
			return es;
		}
		
		public static List<Entity> getEntitiesWithinRadius(World w, Vec3 v, double r)
		{
			List<Entity> es = getEntitiesWithinCube(w, v, r);
			for(int i = 0; i > es.size(); ++i)
			{
				Entity e2 = es.get(i);
				if(e2.getDistance(v.xCoord, v.yCoord, v.zCoord) > r)
				{
					es.remove(i);
					--i;
				}
			}
			return es;
		}
		
		public static List<Entity> getEntitiesWithinCube(World w, Vec3 v, double m)
		{
			List<Entity> es = w.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(v.xCoord - m, v.yCoord - m, v.zCoord - m, v.xCoord + m, v.yCoord + m, v.zCoord + m));
			return es;
		}
		
		public static List<Entity> getEntitiesOnAxis(World w, Vec3 pos, Vec3 p2)
		{
			return getEntitiesOnAxis(w, pos, p2, 0.04);
		}
		
		public static List<Entity> getEntitiesOnAxis(World w, Vec3 pos, Vec3 p2, double r)
		{
			ArrayList<Entity> es = new ArrayList<Entity>();
			
			double h = Math.max(r / 5, 3);
			double d = Coordinates3D.distance(pos, p2);
			int t = (int)Math.ceil(d / h);
			d = h / t;
			
			Vec3 axis = Coordinates3D.subtract(p2, pos);
			for(int i = 0; i < t; ++i)
			{
				List<Entity> es2 = getEntitiesWithinRadius(w, Coordinates3D.add(Coordinates3D.multiply(axis, d * i), pos), r);
				for(int i2 = 0; i2 < es2.size(); ++i2)
				{
					EntitiesInArea.hit.put(es2.get(i2), Coordinates3D.add(Coordinates3D.multiply(axis, d * i), pos));
					es.addAll(es2);
				}
			}
			
			return es;
		}
		
		public static Entity getClosestEntity(List<Entity> es, Vec3 pos)
		{
			Entity e = null;
			for(int i = 0; i < es.size(); ++i)
			{
				if(e == null || e.getDistance(pos.xCoord, pos.yCoord, pos.zCoord) > es.get(i).getDistance(pos.xCoord, pos.yCoord, pos.zCoord))
				{
					;
				}
				{
					e = es.get(i);
				}
			}
			return e;
		}
	}
	
	/** Arrays of multiple numbers **/
	// - sigurd4
	public static final class MathWithMultiple
	{
		public static double distance(double... ds)
		{
			for(int i = 0; i < ds.length; ++i)
			{
				ds[i] *= ds[i];
			}
			return Math.sqrt(addAll(ds));
		}
		
		public static double addAll(double... ds)
		{
			double d = 0;
			for(int i = 0; i < ds.length; ++i)
			{
				d += ds[i];
			}
			return d;
		}
		
		public static double biggest(double... ds)
		{
			double d = ds[0];
			for(int i = 0; i < ds.length; ++i)
			{
				if(ds[i] > d)
				{
					d = ds[i];
				}
			}
			return d;
		}
	}
	
	/** fun with hashmaps **/
	// - sigurd4
	public static final class HashMapStuff
	{
		public static <K, V> K getKeyFromValue(HashMap<K, V> map, V value)
		{
			Iterator<K> keys = map.keySet().iterator();
			while(keys.hasNext())
			{
				K key = keys.next();
				if(map.get(key) == value)
				{
					return key;
				}
			}
			return null;
		}
	}
	
	/** get various stuff **/
	// - sigurd4
	public static final class Reflection
	{
		public static ChunkProviderSettings chunkProviderSettings(World world)
		{
			String s = world.getWorldInfo().getGeneratorOptions();
			
			if(s != null)
			{
				return ChunkProviderSettings.Factory.func_177865_a(s).func_177864_b();
			}
			else
			{
				return ChunkProviderSettings.Factory.func_177865_a("").func_177864_b();
			}
		}
	}
	
	/** guis **/
	// - sigurd4
	public static final class GuiStuff
	{
		public static <T extends Container & IContainerAddPlayerSlots> void addPlayerInventorySlots(T container, InventoryPlayer playerInventory, int x, int y)
		{
			for(int a = 0; a < 3; ++a)
			{
				for(int b = 0; b < 9; ++b)
				{
					try
					{
						container.addSlotToContainer2(new Slot(playerInventory, b + a * 9 + 9, 8 + b * 18 + x, 84 + a * 18 + y));
					}
					catch(Throwable e)
					{
						e.printStackTrace();
					}
				}
			}
			
			for(int a = 0; a < 9; ++a)
			{
				try
				{
					container.addSlotToContainer2(new Slot(playerInventory, a, 8 + a * 18 + x, 142 + y));
				}
				catch(Throwable e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	/** string utilities **/
	// - sigurd4
	public static final class Strings
	{
		public static String UnderscoresToCamelSpaces(String s)
		{
			s = s.toLowerCase();
			ArrayList<Character> cs = Lists.newArrayList();
			for(int i = 0; i < s.length(); ++i)
			{
				cs.add(s.charAt(i));
			}
			for(int i = 0; i < cs.size(); ++i)
			{
				char c = cs.get(i);
				if(c == '_')
				{
					cs.remove(i);
					cs.set(i, Character.toUpperCase(cs.get(i)));
					--i;
				}
			}
			s = "";
			for(int i = 0; i < cs.size(); ++i)
			{
				char c = cs.get(i);
				s = s + c;
			}
			return s;
		}
		
		public static String possessive(String s)
		{
			return s.length() > 0 && s.charAt(s.length() - 1) == 's' ? "'" : "'s";
		}
		
		public static String indefiniteArticle(String s)
		{
			return s.length() > 0 && isVowel(s.charAt(0)) ? "an" : "a";
		}
		
		public static boolean isVowel(char ch)
		{
			return "aeiou���".indexOf(Character.toLowerCase(ch)) > 0;
		}
		
		public static boolean isConsonant(char ch)
		{
			return !isVowel(ch);
		}
	}
	
	public static final class ItemStacks
	{
		public static ItemStack createNbt(ItemStack stack)
		{
			if(stack.getTagCompound() == null)
			{
				stack.setTagCompound(new NBTTagCompound());
			}
			return stack;
		}
		
		public static void syringeUse(ItemStack stack, World world, EntityPlayer player)
		{
			world.playSoundAtEntity(player, "game.player.hurt", 0.3F, 0.9F);
			--stack.stackSize;
			if(!player.capabilities.isCreativeMode)
			{
				player.inventory.addItemStackToInventory(new ItemStack(M.items.crafting.empty_hypo));
			}
			if(!world.isRemote)
			{
				player.addPotionEffect(new PotionEffect(Potion.confusion.id, 80, 0, true, false));
			}
		}
		
		public static void adamEffect(ItemStack stack, World world, EntityPlayer player)
		{
			ExtendedPlayer props = ExtendedPlayer.get(player);
			player.clearActivePotions();
			props.setDrunkness(0);
			if(!world.isRemote)
			{
				player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 600, 0));
				player.addPotionEffect(new PotionEffect(Potion.resistance.id, 300, 1, true, false));
				player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 300, 5, true, false));
			}
		}
	}
	
	public static final class Render
	{
		public static HashMap<EntityPlayer, RenderPlayer> playerRenders = new HashMap();
		
		public static ModelBiped getModel(EntityPlayer player, ModelBiped model)
		{
			RenderPlayer rp = playerRenders.get(player);
			if(rp != null)
			{
				model.setModelAttributes(rp.getPlayerModel());
			}
			return model;
		}
		
		public static void setPlayerRenderer(EntityPlayer player, RenderPlayer renderer)
		{
			playerRenders.put(player, renderer);
		}
	}
}
