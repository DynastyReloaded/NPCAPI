package com.src.armor;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.src.events.ArmorStandInteractEvent;
import com.src.events.NPCInteractEvent;
import com.src.main.NPCManager;
import com.src.main.NPCTracker;
import com.src.main.util.MessageManager;

public class EntityListener implements Listener {

	public static ArrayList<Player> editorList = new ArrayList<Player>();
	public static HashMap<Player, EntityManager> entityManager = new HashMap<>();
	public static HashMap<Integer, Object> armorstandId = new HashMap<>();
	public static ArrayList<Object> entityBeingEdited = new ArrayList<>();

	@EventHandler
	public void editEntity(NPCInteractEvent e) {

		NPCTracker npct = null;

		if (NPCManager.npctrack.containsKey(NPCManager.skinOwners.get(e.getSkinOwner()))) {

			npct = NPCManager.npctrack.get(NPCManager.skinOwners.get(e.getSkinOwner()));

		}

		if (editorList.contains(e.getPlayer())) {

			MessageManager.getInstance().inform(e.getPlayer(), "You are now editing " + e.getEntityName());
			entityBeingEdited.add(e.getEntity());
			EntityManager em = new EntityManager(e.getPlayer(), e.getId(), e.getEntity());

			entityManager.put(e.getPlayer(), em);

			em.makeArrows();

			editorList.remove(e.getPlayer());
		}

		if (NPCManager.command.containsKey(e.getPlayer())) {

			if (npct != null) {

				npct.setCommand(NPCManager.command.get(e.getPlayer()));

				MessageManager.getInstance().inform(e.getPlayer(),
						"Command: " + NPCManager.command.get(e.getPlayer()) + "has been set!");

				NPCManager.command.remove(e.getPlayer());

			}
			return;
		}

		if (npct.getCommand() != null) {
			
			String s = npct.getCommand().replace("{player}", e.getPlayer().getName());
			
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), s);
	
		}

	}

	@EventHandler
	public void onArrowClick(ArmorStandInteractEvent e) {

		if (entityManager.containsKey(e.getPlayer())) {

			EntityManager em = entityManager.get(e.getPlayer());

			if (e.getEntity().equals(em.HelmetLeft)) {

				em.updateArmor(ArmorType.HEAD, ArmorDirection.LEFT);

				return;
			}
			if (e.getEntity().equals(em.ChestLeft)) {

				em.updateArmor(ArmorType.CHEST, ArmorDirection.LEFT);

				return;
			}
			if (e.getEntity().equals(em.LegsLeft)) {

				em.updateArmor(ArmorType.LEGS, ArmorDirection.LEFT);

				return;
			}
			if (e.getEntity().equals(em.BootsLeft)) {

				em.updateArmor(ArmorType.FEET, ArmorDirection.LEFT);

				return;
			}
			if (e.getEntity().equals(em.HelmetRight)) {

				em.updateArmor(ArmorType.HEAD, ArmorDirection.RIGHT);

				return;
			}
			if (e.getEntity().equals(em.ChestRight)) {

				em.updateArmor(ArmorType.CHEST, ArmorDirection.RIGHT);

				return;
			}
			if (e.getEntity().equals(em.LegsRight)) {

				em.updateArmor(ArmorType.LEGS, ArmorDirection.RIGHT);

				return;
			}
			if (e.getEntity().equals(em.BootsRight)) {

				em.updateArmor(ArmorType.FEET, ArmorDirection.RIGHT);

				return;
			}

		}

	}

}
