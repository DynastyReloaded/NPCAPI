package com.src.events;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.src.events.enums.InteractionType;
import com.src.main.NPCTracker;

public class NPCInteractEvent extends Event implements Cancellable {

	public Object en;
	public UUID enId;
	public String name;
	public String skinOwner;
	public Player p;
	public InteractionType iType;
	public int id;
	public boolean async = false;

	private boolean isCancelled = false;
	private static final HandlerList handlers = new HandlerList();

	public NPCInteractEvent(){
		super(false);
	}
	
	public NPCInteractEvent(Player p, NPCTracker npct, InteractionType iType) {
		en = npct.getEntity();
		enId = npct.getUUID();
		id = npct.getId();
		name = npct.getName();
		skinOwner = npct.getOwnerName();
		this.p = p;
		this.iType = iType;
	}

	public Object getEntity() {
		return en;
	}

	public InteractionType getType() {
		return iType;
	}

	public UUID getUUID() {
		return enId;
	}

	public int getId() {
		return id;
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
