package me.matyyy.kscore.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;

import me.matyyy.kscore.Main;
import me.matyyy.kscore.PluginKScore;

public class BukkitConfig extends AbstractConfigFile {

	private final FileConfiguration config;
	private List<Enun> enums = new ArrayList<>();
	
	public BukkitConfig(File file, PluginKScore main) {
		super(file, main);
		this.config = ((Main) main.getMainAPI()).getConfig();
        this.reload();
    }

    @Override
    public Object get(String path) {
        return this.config.get(path);
    }

    @Override
    public Set<String> getConfigurationSection(String path) {
        return this.config.getConfigurationSection(path) != null ? this.config.getConfigurationSection(path).getKeys(false) : Collections.emptySet();
    }

    @Override
    public final void reload() {
        ((Main) this.scoreapi.getMainAPI()).reloadConfig();
    }

	@Override
	public List<String> getScoreBoardTittleList() {
		return this.scoreapi.translateColorList(this.getStringList("Scoreboard_TittleList", Collections.emptyList()));
	}

	@Override
	public int getScoreBoardTittleUpdate() {
		return this.getInt("Scoreboard_TittleUpdate", 5);
	}
	
	@Override
	public List<Enun> getScoreBoardLines() {
		if(this.enums == null || this.enums.isEmpty()) {
			for(String number : this.getConfigurationSection("ScoreBoard")) {

				Enun en = 
					new Enun(this.scoreapi.translateColor(this.getString("ScoreBoard." + number + ".Name", "")), 
						this.scoreapi.translateColorList(this.getStringList("ScoreBoard." + number + ".Prefix", Arrays.asList("B³¹d #23", "B³¹d #23"))),
						this.getInt("ScoreBoard." + number + ".Time", 5),
						Integer.parseInt(number));
				this.enums.add(en);
			}
		}
		return this.enums;
	}
	
	public class Enun {
		
		private String name;
		private List<String> prefix = new ArrayList<>();
		private int time, slot;
		
		public Enun(String name, List<String> prefix, int time, int slot) {
			this.name = name;
			this.prefix = prefix;
			this.time = time;
			this.slot = slot;
		}
		
		public String getName() {
			return this.name;
		}
		
		public List<String> getPrefixs() {
			return this.prefix;
		}
		
		public int getTime() {
			return this.time;
		}
		
		public int getSlot() {
			return this.slot;
		}
		
	}
}
