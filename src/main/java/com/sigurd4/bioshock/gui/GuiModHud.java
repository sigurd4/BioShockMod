package com.sigurd4.bioshock.gui;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import com.sigurd4.bioshock.audio.AudioHandler;
import com.sigurd4.bioshock.audio.MovingSoundAudioLog;
import com.sigurd4.bioshock.extendedentity.ExtendedPlayer;
import com.sigurd4.bioshock.item.ItemAmmo;
import com.sigurd4.bioshock.item.ItemAudioLog;
import com.sigurd4.bioshock.item.ItemPassiveGear;
import com.sigurd4.bioshock.item.ItemPassiveTonic;
import com.sigurd4.bioshock.item.ItemPassiveTonicSyringe;
import com.sigurd4.bioshock.item.ItemWeaponRanged;
import com.sigurd4.bioshock.passives.Passive;
import com.sigurd4.bioshock.reference.RefMod;

public class GuiModHud extends Gui
{
	protected final Random rand = new Random();
	protected final Minecraft mc;
	
	public static int timer = 0;
	
	protected static int audiologProgress = 0;
	protected static int audiologAnimTick = 0;
	protected static int audiologIsRunning = 0;
	protected static int audiologRunningFor = 0;
	protected static String audiologName = "";
	protected static String audiologOwner = "";
	protected static ArrayList<ResourceLocation> anim = null;
	protected static ArrayList<ResourceLocation> audiolog1AnimTex = new ArrayList<ResourceLocation>();
	protected static ArrayList<ResourceLocation> audiolog2AnimTex = new ArrayList<ResourceLocation>();
	protected static ResourceLocation audiologBackground = new ResourceLocation(RefMod.MODID + ":" + "textures/gui/audiolog/layer_0.png");
	protected static ResourceLocation audiologFrame = new ResourceLocation(RefMod.MODID + ":" + "textures/gui/audiolog/layer_1.png");
	protected static int line1 = 0;
	protected static int w = 0;
	protected static int h = 0;
	
	public GuiModHud(Minecraft mc)
	{
		this.mc = mc;
		audiolog1AnimTex.clear();
		audiolog2AnimTex.clear();
		for(int i = 0; i < 8; ++i)
		{
			audiolog1AnimTex.add(new ResourceLocation(RefMod.MODID + ":" + "textures/gui/audiolog/audio_diary_anim_" + i + ".png"));
		}
		for(int i = 0; i < 5; ++i)
		{
			audiolog2AnimTex.add(new ResourceLocation(RefMod.MODID + ":" + "textures/gui/audiolog/voxophone_anim_" + i + ".png"));
		}
	}
	
	public static enum RenderOrder
	{
		PRE, POST
	}
	
	/**
	 * Render ingame text in the corner, ...
	 */
	public void renderGameOverlay(EntityPlayer player, RenderOrder order, ElementType type)
	{
		ScaledResolution scaledresolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
		w = scaledresolution.getScaledWidth();
		h = scaledresolution.getScaledHeight();
		FontRenderer fr = this.mc.fontRendererObj;
		
		if(this.mc.inGameHasFocus)
		{
			line1 = 0;
			
			this.ammoStats(player, fr, order, type);
			this.eveStats(player, fr, order, type);
			this.shieldsStats(player, fr, order, type);
			this.oxygenStats(player, fr, order, type);
			this.tonicList(player, fr, order, type);
		}
		this.audiologPlaying(player, fr, order, type);
		
		++timer;
	}
	
	private int getInventoryItem(InventoryPlayer inventory, Item item)
	{
		int j = 0;
		int k;
		ItemStack stack;
		
		for(k = 0; k < inventory.mainInventory.length; ++k)
		{
			stack = inventory.mainInventory[k];
			
			if(stack != null && (item == null || stack.getItem() == item))
			{
				if(item instanceof ItemAmmo)
				{
					j += item.getMaxDamage() - stack.getItemDamage();
					if(stack.stackSize > 1)
					{
						j += stack.stackSize - 1;
					}
				}
				else
				{
					j += stack.stackSize;
				}
			}
		}
		return j;
	}
	
	private MovingSoundAudioLog getThePlayingAudiolog()
	{
		ArrayList<ISound> sounds = AudioHandler.sounds.get(this.mc.thePlayer);
		if(sounds != null)
		{
			for(int i = 0; i < sounds.size(); ++i)
			{
				if(sounds.get(i) instanceof MovingSoundAudioLog)
				{
					return (MovingSoundAudioLog)sounds.get(i);
				}
			}
		}
		return null;
	}
	
