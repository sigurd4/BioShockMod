package com.sigurd4.bioshock.event;

import java.util.HashMap;
import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.sigurd4.bioshock.M;
import com.sigurd4.bioshock.Stuff;
import com.sigurd4.bioshock.block.BlockIceMelting;
import com.sigurd4.bioshock.block.IBlockBreakRegardless;
import com.sigurd4.bioshock.element.Element;
import com.sigurd4.bioshock.extendedentity.ExtendedLivingBase;
import com.sigurd4.bioshock.extendedentity.ExtendedPlayer;
import com.sigurd4.bioshock.item.ItemAudioLog;
import com.sigurd4.bioshock.item.ItemEveHypo;
import com.sigurd4.bioshock.item.ItemMoney;
import com.sigurd4.bioshock.item.ItemPlasmid;
import com.sigurd4.bioshock.packet.PacketShieldBreak;
import com.sigurd4.bioshock.plasmids.Plasmid;
import com.sigurd4.bioshock.plasmids.PlasmidHold;
import com.sigurd4.bioshock.plasmids.PlasmidIronsides;
import com.sigurd4.bioshock.plasmids.PlasmidReturnToSender;
import com.sigurd4.bioshock.reference.RefMass;

public class HandlerCommon
{
	// minecraftforge events for both sides here!
	
	public static HashMap<EntityPlayer, EveHypoLog> eveHypo = new HashMap<EntityPlayer, EveHypoLog>();
	
	public static class EveHypoLog
	{
		public final int plasmidSlot;
		public final ItemStack plasmid;
		public final int hypoSlot;
		
		public EveHypoLog(int plasmidSlot, ItemStack plasmid, int hypoSlot)
		{
			this.plasmidSlot = plasmidSlot;
			this.plasmid = plasmid;
			this.hypoSlot = hypoSlot;
		}
		
		public boolean isValid(EntityPlayer player)
		{
			InventoryPlayer inv = player.inventory;
			if(this.plasmidSlot > inv.getHotbarSize() || this.hypoSlot > inv.getHotbarSize())
			{
				return false;
			}
			if(this.plasmid == null || !(this.plasmid.getItem() instanceof ItemPlasmid))
			{
				return false;
			}
			if(inv.currentItem != this.hypoSlot)
			{
				return false;
			}
			
			if(inv.getStackInSlot(this.plasmidSlot) == null || inv.getStackInSlot(this.plasmidSlot).getItem() != this.plasmid.getItem())
			{
				return false;
			}
			if(inv.getStackInSlot(this.hypoSlot) == null || !(inv.getStackInSlot(this.hypoSlot).getItem() instanceof ItemEveHypo))
			{
				return false;
			}
			return true;
		}
	}
	
