package me.matyyy.kscore.api;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Scoreboard;

import me.matyyy.kscore.config.AbstractConfigFile;

public interface MainKScoreAPI {

	public KScoreAPI getPlugin();
	
	public InputStream getResource(String fileName);
	
	public File getDataFolder();
	
	public PluginManager getPluginManager();
	
	public void registerListener(Listener clazz);
	
	public void sendMessage(String msg);
	
	public void sendBroadcastMessage(String msg);
	
    public boolean isOnlineUUID(UUID uuid);

    public boolean isOnlineString(String name);
    
    public Player isOnlinePlayer(String name);
    
    public UUID getUUID(String name);

    public String getName(UUID uuid);
    
    public OfflinePlayer getOfflinePlayer(String name);
    
    public String getOfflineName(UUID uuid);
	
	public void runTask(Runnable run);
	
	public void runTaskAsync(Runnable run);
	
	public void runTaskTimer(Runnable run);
	
	public void setCommand(String command);
	
	public BukkitTask runTaskTimerAsync(Runnable run, long replaces);
	
	public BukkitTask runTaskTimer(Runnable run, long replaces);
	
	public void runTaskLater(Runnable run, long time);
	
	public void runTaskLaterAsynchronously(Runnable run, long time);
	
	public boolean isOnline(UUID uuid);
	
	public void scheduleSyncDelayedTask(Runnable run);
	
	public Scoreboard newScoreBoard();
	
	public AbstractConfigFile getPluginConfig();
}
