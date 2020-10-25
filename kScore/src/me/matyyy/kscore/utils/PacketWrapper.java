package me.matyyy.kscore.utils;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PacketWrapper {

	public String error;
    private Object packet;

    private static Constructor<?> ChatComponentText;
    private static Class<? extends Enum> typeEnumChatFormat;

    public PacketWrapper(String name, int param, List<String> members) {
    	this.packet = PacketAccessor.createPacket();
        if (param != 3 && param != 4) {
            throw new IllegalArgumentException("Method must be join or leave for player constructor");
        }
        setupDefaults(name, param);
        setupMembers(members);
    }

    @SuppressWarnings("unchecked")
    public PacketWrapper(String name, String prefix, String suffix, int param, Collection<?> players) {
    	this.packet = PacketAccessor.createPacket();
        setupDefaults(name, param);
        if (param == 0 || param == 2) {
            try {            	            	
                if (PacketAccessor.isLegacyVersion()) {
                    PacketAccessor.DISPLAY_NAME.set(this.packet, name);
                    PacketAccessor.PREFIX.set(this.packet, prefix);
                    PacketAccessor.SUFFIX.set(this.packet, suffix);
                } else {					
                    String color = ChatColor.getLastColors(prefix);
                    String colorCode = null;

                    if (!color.isEmpty()) {						
                        colorCode = color.substring(color.length() - 1);
                        String chatColor = ChatColor.getByChar(colorCode).name();

                        if (chatColor.equalsIgnoreCase("MAGIC"))
                            chatColor = "OBFUSCATED";

                        Enum<?> colorEnum = Enum.valueOf(typeEnumChatFormat, chatColor);
                        PacketAccessor.TEAM_COLOR.set(this.packet, colorEnum);
                    }

                    PacketAccessor.DISPLAY_NAME.set(this.packet, ChatComponentText.newInstance(name));
                    PacketAccessor.PREFIX.set(this.packet, ChatComponentText.newInstance(prefix));

                    if (colorCode != null)
                        suffix = ChatColor.getByChar(colorCode) + suffix;

                    PacketAccessor.SUFFIX.set(this.packet, ChatComponentText.newInstance(suffix));
                }

                PacketAccessor.PACK_OPTION.set(this.packet, 1);

                if (PacketAccessor.VISIBILITY != null) {
                    PacketAccessor.VISIBILITY.set(this.packet, "always");
                }

                if (param == 0) {
                    ((Collection) PacketAccessor.MEMBERS.get(this.packet)).addAll(players);
                }
            } catch (Exception e) {
                error = e.getMessage();
            }
        }
    }

    private void setupMembers(Collection<?> players) {
        try {
            players = players == null || players.isEmpty() ? new ArrayList<>() : players;
            ((Collection) PacketAccessor.MEMBERS.get(this.packet)).addAll(players);
        } catch (Exception e) {
            error = e.getMessage();
        }
    }

    private void setupDefaults(String name, int param) {
        try {
            PacketAccessor.TEAM_NAME.set(this.packet, name);
            PacketAccessor.PARAM_INT.set(this.packet, param);

            if (PacketAccessor.PUSH != null) {
                PacketAccessor.PUSH.set(this.packet, "never");
            }
        } catch (Exception e) {
            error = e.getMessage();
        }
    }
    
    public void send() {
        PacketAccessor.sendPacket(Bukkit.getOnlinePlayers(), this.packet);
    }

    public void send(Player player) {
    	PacketAccessor.sendPacket(player, this.packet);
    }
    
    static {
        try {
        	if (!PacketAccessor.isLegacyVersion()) {
        		final String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
            	final Class<?> typeChatComponentText = Class.forName("net.minecraft.server." + version + ".ChatComponentText");
            	ChatComponentText = typeChatComponentText.getConstructor(String.class);
            	typeEnumChatFormat = (Class<? extends Enum>) Class.forName("net.minecraft.server." + version + ".EnumChatFormat");	
        	}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
