package me.matyyy.kscore;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import me.matyyy.kscore.api.KScoreAPI;
import me.matyyy.kscore.listener.PlayerListener;

public class PluginKScore extends ZoomonKScore {

	PluginKScore(Main mainAPI) {
		super(mainAPI);
	}

	@Override
	public void load() {
		super.load();
	}

	@Override
	public void enable() {
		super.enable();
		
		Bukkit.getPluginManager().registerEvents(new PlayerListener((KScoreAPI) this), (Plugin) ((Main) this.getMainAPI()));
	}

	@Override
	public void disable() {
		super.disable();
	}
}
