package com.src.main.packets;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.entity.Player;

import io.netty.channel.Channel;

public class PacketInjector {
	private Field EntityPlayer_playerConnection;
	private Class<?> PlayerConnection;
	private Field PlayerConnection_networkManager;

	private Class<?> NetworkManager;
	private Field k;
	private Field m;

	public PacketInjector() {
		try {
			EntityPlayer_playerConnection = Reflection.getField(NMSManager.EntityPlayer,
					"playerConnection");

			PlayerConnection = Reflection.getNMSClass("PlayerConnection");
			PlayerConnection_networkManager = Reflection.getField(
					PlayerConnection, "networkManager");

			NetworkManager = Reflection.getNMSClass("NetworkManager");
			k = Reflection.getField(NetworkManager, "channel");
			m = Reflection.getField(NetworkManager, "m");
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public void addPlayer(Player p) {
		try {
	        Method playerHandle = Reflection.getMethod(p.getClass(), "getHandle");
			Channel ch = getChannel(getNetworkManager(playerHandle.invoke(p)));
			if (ch.pipeline().get("PacketInjector") == null) {
				PacketHandler h = new PacketHandler(p);
				ch.pipeline().addBefore("packet_handler", "PacketInjector", h);
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public void removePlayer(Player p) {
		try {
	        Method playerHandle = Reflection.getMethod(p.getClass(), "getHandle");
			Channel ch = getChannel(getNetworkManager(playerHandle.invoke(p)));
			if (ch.pipeline().get("PacketInjector") != null) {
				ch.pipeline().remove("PacketInjector");
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	private Object getNetworkManager(Object ep) {
		return Reflection.getFieldValue(PlayerConnection_networkManager, Reflection.getFieldValue(EntityPlayer_playerConnection, ep));
	}

	private Channel getChannel(Object networkManager) {
		Channel ch = null;
		try {
			ch = Reflection.getFieldValue(k, networkManager);
		} catch (Exception e) {
			ch = Reflection.getFieldValue(m, networkManager);
		}
		return ch;
	}

}
