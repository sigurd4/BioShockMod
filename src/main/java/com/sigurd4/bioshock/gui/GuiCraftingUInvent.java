package com.sigurd4.bioshock.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import com.sigurd4.bioshock.inventory.ContainerUInvent;
import com.sigurd4.bioshock.reference.RefMod;

@SideOnly(Side.CLIENT)
public class GuiCraftingUInvent extends GuiContainer
{
	private static final ResourceLocation craftingTableGuiTextures = new ResourceLocation(RefMod.MODID + ":" + "textures/gui/u_invent.png");
	
	public GuiCraftingUInvent(InventoryPlayer inventory, World world, BlockPos pos)
	{
		super(new ContainerUInvent(inventory, world, pos));
	}
	
	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of
	 * the items)
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
	{
		this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 1, 0x4C3C17);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(craftingTableGuiTextures);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		
		this.drawTexturedModalRect(k - 40, l - 40, 0, 0, this.xSize + 80, this.ySize + 80);
	}
}
