package com.sigurd4.bioshock.gui;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import com.sigurd4.bioshock.reference.RefMod;

public class GuiHelmetOverlay extends Gui
{
	protected ResourceLocation texPath;
	protected final Random rand = new Random();
	protected final Minecraft mc;
	
	public GuiHelmetOverlay(Minecraft p_i1036_1_)
	{
		this.mc = p_i1036_1_;
		texPath = new ResourceLocation(RefMod.MODID + ":" + "textures/misc/helmet_overlay_diving_suit_1.png");
	}
	
	/**
	 * Render the ingame overlay with quick icon bar, ...
	 */
	public void renderGameOverlay(String texture)
	{
		texPath = new ResourceLocation(RefMod.MODID + ":" + "textures/misc/helmet_overlay_" + texture + ".png");
		
		ScaledResolution scaledresolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
		int i = scaledresolution.getScaledWidth();
		int j = scaledresolution.getScaledHeight();
		this.mc.entityRenderer.setupOverlayRendering();
		GlStateManager.enableBlend();
		
		ItemStack stack = this.mc.thePlayer.inventory.armorItemInSlot(3);
		
		if(this.mc.gameSettings.thirdPersonView == 0)
		{
			GlStateManager.disableDepth();
			GlStateManager.depthMask(false);
			GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.disableAlpha();
			this.mc.getTextureManager().bindTexture(texPath);
			Tessellator tessellator = Tessellator.getInstance();
			WorldRenderer worldrenderer = tessellator.getWorldRenderer();
			worldrenderer.startDrawingQuads();
			worldrenderer.addVertexWithUV(0.0D, scaledresolution.getScaledHeight(), -90.0D, 0.0D, 1.0D);
			worldrenderer.addVertexWithUV(scaledresolution.getScaledWidth(), scaledresolution.getScaledHeight(), -90.0D, 1.0D, 1.0D);
			worldrenderer.addVertexWithUV(scaledresolution.getScaledWidth(), 0.0D, -90.0D, 1.0D, 0.0D);
			worldrenderer.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
			tessellator.draw();
			GlStateManager.depthMask(true);
			GlStateManager.enableDepth();
			GlStateManager.enableAlpha();
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		}
		
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(icons);
		
		GlStateManager.enableBlend();
		
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.disableLighting();
		GlStateManager.enableAlpha();
	}
}