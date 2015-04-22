package com.sigurd4.bioshock.item;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.sigurd4.bioshock.M;

public class ItemMonsterPlacerMod extends ItemMonsterPlacer
{
	public ItemMonsterPlacerMod()
	{
		super();
		this.setCreativeTab(M.tabs.core);
		this.setUnlocalizedName("monsterPlacer");
	}
	
	/**
	 * Called when a Block is right-clicked with this Item
	 * 
	 * @param pos
	 *            The block being right-clicked
	 * @param side
	 *            The side being right-clicked
	 */
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if(worldIn.isRemote)
		{
			return true;
		}
		else if(!playerIn.canPlayerEdit(pos.offset(side), side, stack))
		{
			return false;
		}
		else
		{
			IBlockState iblockstate = worldIn.getBlockState(pos);
			
			if(iblockstate.getBlock() == Blocks.mob_spawner)
			{
				TileEntity tileentity = worldIn.getTileEntity(pos);
				
				if(tileentity instanceof TileEntityMobSpawner)
				{
					MobSpawnerBaseLogic mobspawnerbaselogic = ((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic();
					mobspawnerbaselogic.setEntityName(EntityList.getStringFromID(stack.getMetadata()));
					tileentity.markDirty();
					worldIn.markBlockForUpdate(pos);
					
					if(!playerIn.capabilities.isCreativeMode)
					{
						--stack.stackSize;
					}
					
					return true;
				}
			}
			
			pos = pos.offset(side);
			double d0 = 0.0D;
			
			if(side == EnumFacing.UP && iblockstate instanceof BlockFence)
			{
				d0 = 0.5D;
			}
			
			Entity entity = spawnCreature(worldIn, stack.getMetadata(), pos.getX() + 0.5D, pos.getY() + d0, pos.getZ() + 0.5D);
			
			if(entity != null)
			{
				if(entity instanceof EntityLivingBase && stack.hasDisplayName())
				{
					entity.setCustomNameTag(stack.getDisplayName());
				}
				
				if(!playerIn.capabilities.isCreativeMode)
				{
					--stack.stackSize;
				}
			}
			
			return true;
		}
	}
	
	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
	{
		if(worldIn.isRemote)
		{
			return itemStackIn;
		}
		else
		{
			MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(worldIn, playerIn, true);
			
			if(movingobjectposition == null)
			{
				return itemStackIn;
			}
			else
			{
				if(movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
				{
					BlockPos blockpos = movingobjectposition.getBlockPos();
					
					if(!worldIn.isBlockModifiable(playerIn, blockpos))
					{
						return itemStackIn;
					}
					
					if(!playerIn.canPlayerEdit(blockpos, movingobjectposition.sideHit, itemStackIn))
					{
						return itemStackIn;
					}
					
					if(worldIn.getBlockState(blockpos).getBlock() instanceof BlockLiquid)
					{
						Entity entity = spawnCreature(worldIn, itemStackIn.getMetadata(), blockpos.getX() + 0.5D, blockpos.getY() + 0.5D, blockpos.getZ() + 0.5D);
						
						if(entity != null)
						{
							if(entity instanceof EntityLivingBase && itemStackIn.hasDisplayName())
							{
								((EntityLiving)entity).setCustomNameTag(itemStackIn.getDisplayName());
							}
							
							if(!playerIn.capabilities.isCreativeMode)
							{
								--itemStackIn.stackSize;
							}
							
							playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
						}
					}
				}
				
				return itemStackIn;
			}
		}
	}
	
	/**
	 * Spawns the creature specified by the egg's type in the location specified
	 * by the last three parameters.
	 * Parameters: world, entityID, x, y, z.
	 */
	public static Entity spawnCreature(World world, int metadata, double x, double y, double z)
	{
		if(!EntityList2.containsID(metadata))
		{
			return null;
		}
		else
		{
			Entity entity = null;
			
			for(int j = 0; j < 1; ++j)
			{
				entity = EntityList2.createEntityByID(metadata, world);
				
				if(entity instanceof EntityLivingBase)
				{
					EntityLiving entityliving = (EntityLiving)entity;
					entity.setLocationAndAngles(x, y, z, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0F), 0.0F);
					entityliving.rotationYawHead = entityliving.rotationYaw;
					entityliving.renderYawOffset = entityliving.rotationYaw;
					entityliving.func_180482_a(world.getDifficultyForLocation(new BlockPos(entityliving)), (IEntityLivingData)null);
					world.spawnEntityInWorld(entity);
					entityliving.playLivingSound();
				}
			}
			
			return entity;
		}
	}
	
	/**
	 * returns a list of items with the same ID, but different meta (eg: dye
	 * returns 16 items)
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		Iterator iterator = EntityList2.spawnables.values().iterator();
		
		while(iterator.hasNext())
		{
			EntityList.EntityEggInfo entityegginfo = ((EntityList2.e)iterator.next()).entityEggInfo;
			list.add(new ItemStack(item, 1, entityegginfo.spawnedID));
		}
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		String s = ("" + StatCollector.translateToLocal(Items.spawn_egg.getUnlocalizedName() + ".name")).trim();
		String s1 = "null";
		if(EntityList2.getSpawnableFromId(stack.getItemDamage()) != null)
		{
			s1 = EntityList2.getSpawnableFromId(stack.getItemDamage()).stringID;
		}
		
		if(s1 != null)
		{
			s = s + " " + StatCollector.translateToLocal("entity." + s1 + ".name");
		}
		
		return s;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass)
	{
		EntityList.EntityEggInfo entityegginfo = EntityList2.getSpawnableFromId(stack.getItemDamage()).entityEggInfo;
		return entityegginfo != null ? pass == 0 ? entityegginfo.primaryColor : entityegginfo.secondaryColor : 16777215;
	}
	
	public static class EntityList2
	{
		public static World world = null;
		
		private static class e
		{
			public final int ID;
			public final String stringID;
			public final EntityList.EntityEggInfo entityEggInfo;
			
			public e(int id, String stringid, int primaryColor, int secondaryColor)
			{
				this.ID = id;
				this.stringID = stringid;
				this.entityEggInfo = new EntityList.EntityEggInfo(id, primaryColor, secondaryColor);
			}
		}
		
		public static Entity createEntityFromClass(Class<? extends Entity> clazz, World world)
		{
			EntityList2.world = world;
			try
			{
				if(clazz != null)
				{
					Object e = clazz.getConstructor(new Class[]{World.class}).newInstance(new Object[]{world});
					if(e instanceof Entity)
					{
						return (Entity)e;
					}
				}
			}
			catch(Exception exception)
			{
				exception.printStackTrace();
			}
			return null;
		}
		
		private static HashMap<Class<? extends Entity>, e> spawnables = new HashMap<Class<? extends Entity>, e>();
		
		public static e getSpawnableFromId(int ID)
		{
			Iterator<e> es = spawnables.values().iterator();
			while(es.hasNext())
			{
				e e = es.next();
				if(e.ID == ID)
				{
					return e;
				}
			}
			return null;
		}
		
		public static e getSpawnableFromStringId(String ID)
		{
			Iterator<e> es = spawnables.values().iterator();
			while(es.hasNext())
			{
				e e = es.next();
				if(e.stringID == ID)
				{
					return e;
				}
			}
			return null;
		}
		
		public static int getUnusedId()
		{
			for(int i = 0; i < Integer.MAX_VALUE; ++i)
			{
				if(!containsID(i))
				{
					return i;
				}
			}
			return -1;
		}
		
		public static boolean containsID(int ID)
		{
			Iterator<e> es = spawnables.values().iterator();
			while(es.hasNext())
			{
				e e = es.next();
				if(e.ID == ID)
				{
					return true;
				}
			}
			return false;
		}
		
		public static boolean containsStringID(String ID)
		{
			Iterator<e> es = spawnables.values().iterator();
			while(es.hasNext())
			{
				e e = es.next();
				if(e.stringID == ID)
				{
					return true;
				}
			}
			return false;
		}
		
		public static boolean registerEntity(Class<? extends Entity> clas, int ID, String stringID, int color1, int color2)
		{
			if(!spawnables.containsKey(clas) && !containsID(ID))
			{
				e e = new e(ID, stringID, color1, color2);
				spawnables.put(clas, e);
				return true;
			}
			return false;
		}
		
		public static e getEFromEntity(Class<? extends Entity> e)
		{
			if(spawnables.containsKey(e))
			{
				return spawnables.get(e);
			}
			return null;
		}
		
		public static int getIdFromEntity(Class<? extends Entity> clas)
		{
			e e = getEFromEntity(clas);
			if(e != null)
			{
				return e.ID;
			}
			return -1;
		}
		
		public static String getStringIdFromEntity(Class<? extends Entity> clas)
		{
			e e = getEFromEntity(clas);
			if(e != null)
			{
				return e.stringID;
			}
			return null;
		}
		
		public static Class<? extends Entity> getClassFromId(int id)
		{
			if(containsID(id))
			{
				Iterator<Class<? extends Entity>> ids = spawnables.keySet().iterator();
				while(ids.hasNext())
				{
					Class<? extends Entity> clas = ids.next();
					if(spawnables.containsKey(clas) && spawnables.get(clas).ID == id)
					{
						return clas;
					}
				}
			}
			return null;
		}
		
		/**
		 * Create a new instance of an entity in the world by using an entity
		 * ID.
		 */
		public static Entity createEntityByID(int ID, World world)
		{
			EntityList2.world = world;
			Entity entity = null;
			Class oclass = getClassFromId(ID);
			
			entity = createEntityFromClass(oclass, world);
			
			return entity;
		}
		
		public static int getDWID(Entity entity)
		{
			if(entity != null)
			{
				DataWatcher dw = new DataWatcher(entity);
				for(int i = 0; i <= 31; ++i)
				{
					boolean b = true;
					List list = dw.getAllWatched();
					if(list != null)
					{
						for(int i2 = 0; i2 < list.size(); ++i2)
						{
							if(list.get(i2) instanceof DataWatcher.WatchableObject)
							{
								DataWatcher.WatchableObject obj = (DataWatcher.WatchableObject)list.get(i2);
								if(obj.getDataValueId() == i)
								{
									b = false;
								}
							}
						}
						if(b)
						{
							return i;
						}
					}
				}
			}
			return -1;
		}
	}
}
