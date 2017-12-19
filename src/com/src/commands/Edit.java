package com.src.commands;

import org.bukkit.entity.Player;

import com.src.armor.EntityListener;
import com.src.main.util.MessageManager;

public class Edit  extends SubCommand{

	
	
	@Override
	public void onCommand(Player p, String[] args) {
		
		if(EntityListener.editorList.contains(p)){
			return;
		}else{
			EntityListener.editorList.add(p);
			MessageManager.getInstance().inform(p, "Next NPC clicked will open editor!");
		}
		
	}

	@Override
	public String name() {
		return "Edit";
	}

	@Override
	public String info() {
		return "Edit the armor on the entity";
	}

	@Override
	public String perm() {
		return "NPC.ADMIN";
	}

	
	
}