	private ItemStack getThePlayingAudiolog(MovingSoundAudioLog sound)
	{
		for(int i = 0; i < this.mc.thePlayer.inventory.getSizeInventory(); ++i)
		{
			ItemStack stack = this.mc.thePlayer.inventory.getStackInSlot(i);
			if(stack != null && stack.getItem() instanceof ItemAudioLog)
			{
				if(stack.getTagCompound() != null)
				{
					if(ItemAudioLog.LOG.get(stack).equals(sound.name) && ItemAudioLog.OWNER.get(stack).equals(sound.owner))
					{
						return stack;
					}
				}
			}
		}
		return null;
	}
	
	private void renderAudioLogPlaying(ResourceLocation rl)
	{
		ScaledResolution scaledresolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
		int w = scaledresolution.getScaledWidth();
		int h = scaledresolution.getScaledHeight();
		
		float f = 0.25F;
		GlStateManager.scale(f, f, f);
		
		this.mc.getTextureManager().bindTexture(rl);
		this.drawTexturedModalRect((int)(w / f - audiologProgress / f), (int)(h / f * 3 / 4), 0, 0, (int)(64 / f), (int)(64 / f));
		
		f = 1 / f;
		GlStateManager.scale(f, f, f);
	}
	
	private void renderPlayerSkin(int posX, int posY, float scale, int offsetX, int offsetY, int sizeX, int sizeY)
	{
		ScaledResolution scaledresolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
		int w = scaledresolution.getScaledWidth();
		int h = scaledresolution.getScaledHeight();
		
		ResourceLocation resourcelocation = DefaultPlayerSkin.getDefaultSkinLegacy();
		
		GameProfile profile = audiologOwner != null && audiologOwner.length() > 0 ? new GameProfile((UUID)null, audiologOwner) : null;
		if(profile != null)
		{
			//AbstractClientPlayer.getLocationSkin(audiologOwner)
			Map map = this.mc.getSkinManager().loadSkinFromCache(profile);
			
			if(map.containsKey(Type.SKIN))
			{
				resourcelocation = this.mc.getSkinManager().loadSkin((MinecraftProfileTexture)map.get(Type.SKIN), Type.SKIN);
			}
		}
		if(resourcelocation != null)
		{
			float f = 0.25F;
			float y = f * 2;
			GlStateManager.scale(y, f, f);
			GlStateManager.scale(scale, scale, scale);
			
			this.mc.getTextureManager().bindTexture(resourcelocation);
			this.drawTexturedModalRect((int)((w - audiologProgress + posX) / y / scale), (int)((h * 3 / 4 + posY) / f / scale), (int)(offsetX / f), (int)(offsetY / f), (int)(sizeX / y), (int)(sizeY / f));
			
			f = 1 / f;
			y = 1 / y;
			GlStateManager.scale(y, f, f);
			GlStateManager.scale(1 / scale, 1 / scale, 1 / scale);
		}
	}
	
	private void ammoStats(EntityPlayer player, FontRenderer fontrenderer, RenderOrder order, ElementType type)
	{
		this.mc.mcProfiler.startSection("ammoStats");
		GlStateManager.pushMatrix();
		if(player.getHeldItem() != null)
		{
			if(player.getHeldItem().getItem() instanceof ItemWeaponRanged)
			{
				ItemStack stack = player.getHeldItem();
				ItemWeaponRanged item = (ItemWeaponRanged)stack.getItem();
				fontrenderer.drawStringWithShadow("Ammo: " + ItemWeaponRanged.AMMO.get(stack) + "/" + ((ItemWeaponRanged)stack.getItem()).CAPACITY.get(stack), 2, h - 10 - 10 * ((ItemWeaponRanged)stack.getItem()).allAmmoTypes.length, 0xAF8A33);
				for(int i = ((ItemWeaponRanged)stack.getItem()).allAmmoTypes.length; i > 0 && i <= ((ItemWeaponRanged)stack.getItem()).allAmmoTypes.length; --i)
				{
					String s = item.allAmmoTypes[item.allAmmoTypes.length - i].toString();
					fontrenderer.drawStringWithShadow(s, 2, h - 10 - 10 * (item.allAmmoTypes.length - i), (Boolean)((ItemWeaponRanged)stack.getItem()).UPGRADES.get(stack, item.allAmmoTypes[item.allAmmoTypes.length - i]) ? 0xAF8A33 : 0x424242);
				}
			}
		}
		GlStateManager.popMatrix();
		this.mc.mcProfiler.endSection();
	}
	
