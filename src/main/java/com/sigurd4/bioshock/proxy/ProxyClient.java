package com.sigurd4.bioshock.proxy;

import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.sigurd4.bioshock.M;
import com.sigurd4.bioshock.M.Id;
import com.sigurd4.bioshock.audio.AudioHandler;
import com.sigurd4.bioshock.audio.MovingSoundAudioLog;
import com.sigurd4.bioshock.event.HandlerClient;
import com.sigurd4.bioshock.event.HandlerClientFML;
import com.sigurd4.bioshock.item.ItemAudioLog;
import com.sigurd4.bioshock.item.ItemWeaponSkyHook;
import com.sigurd4.bioshock.key.KeyBindings;
import com.sigurd4.bioshock.packet.PacketPlayerSkin;
import com.sigurd4.bioshock.particles.ParticleHandler;
import com.sigurd4.bioshock.particles.ParticleHandler.EnumParticleTypes2;
import com.sigurd4.bioshock.reference.RefMod;

public class ProxyClient extends ProxyCommon
{
	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);
		
		MinecraftForge.EVENT_BUS.register(new HandlerClient());
		FMLCommonHandler.instance().bus().register(new HandlerClientFML());
		KeyBindings.init();
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
	
	@Override
	public World world(int dimension)
	{
		return Minecraft.getMinecraft().theWorld;
	}
	
	@Override
	public void sendPlayerSkinData(EntityPlayer entity)
	{
		if(entity instanceof AbstractClientPlayer)
		{
			if(((AbstractClientPlayer)entity).getLocationSkin().toString() != null)
			{
				M.network.sendToServer(new PacketPlayerSkin(((AbstractClientPlayer)entity).getLocationSkin().toString()));
			}
		}
		super.sendPlayerSkinData(entity);
	}
	
	@Override
	public void playTargetSound(BlockPos oldHookCoords, MovingObjectPosition pos, EntityLivingBase thrower)
	{
		String sound = RefMod.MODID + ":" + "item.weapon.skyhook.target";
		if(thrower.getHeldItem() != null && thrower.getHeldItem().getItem() instanceof ItemWeaponSkyHook && oldHookCoords != null)
		{
			if(ItemWeaponSkyHook.CAN_HOOK.get(thrower.getHeldItem()) > 0 && (oldHookCoords.getX() != pos.getBlockPos().getX() || oldHookCoords.getY() != pos.getBlockPos().getY() || oldHookCoords.getZ() != pos.getBlockPos().getZ()) && AudioHandler.getSound(thrower, sound) == null && Minecraft.getMinecraft().thePlayer.equals(thrower))
			{
				if(ItemWeaponSkyHook.soundDelay <= 0)
				{
					AudioHandler.stopSound(thrower, sound);
					AudioHandler.createMovingEntitySound(thrower, sound, 1, 1, false);
					ItemWeaponSkyHook.soundDelay = 30;
				}
			}
		}
		super.playTargetSound(oldHookCoords, pos, thrower);
	}
	
	@Override
	public void createAudioLogSound(Entity entity, ItemStack stack, boolean b)
	{
		//stopAllAudioLogSounds(entity);
		ISound sound = new MovingSoundAudioLog(entity, ItemAudioLog.LOG.get(stack), ItemAudioLog.OWNER.get(stack), b);
		if(Minecraft.getMinecraft().getSoundHandler().getSound(sound.getSoundLocation()) == null)
		{
			AudioHandler.soundsToStop.add(sound);
			return;
		}
		if(AudioHandler.sounds.get(entity) == null)
		{
			AudioHandler.sounds.put(entity, new ArrayList<ISound>());
		}
		AudioHandler.sounds.get(entity).add(sound);
		AudioHandler.soundsToPlay.add(sound);
		
		super.createAudioLogSound(entity, stack, b);
	}
	
	@Override
	public void stopAllAudioLogSounds(Entity entity)
	{
		if(AudioHandler.sounds.get(entity) != null)
		{
			for(int i = 0; i < AudioHandler.sounds.get(entity).size(); ++i)
			{
				if(AudioHandler.sounds.get(entity).get(i) instanceof MovingSoundAudioLog)
				{
					AudioHandler.stopSound(entity, AudioHandler.sounds.get(entity).get(i).toString());
				}
			}
		}
		super.stopAllAudioLogSounds(entity);
	}
	
	@Override
	public void particle(EnumParticleTypes2 particleEnum, World world, boolean ignoreDistance, double x, double y, double z, double mx, double my, double mz, int... par)
	{
		ParticleHandler.particle(particleEnum, world, ignoreDistance, x, y, z, mx, my, mz, par);
	}
}