package com.src.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.src.main.NPCS;
import com.src.main.util.MessageManager;

public class Spawn extends SubCommand{

	@Override
	public void onCommand(Player p, String[] args) {
		
		if(args.length < 2){
			MessageManager.getInstance().inform(p, ChatColor.RED+ "Correct usage /npc spawn <SkinOwner Name> <NPC Name>");
			return;
		}
		
		StringBuilder sb = new StringBuilder();
		
		for(int i = 1; i < args.length; i++){
			sb.append(args[i]).append(" ");
		}
		
		String s = sb.toString().trim();
		Location loc = p.getLocation();
		
		if(s.length() > 16){
			MessageManager.getInstance().inform(p, "The Custom Name is too long to spawn!");
			return;
		}
		
		if(loc.getYaw() < 0){
			loc.setYaw(loc.getYaw() + 360);
		}
		
		if(loc.getYaw() < 180){
			loc.setYaw(loc.getYaw() + 90 + 180);
		}else{
			loc.setYaw(loc.getYaw() - 90 );
		}
		
		NPCS.npcspawn.spawnNPC(args[0].trim(), s, loc);
		
	}

	@Override
	public String name() {
		return "Spawn";
	}

	@Override
	public String info() {
		return "Spawns NPC";
	}

	@Override
	public String perm() {
		return "NPC.ADMIN";
	}

}
