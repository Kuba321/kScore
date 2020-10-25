package me.matyyy.kscore.utils;

import java.beans.ConstructorProperties;
import java.util.List;

import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public class GroupData implements INametag {

	private String groupName;
    private String prefix;
    private String suffix;
    private String permission;
    private Permission bukkitPermission;
    private int sortPriority;
	private List<String> list;
	private int iList = -1, maxList;
	
    public GroupData() {
    }
    
    public void setPermission(final String permission) {
        this.permission = permission;
        this.bukkitPermission = new Permission(permission, PermissionDefault.FALSE);
    }
    
    @Override
    public boolean isPlayerTag() {
        return false;
    }
    
	private int next() {
		this.iList++;
		if(this.iList >= this.maxList) {
			this.iList = 0;
		}
		return this.iList;
	}
    
    public String getGroupName() {
        return this.groupName;
    }
    
    @Override
    public String getPrefix() {
        return this.prefix;
    }
    
    @Override
    public String getSuffix() {
        return this.suffix;
    }
    
    public String getPermission() {
        return this.permission;
    }
    
    public Permission getBukkitPermission() {
        return this.bukkitPermission;
    }
    
    @Override
    public int getSortPriority() {
        return this.sortPriority;
    }
    
    public void setGroupName(final String groupName) {
        this.groupName = groupName;
    }
    
    public void setPrefix(final String prefix) {
        this.prefix = prefix;
    }
    
    public void setSuffix(final String suffix) {
        this.suffix = suffix;
    }
    
    public void setBukkitPermission(final Permission bukkitPermission) {
        this.bukkitPermission = bukkitPermission;
    }
    
    public void setSortPriority(final int sortPriority) {
        this.sortPriority = sortPriority;
    }
    
    @ConstructorProperties({ "groupName", "prefix", "suffix", "permission", "bukkitPermission", "sortPriority" })
    public GroupData(final String groupName, final String prefix, final String suffix, final String permission, final Permission bukkitPermission, final int sortPriority) {
        this.groupName = groupName;
        this.prefix = prefix;
        this.suffix = suffix;
        this.permission = permission;
        this.bukkitPermission = bukkitPermission;
        this.sortPriority = sortPriority;
    }
}
