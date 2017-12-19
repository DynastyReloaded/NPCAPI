package com.src.main.packets;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;

import com.mojang.authlib.GameProfile;

public class NMSManager {

	public static Class<?> EntityPlayer;
	public static Class<?> PacketPlayOutEntityDestroy;
	public static Class<?> PacketPlayOutPlayerInfo;
	@SuppressWarnings("rawtypes")
	public static Class<? extends Enum> EnumPlayerInfo;
	public static Class<?> PacketPlayOutNamedEntitySpawn;
	public static Class<?> EntityHuman;
	public static Class<?> PlayerInteractManager;
	public static Class<?> Entity;
	public static Class<?> World;
	public static Class<?> MinecraftServer;
	public static Class<?> WorldServer;
	public static Class<?> CraftWorld;
	public static Class<?> CraftServer;
	@SuppressWarnings("rawtypes")
	public static Class<? extends Enum> EnumEntityActionUse;
	public static Class<?> PacketPlayOutEntityHeadRotation;
	public static Class<?> PacketPlayOutEntityLook;
	public static Class<?> CraftBossBar;
	public static Class<?> ItemStack;
	@SuppressWarnings("rawtypes")
	public static Class<? extends Enum> EnumItemSlot;
	public static Class<?> CraftItemStack;
	public static Class<?> PacketPlayOutEntityEquipment;
	public static Class<?> EntityArmorStand;
	public static Class<?> PacketPlayOutSpawnEntityLiving;
	public static Class<?> EntityLiving;

	public static Constructor<?> ConstructorEntityDestroy;
	public static Constructor<?> ConstructorPacketPlayOutPlayerInfo;
	public static Constructor<?> ConstructorPacketPlayOutNamedEntitySpawn;
	public static Constructor<?> ConstructorEntityPlayer;
	public static Constructor<?> ConstructorPlayerInteractManager;
	public static Constructor<?> ConstructorPacketPlayOutEntityHeadRotation;
	public static Constructor<?> ConstructorPacketPlayOutEntityLook;
	public static Constructor<?> ConstructorCraftBossBar;
	public static Constructor<?> ConstructorPacketPlayOutEntityEquipment;
	public static Constructor<?> ConstructorEntityArmorStand;
	public static Constructor<?> ConstructorPacketPlayOutSpawnEntityLiving;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void setupReflections(){
		
		PacketPlayOutEntityHeadRotation = Reflection.getNMSClass("PacketPlayOutEntityHeadRotation");
		EntityPlayer = Reflection.getNMSClass("EntityPlayer");
		Entity = Reflection.getNMSClass("Entity");
		PacketPlayOutEntityDestroy = Reflection.getNMSClass("PacketPlayOutEntityDestroy");
		PacketPlayOutPlayerInfo = Reflection.getNMSClass("PacketPlayOutPlayerInfo");
		PacketPlayOutNamedEntitySpawn = Reflection.getNMSClass("PacketPlayOutNamedEntitySpawn");
		EntityHuman = Reflection.getNMSClass("EntityHuman");
		PlayerInteractManager = Reflection.getNMSClass("PlayerInteractManager");
		World = Reflection.getNMSClass("World");
		MinecraftServer = Reflection.getNMSClass("MinecraftServer");
		WorldServer = Reflection.getNMSClass("WorldServer");
		CraftWorld = Reflection.getCraftClass("CraftWorld");
		CraftServer = Reflection.getCraftClass("CraftServer");
		EnumEntityActionUse = (Class<? extends Enum>) Reflection.getNMSClass("PacketPlayInUseEntity$EnumEntityUseAction");
		PacketPlayOutEntityLook = Reflection.getNMSClass("PacketPlayOutEntity$PacketPlayOutEntityLook");
		CraftBossBar = Reflection.getCraftClass("boss.CraftBossBar");
		ItemStack = Reflection.getNMSClass("ItemStack");
		EnumItemSlot = (Class<? extends Enum>) Reflection.getNMSClass("EnumItemSlot");
		CraftItemStack = Reflection.getCraftClass("inventory.CraftItemStack");
		PacketPlayOutEntityEquipment = Reflection.getNMSClass("PacketPlayOutEntityEquipment");
		EntityArmorStand = Reflection.getNMSClass("EntityArmorStand");
		PacketPlayOutSpawnEntityLiving = Reflection.getNMSClass("PacketPlayOutSpawnEntityLiving");
		EntityLiving = Reflection.getNMSClass("EntityLiving");
		
		for (Class<?> c : PacketPlayOutPlayerInfo.getDeclaredClasses()) {
			if (c.getSimpleName().equals("EnumPlayerInfoAction")) {
				Class<? extends Enum> temp = (Class<? extends Enum>) c;
				
				EnumPlayerInfo = temp;
			}
		}
		
		Class<? extends Object> a = Array.newInstance(EntityPlayer, 0).getClass();
		Class<? extends Object> ab = Array.newInstance(BarFlag.class, 0).getClass();
		//Constructors
		try {
			
			ConstructorPlayerInteractManager = PlayerInteractManager.getConstructor(World);
			ConstructorEntityDestroy = PacketPlayOutEntityDestroy.getConstructor(int[].class);
			ConstructorPacketPlayOutPlayerInfo = PacketPlayOutPlayerInfo.getConstructor(EnumPlayerInfo, a);
			ConstructorPacketPlayOutNamedEntitySpawn = PacketPlayOutNamedEntitySpawn.getConstructor(EntityHuman);
			ConstructorEntityPlayer = EntityPlayer.getConstructor(MinecraftServer, WorldServer, GameProfile.class, PlayerInteractManager);
			ConstructorPacketPlayOutEntityHeadRotation = PacketPlayOutEntityHeadRotation.getConstructor(Entity, byte.class);
			ConstructorPacketPlayOutEntityLook = PacketPlayOutEntityLook.getConstructor(Integer.TYPE, byte.class, byte.class, Boolean.TYPE);
			ConstructorCraftBossBar = CraftBossBar.getConstructor(String.class, BarColor.class, BarStyle.class, ab);
			ConstructorPacketPlayOutEntityEquipment = PacketPlayOutEntityEquipment.getConstructor(Integer.TYPE, EnumItemSlot, ItemStack);
			ConstructorEntityArmorStand = EntityArmorStand.getConstructor(World);
			ConstructorPacketPlayOutSpawnEntityLiving = PacketPlayOutSpawnEntityLiving.getConstructor(EntityLiving);
			
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		
	}
	
}
