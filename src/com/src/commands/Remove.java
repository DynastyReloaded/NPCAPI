package com.src.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.src.armor.EntityListener;
import com.src.constructors.BossBarAPI;
import com.src.main.NPCManager;
import com.src.main.NPCS;
import com.src.main.packets.NMSManager;
import com.src.main.util.MessageManager;

public class Remove extends SubCommand {

	@Override
	public void onCommand(Player p, String[] args) {

		if (args.length < 1) {
			MessageManager.getInstance().inform(p, ChatColor.RED + "Correct usage /npc remove <Name>");
			return;
		}
		
		String name;
		
		if(args.length > 1){
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < args.length; i++){
				
				sb.append(args[i]);
				
			}
			name = sb.toString().replace(" ", "").toLowerCase();
		}else{
			name = args[0].toLowerCase();
		}
		
		Class<?> epc = NMSManager.EntityPlayer;
		

		if (NPCManager.entityByName.containsKey(name)) {
			ArrayList<Object> epL = NPCManager.entityByName.get(name);
			
			for (Object ep : epL) {
				
				epc.cast(ep);
				
				if(EntityListener.entityBeingEdited.contains(ep)){
					MessageManager.getInstance().inform(p, "Entity is being edited! Cannot remove.");
					return;
					
				}
				
				for(Player pl : Bukkit.getOnlinePlayers()){
					
					if(NPCS.Pmove.playerBar.containsKey(pl)){
						
						if(NPCS.Pmove.playerBar.get(pl).equals(NPCS.Pmove.entityBar.get(ep))){
							
							BossBarAPI bb = NPCS.Pmove.entityBar.get(ep);
							
							bb.removePlayer(pl);
							
						}
						
					}
					
				}
				
				NPCS.Pmove.entityBar.remove(ep);
				
				NPCS.npcspawn.deleteNPC(ep);
			}
			NPCManager.entityByName.remove(name);
		} else {
			MessageManager.getInstance().inform(p, ChatColor.RED + "Entity Does Not Exist");
			return;
		}

	}

	@Override
	public String name() {
		return "Remove";
	}

	@Override
	public String info() {
		return "Removes NPC";
	}

	@Override
	public String perm() {
		return "NPC.ADMIN";
	}

}
