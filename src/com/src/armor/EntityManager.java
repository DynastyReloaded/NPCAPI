package com.src.armor;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.src.main.NPCManager;
import com.src.main.NPCTracker;
import com.src.main.packets.NMSManager;
import com.src.main.packets.Reflection;

import net.md_5.bungee.api.ChatColor;

public class EntityManager {

	private int enId;
	private Player p;
	private Object entity;
	private ArrayList<Integer> ids = new ArrayList<>();

	private ItemStack[] head = new ItemStack[] { ItemStackManager.LEATHER.getHead(), ItemStackManager.CHAIN.getHead(),
			ItemStackManager.IRON.getHead(), ItemStackManager.GOLD.getHead(), ItemStackManager.DIAMOND.getHead(),
			ItemStackManager.AIR.getHead() };
	private ItemStack[] chest = new ItemStack[] { ItemStackManager.LEATHER.getChest(),
			ItemStackManager.CHAIN.getChest(), ItemStackManager.IRON.getChest(), ItemStackManager.GOLD.getChest(),
			ItemStackManager.DIAMOND.getChest(), ItemStackManager.AIR.getChest() };;
	private ItemStack[] legs = new ItemStack[] { ItemStackManager.LEATHER.getLeggings(),
			ItemStackManager.CHAIN.getLeggings(), ItemStackManager.IRON.getLeggings(),
			ItemStackManager.GOLD.getLeggings(), ItemStackManager.DIAMOND.getLeggings(),
			ItemStackManager.AIR.getLeggings() };;
	private ItemStack[] feet = new ItemStack[] { ItemStackManager.LEATHER.getBoots(), ItemStackManager.CHAIN.getBoots(),
			ItemStackManager.IRON.getBoots(), ItemStackManager.GOLD.getBoots(), ItemStackManager.DIAMOND.getBoots(),
			ItemStackManager.AIR.getBoots() };;

	private int helmetNumber = 5;
	private int chestNumber = 5;
	private int leggingsNumber = 5;
	private int bootsNumber = 5;

	// Helmet
	public Object HelmetLeft;
	public Object HelmetRight;
	public Object HLT;
	public Object HRT;

	// Chest
	public Object ChestLeft;
	public Object ChestRight;
	public Object CLT;
	public Object CRT;

	// Legs
	public Object LegsLeft;
	public Object LegsRight;
	public Object LLT;
	public Object LRT;

	// Boots
	public Object BootsLeft;
	public Object BootsRight;
	public Object BLT;
	public Object BRT;

	// NMSWorld
	public Object nmsWorld;
	public Location l;

