package org.esoteric.tss.minecraft.plugins.ranks;

import foundation.esoteric.tss.minecraft.plugins.core.TSSCorePlugin;
import foundation.esoteric.tss.minecraft.plugins.core.managers.FileManager;
import org.esoteric.tss.minecraft.plugins.ranks.commands.SetRankCommand;
import org.esoteric.tss.minecraft.plugins.ranks.event.listeners.ChatListener;
import org.esoteric.tss.minecraft.plugins.ranks.event.listeners.ConnectionListener;
import org.esoteric.tss.minecraft.plugins.ranks.managers.NameTagManager;
import org.esoteric.tss.minecraft.plugins.ranks.managers.RankManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class TSSRanksPlugin extends JavaPlugin {

  private final TSSCorePlugin core = (TSSCorePlugin) Bukkit.getPluginManager().getPlugin("TSSCore");

  private FileManager fileManager;
  private RankManager rankManager;
  private NameTagManager nameTagManager;

  private File ranksFile;

  public TSSCorePlugin getCore() {
	return core;
  }

  public FileManager getFileManager() {
	return fileManager;
  }

  public RankManager getRankManager() {
	return rankManager;
  }

  public NameTagManager getNameTagManager() {
	return nameTagManager;
  }

  public File getRanksFile() {
	return ranksFile;
  }

  @Override
  public void onEnable() {
	getDataFolder().mkdir();

	getConfig().options().copyDefaults();
	saveDefaultConfig();

	fileManager = new FileManager(this);
	ranksFile = fileManager.initiateJsonFile("ranks");

	rankManager = new RankManager(this);
	nameTagManager = new NameTagManager(this);

	new SetRankCommand(this);

	PluginManager pluginManager = Bukkit.getPluginManager();

	pluginManager.registerEvents(new ChatListener(this), this);
	pluginManager.registerEvents(new ConnectionListener(this), this);

	for (final Player player : Bukkit.getOnlinePlayers()) {
	  nameTagManager.setNameTags(player);
	  nameTagManager.addNewNameTag(player);
	}
  }
}
