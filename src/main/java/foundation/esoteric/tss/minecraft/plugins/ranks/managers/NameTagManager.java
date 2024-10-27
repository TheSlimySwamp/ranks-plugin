package foundation.esoteric.tss.minecraft.plugins.ranks.managers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import foundation.esoteric.tss.minecraft.plugins.core.data.Rank;
import foundation.esoteric.tss.minecraft.plugins.ranks.TSSRanksPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public final class NameTagManager {

  private final TSSRanksPlugin plugin;

  public NameTagManager(TSSRanksPlugin plugin) {
	this.plugin = plugin;
  }

  public void setNameTags(@NotNull Player player) {
	Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
	player.setScoreboard(scoreboard);

	RankManager rankManager = plugin.getRankManager();
	for (Rank rank : rankManager.getRanks()) {
	  Team team = scoreboard.registerNewTeam(rank.getName());

	  TextComponent rankDisplayName = rank.getDisplayName();
	  if (!rankDisplayName.equals(Component.empty())) {
		team.prefix(rank.getNamePrefix());
		team.suffix(rank.getNameSuffix());
	  }
	}

	UUID playerUUID = player.getUniqueId();

	for (Player target : Bukkit.getOnlinePlayers()) {
	  UUID targetUUID = target.getUniqueId();

	  if (!targetUUID.equals(playerUUID)) {
		Rank targetRank = rankManager.getPlayerRank(targetUUID);

		Team targetRankTeam = scoreboard.getTeam(targetRank.getName());

		if (targetRankTeam != null) {
		  targetRankTeam.addEntry(target.getName());
		}
	  }
	}
  }

  public void addNewNameTag(@NotNull Player player) {
	RankManager rankManager = plugin.getRankManager();
	Rank playerRank = rankManager.getPlayerRank(player);

	String rankName = playerRank.getName();
	String playerName = player.getName();

	for (Player target : Bukkit.getOnlinePlayers()) {
	  Team targetRankTeam = target.getScoreboard().getTeam(rankName);

	  if (targetRankTeam != null) {
		targetRankTeam.addEntry(playerName);
	  }
	}
  }

  public void removeNameTag(@NotNull Player player) {
	String playerName = player.getName();

	for (Player target : Bukkit.getOnlinePlayers()) {
	  Team playerTeam = target.getScoreboard().getEntryTeam(playerName);

	  if (playerTeam != null) {
		playerTeam.removeEntry(playerName);
	  }
	}
  }
}
