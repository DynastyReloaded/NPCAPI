package com.src.main.packets;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.src.armor.EntityListener;
import com.src.events.ArmorStandInteractEvent;
import com.src.events.NPCInteractEvent;
import com.src.events.enums.InteractionType;
import com.src.main.NPCManager;
import com.src.main.NPCS;
import com.src.main.util.MathUtil;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

public class PacketHandler extends ChannelDuplexHandler {
	private Player p;
	private ArrayList<UUID> firedTwice = new ArrayList<UUID>();
	private long cooldown = MathUtil.ClickCooldown * 20;
	
	public PacketHandler(final Player p) {
		this.p = p;
	}

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		super.write(ctx, msg, promise);
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public void channelRead(ChannelHandlerContext c, Object m) throws Exception {
		if (m.getClass().getSimpleName().equalsIgnoreCase("PacketPlayInUseEntity")) {
			Object s = Reflection.getField(m.getClass(), "action").get(m).toString();
			int i = (int) Reflection.getField(m.getClass(), "a").get(m);

			if (s.equals(Enum.valueOf(NMSManager.EnumEntityActionUse, "INTERACT").toString()) && NPCManager.npctrack.containsKey(i)) {
				if (!firedTwice.contains(p.getUniqueId())) {

					firedTwice.add(p.getUniqueId());

					UUID id = p.getUniqueId();
					NPCInteractEvent ev = new NPCInteractEvent(p, NPCManager.npctrack.get(i),
							InteractionType.RIGHT_CLICKED);
					Bukkit.getServer().getPluginManager().callEvent(ev);

					Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(NPCS.getInstance(), new Runnable() {

						@Override
						public void run() {

							firedTwice.remove(id);

						}
					}, cooldown);

				}

			} else if (s.equals(Enum.valueOf(NMSManager.EnumEntityActionUse, "ATTACK").toString()) && NPCManager.npctrack.containsKey(i)) {
				if (!firedTwice.contains(p.getUniqueId())) {

					firedTwice.add(p.getUniqueId());
					UUID id = p.getUniqueId();

					NPCInteractEvent ev = new NPCInteractEvent(p, NPCManager.npctrack.get(i), InteractionType.ATTACK);
					Bukkit.getServer().getPluginManager().callEvent(ev);

					Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(NPCS.getInstance(), new Runnable() {

						@Override
						public void run() {

							firedTwice.remove(id);

						}
					}, cooldown);

				}

			}else if(EntityListener.armorstandId.containsKey(i)){
				
				ArmorStandInteractEvent ev = new ArmorStandInteractEvent(p, i);
				Bukkit.getServer().getPluginManager().callEvent(ev);
				
			}else{
				super.channelRead(c, m);
			}
			
			

		} else {
			super.channelRead(c, m);
		}

	}

}
