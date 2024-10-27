package foundation.esoteric.tss.minecraft.plugins.ranks.event.listeners;

import foundation.esoteric.tss.minecraft.plugins.ranks.TSSRanksPlugin;
import foundation.esoteric.tss.minecraft.plugins.ranks.managers.NameTagManager;
import foundation.esoteric.tss.minecraft.plugins.ranks.managers.RankManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ConnectionListener implements Listener {

  private final TSSRanksPlugin plugin;

  public ConnectionListener(TSSRanksPlugin plugin) {
	this.plugin = plugin;
  }

  @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
  public void onJoin(@NotNull PlayerJoinEvent event) {
	Player player = event.getPlayer();
	UUID playerUuid = player.getUniqueId();

	RankManager rankManager = plugin.getRankManager();
	String rankName = plugin.getCore().getPlayerManager().getProfile(playerUuid).getRankName();

	if (!event.getPlayer().hasPlayedBefore() || rankName == null) {
	  rankManager.setRank(playerUuid, rankManager.getDefaultRank(), true);
	} else {
	  rankManager.setRank(playerUuid, rankName);
	}

	NameTagManager nameTagManager = plugin.getNameTagManager();

	nameTagManager.setNameTags(player);
	nameTagManager.addNewNameTag(player);

	rankManager.setPermissions(player);
  }

  @EventHandler
  public void onDisconnect(@NotNull PlayerQuitEvent event) {
	Player player = event.getPlayer();
	UUID playerUUID = player.getUniqueId();

	plugin.getNameTagManager().removeNameTag(player);
	plugin.getRankManager().getPlayerPermissionsMap().remove(playerUUID);
  }
}
