package me.matyyy.kscore.api;

public class PluginScoreAPI {

	private static KScoreAPI PLUGIN = null;
	
	public static void setPlugin(KScoreAPI api) {
		if(PLUGIN == null) {
			PLUGIN = api;
		}
	}
	
	public static KScoreAPI getPlugin() {
		return PLUGIN;
	}
	
	private PluginScoreAPI() {
	}
	
}
