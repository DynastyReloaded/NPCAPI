package com.src.events;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.src.constructors.BossBarAPI;
import com.src.main.NPCManager;
import com.src.main.NPCS;
import com.src.main.NPCTracker;
import com.src.main.packets.NMSManager;
import com.src.main.util.MathUtil;

public class PlayerMove {

	public double radius = 5;
	public ArrayList<UUID> playerCooldown = new ArrayList<>();

	public boolean moveEnabled = false;
	public boolean rotateEnabled = false;
	public int runnable;

	public HashMap<Object, BossBarAPI> entityBar = new HashMap<>();
	public HashMap<Player, BossBarAPI> playerBar = new HashMap<>();

	public PlayerMove() {

	}

	public void saveBossBar(Object entity, BossBarAPI bb) {

		entityBar.put(entity, bb);

	}

	@SuppressWarnings("deprecation")
	public void onPlayerMove(Player p, double x, double y, double z) {
		
		Class<?> epc = NMSManager.EntityPlayer;
		UUID id = p.getUniqueId();

		for (Object ep : NPCManager.npcLocation.keySet()) {

			double Ex = NPCManager.npcLocation.get(ep).getX();
			double Ey = NPCManager.npcLocation.get(ep).getY();
			double Ez = NPCManager.npcLocation.get(ep).getZ();

			double distance = MathUtil.get3DDistance(x, y, z, Ex, Ey, Ez);

			if (distance <= radius) {

				playerCooldown.add(p.getUniqueId());

				if (!playerBar.containsKey(p)) {
					BossBarAPI bba = entityBar.get(ep);

					bba.addPlayer(p);
					playerBar.put(p, bba);
				}

				try {

					Method m = epc.getSuperclass().getSuperclass().getSuperclass().getDeclaredMethod("getId");

					NPCTracker npct = NPCManager.npctrack.get(m.invoke(ep));

					Bukkit.getServer().getPluginManager().callEvent(new NPCNearEvent(p, npct));

				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}

				Bukkit.getScheduler().scheduleAsyncDelayedTask(NPCS.getInstance(), new Runnable() {

					@Override
					public void run() {
						if (MathUtil.get3DDistance(p.getLocation().getX(), p.getLocation().getY(),
								p.getLocation().getZ(), Ex, Ey, Ez) > radius) {
							if (playerBar.containsKey(p)) {
								BossBarAPI bba = playerBar.get(p);

								bba.removePlayer(p);
								playerBar.remove(p);
							}
						}
						playerCooldown.remove(id);

					}
				}, 120);

				return;
			}
			
			if (playerBar.containsKey(p)) {

				if (MathUtil.get3DDistance(p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), Ex,
						Ey, Ez) > radius) {
					BossBarAPI bba = playerBar.get(p);

					if (bba.equals(entityBar.get(ep))) {
						bba.removePlayer(p);
						playerBar.remove(p);
					}
				}
			}

		}

	}

	public void rotateEnt(Player p, double x, double y, double z) {

		Class<?> epc = NMSManager.EntityPlayer;
		for (Object ep : NPCManager.npcLocation.keySet()) {
			double Ex = NPCManager.npcLocation.get(epc.cast(ep)).getX();
			double Ey = NPCManager.npcLocation.get(epc.cast(ep)).getY();
			double Ez = NPCManager.npcLocation.get(epc.cast(ep)).getZ();

			double distance = MathUtil.get3DDistance(x, y, z, Ex, Ey, Ez);

			if (distance <= radius) {

				float distance2 = (float) Math.sqrt((Ez - z) * (Ez - z) + (Ex - x) * (Ex - x));
				float pitch = (float) (Math.atan2((Ey - y), distance2) * (180 / Math.PI));

				double xDiff = Ex - x;
				double zDiff = Ez - z;

				double yaw = Math.atan2((xDiff * (Math.PI / 180)), zDiff * (Math.PI / 180));

				if (xDiff > 0 && zDiff < 0) {
					yaw = (yaw * (180 / Math.PI) * -1) - 90;
				} else if (xDiff < 0 && zDiff < 0) {
					yaw = (yaw * (180 / Math.PI) * -1) + 90;
				} else if (xDiff == 0 && zDiff > 0) {
					yaw = (0);
				} else if (xDiff < 0 && zDiff > 0) {
					yaw = (yaw * (180 / Math.PI) * -1) - 140;
				} else if (xDiff > 0 && zDiff == 0) {
					yaw = 90;
				} else {

					yaw = (yaw * (180 / Math.PI) * -1) + 140;
				}

				try {
					Constructor<?> Packet = NMSManager.ConstructorPacketPlayOutEntityHeadRotation;
					Object o = Packet.newInstance(epc.cast(ep), (byte) yaw);
					NPCS.npcspawn.sendPacket(p, o);

					Constructor<?> pack = NMSManager.ConstructorPacketPlayOutEntityLook;

					Method m = epc.getSuperclass().getSuperclass().getSuperclass().getDeclaredMethod("getId");
					Object oj = pack.newInstance((int) m.invoke(ep), (byte) yaw, (byte) pitch, false);
					NPCS.npcspawn.sendPacket(p, oj);

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
		}
	}

	public void runPlayer() {
		if (moveEnabled) {
			Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(NPCS.getInstance(), new Runnable() {

				@Override
				public void run() {

					for (Player p : Bukkit.getOnlinePlayers()) {
						if (!playerCooldown.contains(p.getUniqueId())) {
							onPlayerMove(p, p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ());
						}
					}
				}
			}, 40, 40);
		}

		if (rotateEnabled) {
			Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(NPCS.getInstance(), new Runnable() {

				@Override
				public void run() {

					for (Player p : Bukkit.getOnlinePlayers()) {
						rotateEnt(p, p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ());

					}
				}
			}, 1, 1);
		}
	}

	public void setRadius(double d) {

		radius = d;

	}

	public void setMoveEnabled(boolean b) {
		moveEnabled = b;
	}

	public void setRotateEnabled(boolean b) {
		rotateEnabled = b;
	}

	public static float getLookAtYaw(Vector motion) {
		double dx = motion.getX();
		double dz = motion.getZ();
		double yaw = 0;
		// Set yaw
		if (dx != 0) {
			// Set yaw start value based on dx
			if (dx < 0) {
				yaw = 1.5 * Math.PI;
			} else {
				yaw = 0.5 * Math.PI;
			}
			yaw -= Math.atan(dz / dx);
		} else if (dz < 0) {
			yaw = Math.PI;
		}
		return (float) (-yaw * 180 / Math.PI - 90);
	}

}
