package com.src.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.src.armor.EntityListener;

public class ArmorStandInteractEvent extends Event implements Cancellable {


	public Object en;
	public Player p;
	public int id;

	private boolean isCancelled = false;
	private static final HandlerList handlers = new HandlerList();

	public ArmorStandInteractEvent(Player p, int id) {

		this.p = p;
		this.id = id;
		en = EntityListener.armorstandId.get(id);
		
	}

	public Object getEntity() {
		return en;
	}
	
	public int getId(){
		return id;
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
