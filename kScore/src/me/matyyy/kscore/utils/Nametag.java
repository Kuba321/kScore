package me.matyyy.kscore.utils;

import java.beans.ConstructorProperties;

public class Nametag {

    private String prefix;
    private String suffix;
    
    public String getPrefix() {
        return this.prefix;
    }
    
    public String getSuffix() {
        return this.suffix;
    }
    
    public void setPrefix(final String prefix) {
        this.prefix = prefix;
    }
    
    public void setSuffix(final String suffix) {
        this.suffix = suffix;
    }
    
    @ConstructorProperties({ "prefix", "suffix" })
    public Nametag(final String prefix, final String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }
}
