package com.src.commands;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.src.main.util.MessageManager;

public class CommandManager implements CommandExecutor{
	private ArrayList<SubCommand> commands = new ArrayList<SubCommand>();

	public void setup() {
		commands.add(new Spawn());
		commands.add(new Remove());
		commands.add(new Edit());
		commands.add(new Done());
		commands.add(new com.src.commands.Command());
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args) {
		
		
		if (!(sender instanceof Player)) {
			System.out.println(ChatColor.RED + "Console cannot run command!");
			return true;
		}
		
		Player p = (Player) sender;
		
		if(!p.hasPermission("NPC.ADMIN")){
			return false;
		}
		
		if (command.getName().equalsIgnoreCase("NPC")) {
			if (args.length == 0) {
				for (SubCommand c : commands) {
						MessageManager.getInstance().inform(p, "/NPC " + c.name()+ " - " + c.info());		
				}
				return true;
			}
			
			SubCommand target = get(args[0]);
			
			if (target == null) {
				MessageManager.getInstance().inform(p, "/NPC " + args[0] + " is not a valid subcommand!");
				return true;
			}
			
			if(!p.hasPermission(target.perm())){
				return false;
			}
			
			ArrayList<String> a = new ArrayList<String>();
			a.addAll(Arrays.asList(args));
			a.remove(0);
			args = a.toArray(new String[a.size()]);
			
			try {
				target.onCommand(p, args);
			}
			
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return true;
	}
	
	private SubCommand get(String name) {
		for (SubCommand cmd : commands) {
			if (cmd.name().equalsIgnoreCase(name)) return cmd;
		}
		return null;
	}

}
