package com.src.commands;

import org.bukkit.entity.Player;

import com.src.armor.EntityListener;
import com.src.armor.EntityManager;

public class Done extends SubCommand{

	@Override
	public void onCommand(Player p, String[] args) {
		
		if(EntityListener.entityManager.containsKey(p)){
			
			EntityManager em = EntityListener.entityManager.get(p);

			em.Done();
			
			EntityListener.entityManager.remove(p);
			
			
		}
		
	}

	@Override
	public String name() {
		return "Done";
	}

	@Override
	public String info() {
		return "Stops editing npc";
	}

	@Override
	public String perm() {
		return "NPC.ADMIN";
	}

}
