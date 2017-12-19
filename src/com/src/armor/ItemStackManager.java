package com.src.armor;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum ItemStackManager {

	LEATHER(new ItemStack(Material.LEATHER_HELMET), new ItemStack(Material.LEATHER_CHESTPLATE),
			new ItemStack(Material.LEATHER_LEGGINGS),
			new ItemStack(Material.LEATHER_BOOTS)), 
	CHAIN(new ItemStack(Material.CHAINMAIL_HELMET),
					new ItemStack(Material.CHAINMAIL_CHESTPLATE), new ItemStack(Material.CHAINMAIL_LEGGINGS),
					new ItemStack(Material.CHAINMAIL_BOOTS)),
	IRON(new ItemStack(Material.IRON_HELMET),
			new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS),
			new ItemStack(Material.IRON_BOOTS)),
	GOLD(new ItemStack(Material.GOLD_HELMET),
			new ItemStack(Material.GOLD_CHESTPLATE), new ItemStack(Material.GOLD_LEGGINGS),
			new ItemStack(Material.GOLD_BOOTS)),
	DIAMOND(new ItemStack(Material.DIAMOND_HELMET),
			new ItemStack(Material.DIAMOND_CHESTPLATE), new ItemStack(Material.DIAMOND_LEGGINGS),
			new ItemStack(Material.DIAMOND_BOOTS)),
	AIR(new ItemStack(Material.AIR),
			new ItemStack(Material.AIR), new ItemStack(Material.AIR),
			new ItemStack(Material.AIR));

	private ItemStack head;
	private ItemStack chest;
	private ItemStack leggings;
	private ItemStack boots;
	
	ItemStackManager(ItemStack head, ItemStack chest, ItemStack leggings, ItemStack boots) {

		this.head = head;
		this.chest = chest;
		this.leggings = leggings;
		this.boots = boots;
		
	}

	public ItemStack getHead(){
		return head;
	}
	
	public ItemStack getChest(){
		return chest;
	}
	
	public ItemStack getLeggings(){
		return leggings;
	}
	
	public ItemStack getBoots(){
		return boots;
	}
	
}
