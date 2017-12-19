package com.src.main.packets;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.src.main.NPCS;

public class PacketManager implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {

		NPCS.injector.addPlayer(e.getPlayer());

		NPCS.npcspawn.updateAllNPCS(e.getPlayer());

	}

}
