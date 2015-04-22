package com.sigurd4.bioshock.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.sigurd4.bioshock.extendedentity.ExtendedPlayer;

public class PacketKey implements IMessage
{
	public static enum Key
	{
		RELOAD(0),
		SWITCH_AMMO_TYPE(1),
		RIGHT_CLICK(2),
		NOT_RIGHT_CLICK(3),
		JUMP(4),
		NOT_JUMP(5),
		ZOOM(6),
		NOT_ZOOM(7);
		
		public final int id;
		
		private Key(int id)
		{
			this.id = id;
		}
		
		public static Key get(int id)
		{
			for(int i = 0; i < Key.values().length; ++i)
			{
				if(Key.values()[i].id == id)
				{
					return Key.values()[i];
				}
			}
			return null;
		}
	}
	
	private int i;
	
	public PacketKey()
	{
	}
	
	public PacketKey(Key k)
	{
		this.i = k.id;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.i = buf.readInt();
	}
	
	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.i);
	}
	
	public static class Handler implements IMessageHandler<PacketKey, IMessage>
	{
		public Handler()
		{
		}
		
		@Override
		public IMessage onMessage(PacketKey message, MessageContext context)
		{
			EntityPlayer player = context.getServerHandler().playerEntity;
			return onMessage(message, player);
		}
		
		public static IMessage onMessage(PacketKey message, EntityPlayer player)
		{
			if(player == null)
			{
				return null;
			}
			ExtendedPlayer props = ExtendedPlayer.get(player);
			Key k = Key.get(message.i);
			switch(k)
			{
			case RIGHT_CLICK:
			{
				if(props != null)
				{
					props.setRightClick(true);
				}
				break;
			}
			case NOT_RIGHT_CLICK:
			{
				if(props != null)
				{
					props.setRightClick(false);
				}
				break;
			}
			}
			return null;
		}
	}
}