	private void eveStats(EntityPlayer player, FontRenderer fontrenderer, RenderOrder order, ElementType type)
	{
		this.mc.mcProfiler.startSection("eveStats");
		GlStateManager.pushMatrix();
		ExtendedPlayer props = ExtendedPlayer.get(player);
		
		if(props != null && props.getMaxEve() > 0 && props.getEve() > 0 && props.hasAnyPlasmids())
		{
			fontrenderer.drawStringWithShadow("Eve: " + props.getEve() + "/" + props.getMaxEve(), 2, 2 + 9 * line1, 0x5555FF);
			++line1;
		}
		GlStateManager.popMatrix();
		this.mc.mcProfiler.endSection();
	}
	
	private void shieldsStats(EntityPlayer player, FontRenderer fontrenderer, RenderOrder order, ElementType type)
	{
		this.mc.mcProfiler.startSection("shieldsStats");
		GlStateManager.pushMatrix();
		ExtendedPlayer props = ExtendedPlayer.get(player);
		if(props != null && props.getMaxShields() > 0)
		{
			fontrenderer.drawStringWithShadow("Shields: " + Math.ceil(props.getShields() * 10) / 10 + "/" + props.getMaxShields(), 2, 2 + 9 * line1, props.getShields() > 0 ? 0xFFAA00 : 0x555555);
			++line1;
		}
		GlStateManager.popMatrix();
		this.mc.mcProfiler.endSection();
	}
	
	private void oxygenStats(EntityPlayer player, FontRenderer fontrenderer, RenderOrder order, ElementType type)
	{
		this.mc.mcProfiler.startSection("oxygenStats");
		GlStateManager.pushMatrix();
		if(player.getEquipmentInSlot(3) != null && player.getEquipmentInSlot(3).getItem() instanceof ItemArmorDivingSuitTank)
		{
			ItemStack stack = player.getEquipmentInSlot(3);
			if(ItemArmorDivingSuitTank.ISSEALED.get(stack))
			{
				fontrenderer.drawStringWithShadow("Oxygen: " + ItemArmorDivingSuitTank.AIR.get(stack) + "/" + ItemArmorDivingSuitTank.MAXAIR.get(stack), 2, 2 + 9 * line1, 0xD1EBFF);
				++line1;
			}
		}
		GlStateManager.popMatrix();
		this.mc.mcProfiler.endSection();
	}
	
	private void tonicList(EntityPlayer player, FontRenderer fontrenderer, RenderOrder order, ElementType type)
	{
		this.mc.mcProfiler.startSection("tonicList");
		GlStateManager.pushMatrix();
		ExtendedPlayer props = ExtendedPlayer.get(player);
		if(player.getHeldItem() != null && (player.getHeldItem().getItem() instanceof ItemPassiveTonic || player.getHeldItem().getItem() instanceof ItemPassiveTonicSyringe))
		{
			ArrayList<Passive> p = props.tonicsCombat;
			int x = 130;
			int line2 = 0;
			fontrenderer.drawStringWithShadow("(" + p.size() + "/" + props.maxCombatTonics + ") Combat Tonics:", w - x, 2 + 9 * line2, 0x9CEE00);
			++line2;
			for(int i = 0; i < p.size(); ++i)
			{
				fontrenderer.drawStringWithShadow(" - " + StatCollector.translateToLocal("item.tonic." + p.get(i).name + ".name"), w - x, 2 + 9 * line2, 0x9CEE00);
				++line2;
			}
			
			p = props.tonicsEngineering;
			fontrenderer.drawStringWithShadow("(" + p.size() + "/" + props.maxEngineeringTonics + ") Engineering Tonics:", w - x, 2 + 9 * line2, 0xEEB900);
			++line2;
			for(int i = 0; i < p.size(); ++i)
			{
				fontrenderer.drawStringWithShadow(" - " + StatCollector.translateToLocal("item.tonic." + p.get(i).name + ".name"), w - x, 2 + 9 * line2, 0xEEB900);
				++line2;
			}
			
			p = props.tonicsPhysical;
			fontrenderer.drawStringWithShadow("(" + p.size() + "/" + props.maxPhysicalTonics + ") Physical Tonics:", w - x, 2 + 9 * line2, 0x68BDD1);
			++line2;
			for(int i = 0; i < p.size(); ++i)
			{
				fontrenderer.drawStringWithShadow(" - " + StatCollector.translateToLocal("item.tonic." + p.get(i).name + ".name"), w - x, 2 + 9 * line2, 0x68BDD1);
				++line2;
			}
			
			p = props.getAllGear();
			fontrenderer.drawStringWithShadow("Gear:", w - x, 2 + 9 * line2, 0x5555FF);
			++line2;
			ItemStack[] armor = player.inventory.armorInventory;
			for(int i = 0; i < armor.length; ++i)
			{
				if(armor[i] != null && armor[i].getItem() instanceof ItemPassiveGear)
				{
					String s = "   -  ";
					switch(i)
					{
					case 3:
						s = "Hat:   ";
						break;
					case 2:
						s = "Shirt: ";
						break;
					case 1:
						s = "Pants: ";
						break;
					case 0:
						s = "Boots: ";
						break;
					}
					fontrenderer.drawStringWithShadow(" " + s + StatCollector.translateToLocal(armor[i].getItem().getUnlocalizedName() + ".name"), w - x, 2 + 9 * line2, 0x5555FF);
					++line2;
				}
			}
		}
		GlStateManager.popMatrix();
		this.mc.mcProfiler.endSection();
	}
	
