package me.matyyy.kscore.score;

import java.util.List;

public class ScoreLine extends ScoreUpdate {
	
	public ScoreLine(int reload, List<String> list, String entry) {
		super(reload, list);
		this.entry = entry;
		this.objective = false;
	}
}
