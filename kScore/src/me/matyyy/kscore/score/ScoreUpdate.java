package me.matyyy.kscore.score;

import java.util.List;

import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;

public class ScoreUpdate {

	private int reload, save, iList = -1, maxList;
	private List<String> list;
	protected boolean objective;
	protected String entry;
	
	public ScoreUpdate(int reload, List<String> list) {
		this.reload = this.save = reload;
		this.list = list;
		this.maxList = list.size();
	}
	
	public boolean getTime() {
		if(this.reload < 1) {
			this.reload = this.save;
			return true;
		} 
		this.reload--;
		return false;
	}
	
	private int next() {
		this.iList++;
		if(this.iList >= this.maxList) {
			this.iList = 0;
		}
		return this.iList;
	}
	
	public String replace(Player p) {
		int iList = this.next();
		String rep = PlaceholderAPI.setPlaceholders(p, this.list.get(iList));
		return rep;
	}
	
	public String getTitle() {
		int iList = this.next();
		String rep = this.list.get(iList);
		return rep;
	}
	
	public boolean getObjective() {
		return this.objective;
	}
	
	public String getEntry() {
		return this.entry;
	}
}
