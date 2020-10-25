package me.matyyy.kscore.listener;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.matyyy.kscore.api.KScoreAPI;

public class PlayerListener implements Listener {

	private final KScoreAPI kScoreAPI;
	
	public PlayerListener(KScoreAPI kScoreAPI) {
		this.kScoreAPI = kScoreAPI;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		
		this.kScoreAPI.setPlayerFLScoreBoard(player);
	}
	
	@EventHandler
	public void onJoin(PlayerQuitEvent e) {
		UUID uuid = e.getPlayer().getUniqueId();
		
		this.kScoreAPI.removePlayerFLScoreBoard(uuid);
	}
}
