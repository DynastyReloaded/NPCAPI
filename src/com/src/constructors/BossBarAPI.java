package com.src.constructors;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;

import com.src.events.enums.BossColor;
import com.src.main.packets.NMSManager;

public class BossBarAPI {

	String name;
	BarColor bc;
	BarStyle bs = BarStyle.SOLID;
	Object entity;
	Object bar;
	
	public BossBarAPI(String name, Object ep){
		
		this.name = name;
		this.entity = ep;
		
		Object array = Array.newInstance(BarFlag.class, 1);
		Array.set(array, 0, BarFlag.PLAY_BOSS_MUSIC);
		
		 String color = ChatColor.getLastColors(name);
		 
		 
		 BarColor bc = null;
		 
		 for(BossColor bosscolor : BossColor.values()){
			 
			 if(bosscolor.getChatColor().toString().equalsIgnoreCase(color)){
				 bc = bosscolor.getBarColor();
			 }
			 
		 }
		 
		 if(bc == null){
			 this.bc = BarColor.RED;
		 }else{
			 this.bc = bc;
		 }
		 
		 
		try {
			Object bar = NMSManager.ConstructorCraftBossBar.newInstance(name, this.bc, bs, array);
			
			this.bar = bar;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
	}
	
	public void addPlayer(Player p){
		
		try {
			Method m = NMSManager.CraftBossBar.getDeclaredMethod("addPlayer", Player.class);
			m.invoke(bar, p);
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
		
	}
	
	public void removePlayer(Player p){
		
		try {
			Method m = NMSManager.CraftBossBar.getDeclaredMethod("removePlayer", Player.class);
			m.invoke(bar, p);
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
		
	}
	
	
	
}
