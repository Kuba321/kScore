package me.matyyy.kscore.utils;

import me.matyyy.kscore.api.PluginScoreAPI;

public enum EGroup {

	Wlasciciel("Wlasciciel", PluginScoreAPI.getPlugin().translateColor("&4&lW &e"), "", "nte.wlasciciel", 1),
	TECHNIK("Technik", PluginScoreAPI.getPlugin().translateColor("&c&lT &e"), "", "nte.technik", 2),
	Admin("Admin", PluginScoreAPI.getPlugin().translateColor("&c&lA &e"), "", "nte.admin", 3),
	Budowniczy("Budowniczy", PluginScoreAPI.getPlugin().translateColor("&b&lB &e"), "", "nte.budowniczy", 6),
	ChatModerator("ChatModerator", PluginScoreAPI.getPlugin().translateColor("&a&lCM &e"), "", "nte.chatmoderator", 8),
	Platyna("Platyna", PluginScoreAPI.getPlugin().translateColor("&5&lP &e"), "", "nte.platyna", 9),
	Svip("SVIP", PluginScoreAPI.getPlugin().translateColor("&6&LS&E&lV &e"), "", "nte.svip", 10),
	Vip("VIP", PluginScoreAPI.getPlugin().translateColor("&e&lVip &e"), "", "nte.vip", 11),
	Gracz("Gracz", PluginScoreAPI.getPlugin().translateColor("&e"), "", "nte.gracz", 12);
	
	public String name, prefix, suffix, permission;
	public int sortPriority;
	
	EGroup(String name, String prefix, String suffix, String permission, int sortPriority) {
		this.name = name;
		this.prefix = prefix;
		this.suffix = suffix;
		this.permission = permission;
		this.sortPriority = sortPriority;
	}
}
