package com.sigurd4.bioshock.proxy;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.OreDictionary;

import com.sigurd4.bioshock.M;
import com.sigurd4.bioshock.M.Id;
import com.sigurd4.bioshock.config.Config;
import com.sigurd4.bioshock.entity.EntityFlame;
import com.sigurd4.bioshock.entity.EntityWaterElectro;
import com.sigurd4.bioshock.entity.projectile.EntityGunBullet;
import com.sigurd4.bioshock.entity.projectile.EntityPlasmidBullet;
import com.sigurd4.bioshock.entity.projectile.EntityPlasmidProjectile;
import com.sigurd4.bioshock.event.HandlerCommon;
import com.sigurd4.bioshock.event.HandlerCommonFML;
import com.sigurd4.bioshock.extendedentity.ExtendedPlayer;
import com.sigurd4.bioshock.gui.GuiHandler;
import com.sigurd4.bioshock.item.IItemInit;
import com.sigurd4.bioshock.item.ItemAudioLog;
import com.sigurd4.bioshock.packet.PacketKey;
import com.sigurd4.bioshock.packet.PacketPlayerData;
import com.sigurd4.bioshock.packet.PacketPlayerSkin;
import com.sigurd4.bioshock.packet.PacketShieldBreak;
import com.sigurd4.bioshock.particles.ParticleHandler.EnumParticleTypes2;
import com.sigurd4.bioshock.reference.RefMod;

public abstract class ProxyCommon
{
	public void preInit(FMLPreInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new HandlerCommon());
		FMLCommonHandler.instance().bus().register(new HandlerCommonFML());
		
		NetworkRegistry.INSTANCE.registerGuiHandler(M.instance, new GuiHandler());
		
