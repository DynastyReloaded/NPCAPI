package com.src.main;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class NPCManager {

	public static HashMap<Integer, NPCTracker> npctrack = new HashMap<>();
	public static HashMap<String, Integer> skinOwners = new HashMap<>();
	public static ArrayList<Object> npcs = new ArrayList<>();
	public static HashMap<Object, Location> npcLocation = new HashMap<>();
	public static HashMap<String, ArrayList<Object>> entityByName = new HashMap<>();
	public static HashMap<Player, String> command = new HashMap<>();
	
}