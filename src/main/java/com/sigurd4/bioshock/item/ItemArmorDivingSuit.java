package com.sigurd4.bioshock.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.sigurd4.bioshock.M;
import com.sigurd4.bioshock.Stuff;
import com.sigurd4.bioshock.gui.GuiHelmetOverlay;
import com.sigurd4.bioshock.reference.RefMod;
import com.sigurd4.bioshock.render.model.ModelArmorDivingSuit;

public class ItemArmorDivingSuit extends ItemArmor
{
	@SideOnly(Side.CLIENT)
	public static final ModelArmorDivingSuit modelArmorDivingSuit1 = new ModelArmorDivingSuit(1.0f, false);
	@SideOnly(Side.CLIENT)
	public static final ModelArmorDivingSuit modelArmorDivingSuit2 = new ModelArmorDivingSuit(0.5f, false);
	
	public String modelTexture;
	public boolean isMetal;
	public String texture;
	public String overlayTexture = null;
	
	public ItemArmorDivingSuit(ArmorMaterial material, EnumArmorType armorType, String texture, boolean isMetal)
	{
		super(material, 5, armorType.ordinal());
		this.setCreativeTab(M.tabs.core);
		this.texture = texture;
		this.modelTexture = RefMod.MODID + ":" + "textures/models/armor/" + texture + ".png";
		this.isMetal = isMetal;
	}
	
	public ItemArmorDivingSuit(ArmorMaterial material, EnumArmorType armorType, String texture, String overlayTexture, boolean isMetal)
	{
		this(material, armorType, texture, isMetal);
		this.overlayTexture = overlayTexture;
	}
	
	/**
	 * Called when the client starts rendering the HUD, for whatever item the
	 * player currently has as a helmet.
	 * This is where pumpkins would render there overlay.
	 * 
	 * @param stack
	 *            The ItemStack that is equipped
	 * @param player
	 *            Reference to the current client entity
	 * @param resolution
	 *            Resolution information about the current viewport and
	 *            configured GUI Scale
	 * @param partialTicks
	 *            Partial ticks for the renderer, useful for interpolation
	 * @param hasScreen
	 *            If the player has a screen up, which will be rendered after
	 *            this.
	 * @param mouseX
	 *            Mouse's X position on screen
	 * @param mouseY
	 *            Mouse's Y position on screen
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void renderHelmetOverlay(ItemStack stack, EntityPlayer player, ScaledResolution resolution, float partialTicks)
	{
		if(this.overlayTexture != null)
		{
			GuiHelmetOverlay renderer = new GuiHelmetOverlay(Minecraft.getMinecraft());
			renderer.renderGameOverlay(this.overlayTexture);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemstack, int armorSlot)
	{
		if(itemstack == null)
		{
			return null;
		}
		else
		{
			ModelBiped armor = null;
			if(!(itemstack.getItem() instanceof ItemArmorDivingSuit))
			{
				return null;
			}
			else
			{
				if(this.armorType() == EnumArmorType.LEGS)
				{
					armor = modelArmorDivingSuit2;
				}
				else
				{
					armor = modelArmorDivingSuit1;
				}
			}
			if(armor != null && entityLiving instanceof EntityPlayer)
			{
				armor = Stuff.Render.getModel((EntityPlayer)entityLiving, armor);
			}
			return armor;
		}
	}
	
	@Override
	public String getArmorTexture(ItemStack itemstack, Entity entity, int slot, String type)
	{
		return this.modelTexture;
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
	{
		if(!player.onGround && !player.isCollidedVertically && !player.isRiding() && !player.isOnLadder())
		{
			double i = this.isMetal ? 1D : 0.2D;
			if(player.motionY < 0)
			{
				if(player.motionY > 0)
				{
					player.motionY = player.motionY * (1 + 0.1D * i);
				}
			}
			else
			{
				player.motionY = player.motionY - 0.012D * i;
			}
			if(player.motionY < 0)
			{
				player.fallDistance = player.fallDistance + 0.252F * (float)i;
			}
		}
		if((player.onGround || player.motionY > 0) && player.getActivePotionEffect(Potion.damageBoost) == null)
		{
			if(player.onGround)
			{
				player.motionX = player.motionX * 0.9D;
				player.motionZ = player.motionZ * 0.9D;
			}
			else
			{
				player.motionX = player.motionX * 0.99D;
				player.motionZ = player.motionZ * 0.99D;
			}
		}
	}
	
	public EnumArmorType armorType()
	{
		if(EnumArmorType.values().length > this.armorType && this.armorType > 0)
		{
			return EnumArmorType.values()[this.armorType];
		}
		return null;
	}
}
