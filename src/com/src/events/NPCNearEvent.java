package com.src.events;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.src.main.NPCTracker;

public class NPCNearEvent extends Event implements Cancellable {

	public Object en;
	public UUID enId;
	public String name;
	public String skinOwner;
	public Player p;
	public int id;

	private boolean isCancelled = false;
	private static final HandlerList handlers = new HandlerList();

	public NPCNearEvent(Player p, NPCTracker npct) {
		en = npct.getEntity();
		enId = npct.getUUID();
		name = npct.getName();
		id = npct.getId();
		skinOwner = npct.getOwnerName();
		this.p = p;
	}

	public Object getEntity() {
		return en;
	}
	
	public int getId(){
		return id;
	}

	public UUID getUUID() {
		return enId;
	}

	public String getEntityName() {
		return name;
	}

	public String getSkinOwner() {
		return skinOwner;
	}

	public Player getPlayer() {
		return p;
	}

	@Override
	public boolean isCancelled() {
		return isCancelled;
	}

	@Override
	public void setCancelled(boolean arg0) {
		isCancelled = arg0;

	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
