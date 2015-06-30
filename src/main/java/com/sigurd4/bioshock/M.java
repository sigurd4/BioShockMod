package com.sigurd4.bioshock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.command.ICommand;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;

import com.sigurd4.bioshock.Stuff.HashMapStuff;
import com.sigurd4.bioshock.commands.CommandSetCash;
import com.sigurd4.bioshock.item.IItemIdFrom;
import com.sigurd4.bioshock.item.IItemTextureVariants;
import com.sigurd4.bioshock.item.ItemCrafting;
import com.sigurd4.bioshock.item.ItemMonsterPlacerMod;
import com.sigurd4.bioshock.proxy.ProxyCommon;
import com.sigurd4.bioshock.reference.RefMod;

@Mod(modid = RefMod.MODID, name = RefMod.NAME, version = RefMod.VERSION, guiFactory = RefMod.GUI_FACTORY_CLASS)
public class M
{
	/** Register entity with egg **/
	public static void registerEntity(Class<? extends Entity> entityClass, String name, int primaryColor, int secondaryColor)
	{
		EntityRegistry.registerGlobalEntityID(entityClass, name, EntityRegistry.findGlobalUniqueEntityId());
		ItemMonsterPlacerMod.EntityList2.registerEntity(entityClass, EntityRegistry.findGlobalUniqueEntityId(), name, primaryColor, secondaryColor);
	}
	
	private static final ArrayList<Integer> modEntityIds = new ArrayList<Integer>();
	
	/**
	 * Register entity without egg
	 * 
	 * @throws Exception
	 **/
	public static void registerEntityNoEgg(Class<? extends Entity> entityClass, String name) throws Exception
	{
		ModContainer mc = FMLCommonHandler.instance().findContainerFor(instance);
		loop:
		for(int id = 0; id < Integer.MAX_VALUE; ++id)
		{
			if(true/* || EntityRegistry.instance().lookupModSpawn(mc, id) == null*/)
			{
				for(Integer i : modEntityIds)
				{
					if(i.equals(id))
					{
						continue loop;
					}
				}
				EntityRegistry.registerModEntity(entityClass, name, id, instance, 64, 1, true);
				modEntityIds.add(id);
				return;
			}
		}
		throw new Exception("Not enough available ids for entity " + '"' + name + '"');
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
	
	public static final String[] otherIcons = {"enrage_ball", "hypnotize_big_daddy_ball", "return_to_sender_ball", "infinite_eve_orb", "infinite_ammo_orb", "infinite_shields_orb"};
	public static int otherIconsIndex = -1;
	
	public static HashMap<Integer, ArrayList<String>> getTypes(Item item)
	{
		HashMap<Integer, ArrayList<String>> types = new HashMap();
		for(int meta = 0; meta <= item.getMaxDamage(); ++meta)
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
			if(item instanceof ItemCrafting)
			{
				for(int i = 0; i < otherIcons.length; ++i)
				{
					types.get(meta).add(RefMod.MODID + ":" + otherIcons[i]);
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
		public ItemBlock blockItem;
		
		public boolean shouldBeReplaced()
		{
			return this.oreDictNames.length <= 0 || !this.replacedIfAlreadyAnOreDict;
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
		return registerBlock(id, block, (ItemBlock)null, replacedIfAlreadyAnOreDict, oreDictNames);
	}
	
	public static <T extends Block> T registerBlock(String id, String modid, T block, boolean replacedIfAlreadyAnOreDict, String[] oreDictNames)
	{
		return registerBlock(id, modid, block, (ItemBlock)null, replacedIfAlreadyAnOreDict, oreDictNames);
	}
	
	public static <T extends Block> T registerBlock(String id, T block, boolean replacedIfAlreadyAnOreDict, String[] oreDictNames, ItemBlock blockItem)
	{
		return registerBlock(id, block, blockItem, replacedIfAlreadyAnOreDict, oreDictNames);
	}
	
	public static <T extends Block> T registerBlock(String id, String modid, T block, boolean replacedIfAlreadyAnOreDict, String[] oreDictNames, ItemBlock blockItem)
	{
		return registerBlock(id, modid, block, blockItem, replacedIfAlreadyAnOreDict, oreDictNames);
	}
	
	public static <T extends Block> T registerBlock(String id, T block, ItemBlock blockItem, boolean replacedIfAlreadyAnOreDict, String[] oreDictNames)
	{
		return registerBlock(id, RefMod.MODID, block, blockItem, replacedIfAlreadyAnOreDict, oreDictNames);
	}
	
	public static <T extends Block> T registerBlock(String id, String modid, T block, ItemBlock blockItem, boolean replacedIfAlreadyAnOreDict, String[] oreDictNames)
	{
		if(!ids.containsKey(block))
		{
			Id ID = new Id(id, modid, replacedIfAlreadyAnOreDict, oreDictNames);
			if(blockItem != null)
			{
				ID.blockItem = blockItem;
			}
			ids.put(block, ID);
			idsToBeRegistered.add(ID);
		}
		return block;
	}
	
	@Instance(RefMod.MODID)
	public static M instance;
	
	public static SimpleNetworkWrapper network;
	
	// //TABS:
	public static MTabs tabs = new MTabs();
	
	// //ITEMS:
	public static MItems items = new MItems();
	
	// //BLOCKS:
	public static MBlocks blocks = new MBlocks();
	
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
	
	@EventHandler
	public void serverLoad(FMLServerStartingEvent event)
	{
		ICommand[] c = {new CommandSetCash()};
		
		for(int i = 0; i < c.length; ++i)
		{
			Side s = FMLCommonHandler.instance().getSide();
			if(s == Side.SERVER)
			{
				event.registerServerCommand(c[i]);
			}
			if(s == Side.CLIENT)
			{
				ClientCommandHandler.instance.registerCommand(c[i]);
			}
		}
	}
}