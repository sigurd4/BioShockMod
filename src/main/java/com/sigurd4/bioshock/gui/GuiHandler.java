package com.sigurd4.bioshock.gui;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import com.sigurd4.bioshock.inventory.ContainerUInvent;

public class GuiHandler implements IGuiHandler
{
	public static enum GuiIDs
	{
		U_INVENT
		{
			@Override
			public Container server(EntityPlayer player, World world, BlockPos pos)
			{
				return new ContainerUInvent(player.inventory, world, pos);
			}
			
			@Override
			public Gui client(EntityPlayer player, World world, BlockPos pos)
			{
				return new GuiCraftingUInvent(player.inventory, world, pos);
			}
		};
		
		public abstract Container server(EntityPlayer player, World world, BlockPos tileEntity);
		
		public abstract Gui client(EntityPlayer player, World world, BlockPos tileEntity);
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
		if(GuiIDs.values()[ID] != null)
		{
			return GuiIDs.values()[ID].server(player, world, new BlockPos(x, y, z));
		}
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
		if(GuiIDs.values()[ID] != null)
		{
			return GuiIDs.values()[ID].client(player, world, new BlockPos(x, y, z));
		}
		return null;
	}
}
