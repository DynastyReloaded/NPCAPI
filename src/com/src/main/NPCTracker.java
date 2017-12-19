package com.src.main;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.mojang.authlib.GameProfile;
import com.src.armor.ItemStackManager;
import com.src.main.packets.NMSManager;
import com.src.main.packets.Reflection;


public class NPCTracker{
	
	Object ep;
	UUID id;
	String name;
	String SkinOwner;
	int entityId;
	GameProfile gp;
	Location l;
	ItemStack head = ItemStackManager.AIR.getHead();
	ItemStack chest = ItemStackManager.AIR.getChest();
	ItemStack legs = ItemStackManager.AIR.getLeggings();
	ItemStack feet = ItemStackManager.AIR.getBoots();
	String command;
	
	public NPCTracker(Object ep, UUID id, String Name, String SkinOwner, int eid, GameProfile gp, Location loc){
	
		this.ep = ep;
		this.id = id;
		this.name = Name;
		this.SkinOwner = SkinOwner;
		entityId = eid;
		this.gp = gp;
		l = loc;
	}
	
	public void setCommand(String command){
		this.command = command;
	}
	
	public String getCommand(){
		return command;
	}
	
	public Location getLocation(){
		return l;
	}
	
	public Object getEntity(){
		return ep;
	}
	
	public GameProfile getProfile(){
		return gp;
	}
	
	public UUID getUUID(){
		return id;
	}

	public int getId(){
		return entityId;
	}
	
	public String getName(){
		return name;
	}
	
	public String getOwnerName(){
		return SkinOwner;
	}
	
	public void setHead(ItemStack head){
		this.head = head;
	}
	
	public void setChest(ItemStack chest){
		this.chest = chest;
	}
	
	public void setLegs(ItemStack legs){
		this.legs = legs;
	}
	
	public void setFeet(ItemStack feet){
		this.feet = feet;
	}
	
	@SuppressWarnings("unchecked")
	public void updateAllArmor(){
	
		ItemStack temp;
		
		try {
			Method item = NMSManager.CraftItemStack.getDeclaredMethod("asNMSCopy", ItemStack.class);
			
			temp = head;
			Object packet = NMSManager.ConstructorPacketPlayOutEntityEquipment.newInstance(entityId,
					Enum.valueOf(NMSManager.EnumItemSlot, "HEAD"), NMSManager.ItemStack.cast(item.invoke(temp, head)));
			updateAllPackets(packet);
			
			temp = chest;
			packet = NMSManager.ConstructorPacketPlayOutEntityEquipment.newInstance(entityId,
					Enum.valueOf(NMSManager.EnumItemSlot, "CHEST"), NMSManager.ItemStack.cast(item.invoke(temp, chest)));
			updateAllPackets(packet);
			
			temp = legs;
			packet = NMSManager.ConstructorPacketPlayOutEntityEquipment.newInstance(entityId,
					Enum.valueOf(NMSManager.EnumItemSlot, "LEGS"), NMSManager.ItemStack.cast(item.invoke(temp, legs)));
			updateAllPackets(packet);
			
			temp = feet;
			packet = NMSManager.ConstructorPacketPlayOutEntityEquipment.newInstance(entityId,
					Enum.valueOf(NMSManager.EnumItemSlot, "FEET"), NMSManager.ItemStack.cast(item.invoke(temp, feet)));
			updateAllPackets(packet);
			
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
	

	public void updateAllPackets(Object packet) {

		try {
			for (Player pl : Bukkit.getOnlinePlayers()) {
				
				Object j = Reflection.getConnection(pl);
				j.getClass().getMethod("sendPacket", Reflection.getNMSClass("Packet")).invoke(j, packet);
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

	}
	
	public ItemStack getHead(){
		return head;
	}
	
	public ItemStack getChest(){
		return chest;
	}
	
	public ItemStack getLegs(){
		return legs;
	}
	
	public ItemStack getFeet(){
		return feet;
	}
}
