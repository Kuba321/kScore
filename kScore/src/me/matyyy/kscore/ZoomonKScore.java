package me.matyyy.kscore;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.matyyy.kscore.api.KScoreAPI;
import me.matyyy.kscore.api.MainKScoreAPI;
import me.matyyy.kscore.api.PluginScoreAPI;
import me.matyyy.kscore.config.AbstractConfigFile;
import me.matyyy.kscore.score.ScoreBoard;
import me.matyyy.kscore.utils.NametagManager;

public abstract class ZoomonKScore implements KScoreAPI {

	private final MainKScoreAPI api;
	final FileManager fileManager;
	private Map<UUID, ScoreBoard> uuidsScores = new HashMap<UUID, ScoreBoard>();
	
	public ZoomonKScore(MainKScoreAPI mainAPI) {
		
		PluginScoreAPI.setPlugin(this);
		
		this.api = mainAPI;
		
		this.fileManager = new FileManager(this);
		
	}

	public void load() {
		this.api.sendMessage("£adowanie pluginu");
		this.fileManager.createFiles();
	}
	
	public void enable() {
		this.api.sendMessage("W³¹czenie pluginu enable 1/2");
		this.api.runTaskTimerAsync(() -> {
			for(ScoreBoard playerScoreboard : this.uuidsScores.values()) {
				playerScoreboard.updateScoreBoard();
			}
		}, 1L);
		this.api.sendMessage("W³¹czenie pluginu enable 2/2");
	}
	
	public void disable() {
		this.api.sendMessage("Wy³¹czenie pluginu");
		this.uuidsScores.values().forEach( cs -> cs.remove());
		this.uuidsScores.clear();
	}
	
	@Override
	public Collection<ScoreBoard> getBoardList() {
		return this.uuidsScores.values();
	}
	
	@Override
	public void setPlayerFLScoreBoard(Player player) {
		this.api.sendMessage("setPlayerFLScoreBoard pluginu 1/2");
		this.uuidsScores.put(player.getUniqueId(), new ScoreBoard(this, player));
		this.api.sendMessage("setPlayerFLScoreBoard pluginu 2/2");
	}
	
	@Override
    public void removePlayerFLScoreBoard(UUID uuid) {
    	ScoreBoard scoreBoard = this.uuidsScores.get(uuid);
    	if(scoreBoard != null) {
    		scoreBoard.remove();
    		this.uuidsScores.remove(uuid);
    	}
    }
    
    @Override
    public String translateColor(String string) {
    	if(string == null) {
    		return "";
    	}
        return ChatColor.translateAlternateColorCodes((char)'&', (String)string);
    }

	@Override
	public List<String> translateColorList(List<String> string) {
		return string.stream().map(this::translateColor).collect(Collectors.toList());
	}

	@Override
	public MainKScoreAPI getMainAPI() {
		return this.api;
	}

	@Override
	public AbstractConfigFile getConfig() {
		return this.api.getPluginConfig();
	}

	@Override
	public NametagManager getNametagManager() {
		return null;
	}
	
}
