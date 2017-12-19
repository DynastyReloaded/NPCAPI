package com.src.main.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import com.src.main.NPCManager;
import com.src.main.NPCS;
import com.src.main.NPCTracker;
import com.src.main.packets.NMSManager;

public class SLAPI {

	private static File file = new File(NPCS.getInstance().getDataFolder(), "NPCS.yml");
	private static YamlConfiguration YAML = new YamlConfiguration();

	public static void saveNPCs() {
		try {

			if (!NPCS.getInstance().getDataFolder().exists()) {
				NPCS.getInstance().getDataFolder().mkdirs();
			}

			if (!file.exists()) {
				file.createNewFile();
			}

			YAML.load(file);

			Class<?> epc = NMSManager.EntityPlayer;
			Method m;
			try {
				m = epc.getSuperclass().getSuperclass().getSuperclass().getMethod("getId");

				for (Object ep : NPCManager.npcs) {

					NPCTracker t = NPCManager.npctrack.get(m.invoke(ep));

					YAML.set(t.getName() + ".Name", t.getName());
					YAML.set(t.getName() + ".Skin", t.getOwnerName());
					YAML.set(t.getName() + ".Location.World", t.getLocation().getWorld().getName());
					YAML.set(t.getName() + ".Location.X", t.getLocation().getX());
					YAML.set(t.getName() + ".Location.Y", t.getLocation().getY());
					YAML.set(t.getName() + ".Location.Z", t.getLocation().getZ());
					YAML.set(t.getName() + ".Location.Yaw", t.getLocation().getYaw());
					YAML.set(t.getName() + ".Location.Pitch", t.getLocation().getPitch());
					YAML.set(t.getName() + ".armor.head", t.getHead());
					YAML.set(t.getName() + ".armor.chest", t.getChest());
					YAML.set(t.getName() + ".armor.legs", t.getLegs());
					YAML.set(t.getName() + ".armor.feet", t.getFeet());
					YAML.set(t.getName() + ".command", t.getCommand());
					
				}
				YAML.save(file);

			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}

	}

	public static void loadNPCs() {

		try {

			if (!NPCS.getInstance().getDataFolder().exists()) {
				NPCS.getInstance().getDataFolder().mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}
			YAML.load(file);

			for (String t : YAML.getKeys(false)) {

				Bukkit.getServer().getLogger().log(Level.INFO, t);

				String name = YAML.getString((t + ".Name"));
				String skin = YAML.getString(t + ".Skin");
				String world = YAML.getString(t + ".Location.World");
				Double x = YAML.getDouble(t + ".Location.X");
				Double y = YAML.getDouble(t + ".Location.Y");
				Double z = YAML.getDouble(t + ".Location.Z");
				Float yaw = (float) YAML.getDouble(t + ".Location.Yaw");
				Float pitch = (float) YAML.getDouble(t + ".Location.Pitch");

				Location l = new Location(Bukkit.getServer().getWorld(world), x, y, z, yaw, pitch);

				NPCS.npcspawn.spawnNPC(skin, name, l);

				if (YAML.contains(t + ".armor.head")) {
					NPCTracker npct = NPCManager.npctrack.get(NPCManager.skinOwners.get(skin));
					npct.setHead(YAML.getItemStack(t + ".armor.head"));
					npct.setChest(YAML.getItemStack(t + ".armor.chest"));
					npct.setLegs(YAML.getItemStack(t + ".armor.legs"));
					npct.setFeet(YAML.getItemStack(t + ".armor.feet"));
					npct.setCommand(YAML.getString(t + ".command"));
					npct.updateAllArmor();
				}
				YAML.set(t, null);

			}

			YAML.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}

	}

}
