package me.matyyy.kscore.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import me.matyyy.kscore.Main;
import me.matyyy.kscore.api.KScoreAPI;

public class NametagHandler implements Listener {

    public static boolean DISABLE_PUSH_ALL_TAGS = false;

    private List<GroupData> groupData = new ArrayList<>();
    private Map<UUID, PlayerData> playerData = new HashMap<>();

    private NametagManager nametagManager;
    private final KScoreAPI kscoreAPI;
    
    public NametagHandler(KScoreAPI plugin, NametagManager nametagManager) {
        this.kscoreAPI = plugin;
        this.nametagManager = nametagManager;

        Bukkit.getPluginManager().registerEvents(this, (Plugin) ((Main) this.kscoreAPI.getMainAPI()));
        
        for(EGroup e : EGroup.values()) {
        	GroupData data = new GroupData();
        	data.setGroupName(e.name);
        	data.setPrefix(e.prefix);
        	data.setSuffix(e.suffix);
        	data.setPermission(e.permission);
        	data.setSortPriority(e.sortPriority);
        	this.groupData.add(data);
        }
        
        this.applyConfig();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
    	this.nametagManager.reset(event.getPlayer().getName());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        this.nametagManager.sendTeams(player);
    }

    public PlayerData getPlayerData(Player player) {
        return player == null ? null : this.playerData.get(player.getUniqueId());
    }

    public List<GroupData> getGroupData() {
    	return new ArrayList<>(this.groupData);
    }

    public GroupData getGroupData(String key) {
        for (GroupData groupData : getGroupData()) {
            if (groupData.getGroupName().equalsIgnoreCase(key)) {
                return groupData;
            }
        }

        return null;
    }

    public void reload() {
        applyConfig();
        this.nametagManager.reset();
    }

    private void applyConfig() {

    	this.kscoreAPI.getMainAPI().runTaskTimer(() -> {
            applyTags();
    	}, 7L);
    	
    }

    public void applyTags() {
        if (!Bukkit.isPrimaryThread()) {
        	this.kscoreAPI.getMainAPI().runTask(() -> applyTags());

            return;
        }
        
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online != null) {
                this.applyTagToPlayer(online, false);
            }
        }
        
    }

    public void applyTagToPlayer(final Player player, final boolean loggedIn) {
        if (Bukkit.isPrimaryThread()) {
        	
        	this.kscoreAPI.getMainAPI().runTaskAsync(() -> {
        		 applyTagToPlayer(player, loggedIn);
        	});
        	
            return;
            
        }
        
        INametag nametagg = null;
        for (GroupData group : getGroupData()) {
        	if (player.hasPermission(group.getBukkitPermission())) {
        		nametagg = group;
        		break;
        	}
        }
        
        if (nametagg == null) return;

        final INametag nametag = nametagg;
        this.kscoreAPI.getMainAPI().runTask(() -> this.nametagManager.setNametag(player.getName(), nametag.getPrefix(), nametag.getSuffix(), nametag.getSortPriority()));

    }

}
