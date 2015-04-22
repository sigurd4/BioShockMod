package com.sigurd4.bioshock.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.sigurd4.bioshock.Stuff;

public class PacketShieldBreak implements IMessage
{
	public PacketShieldBreak()
	{
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
	}
	
	@Override
	public void toBytes(ByteBuf buf)
	{
	}
	
	public static class Handler implements IMessageHandler<PacketShieldBreak, IMessage>
	{
		@Override
		public IMessage onMessage(PacketShieldBreak message, MessageContext context)
		{
			if(context.side == Side.CLIENT)
			{
				action();
			}
			return null;
		}
		
		@SideOnly(Side.CLIENT)
		public void action()
		{
			EntityPlayer player = Minecraft.getMinecraft().thePlayer;
			Block block = Blocks.stained_glass_pane;
			EnumDyeColor color = EnumDyeColor.ORANGE;
			for(int i = 0; i < 32; ++i)
			{
				double d = 1;
				player.worldObj.spawnParticle(EnumParticleTypes.BLOCK_CRACK, true, player.posX, player.posY + player.getEyeHeight(), player.posZ, Stuff.Randomization.r(d), Stuff.Randomization.r(d), Stuff.Randomization.r(d), new int[]{Block.getStateId(block.getDefaultState().withProperty(BlockStainedGlass.COLOR, color))});
			}
			player.worldObj.playSound(player.posX, player.posY + player.getEyeHeight(), player.posZ, block.stepSound.getBreakSound(), block.stepSound.volume, block.stepSound.frequency, true);
		}
	}
}
