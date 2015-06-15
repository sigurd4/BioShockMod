package com.sigurd4.bioshock.render.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelArmorDivingSuit extends ModelBiped
{
	public ModelRenderer tank1;
	public ModelRenderer tank2;
	public ModelRenderer tank3;
	
	public boolean renderTank;
	
	public ModelArmorDivingSuit(float f, boolean renderTank)
	{
		super(f, 0, 64, 64);
		
		this.renderTank = renderTank;
		
		this.tank1 = new ModelRenderer(this, 0, 32);
		this.tank1.addBox(-0.5F, 1F, 2F, 4, 10, 4);
		this.tank1.setRotationPoint(0F, 0F, 0F);
		this.tank1.setTextureSize(64, 64);
		this.tank1.mirror = true;
		this.setRotation(this.tank1, 0F, 0F, 0F);
		this.bipedBody.addChild(this.tank1);
		this.tank2 = new ModelRenderer(this, 9, 32);
		this.tank2.addBox(0F, 0F, 2.5F, 3, 0, 3);
		this.tank2.setRotationPoint(0F, 0F, 0F);
		this.tank2.setTextureSize(64, 64);
		this.tank2.mirror = true;
		this.setRotation(this.tank2, 0F, 0F, 0F);
		this.bipedBody.addChild(this.tank2);
		this.tank3 = new ModelRenderer(this, 0, 32);
		this.tank3.addBox(1F, 0F, 3.5F, 1, 1, 1);
		this.tank3.setRotationPoint(0F, 0F, 0F);
		this.tank3.setTextureSize(64, 64);
		this.tank3.mirror = true;
		this.setRotation(this.tank3, 0F, 0F, 0F);
		this.bipedBody.addChild(this.tank3);
	}
	
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		this.isChild = false;
		super.render(entity, f, f1, f2, f3, f4, f5);
		if(this.renderTank)
		{
			GlStateManager.pushMatrix();
			GlStateManager.scale(1.01F, 1.01F, 1.01F);
			
			if(this.isChild)
			{
				float f6 = 2.0F;
				GlStateManager.scale(1.0F / f6, 1.0F / f6, 1.0F / f6);
				GlStateManager.translate(0.0F, 24.0F * f5, 0.0F);
				this.tank1.render(f5);
				this.tank2.render(f5);
				this.tank3.render(f5);
			}
			else
			{
				if(entity.isSneaking())
				{
					GlStateManager.translate(0.0F, 0.2F, 0.0F);
				}
				
				this.tank1.render(f5);
				this.tank2.render(f5);
				this.tank3.render(f5);
			}
			GlStateManager.popMatrix();
		}
	}
	
	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
