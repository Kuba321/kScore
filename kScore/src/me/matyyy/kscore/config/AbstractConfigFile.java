/*
 * Decompiled with CFR 0_132.
 */
package me.matyyy.kscore.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import me.matyyy.kscore.api.KScoreAPI;
import me.matyyy.kscore.config.BukkitConfig.Enun;

public abstract class AbstractConfigFile {
	
    protected final KScoreAPI scoreapi;
    protected final File file;

    public abstract Object get(String var1);

    public String getString(String path) {
        return (String)this.get(path);
    }

    public List<String> getStringList(String path) {
        return this.get(path) != null ? (List) this.get(path) : new ArrayList<String>();
    }

    public boolean getBoolean(String path) {
        return (Boolean)this.get(path, false);
    }

    public int getInt(String path) {
        return this.get(path) instanceof Number ? ((Number)this.get(path)).intValue() : -1;
    }

    public double getDouble(String path) {
        return this.get(path) instanceof Number ? ((Number)this.get(path)).doubleValue() : -1.0;
    }

    public Object get(String path, Object def) {
        return this.get(path) != null ? this.get(path) : def;
    }

    public String getString(String path, String def) {
        return this.get(path) != null ? (String)this.get(path) : def;
    }

    public List<String> getStringList(String path, List<String> def) {
        return this.get(path) != null ? this.getStringList(path) : def;
    }

    public boolean getBoolean(String path, boolean def) {
        return this.get(path) != null ? (Boolean)this.get(path) : def;
    }

    public int getInt(String path, int def) {
        return this.get(path) != null ? this.getInt(path) : def;
    }

    public double getDouble(String path, double def) {
        return this.get(path) != null ? this.getDouble(path) : def;
    }

    public abstract Set<String> getConfigurationSection(String var1);

    public abstract void reload();

    public abstract List<String> getScoreBoardTittleList();
    
    public abstract int getScoreBoardTittleUpdate();
    
    public abstract List<Enun> getScoreBoardLines();
    
    protected AbstractConfigFile(File file, KScoreAPI api) {
        this.file = file;
        this.scoreapi = api;
    }
}

