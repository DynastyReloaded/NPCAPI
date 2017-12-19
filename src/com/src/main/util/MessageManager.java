package com.src.main.util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class MessageManager {

	private MessageManager() {
	}

	private String prefix = ChatColor.GREEN + "[NPC] ";

	private static MessageManager instance = new MessageManager();

	public static MessageManager getInstance() {
		return instance;
	}

	public void inform(CommandSender s, String msg) {
		msg(s, ChatColor.WHITE, msg);
	}

	private void msg(CommandSender s, ChatColor color, String msg) {
		s.sendMessage(prefix + color + msg);
	}

}
