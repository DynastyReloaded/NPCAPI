package com.src.events.enums;

import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;

public enum BossColor {

	RED(ChatColor.RED, BarColor.RED), BLUE(ChatColor.BLUE, BarColor.BLUE), AQUA(ChatColor.AQUA,
			BarColor.BLUE), DARK_AQUA(ChatColor.DARK_AQUA, BarColor.BLUE), DARK_BLUE(ChatColor.DARK_BLUE,
					BarColor.BLUE), DARK_RED(ChatColor.DARK_RED, BarColor.RED), YELLOW(ChatColor.YELLOW,
							BarColor.YELLOW), GOLD(ChatColor.GOLD, BarColor.YELLOW), DARK_GREEN(ChatColor.DARK_GREEN,
									BarColor.GREEN), GREEN(ChatColor.GREEN, BarColor.GREEN), DARK_PURPLE(
											ChatColor.DARK_PURPLE, BarColor.PURPLE), PURPLE(ChatColor.LIGHT_PURPLE,
													BarColor.PURPLE), WHITE(ChatColor.WHITE, BarColor.PINK), GRAY(
															ChatColor.GRAY, BarColor.WHITE), DARK_GRAY(
																	ChatColor.DARK_GRAY, BarColor.WHITE), BLACK(
																			ChatColor.BLACK, BarColor.PINK);

	private ChatColor color;
	private BarColor bc;

	BossColor(ChatColor color, BarColor bc) {

		this.color = color;
		this.bc = bc;

	}

	public ChatColor getChatColor() {
		return color;
	}

	public BarColor getBarColor() {
		return bc;
	}

}
