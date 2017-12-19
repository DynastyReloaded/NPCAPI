package com.src.commands;

import org.bukkit.entity.Player;

import com.src.main.NPCManager;
import com.src.main.util.MessageManager;

public class Command extends SubCommand {

	@Override
	public void onCommand(Player p, String[] args) {

		if (args.length < 1) {
			MessageManager.getInstance().inform(p, "Correct usage /npc command <command>");
			return;
		}

		StringBuilder sb = new StringBuilder();
		for (String s : args) {
			sb.append(s).append(" ");
		}

		String temp = sb.toString();
		if (temp.contains("Gamemode") || temp.contains("ban") || temp.contains("give") || temp.contains("kick")
				|| temp.contains("kill") || temp.contains("pardon")) {
			MessageManager.getInstance().inform(p, "NPC Commands do not work with this command");
			return;
		}

		NPCManager.command.put(p, temp);
		
		MessageManager.getInstance().inform(p, "Right click the NPC to set the command : " + temp);

	}

	@Override
	public String name() {
		return "Command";
	}

	@Override
	public String info() {
		return "Add command to an NPC";
	}

	@Override
	public String perm() {
		return "NPC.ADMIN";
	}

}
