package me.matyyy.kscore.api;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

import me.matyyy.kscore.config.AbstractConfigFile;
import me.matyyy.kscore.score.ScoreBoard;
import me.matyyy.kscore.utils.NametagManager;

public interface KScoreAPI {

void load();
	
	void enable();
	
	void disable();
	
	Collection<ScoreBoard> getBoardList();
	
	void setPlayerFLScoreBoard(Player player);
	
	void removePlayerFLScoreBoard(UUID uuid);
	
	String translateColor(String string);
	
	List<String> translateColorList(List<String> string);
	
	MainKScoreAPI getMainAPI();
	
	AbstractConfigFile getConfig();
	
	NametagManager getNametagManager();
}
