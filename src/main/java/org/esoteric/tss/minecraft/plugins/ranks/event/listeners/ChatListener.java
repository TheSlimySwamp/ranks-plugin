package org.esoteric.tss.minecraft.plugins.ranks.event.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import foundation.esoteric.tss.minecraft.plugins.core.data.Colour;
import foundation.esoteric.tss.minecraft.plugins.core.data.Rank;
import org.esoteric.tss.minecraft.plugins.ranks.TSSRanksPlugin;
import org.esoteric.tss.minecraft.plugins.ranks.managers.RankManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public final class ChatListener implements Listener {

  private final RankManager rankManager;

  public ChatListener(@NotNull TSSRanksPlugin plugin) {
	this.rankManager = plugin.getRankManager();
  }

  @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
  public void onChat(@NotNull AsyncChatEvent event) {
	event.setCancelled(true);

	Player player = event.getPlayer();
	Rank playerRank = rankManager.getPlayerRank(player);
	TextComponent prefix = playerRank.getNamePrefix();
	TextComponent suffix = playerRank.getNameSuffix();

	Bukkit.broadcast(
			prefix.append(Component.text(player.getName(), Colour.WHITE)).append(suffix).append(Component.text(" » ", Colour.WHITE)).append(event.message().color(Colour.WHITE))
	);
  }
}
