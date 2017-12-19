package com.src.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.src.armor.EntityListener;
import com.src.commands.CommandManager;
import com.src.events.PlayerMove;
import com.src.main.packets.NMSManager;
import com.src.main.packets.PacketInjector;
import com.src.main.packets.PacketManager;
import com.src.main.util.MathUtil;
import com.src.main.util.SLAPI;

public class NPCS extends JavaPlugin {

	public static Plugin pl;
	public static PacketInjector injector;

	public static NPCSpawn npcspawn;

	public static PlayerMove Pmove;

	public void onEnable() {
		pl = this;
		NMSManager.setupReflections();

		injector = new PacketInjector();
		npcspawn = new NPCSpawn();
		Pmove = new PlayerMove();

		createConfig();

		getServer().getPluginManager().registerEvents(new PacketManager(), this);
		getServer().getPluginManager().registerEvents(new EntityListener(), this);

		CommandManager cm = new CommandManager();
		cm.setup();
		getCommand("NPC").setExecutor(cm);

		SLAPI.loadNPCs();

		loadConfig();

		Pmove.runPlayer();

	}

	public void onDisable() {

		SLAPI.saveNPCs();

		for (Entity e : Bukkit.getWorld("World").getEntities()) {

			if (e instanceof Player) {
				Player p = (Player) e;
				p.kickPlayer(ChatColor.RED + "Server Rebooting");
			}

			e.remove();

		}
	}

	private void createConfig() {

		FileConfiguration c = getConfig();

		if (c.contains("NPCMove.Radius") && c.contains("NPCRotate")) {
			return;
		}

		c.options().header("Change Radius for NPCNearEvent and If NPCs rotate! Default = false");

		c.addDefault("NPCMove.Radius", 5);

		c.addDefault("NPCMove.Enabled", false);

		c.addDefault("NPCRotate", false);
		c.addDefault("ClickCooldown", 1);

		c.options().copyDefaults(true);

		saveConfig();

		reloadConfig();
	}

	public void loadConfig() {

		if (getConfig().contains("NPCMove.Radius")) {
			double d = getConfig().getDouble("NPCMove.Radius");
			if (d == 0.0) {
				d = 5.0;
				getConfig().set("NPCMove.Radius", 5.0);
				saveConfig();
			}
			Pmove.setRadius(d);
		}

		if (getConfig().contains("ClickCooldown")) {
			long cool = getConfig().getLong("ClickCooldown");
			if (cool < 1) {
				cool = 1;
				getConfig().set("ClickCooldown", 1);
				saveConfig();
			}

			MathUtil.setCooldown(cool);

		} else {
			getConfig().set("ClickCooldown", 1);
			saveConfig();
		}

		if (getConfig().contains("NPCMove.Enabled")) {
			Pmove.setMoveEnabled(getConfig().getBoolean("NPCMove.Enabled"));
		}

		if (getConfig().contains("NPCRotate")) {
			Pmove.setRotateEnabled(getConfig().getBoolean("NPCRotate"));
		}

	}

	public static Plugin getInstance() {
		return pl;
	}

}
