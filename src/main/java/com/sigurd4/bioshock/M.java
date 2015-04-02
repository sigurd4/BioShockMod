package com.sigurd4.bioshock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import com.sigurd4.bioshock.Stuff.HashMapStuff;
import com.sigurd4.bioshock.item.IItemIdFrom;
import com.sigurd4.bioshock.item.IItemTextureVariants;
import com.sigurd4.bioshock.proxy.ProxyCommon;
import com.sigurd4.bioshock.reference.RefMod;
import com.sigurd4.bioshock.tab.TabGeneric;

@Mod(modid = RefMod.MODID, name = RefMod.NAME, version = RefMod.VERSION, guiFactory = RefMod.GUI_FACTORY_CLASS)
public class M
{
	/** Register entity with egg **/
	public static void registerEntity(Class<? extends Entity> entityClass, String name, int entityID, int primaryColor, int secondaryColor)
	{
		EntityRegistry.registerGlobalEntityID(entityClass, name, entityID);
		ItemMonsterPlacer2.EntityList2.registerEntity(entityClass, entityID, name, primaryColor, secondaryColor);
	}
	
	/** Register entity without egg **/
	public static void registerEntityNoEgg(Class<? extends Entity> entityClass, String name, int entityID)
	{
		EntityRegistry.registerModEntity(entityClass, name, entityID, instance, 64, 1, true);
	}
	
	private static final HashMap<Object, Id> ids = new HashMap<Object, Id>();
	public static final ArrayList<Id> idsToBeRegistered = new ArrayList<Id>();
	public static final HashMap<Object, CreativeTabs[]> creativeTabs = new HashMap<Object, CreativeTabs[]>();
	
	public static Iterator<Id> getIds()
	{
		return ((HashMap<Object, Id>)ids.clone()).values().iterator();
	}
	
	public static Id getId(Item item)
	{
		if(ids.containsKey(item))
		{
			return ids.get(item);
		}
		return null;
	}
	
	public static Id getId(Block block)
	{
		if(ids.containsKey(block))
		{
			return ids.get(block);
		}
		return null;
	}
	
	public static Object getItem(Id id)
	{
		if(ids.containsValue(id))
		{
			Object v = HashMapStuff.getKeyFromValue((HashMap<Object, Id>)ids.clone(), id);
			if(v instanceof Item || v instanceof Block)
			{
				return v;
			}
		}
		return null;
	}
	
	public static boolean hasId(Id id)
	{
		return ids.containsValue(id);
	}
	
	public static boolean hasItem(Item item)
	{
		return hasItem((Object)item);
	}
	
	public static boolean hasItem(Block block)
	{
		return hasItem((Object)block);
	}
	
	@Deprecated
	private static boolean hasItem(Object item)
	{
		return ids.containsKey(item) && ids.get(item).visible;
	}
	
	public static boolean visible(Object item)
	{
		return !ids.containsKey(item) || ids.get(item).visible;
	}
	
	public static HashMap<Integer, ArrayList<String>> getTypes(Item item)
	{
		HashMap<Integer, ArrayList<String>> types = new HashMap();
		for(int meta = 0; meta < item.getMaxDamage(); ++meta)
		{
			types.put(meta, new ArrayList());
			if(item instanceof IItemTextureVariants)
			{
				String[] variants = ((IItemTextureVariants)item).getTextureVariants(meta);
				for(int i = 0; i < variants.length; ++i)
				{
					types.get(meta).add(variants[i].toLowerCase());
				}
			}
			if(types.get(meta).size() <= 0)
			{
				if(getId(item) != null)
				{
					Id id = getId(item);
					types.get(meta).add(id.mod + ":" + id.id);
				}
				else
				{
					types.get(meta).add("" + Item.itemRegistry.getNameForObject(item));
				}
			}
		}
		return types;
	}
	
	public static class Id
	{
		public final String id;
		public final String mod;
		public final String[] oreDictNames;
		public final boolean replacedIfAlreadyAnOreDict;
		
		public boolean shouldBeReplaced()
		{
			return oreDictNames.length <= 0 || !replacedIfAlreadyAnOreDict;
		};
		
		public boolean visible;
		public final boolean dungeonLoot;
		public final int dungeonLootMin;
		public final int dungeonLootMax;
		public final int dungeonLootChance;
		
		public Id(String id, String mod, boolean replacedIfAlreadyAnOreDict, String[] oreDictNames)
		{
			this.id = id;
			this.mod = mod;
			this.replacedIfAlreadyAnOreDict = replacedIfAlreadyAnOreDict;
			this.oreDictNames = oreDictNames;
			
			this.dungeonLoot = false;
			this.dungeonLootMin = 0;
			this.dungeonLootMax = 0;
			this.dungeonLootChance = 0;
		}
		