	@SubscribeEvent
	public void onLivingDeathEvent(LivingDeathEvent event)
	{
		ExtendedLivingBase props = ExtendedLivingBase.get((EntityLivingBase)event.entity);
		props.dropItems();
		if(event.entity instanceof EntityPlayer)
		{
			ExtendedPlayer props2 = ExtendedPlayer.get((EntityPlayer)event.entity);
			if(!event.entity.worldObj.isRemote)
			{
				NBTTagCompound playerData = new NBTTagCompound();
				props2.saveNBTData(playerData, false);
				HandlerCommonFML.playerDeathData.put((EntityPlayer)event.entity, playerData);
			}
			int money = 10;
			money -= ItemMoney.consumeCash(M.items.money.dollars, (EntityPlayer)event.entity, money) - money;
			money -= ItemMoney.consumeCash(M.items.money.silver_eagles, (EntityPlayer)event.entity, money) - money;
		}
		if(event.entity instanceof EntityLivingBase)
		{
			if(Element.entityElements.get(event.entity) != null)
			{
				Element.entityElements.remove(event.entity);
			}
		}
	}
	
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event)
	{
		if(event.entity instanceof EntityPlayer && ExtendedPlayer.get((EntityPlayer)event.entity) == null)
		{
			ExtendedPlayer.register((EntityPlayer)event.entity);
		}
		if(event.entity instanceof EntityLivingBase && ExtendedLivingBase.get((EntityLivingBase)event.entity) == null)
		{
			ExtendedLivingBase.register((EntityLivingBase)event.entity);
		}
	}
	
	@SubscribeEvent
	public void livingDropsEvent(LivingDropsEvent event)
	{
		for(int i = 0; i < event.drops.size(); ++i)
		{
			ItemStack stack = event.drops.get(i).getEntityItem();
			if(stack.getItem() instanceof ItemAudioLog)
			{
				if(stack.getTagCompound() != null && ItemAudioLog.ISRUNNING.get(stack) > 0)
				{
					if(event.entity.worldObj.isRemote)
					{
						M.proxy.stopAllAudioLogSounds(event.entity);
					}
					ItemAudioLog.ISRUNNING.set(stack, 0);
				}
			}
		}
	}
	
	@SubscribeEvent
	public void livingUpdateEvent(LivingUpdateEvent event)
	{
		if(event.entity instanceof EntityPlayer)
		{
			ExtendedPlayer props = ExtendedPlayer.get((EntityPlayer)event.entity);
			if(props != null)
			{
				props.passiveController.update(event);
			}
			if(event.entity.worldObj.isRemote)
			{
				if(HandlerCommon.eveHypo.containsKey(event.entity) && HandlerCommon.eveHypo.get(event.entity) != null && HandlerCommon.eveHypo.get(event.entity).plasmid == null)
				{
					((EntityPlayer)event.entity).inventory.currentItem = HandlerCommon.eveHypo.get(event.entity).plasmidSlot;
					HandlerCommon.eveHypo.remove(event.entity);
				}
			}
		}
		ExtendedLivingBase props = ExtendedLivingBase.get((EntityLivingBase)event.entity);
		props.onUpdate();
		if(event.entity.isInsideOfMaterial(Material.ice))
		{
			for(int i = 0; i < Math.ceil(event.entity.getEyeHeight()); ++i)
			{
				if(event.entity.worldObj.getBlockState(new BlockPos((int)Math.floor(event.entity.posX + i), (int)Math.floor(event.entity.posY + i), (int)Math.floor(event.entity.posZ + i))).getBlock() instanceof BlockIceMelting)
				{
					event.entityLiving.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 10, 2, true, false));
					event.entityLiving.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 10, 0, true, false));
					event.entityLiving.addPotionEffect(new PotionEffect(Potion.weakness.id, 1200, 4, false, false));
					event.entityLiving.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 10, 1000, true, false));
					event.entityLiving.extinguish();
					event.entity.setPosition((int)Math.floor(event.entity.prevPosX) + 0.5, (int)Math.floor(event.entity.prevPosY + 0.2), (int)Math.floor(event.entity.prevPosZ) + 0.5);
					event.entity.posX = (int)Math.floor(event.entity.posX) + 0.5;
					event.entity.posY = (int)Math.floor(event.entity.posY + 0.2);
					event.entity.posZ = (int)Math.floor(event.entity.posZ) + 0.5;
					event.entity.motionX = event.entity.motionY = event.entity.motionZ = 0;
					event.entity.rotationPitch = event.entity.prevRotationPitch;
					event.entity.rotationYaw = event.entity.prevRotationYaw;
					break;
				}
			}
		}
		if(event.entity instanceof EntityMob)
		{
			EntityMob mob = (EntityMob)event.entity;
			Entity target = null;
			if(EntityCrossbowBolt.isEntityInGasCloud(mob) || mob.getActivePotionEffect(Potion.blindness) != null)
			{
				if(mob.getAttackTarget() != null && mob.getAttackTarget().getDistanceToEntity(mob) > 1.4 && !ExtendedLivingBase.canBlindedEntityTarget(mob))
				{
					target = mob.getAttackTarget();
					mob.setAttackTarget(null);
					mob.setRevengeTarget(null);
				}
			}
			if(!ExtendedLivingBase.canBlindedEntityTarget(mob))
			{
				target = mob.getAttackTarget();
				mob.setAttackTarget(null);
				mob.setRevengeTarget(null);
			}
			if(target != null)
			{
				PathNavigate n = mob.getNavigator();
				//n.clearPathEntity();
				n.tryMoveToEntityLiving(target, mob.getAIMoveSpeed());
			}
		}
		Element.update(event.entityLiving);
	}
	
	@SubscribeEvent
	public void livingSetAttackTargetEvent(LivingSetAttackTargetEvent event)
	{
		if(event.entityLiving != null && event.entityLiving instanceof EntityMob)
		{
			if(!ExtendedLivingBase.canBlindedEntityTarget((EntityMob)event.entityLiving, event.target))
			{
				if(event.target != null)
				{
					((EntityMob)event.entityLiving).setAttackTarget(null);
					((EntityMob)event.entityLiving).setRevengeTarget(null);
				}
				//((EntityMob)event.entityLiving).getNavigator().clearPathEntity();
			}
		}
	}
	
	@SubscribeEvent
	public void livingHurtEvent(LivingHurtEvent event)
	{
		if(event.entity instanceof EntityPlayer)
		{
			ExtendedPlayer props = ExtendedPlayer.get((EntityPlayer)event.entity);
			if(props != null)
			{
				props.passiveController.hurt(event);
			}
		}
		if(event.source != null && event.source.isFireDamage() && event.entityLiving.func_94060_bK() instanceof EntityPlayer)
		{
			ExtendedPlayer props = ExtendedPlayer.get((EntityPlayer)event.entityLiving.func_94060_bK());
			if(props != null)
			{
				props.passiveController.fireDamage(event);
			}
		}
		if(event.source != null && event.source.getEntity() instanceof EntityPlayer)
		{
			ExtendedPlayer props = ExtendedPlayer.get((EntityPlayer)event.source.getEntity());
			if(props != null)
			{
				props.passiveController.attack(event);
			}
		}
		if(event.source.getDamageType() == "arrow")
		{
			if(event.entity instanceof EntityPlayer)
			{
				if(((EntityLivingBase)event.entity).getHeldItem() != null && ((EntityLivingBase)event.entity).getHeldItem().getItem() instanceof ItemPlasmid)
				{
					Plasmid plasmid = ((ItemPlasmid)((EntityLivingBase)event.entity).getHeldItem().getItem()).plasmid;
					ExtendedPlayer props = ExtendedPlayer.get((EntityPlayer)event.entity);
					ExtendedLivingBase.knockback(event.entity, 0);
					if(props.hasPlasmid(plasmid) && plasmid instanceof PlasmidIronsides && PlasmidHold.HELDFOR.get(((EntityLivingBase)event.entity).getHeldItem()) > 0)
					{
						((EntityPlayer)event.entity).inventory.addItemStackToInventory(new ItemStack(Items.arrow));
						event.setCanceled(true);
					}
					ItemStack stack = ((EntityLivingBase)event.entity).getHeldItem();
					if(props.hasPlasmid(plasmid) && plasmid instanceof PlasmidReturnToSender && PlasmidHold.HELDFOR.get(stack) > 0)
					{
						PlasmidReturnToSender.BULLETSHIELDDAMAGE.add(stack, event.ammount);
						PlasmidReturnToSender.BULLETSHIELDMASS.add(stack, RefMass.ARROW);
						PlasmidReturnToSender.BULLETSHIELDSPEED.add(stack, (float)Stuff.MathWithMultiple.distance(event.entity.motionX, event.entity.motionY, event.entity.motionZ));
						event.setCanceled(true);
					}
				}
			}
			if(event.isCanceled())
			{
				((EntityLivingBase)event.entity).setArrowCountInEntity(((EntityLivingBase)event.entity).getArrowCountInEntity() - 1);
			}
		}
		//shields
		if(event.entity instanceof EntityPlayer && event.ammount > 0 && !event.source.isDamageAbsolute())
		{
			ExtendedPlayer props = ExtendedPlayer.get((EntityPlayer)event.entity);
			if(props.getMaxShields() > 0)
			{
				if(props.getShields() > 0)
				{
					props.consumeShields(event.ammount / 2);
					props.setShieldsKnockbackResistance();
					if(props.getShields() > event.ammount / 2)
					{
						event.ammount = 0;
						event.setCanceled(true);
					}
					else
					{
						event.ammount -= props.getShields() * 2;
					}
					
					if(props.getShields() <= 0)
					{
						if(!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayerMP)
						{
							M.network.sendTo(new PacketShieldBreak(), (EntityPlayerMP)event.entity);
						}
						props.setShieldsTimer(640);
						props.passiveController.shieldsBreak(event);
					}
					else
					{
						props.setShieldsTimer(320);
					}
				}
			}
		}
		if(event.ammount < 1)
		{
			event.setCanceled(true);
		}
		if(event.isCanceled())
		{
			ExtendedLivingBase.knockback(event.entity, 0.1F);
			event.setCanceled(false);
		}
		List<Element> es = Element.getElements(event.source);
		for(int i = 0; i < es.size(); ++i)
		{
			Element.addToEntityElement(event.entityLiving, es.get(i), (int)Math.floor(event.ammount * 5 / (1 + (es.size() - 1) / 2)));
		}
	}
	
	@SubscribeEvent
	public void BlockBreakEvent(BlockEvent.BreakEvent event)
	{
		if(!(event.state.getBlock() instanceof IBlockBreakRegardless))
		{
			if(event.getPlayer() != null)
			{
				ItemStack stack = event.getPlayer().getHeldItem();
				if(stack != null)
				{
					Item item = stack.getItem();
					if(item.onBlockStartBreak(stack, event.pos, event.getPlayer()))
					{
						event.setCanceled(true);
					}
				}
			}
		}
	}
}
