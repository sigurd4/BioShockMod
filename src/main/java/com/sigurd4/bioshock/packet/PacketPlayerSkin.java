package com.sigurd4.bioshock.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.sigurd4.bioshock.item.ItemPlasmid;

public class PacketPlayerSkin implements IMessage
{
	private String skin;
	
	public PacketPlayerSkin()
	{
	}
	
	public PacketPlayerSkin(String s)
	{
		this.skin = s;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		skin = ByteBufUtils.readUTF8String(buf);
	}
	
	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, skin);
	}
	
	public static class Handler implements IMessageHandler<PacketPlayerSkin, IMessage>
	{
		@Override
		public IMessage onMessage(PacketPlayerSkin message, MessageContext context)
		{
			EntityPlayer player = context.getServerHandler().playerEntity;
			if(message.skin != null)
			{
				if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemPlasmid)
				{
					ItemPlasmid.skins.put(player, message.skin);
				}
			}
			return null;
		}
	}
}