		this.registerConfig(event.getSuggestedConfigurationFile());
		this.registerItems();
		this.oreDictionary();
		this.packets();
		this.entities();
		this.worldGen();
		M.idsToBeRegistered.clear();
	}
	
	public void init(FMLInitializationEvent event)
	{
		this.recipes();
		this.tileEntities();
	}
	
	public void postInit(FMLPostInitializationEvent event)
	{
		this.itemInit();
	}
	
	public abstract World world(int dimension);
	
	private void packets()
	{
		M.network = NetworkRegistry.INSTANCE.newSimpleChannel(RefMod.MODID);
		M.network.registerMessage(PacketKey.Handler.class, PacketKey.class, 0, Side.SERVER);
		M.network.registerMessage(PacketPlayerData.Handler.class, PacketPlayerData.class, 1, Side.CLIENT);
		M.network.registerMessage(PacketPlayerSkin.Handler.class, PacketPlayerSkin.class, 2, Side.SERVER);
		M.network.registerMessage(PacketShieldBreak.Handler.class, PacketShieldBreak.class, 3, Side.CLIENT);
	}
	
	private void recipes()
	{
		/*
		 * if(M.visible(M.container))
		 * {
		 * //recipe
		 * }
		 */
	}
	
	private void registerNugget(Item nugget, Item bar)
	{
		if(M.visible(nugget) && M.visible(bar))
		{
			GameRegistry.addShapedRecipe(new ItemStack(bar, 1), new Object[]{"NNN", "NNN", "NNN", 'N', nugget});
			GameRegistry.addShapedRecipe(new ItemStack(nugget, 9), new Object[]{"B", 'B', bar});
		}
	}
	
	private void oreDictionary()
	{
		Iterator<Id> ids = M.idsToBeRegistered.iterator();
		while(ids.hasNext())
		{
			Id id = ids.next();
			if(id != null)
			{
				Object item = M.getItem(id);
				for(int i = 0; i < id.oreDictNames.length; ++i)
				{
					if(M.visible(item))
					{
						if(item != null && item instanceof Block)
						{
							OreDictionary.registerOre(id.oreDictNames[i], (Block)item);
						}
						if(item != null && item instanceof Item)
						{
							OreDictionary.registerOre(id.oreDictNames[i], (Item)item);
						}
					}
				}
			}
		}
	}
	
	private void entities()
	{
		try
		{
			M.registerEntityNoEgg(EntityGunBullet.class, "bullet");
			M.registerEntityNoEgg(EntityPlasmidProjectile.class, "plasmidProjectile");
			M.registerEntityNoEgg(EntityPlasmidBullet.class, "plasmidBullet");
			M.registerEntityNoEgg(EntityFlame.class, "entityFlame");
			M.registerEntityNoEgg(EntityWaterElectro.class, "entityWaterElectro");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Minecraft.getMinecraft().shutdown();
		}
	}
	
	private void registerItems()
	{
		Iterator<Id> ids = M.idsToBeRegistered.iterator();
		while(ids.hasNext())
		{
			Id id = ids.next();
			if(id != null)
			{
				Object item = M.getItem(id);
				if(item != null && item instanceof Block)
				{
					if(id.blockItem == null)
					{
						GameRegistry.registerBlock((Block)item, id.id);
					}
					else
					{
						GameRegistry.registerBlock((Block)item, id.blockItem.getClass(), id.id);
					}
				}
				if(item != null && item instanceof Item)
				{
					GameRegistry.registerItem((Item)item, id.id);
				}
				
				if(id.replacedIfAlreadyAnOreDict)
				{
					id.visible = false;
					for(int i2 = 0; i2 < id.oreDictNames.length; ++i2)
					{
						List<ItemStack> oreDicts = OreDictionary.getOres(id.oreDictNames[i2]);
						if(oreDicts != null)
						{
							for(int i3 = 0; i3 < oreDicts.size(); ++i3)
							{
								if(oreDicts.get(i3) != null && oreDicts.get(i3).getItem() == item)
								{
									oreDicts.remove(i3);
									--i3;
								}
							}
						}
						if(oreDicts == null || oreDicts.size() <= 0)
						{
							id.visible = true;
						}
					}
				}
				else
				{
					id.visible = true;
				}
			}
		}
	}
	
	private void worldGen()
	{
		Iterator<Id> ids = M.idsToBeRegistered.iterator();
		while(ids.hasNext())
		{
			Id id = ids.next();
			if(id != null)
			{
				Object item = M.getItem(id);
				for(int i = 0; i < id.oreDictNames.length; ++i)
				{
					/*if(item != null && item instanceof BlockOre2)
					{
						IWorldGenerator worldGen = ((BlockOre2)item).worldGen();
						if(worldGen != null)
						{
							GameRegistry.registerWorldGenerator(worldGen, 0);
						}
					}*/
				}
			}
		}
	}
	
	private void tileEntities()
	{
		//GameRegistry.registerTileEntity(TileEntityGunWorkbench.class, TileEntityGunWorkbench.getID());
	}
	
	private void registerConfig(File file)
	{
		if(Config.config == null)
		{
			Config.config = new Configuration(file);
		}
		for(int i = 0; i < Config.entries.size(); ++i)
		{
			Config.entries.get(i).set(Config.config);
		}
		if(Config.config.hasChanged())
		{
			Config.config.save();
		}
		
		//stuff on init
	}
	
	private void itemInit()
	{
		Iterator<Id> ids = M.getIds();
		while(ids.hasNext())
		{
			Id id = ids.next();
			if(id != null)
			{
				if(M.getItem(id) instanceof IItemInit)
				{
					((IItemInit)M.getItem(id)).init();
				}
			}
		}
	}
	
	public void sendPlayerSkinData(EntityPlayer entity)
	{
		
	}
	
	public void playTargetSound(BlockPos oldHookCoords, MovingObjectPosition pos, EntityLivingBase thrower)
	{
		
	}
	
	public void createAudioLogSound(Entity entity, ItemStack stack, boolean b)
	{
		if(entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)entity;
			ExtendedPlayer props = ExtendedPlayer.get(player);
			String sound = "audiologs." + ItemAudioLog.OWNER.get(stack) + "." + ItemAudioLog.LOG.get(stack);
			props.passiveController.listenToAudiolog(stack, sound);
			props.audiologsObtained.add(sound);
		}
	}
	
	public void stopAllAudioLogSounds(Entity entity)
	{
		
	}
	
	public void particle(EnumParticleTypes2 particleEnum, World world, boolean ignoreDistance, double x, double y, double z, double mx, double my, double mz, int... par)
	{
		
	}
}