	public EntityManager(Player p, int enId, Object entity) {

		this.p = p;
		this.enId = enId;
		this.entity = entity;
		l = NPCManager.npcLocation.get(entity);

		Class<?> wsc = NMSManager.WorldServer;
		Method m;
		try {
			m = NMSManager.CraftWorld.getDeclaredMethod("getHandle");
			nmsWorld = wsc.cast(m.invoke(NMSManager.CraftWorld.cast(p.getLocation().getWorld())));
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

	public void makeArrows() {

		try {

			Method setLoc = NMSManager.Entity.getDeclaredMethod("setLocation", Double.TYPE, Double.TYPE, Double.TYPE,
					Float.TYPE, Float.TYPE);
			Method setSmall = NMSManager.EntityArmorStand.getDeclaredMethod("setSmall", Boolean.TYPE);
			Method setName = NMSManager.Entity.getDeclaredMethod("setCustomName", String.class);
			Method setVisible = NMSManager.Entity.getDeclaredMethod("setInvisible", Boolean.TYPE);
			Method setCustom = NMSManager.Entity.getDeclaredMethod("setCustomNameVisible", Boolean.TYPE);
			Method getId = NMSManager.Entity.getDeclaredMethod("getId");

			/*
			 * 
			 * Left side of the Body
			 * 
			 */

			HelmetLeft = NMSManager.ConstructorEntityArmorStand.newInstance(nmsWorld);

			setLoc.invoke(HelmetLeft, l.getX() - 0.7, l.getY() + 1.45, l.getZ(), 0f, 0f);
			setSmall.invoke(HelmetLeft, true);
			setVisible.invoke(HelmetLeft, true);

			EntityListener.armorstandId.put((int) getId.invoke(HelmetLeft), HelmetLeft);
			ids.add((int) getId.invoke(HelmetLeft));

			Object packet = NMSManager.ConstructorPacketPlayOutSpawnEntityLiving.newInstance(HelmetLeft);
			updatePacket(packet);

			HLT = NMSManager.ConstructorEntityArmorStand.newInstance(nmsWorld);

			int multiplierZ = 0;

			if (p.getLocation().getZ() > l.getZ()) {
				multiplierZ--;
			} else {
				multiplierZ++;
			}

			setLoc.invoke(HLT, l.getX() - 0.7, l.getY() - 0.5, l.getZ() + (0.2 * multiplierZ), 0f, 0f);
			setName.invoke(HLT, ChatColor.GRAY + "" + ChatColor.BOLD + "⬅");
			setVisible.invoke(HLT, true);
			setCustom.invoke(HLT, true);
			ids.add((int) getId.invoke(HLT));

			packet = NMSManager.ConstructorPacketPlayOutSpawnEntityLiving.newInstance(HLT);
			updatePacket(packet);

			// Chest Left

			ChestLeft = NMSManager.ConstructorEntityArmorStand.newInstance(nmsWorld);

			setLoc.invoke(ChestLeft, l.getX() - 0.7, l.getY() + 0.95, l.getZ(), 0f, 0f);
			setSmall.invoke(ChestLeft, true);
			setVisible.invoke(ChestLeft, true);

			EntityListener.armorstandId.put((int) getId.invoke(ChestLeft), ChestLeft);
			ids.add((int) getId.invoke(ChestLeft));

			packet = NMSManager.ConstructorPacketPlayOutSpawnEntityLiving.newInstance(ChestLeft);
			updatePacket(packet);

			CLT = NMSManager.ConstructorEntityArmorStand.newInstance(nmsWorld);

			setLoc.invoke(CLT, l.getX() - 0.7, l.getY() - 1.15, l.getZ() + (0.2 * multiplierZ), 0f, 0f);
			setName.invoke(CLT, ChatColor.BOLD + "⬅");
			setVisible.invoke(CLT, true);
			setCustom.invoke(CLT, true);
			ids.add((int) getId.invoke(CLT));

			packet = NMSManager.ConstructorPacketPlayOutSpawnEntityLiving.newInstance(CLT);
			updatePacket(packet);

			// Legs Left
			LegsLeft = NMSManager.ConstructorEntityArmorStand.newInstance(nmsWorld);

			setLoc.invoke(LegsLeft, l.getX() - 0.7, l.getY() + 0.50, l.getZ(), 0f, 0f);
			setSmall.invoke(LegsLeft, true);
			setVisible.invoke(LegsLeft, true);

			EntityListener.armorstandId.put((int) getId.invoke(LegsLeft), LegsLeft);
			ids.add((int) getId.invoke(LegsLeft));

			packet = NMSManager.ConstructorPacketPlayOutSpawnEntityLiving.newInstance(LegsLeft);
			updatePacket(packet);

			LLT = NMSManager.ConstructorEntityArmorStand.newInstance(nmsWorld);

			setLoc.invoke(LLT, l.getX() - 0.7, l.getY() - 1.70, l.getZ() + (0.2 * multiplierZ), 0f, 0f);
			setName.invoke(LLT, ChatColor.BOLD + "⬅");
			setVisible.invoke(LLT, true);
			setCustom.invoke(LLT, true);
			ids.add((int) getId.invoke(LLT));

			packet = NMSManager.ConstructorPacketPlayOutSpawnEntityLiving.newInstance(LLT);
			updatePacket(packet);

			// Legs Left
			BootsLeft = NMSManager.ConstructorEntityArmorStand.newInstance(nmsWorld);

			setLoc.invoke(BootsLeft, l.getX() - 0.7, l.getY(), l.getZ(), 0f, 0f);
			setSmall.invoke(BootsLeft, true);
			setVisible.invoke(BootsLeft, true);

			EntityListener.armorstandId.put((int) getId.invoke(BootsLeft), BootsLeft);
			ids.add((int) getId.invoke(BootsLeft));

			packet = NMSManager.ConstructorPacketPlayOutSpawnEntityLiving.newInstance(BootsLeft);
			updatePacket(packet);

			BLT = NMSManager.ConstructorEntityArmorStand.newInstance(nmsWorld);

			setLoc.invoke(BLT, l.getX() - 0.7, l.getY() - 2.25, l.getZ() + (0.2 * multiplierZ), 0f, 0f);
			setName.invoke(BLT, ChatColor.BOLD + "⬅");
			setVisible.invoke(BLT, true);
			setCustom.invoke(BLT, true);
			ids.add((int) getId.invoke(BLT));

			packet = NMSManager.ConstructorPacketPlayOutSpawnEntityLiving.newInstance(BLT);
			updatePacket(packet);

			/*
			 * 
			 * Right Side of body
			 * 
			 */

			HelmetRight = NMSManager.ConstructorEntityArmorStand.newInstance(nmsWorld);

			setLoc.invoke(HelmetRight, l.getX() + 0.7, l.getY() + 1.45, l.getZ(), 0f, 0f);
			setSmall.invoke(HelmetRight, true);
			setVisible.invoke(HelmetRight, true);

			EntityListener.armorstandId.put((int) getId.invoke(HelmetRight), HelmetRight);
			ids.add((int) getId.invoke(HelmetRight));

			packet = NMSManager.ConstructorPacketPlayOutSpawnEntityLiving.newInstance(HelmetRight);
			updatePacket(packet);

			HRT = NMSManager.ConstructorEntityArmorStand.newInstance(nmsWorld);

			setLoc.invoke(HRT, l.getX() + 0.7, l.getY() - 0.5, l.getZ() + (0.2 * multiplierZ), 0f, 0f);
			setName.invoke(HRT, ChatColor.GRAY + "" + ChatColor.BOLD + "➡");
			setVisible.invoke(HRT, true);
			setCustom.invoke(HRT, true);
			ids.add((int) getId.invoke(HRT));
			packet = NMSManager.ConstructorPacketPlayOutSpawnEntityLiving.newInstance(HRT);
			updatePacket(packet);

			// Chest Left

			ChestRight = NMSManager.ConstructorEntityArmorStand.newInstance(nmsWorld);

			setLoc.invoke(ChestRight, l.getX() + 0.7, l.getY() + 0.95, l.getZ(), 0f, 0f);
			setSmall.invoke(ChestRight, true);
			setVisible.invoke(ChestRight, true);

			EntityListener.armorstandId.put((int) getId.invoke(ChestRight), ChestRight);
			ids.add((int) getId.invoke(ChestRight));

			packet = NMSManager.ConstructorPacketPlayOutSpawnEntityLiving.newInstance(ChestRight);
			updatePacket(packet);

			CRT = NMSManager.ConstructorEntityArmorStand.newInstance(nmsWorld);

			setLoc.invoke(CRT, l.getX() + 0.7, l.getY() - 1.15, l.getZ() + (0.2 * multiplierZ), 0f, 0f);
			setName.invoke(CRT, ChatColor.BOLD + "➡");
			setVisible.invoke(CRT, true);
			setCustom.invoke(CRT, true);

			ids.add((int) getId.invoke(CRT));
			packet = NMSManager.ConstructorPacketPlayOutSpawnEntityLiving.newInstance(CRT);
			updatePacket(packet);

			// Legs Left
			LegsRight = NMSManager.ConstructorEntityArmorStand.newInstance(nmsWorld);

			setLoc.invoke(LegsRight, l.getX() + 0.7, l.getY() + 0.50, l.getZ(), 0f, 0f);
			setSmall.invoke(LegsRight, true);
			setVisible.invoke(LegsRight, true);

			EntityListener.armorstandId.put((int) getId.invoke(LegsRight), LegsRight);
			ids.add((int) getId.invoke(LegsRight));

			packet = NMSManager.ConstructorPacketPlayOutSpawnEntityLiving.newInstance(LegsRight);
			updatePacket(packet);

			LRT = NMSManager.ConstructorEntityArmorStand.newInstance(nmsWorld);

			setLoc.invoke(LRT, l.getX() + 0.7, l.getY() - 1.70, l.getZ() + (0.2 * multiplierZ), 0f, 0f);
			setName.invoke(LRT, ChatColor.BOLD + "➡");
			setVisible.invoke(LRT, true);
			setCustom.invoke(LRT, true);
			ids.add((int) getId.invoke(LRT));

			packet = NMSManager.ConstructorPacketPlayOutSpawnEntityLiving.newInstance(LRT);
			updatePacket(packet);

			// Legs Left
			BootsRight = NMSManager.ConstructorEntityArmorStand.newInstance(nmsWorld);

			setLoc.invoke(BootsRight, l.getX() + 0.7, l.getY(), l.getZ(), 0f, 0f);
			setSmall.invoke(BootsRight, true);
			setVisible.invoke(BootsRight, true);

			EntityListener.armorstandId.put((int) getId.invoke(BootsRight), BootsRight);
			ids.add((int) getId.invoke(BootsRight));

			packet = NMSManager.ConstructorPacketPlayOutSpawnEntityLiving.newInstance(BootsRight);
			updatePacket(packet);

			BRT = NMSManager.ConstructorEntityArmorStand.newInstance(nmsWorld);

			setLoc.invoke(BRT, l.getX() + 0.7, l.getY() - 2.25, l.getZ() + (0.2 * multiplierZ), 0f, 0f);
			setName.invoke(BRT, ChatColor.BOLD + "➡");
			setVisible.invoke(BRT, true);
			setCustom.invoke(BRT, true);
			ids.add((int) getId.invoke(BRT));

			packet = NMSManager.ConstructorPacketPlayOutSpawnEntityLiving.newInstance(BRT);
			updatePacket(packet);

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
		} catch (InstantiationException e) {
			e.printStackTrace();
		}

	}

	public void updateArmor(ArmorType at, ArmorDirection direction) {

		ItemStack i;

		if (at.equals(ArmorType.HEAD)) {

			if (direction.equals(ArmorDirection.LEFT)) {

				if (helmetNumber != 0) {
					helmetNumber--;
				} else {
					helmetNumber = 5;
				}

			} else {

				if (helmetNumber != 5) {
					helmetNumber++;
				} else {
					helmetNumber = 0;
				}

			}

			i = head[helmetNumber];

			setHeadPlate(i, enId);

		} else if (at.equals(ArmorType.CHEST)) {

			if (direction.equals(ArmorDirection.LEFT)) {

				if (chestNumber != 0) {
					chestNumber--;
				} else {
					chestNumber = 5;
				}

			} else {

				if (chestNumber != 5) {
					chestNumber++;
				} else {
					chestNumber = 0;
				}

			}

			i = chest[chestNumber];

			setChestPlate(i, enId);

		} else if (at.equals(ArmorType.LEGS)) {

			if (direction.equals(ArmorDirection.LEFT)) {

				if (leggingsNumber != 0) {
					leggingsNumber--;
				} else {
					leggingsNumber = 5;
				}

			} else {

				if (leggingsNumber != 5) {
					leggingsNumber++;
				} else {
					leggingsNumber = 0;
				}

			}

			i = legs[leggingsNumber];

			setLegsPlate(i, enId);

		} else if (at.equals(ArmorType.FEET)) {

			if (direction.equals(ArmorDirection.LEFT)) {

				if (bootsNumber != 0) {
					bootsNumber--;
				} else {
					bootsNumber = 5;
				}

			} else {

				if (bootsNumber != 5) {
					bootsNumber++;
				} else {
					bootsNumber = 0;
				}

			}

			i = feet[bootsNumber];

			setBootsPlate(i, enId);

		}

	}

	@SuppressWarnings("unchecked")
	public void setHeadPlate(ItemStack i, int id) {

		try {
			
			Method item = NMSManager.CraftItemStack.getDeclaredMethod("asNMSCopy", ItemStack.class);

			Object packet = NMSManager.ConstructorPacketPlayOutEntityEquipment.newInstance(id,
					Enum.valueOf(NMSManager.EnumItemSlot, "HEAD"), NMSManager.ItemStack.cast(item.invoke(i, i)));

			updatePacket(packet);
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

	@SuppressWarnings("unchecked")
	public void setChestPlate(ItemStack i, int id) {

		try {

			Method item = NMSManager.CraftItemStack.getDeclaredMethod("asNMSCopy", ItemStack.class);

			Object packet = NMSManager.ConstructorPacketPlayOutEntityEquipment.newInstance(id,
					Enum.valueOf(NMSManager.EnumItemSlot, "CHEST"), NMSManager.ItemStack.cast(item.invoke(i, i)));

			updatePacket(packet);
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

	@SuppressWarnings("unchecked")
	public void setLegsPlate(ItemStack i, int id) {

		try {

			Method item = NMSManager.CraftItemStack.getDeclaredMethod("asNMSCopy", ItemStack.class);

			Object packet = NMSManager.ConstructorPacketPlayOutEntityEquipment.newInstance(id,
					Enum.valueOf(NMSManager.EnumItemSlot, "LEGS"), NMSManager.ItemStack.cast(item.invoke(i, i)));

			updatePacket(packet);
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

	@SuppressWarnings("unchecked")
	public void setBootsPlate(ItemStack i, int id) {

		try {

			Method item = NMSManager.CraftItemStack.getDeclaredMethod("asNMSCopy", ItemStack.class);

			Object packet = NMSManager.ConstructorPacketPlayOutEntityEquipment.newInstance(id,
					Enum.valueOf(NMSManager.EnumItemSlot, "FEET"), NMSManager.ItemStack.cast(item.invoke(i, i)));

			updatePacket(packet);
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

	public void updatePacket(Object packet) {

		try {

			Object j = Reflection.getConnection(p);
			j.getClass().getMethod("sendPacket", Reflection.getNMSClass("Packet")).invoke(j, packet);

		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
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

	public void removeArmorStands() {

		Object o;
		Object array = Array.newInstance(Integer.TYPE, ids.size());
		System.out.println(ids.size());
		for(int id = 0; id < ids.size(); id++){
			Array.set(array, id, ids.get(id));
		}
		
		try {
			
				o = NMSManager.ConstructorEntityDestroy.newInstance(array);
				updatePacket(o);
			
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
	
	@SuppressWarnings("unchecked")
	public void updateAllAmor(){
		
		int id = enId;
		ItemStack items;
		
		try {
			Method item = NMSManager.CraftItemStack.getDeclaredMethod("asNMSCopy", ItemStack.class);
			
			items = head[helmetNumber];
			Object packet = NMSManager.ConstructorPacketPlayOutEntityEquipment.newInstance(id,
					Enum.valueOf(NMSManager.EnumItemSlot, "HEAD"), NMSManager.ItemStack.cast(item.invoke(items, items)));
			updateAllPackets(packet);
			
			items = chest[chestNumber];
			packet = NMSManager.ConstructorPacketPlayOutEntityEquipment.newInstance(id,
					Enum.valueOf(NMSManager.EnumItemSlot, "CHEST"), NMSManager.ItemStack.cast(item.invoke(items, items)));
			updateAllPackets(packet);
			
			items = legs[leggingsNumber];
			packet = NMSManager.ConstructorPacketPlayOutEntityEquipment.newInstance(id,
					Enum.valueOf(NMSManager.EnumItemSlot, "LEGS"), NMSManager.ItemStack.cast(item.invoke(items, items)));
			updateAllPackets(packet);
			
			items = feet[bootsNumber];
			packet = NMSManager.ConstructorPacketPlayOutEntityEquipment.newInstance(id,
					Enum.valueOf(NMSManager.EnumItemSlot, "FEET"), NMSManager.ItemStack.cast(item.invoke(items, items)));
			updateAllPackets(packet);
			
			NPCTracker npct = NPCManager.npctrack.get(enId);
			npct.setHead(head[helmetNumber]);
			npct.setChest(chest[chestNumber]);
			npct.setLegs(legs[leggingsNumber]);
			npct.setFeet(feet[bootsNumber]);
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

	public void Done() {
		removeArmorStands();
		updateAllAmor();
		EntityListener.armorstandId.remove(enId);
		EntityListener.entityBeingEdited.remove(entity);

		for (int id : ids) {
			EntityListener.armorstandId.remove(id);
		}

	}

}
