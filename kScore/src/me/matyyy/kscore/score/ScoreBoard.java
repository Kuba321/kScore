package me.matyyy.kscore.score;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import me.matyyy.kscore.api.KScoreAPI;
import me.matyyy.kscore.config.AbstractConfigFile;
import me.matyyy.kscore.config.BukkitConfig.Enun;

public class ScoreBoard {

	private final KScoreAPI api;
	private Player p;
	private Scoreboard scoreboard;
	private Objective objective;
	private List<ScoreUpdate> list = new ArrayList<>();
	
	public ScoreBoard(KScoreAPI api, Player player) {
		this.api = api;
		this.p = player;
		this.scoreboard = this.api.getMainAPI().newScoreBoard();
		
		this.api.getMainAPI().runTaskLaterAsynchronously(() -> {

			AbstractConfigFile config = this.api.getConfig();
			
			Objective obj = this.scoreboard.registerNewObjective("Score", "dummy");
			this.objective = obj;
			
			obj.setDisplaySlot(DisplaySlot.SIDEBAR);
			
			this.list.add(new ScoreTitle(config.getScoreBoardTittleUpdate(), config.getScoreBoardTittleList()));
			
			for(Enun enun : config.getScoreBoardLines()) {
				
				Team team = this.scoreboard.registerNewTeam(enun.getName());
				team.addEntry(enun.getName());
				obj.getScore(enun.getName()).setScore(enun.getSlot());
				
				this.list.add(new ScoreLine(enun.getTime(), enun.getPrefixs(), enun.getName()));
			}
		}, 7L);
		
		this.setScoreboard();
	}

	public void setScoreboard() {
		this.p.setScoreboard(this.scoreboard);
	}
	
	public Scoreboard getScoreboard() {
		return this.scoreboard;
	}
	
	public void updateScoreBoard() {
		this.list.forEach(
				
				scoreUpdater -> {
					
					if(scoreUpdater.getTime()) {
						if(scoreUpdater instanceof ScoreLine) {
							Team t = this.scoreboard.getEntryTeam(scoreUpdater.getEntry());
							String suffix = scoreUpdater.replace(this.p);
							t.setSuffix(suffix);
						} else if(scoreUpdater instanceof ScoreTitle) {
							this.objective.setDisplayName(scoreUpdater.getTitle());
						}	
					}
					
				});
	}
	
	public void remove() {
		this.list.clear();
	}
}
