package com.sigurd4.bioshock.proxy;

import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.sigurd4.bioshock.M;
import com.sigurd4.bioshock.M.Id;
import com.sigurd4.bioshock.event.HandlerClient;
import com.sigurd4.bioshock.event.HandlerClientFML;

public class ProxyClient extends ProxyCommon
{
	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);
		
		MinecraftForge.EVENT_BUS.register(new HandlerClient());
		FMLCommonHandler.instance().bus().register(new HandlerClientFML());
		HandlerClientFML.init();
	}
	
	@Override
	public void init(FMLInitializationEvent event)
	{
		super.init(event);
		
		RenderItem ri = Minecraft.getMinecraft().getRenderItem();
		RenderManager rm = Minecraft.getMinecraft().getRenderManager();
		this.registerItemModels(ri);
		this.tileEntityRender(rm, ri);
		this.entityRender(rm, ri);
	}
	
	@Override
	public World world(int dimension)
	{
		return Minecraft.getMinecraft().theWorld;
	}
	
	private void tileEntityRender(RenderManager rm, RenderItem ri)
	{
		//ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGunWorkbench.class, new TileEntityGunWorkbenchRenderer());
	}
	
	private void entityRender(RenderManager rm, RenderItem ri)
	{
		//RenderingRegistry.registerEntityRenderingHandler(EntityShuriken.class, new RenderShuriken(rm, ri));
	}
	
	private void registerItemModels(RenderItem ri)
	{
		Iterator<Id> ids = M.getIds();
		while(ids.hasNext())
		{
			Id id = ids.next();
			if(id != null)
			{
				Object item = M.getItem(id);
				if(item != null && item instanceof Block)
				{
					ri.getItemModelMesher().register(Item.getItemFromBlock((Block)item), 0, new ModelResourceLocation(id.mod.toLowerCase() + ":" + id.id.toLowerCase(), "inventory"));
				}
				else if(item != null && item instanceof Item)
				{
					ri.getItemModelMesher().register((Item)item, 0, new ModelResourceLocation(id.mod.toLowerCase() + ":" + id.id.toLowerCase(), "inventory"));
				}
			}
		}
	}
}