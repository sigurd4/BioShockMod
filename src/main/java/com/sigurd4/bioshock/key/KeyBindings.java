package com.sigurd4.bioshock.key;

import java.util.HashMap;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

import org.lwjgl.input.Keyboard;

import com.sigurd4.bioshock.M;
import com.sigurd4.bioshock.packet.PacketKey;
import com.sigurd4.bioshock.packet.PacketKey.Key;
import com.sigurd4.bioshock.reference.RefKeyBinds;

public final class KeyBindings
{
	public static KeyBinding weaponNextAmmoType;
	public static KeyBinding weaponReload;
	public static KeyBinding weaponZoom;
	
	public static void init()
	{
		weaponNextAmmoType = new KeyBinding(RefKeyBinds.KEY + "." + RefKeyBinds.WEAPON_NEXT_AMMO_TYPE, Keyboard.KEY_B, RefKeyBinds.KEY + "." + RefKeyBinds.CATEGORY_WEAPON);
		weaponReload = new KeyBinding(RefKeyBinds.KEY + "." + RefKeyBinds.WEAPON_RELOAD, Keyboard.KEY_R, RefKeyBinds.KEY + "." + RefKeyBinds.CATEGORY_WEAPON);
		weaponZoom = new KeyBinding(RefKeyBinds.KEY + "." + RefKeyBinds.WEAPON_ZOOM, Keyboard.KEY_Z, RefKeyBinds.KEY + "." + RefKeyBinds.CATEGORY_WEAPON);
		
		ClientRegistry.registerKeyBinding(weaponReload);
		ClientRegistry.registerKeyBinding(weaponZoom);
		ClientRegistry.registerKeyBinding(weaponNextAmmoType);
	}
	
	private static HashMap<KeyBinding, Integer> keyPressed = new HashMap<KeyBinding, Integer>();
	
	private static boolean keypress(KeyBinding k)
	{
		if(!keyPressed.containsKey(k))
		{
			keyPressed.put(k, 0);
		}
		boolean b = true;
		if(!k.isKeyDown() || keyPressed.get(k) > 0)
		{
			b = false;
		}
		keyPressed.put(k, k.isPressed() ? keyPressed.get(k) + 1 : 0);
		return b;
	}
	
	private static boolean keyhold(KeyBinding k, int rate)
	{
		if(!keyPressed.containsKey(k) || !k.isKeyDown())
		{
			keyPressed.put(k, 0);
			return false;
		}
		if(k.isKeyDown() && keyPressed.get(k) >= rate)
		{
			keyPressed.put(k, 0);
			return true;
		}
		keyPressed.put(k, k.isKeyDown() ? keyPressed.get(k) + 1 : 0);
		return false;
	}
	
	private static void sendKey(Key k)
	{
		PacketKey m = new PacketKey(k);
		M.network.sendToServer(m);
		PacketKey.Handler.onMessage(m, Minecraft.getMinecraft().thePlayer);
	}
	
	public static void onKeyInput(InputEvent.KeyInputEvent event)
	{
		if(keypress(weaponNextAmmoType))
		{
			sendKey(Key.SWITCH_AMMO_TYPE);
		}
	}
	
	public static void clientTickEvent(ClientTickEvent event)
	{
		if(keyhold(weaponReload, 0))
		{
			sendKey(Key.RELOAD);
		}
		if(keyhold(Minecraft.getMinecraft().gameSettings.keyBindUseItem, 0))
		{
			sendKey(Key.RIGHT_CLICK);
		}
		else
		{
			sendKey(Key.NOT_RIGHT_CLICK);
		}
		if(keyhold(Minecraft.getMinecraft().gameSettings.keyBindJump, 0))
		{
			sendKey(Key.JUMP);
		}
		else
		{
			sendKey(Key.NOT_JUMP);
		}
		if(keyhold(weaponZoom, 0))
		{
			sendKey(Key.ZOOM);
		}
		else
		{
			sendKey(Key.NOT_ZOOM);
		}
	}
}
