package com.src.main;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;
import com.src.constructors.BossBarAPI;
import com.src.main.packets.NMSManager;
import com.src.main.packets.Reflection;
import com.src.main.util.ProfileLoader;

public class NPCSpawn {

	public NPCSpawn() {

	}

	public void spawnNPC(String SkinName, String CustomName, Location loc) {
		
		try {
			UUID id = UUID.randomUUID();

			GameProfile gp;

			if (!NPCManager.skinOwners.containsKey(SkinName.replace(" ", ""))) {

				gp = new ProfileLoader(id.toString(), CustomName, SkinName).loadProfile();

			} else {

				NPCTracker npct = NPCManager.npctrack.get(NPCManager.skinOwners.get(SkinName.replace(" ", "")));

				gp = npct.getProfile();

			}
			Class<?> msc = NMSManager.MinecraftServer;
			Method temp = NMSManager.CraftServer.getDeclaredMethod("getServer");
			Object ms = msc.cast(temp.invoke((NMSManager.CraftServer.cast(Bukkit.getServer()))));

			Class<?> wsc = NMSManager.WorldServer;
			Method m = NMSManager.CraftWorld.getDeclaredMethod("getHandle");
			Object ws = wsc.cast(m.invoke(NMSManager.CraftWorld.cast(loc.getWorld())));

			Location l = loc;

			Constructor<?> pimconstruct = NMSManager.ConstructorPlayerInteractManager;

			Object p = pimconstruct.newInstance(ws);

			Class<?> epc = NMSManager.EntityPlayer;
			Constructor<?> epconstruct = NMSManager.ConstructorEntityPlayer;

			Object npc = epconstruct.newInstance(ms, ws, gp, p);

			npc = epc.cast(npc);

			l.setPitch(loc.getPitch());
			l.setYaw(loc.getYaw());

			Method m2 = epc.getSuperclass().getSuperclass().getSuperclass().getDeclaredMethod("setPositionRotation",
					Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE);
			
			m2.invoke(npc, l.getX(), l.getY(), l.getZ(), (byte) l.getYaw(), (byte) l.getPitch());

			Method ids = epc.getSuperclass().getSuperclass().getSuperclass().getDeclaredMethod("getId");
			
			if (!NPCManager.npctrack.containsKey(ids.invoke(npc))) {

				NPCTracker tracker = new NPCTracker(epc.cast(npc), id,
						ChatColor.translateAlternateColorCodes('&', CustomName), SkinName, (int) ids.invoke(npc), gp,
						l);

				NPCManager.npctrack.put((int) ids.invoke(npc), tracker);

				NPCManager.skinOwners.put(SkinName.replace(" ", ""), (int) ids.invoke(npc));

				NPCManager.npcs.add(npc);

				NPCManager.npcLocation.put(npc, l);

				ArrayList<Object> epList;
				
				BossBarAPI bb = new BossBarAPI(ChatColor.translateAlternateColorCodes('&', CustomName), npc);
				NPCS.Pmove.saveBossBar(npc, bb);

				if (NPCManager.entityByName.containsKey(CustomName.replace(" ", "").toLowerCase())) {
					epList = NPCManager.entityByName.get(CustomName.replace(" ", "").toLowerCase());
				} else {
					epList = new ArrayList<>();
				}

				epList.add(npc);

				NPCManager.entityByName.put(ChatColor.stripColor(CustomName.replace(" ", "").toLowerCase()), epList);

			}

			for (Player pl : Bukkit.getOnlinePlayers()) {
				
				updateAllNPCS(pl);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deleteNPC(Object ep) {

		try {
			Constructor<?> econstruct = NMSManager.ConstructorEntityDestroy;

			Class<?> npClass = NMSManager.EntityPlayer;
			
			Method m = npClass.getSuperclass().getSuperclass().getSuperclass().getDeclaredMethod("getId");

			ep = NMSManager.EntityPlayer.cast(ep);
			
			for (Player pl : Bukkit.getOnlinePlayers()) {

				Object packetDestroy = econstruct.newInstance(new int[] { (int) m.invoke(ep) });
				
				sendPacket(pl, packetDestroy);

			}

			NPCTracker npct = NPCManager.npctrack.get(m.invoke(ep));
			NPCManager.npcLocation.remove(ep);
			NPCManager.npctrack.remove(m.invoke(ep));
			
			if (isNull(npct)) {

			} else {
				NPCManager.skinOwners.remove(npct.getOwnerName().replace(" ", ""));
				NPCManager.npcs.remove(ep);
				NPCManager.entityByName.remove(npct.getOwnerName());
			}
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
		} catch (InstantiationException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void updateAllNPCS(Player pl) {

		if (NPCManager.npcs.isEmpty()) {
			return;
		}

		Class<?> npClass = NMSManager.EntityPlayer;
		Method ids;

		try {
			ids = npClass.getSuperclass().getSuperclass().getSuperclass().getMethod("getId");

			Constructor<?> EntityDestCon = NMSManager.ConstructorEntityDestroy;

			final Class<? extends Enum> EnumPlayerInfo = NMSManager.EnumPlayerInfo;
			
			Constructor<?> PlayerInCon = NMSManager.ConstructorPacketPlayOutPlayerInfo;

			Constructor<?> NameEntityConst = NMSManager.ConstructorPacketPlayOutNamedEntitySpawn;
			
			Constructor<?> HeadRot = NMSManager.ConstructorPacketPlayOutEntityHeadRotation;
			Constructor<?> EntityLook = NMSManager.ConstructorPacketPlayOutEntityLook;
			
			for (Object ep : NPCManager.npcs) {

				Location loc = NPCManager.npcLocation.get(ep);
				
				Object o = Array.newInstance(npClass, 1);
				
				ep = npClass.cast(ep);
				
				Array.set(o, 0, ep);
				
				Object packetDestroy = EntityDestCon.newInstance(new int[] { (int) ids.invoke(ep) });
				sendPacket(pl, packetDestroy);
				
				Object packInfo = null;
				
				packInfo = PlayerInCon.newInstance(Enum.valueOf(EnumPlayerInfo, "ADD_PLAYER"), o);

				Object packet2 = NameEntityConst.newInstance(ep);
				
	
				sendPacket(pl, packInfo);
				sendPacket(pl, packet2);

				Method m = npClass.getSuperclass().getSuperclass().getSuperclass().getDeclaredMethod("getId");	
				
				Object oj = EntityLook.newInstance((int) m.invoke(ep), (byte) loc.getYaw(), (byte) loc.getPitch(), false);
				NPCS.npcspawn.sendPacket(pl, oj);

				Object hr = HeadRot.newInstance(ep, (byte) loc.getYaw());
				NPCS.npcspawn.sendPacket(pl, hr);
			}

			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(NPCS.getInstance(), new Runnable() {

				@Override
				public void run() {
					for (Object ep : NPCManager.npcs) {

						Object o = Array.newInstance(npClass, 1);
						
						ep = npClass.cast(ep);

						Array.set(o, 0, ep);
						
						Object packet4;
						try {
							packet4 = PlayerInCon.newInstance(Enum.valueOf(EnumPlayerInfo, "REMOVE_PLAYER"), o);

							sendPacket(pl, packet4);

							NPCTracker npct = NPCManager.npctrack.get((int) ids.invoke(ep));
							npct.updateAllArmor();
							
						} catch (InstantiationException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}catch (SecurityException e) {
							e.printStackTrace();
						}


					}
				}
			}, 40);

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
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

	public void sendPacket(Player p, Object o) {

		try {

			Object j = Reflection.getConnection(p);
			j.getClass().getMethod("sendPacket", Reflection.getNMSClass("Packet")).invoke(j, o);

		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

	}

	public Boolean isNull(Object o) {
		if (o == null) {
			return true;
		} else {
			return false;
		}
	}

}
