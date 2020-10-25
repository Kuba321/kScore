package me.matyyy.kscore;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Scoreboard;

import me.matyyy.kscore.api.KScoreAPI;
import me.matyyy.kscore.api.MainKScoreAPI;
import me.matyyy.kscore.api.PluginScoreAPI;
import me.matyyy.kscore.config.AbstractConfigFile;
import me.matyyy.kscore.config.BukkitConfig;
import me.matyyy.kscore.utils.NametagHandler;
import me.matyyy.kscore.utils.NametagManager;

public class Main extends JavaPlugin implements MainKScoreAPI {

	private PluginKScore plugin;
	private BukkitConfig config;
	private NametagManager managertag = new NametagManager();
	
	@Override
	public void onLoad() {
		this.sendMessage("£adowanie pluginu 1/3");
		this.plugin = new PluginKScore(this);
		this.sendMessage("£adowanie pluginu 2/3");
		this.config = new BukkitConfig(null, this.plugin);
		this.sendMessage("£adowanie pluginu 3/3");
		this.plugin.load();
	}
	
	@Override
	public void onEnable() {
		this.sendMessage("Wlaczenie pluginu 1/2");
		this.plugin.enable();
		this.sendMessage("Wlaczenie pluginu 2/2");
		new NametagHandler(((KScoreAPI) this.plugin), this.managertag);
	}
	
	@Override
	public void onDisable() {
		this.plugin.disable();
	}

	@Override
	public void sendMessage(String msg) {
		Bukkit.getConsoleSender().sendMessage(msg);
	}
	
	@Override
	public void sendBroadcastMessage(String msg) {
		if(msg == null) {
			return;
		}
		Bukkit.broadcastMessage(PluginScoreAPI.getPlugin().translateColor(msg));
	}
	
	@Override
	public AbstractConfigFile getPluginConfig() {
		return this.config;
	}
	
	@Override
	public PluginManager getPluginManager() {
		return Bukkit.getPluginManager();
	}
	
	@Override
	public void registerListener(Listener clazz) {
		this.getPluginManager().registerEvents(clazz, this);
	}
	
    @Override
    public boolean isOnlineUUID(UUID uuid) {
        return Bukkit.getPlayer((UUID)uuid) != null;
    }

    @Override
    public boolean isOnlineString(String name) {
        return Bukkit.getPlayer((String)name) != null;
    }
	
    @Override
    public UUID getUUID(String name) {
        return Bukkit.getPlayer((String)name) != null ? Bukkit.getPlayer((String)name).getUniqueId() : null;
    }

    @Override
    public String getName(UUID uuid) {
        return Bukkit.getPlayer((UUID)uuid) != null ? Bukkit.getPlayer((UUID)uuid).getName() : null;
    }
    
	@Override
	public void runTask(Runnable run) {
		Bukkit.getScheduler().runTask(this, run);
	}
	
	@Override
	public void runTaskAsync(Runnable run) {
		Bukkit.getScheduler().runTaskAsynchronously(this, run);
	}
	
	@Override
	public void setCommand(String command) {
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);	
	}
	
	@Override
	public void runTaskLater(Runnable run, long time) {
		Bukkit.getScheduler().runTaskLater(this, run, time);
	}
	
	@Override
	public void runTaskLaterAsynchronously(Runnable run, long time) {
		Bukkit.getScheduler().runTaskLaterAsynchronously(this, run, time);
	}
	
	@Override
	public boolean isOnline(UUID uuid) {
		return Bukkit.getPlayer(uuid) != null;
	}
	
	@Override
	public Player isOnlinePlayer(String name) {
		return Bukkit.getPlayer(name);
	}
	
	@Override
	public final BukkitTask runTaskTimerAsync(Runnable run, long replaces) {
		return Bukkit.getScheduler().runTaskTimerAsynchronously(this, run, 0L, replaces);
	}
	
	@Override
	public PluginKScore getPlugin() {
		return this.plugin;
	}

	@Override
	public void runTaskTimer(Runnable run) {
		Bukkit.getScheduler().runTaskTimer(this, run, 0L, 200L);
	}
	
	@Override
	public BukkitTask runTaskTimer(Runnable run, long replaces) {
		return Bukkit.getScheduler().runTaskTimer(this, run, 0L, replaces);
	}
	
	@Override
	public void scheduleSyncDelayedTask(Runnable run) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, run);
	}
	
	@Override
	public String getOfflineName(UUID uuid) {
		return Bukkit.getOfflinePlayer(uuid).getName();
	}
	
	@Override
	public OfflinePlayer getOfflinePlayer(String name) {
		return Bukkit.getOfflinePlayer(name);
	}

	@Override
	public Scoreboard newScoreBoard() {
		return Bukkit.getScoreboardManager().getNewScoreboard();
	}
}