	private void audiologPlaying(EntityPlayer player, FontRenderer fontrenderer, RenderOrder order, ElementType type)
	{
		if(!(order == RenderOrder.POST && type == ElementType.EXPERIENCE))
		{
			return;
		}
		this.mc.mcProfiler.startSection("audiolog");
		GlStateManager.pushMatrix();
		int max = 80;
		boolean b = false;
		MovingSoundAudioLog sound = this.getThePlayingAudiolog();
		if(sound != null)
		{
			ItemStack stack = this.getThePlayingAudiolog(sound);
			if(stack != null)
			{
				audiologIsRunning = ItemAudioLog.ISRUNNING.get(stack);
				audiologRunningFor = ItemAudioLog.TIME.get(stack);
				
				audiologName = ItemAudioLog.LOG.get(stack);
				audiologOwner = ItemAudioLog.OWNER.get(stack);
				b = true;
				anim = sound.type ? audiolog1AnimTex : audiolog2AnimTex;
			}
		}
		if(!b)
		{
			audiologIsRunning = audiologRunningFor = 0;
			audiologName = audiologOwner = "";
		}
		
		if(audiologRunningFor <= 0)
		{
			audiologProgress -= 2;
			if(audiologProgress < 0)
			{
				audiologProgress = 0;
			}
		}
		else
		{
			if(anim != null && anim.size() > 0)
			{
				int i = (int)Math.ceil(32F / anim.size());
				if(timer % i == i - 1)
				{
					++audiologAnimTick;
				}
			}
			
			audiologProgress += 2;
			if(audiologProgress > max)
			{
				audiologProgress = max;
			}
		}
		
		if(anim != null && audiologProgress > 0)
		{
			int animIndex = audiologAnimTick;
			while(animIndex >= anim.size())
			{
				animIndex -= anim.size();
			}
			ResourceLocation rl = anim.get(animIndex);
			
			this.renderAudioLogPlaying(audiologBackground);
			
			GlStateManager.depthMask(false);;
			this.renderPlayerSkin(12, 14, 1.5F, 8, 16, 16, 16);
			float shade = 0.7F;
			GlStateManager.color(shade, shade, shade);
			this.renderPlayerSkin(12, 38, 1.5F, 20, 40, 16, 16);
			this.renderPlayerSkin(3, 38, 1.5F, 45, 40, 6, 16);
			this.renderPlayerSkin(36, 38, 1.5F, 44, 40, 6, 16);
			GlStateManager.color(1 / shade, 1 / shade, 1 / shade);
			GlStateManager.depthMask(false);
			
			GlStateManager.enableBlend();
			this.renderAudioLogPlaying(audiologFrame);
			GlStateManager.disableBlend();
			this.renderAudioLogPlaying(rl);
		}
		GlStateManager.popMatrix();
		this.mc.mcProfiler.endSection();
	}
}