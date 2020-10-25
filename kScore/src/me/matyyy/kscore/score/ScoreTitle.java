package me.matyyy.kscore.score;

import java.util.List;

public class ScoreTitle extends ScoreUpdate {

	public ScoreTitle(int reload, List<String> list) {
		super(reload, list);
		this.objective = true;
	}
}