		private Id(String id, String mod, boolean replacedIfAlreadyAnOreDict, String[] oreDictNames, int dungeonLootMin, int dungeonLootMax, int dungeonLootChance)
		{
			this.id = id;
			this.mod = mod;
			this.replacedIfAlreadyAnOreDict = replacedIfAlreadyAnOreDict;
			this.oreDictNames = oreDictNames;
			
			dungeonLootMin = dungeonLootMin >= 1 ? dungeonLootMin : 1;
			dungeonLootMax = dungeonLootMax <= 64 ? dungeonLootMax : 64;
			dungeonLootMin = dungeonLootMin <= dungeonLootMax ? dungeonLootMin : dungeonLootMax;
			dungeonLootMax = dungeonLootMax >= dungeonLootMin ? dungeonLootMax : dungeonLootMin;
			
			this.dungeonLoot = true;
			this.dungeonLootMin = dungeonLootMin;
			this.dungeonLootMax = dungeonLootMax;
			this.dungeonLootChance = dungeonLootChance;
		}
		
		public Id(String id, boolean replacedIfAlreadyAnOreDict, String[] oreDictNames)
		{
			this(id, RefMod.MODID, replacedIfAlreadyAnOreDict, oreDictNames);
		}
	}
	
	public static <T extends Item & IItemIdFrom> T registerItem(T item, boolean replacedIfAlreadyAnOreDict, String[] oreDictNames)
	{
		return registerItem(item.getId(), RefMod.MODID, item, replacedIfAlreadyAnOreDict, oreDictNames);
	}
	
	public static <T extends Item> T registerItem(String id, T item, boolean replacedIfAlreadyAnOreDict, String[] oreDictNames)
	{
		return registerItem(id, RefMod.MODID, item, replacedIfAlreadyAnOreDict, oreDictNames);
	}
	
	public static <T extends Item> T registerItem(String id, String modid, T item, boolean replacedIfAlreadyAnOreDict, String[] oreDictNames)
	{
		return registerItem(id, modid, item, replacedIfAlreadyAnOreDict, oreDictNames, 0, 0, 0);
	}
	
	public static <T extends Item & IItemIdFrom> T registerItem(T item, boolean replacedIfAlreadyAnOreDict, String[] oreDictNames, int min, int max, int chance)
	{
		return registerItem(item.getId(), RefMod.MODID, item, replacedIfAlreadyAnOreDict, oreDictNames, min, max, chance);
	}
	
	public static <T extends Item> T registerItem(String id, T item, boolean replacedIfAlreadyAnOreDict, String[] oreDictNames, int min, int max, int chance)
	{
		return registerItem(id, RefMod.MODID, item, replacedIfAlreadyAnOreDict, oreDictNames, min, max, chance);
	}
	
	public static <T extends Item> T registerItem(String id, String modid, T item, boolean replacedIfAlreadyAnOreDict, String[] oreDictNames, int min, int max, int chance)
	{
		if(!ids.containsKey(item) && !ids.containsValue(id))
		{
			Id ID = new Id(id, modid, replacedIfAlreadyAnOreDict, oreDictNames);
			if(chance > 0)
			{
				ID = new Id(id, modid, replacedIfAlreadyAnOreDict, oreDictNames, min, max, chance);
			}
			ids.put(item, ID);
			idsToBeRegistered.add(ID);
		}
		return item;
	}
	
	public static <T extends Block> T registerBlock(String id, T block, boolean replacedIfAlreadyAnOreDict, String[] oreDictNames)
	{
		return registerBlock(id, RefMod.MODID, block, replacedIfAlreadyAnOreDict, oreDictNames);
	}
	
	public static <T extends Block> T registerBlock(String id, String modid, T block, boolean replacedIfAlreadyAnOreDict, String[] oreDictNames)
	{
		if(!ids.containsKey(block))
		{
			Id ID = new Id(id, modid, replacedIfAlreadyAnOreDict, oreDictNames);
			ids.put(block, ID);
			idsToBeRegistered.add(ID);
		}
		return block;
	}
	
	@Instance(RefMod.MODID)
	public static M instance;
	
	public static SimpleNetworkWrapper network;
	
	/** tabs **/
	public static TabGeneric tabCore = new TabGeneric("core");
	public static TabGeneric tabWeapons = new TabGeneric("weapons");
	public static TabGeneric tabPassives = new TabGeneric("passives");
	public static TabGeneric tabCraftingItems = new TabGeneric("craftingItems");
	public static TabGeneric tabConsumables = new TabGeneric("consumables");
	
	// //ITEMS:
	
	// //BLOCKS:
	
	public M()
	{
		
	}
	
	@SidedProxy(clientSide = RefMod.CLIENT_PROXY_CLASS, serverSide = RefMod.SERVER_PROXY_CLASS)
	public static ProxyCommon proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		proxy.preInit(event);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.init(event);
	}
	
	@EventHandler
	public void init(FMLPostInitializationEvent event)
	{
		proxy.postInit(event);
	}
}