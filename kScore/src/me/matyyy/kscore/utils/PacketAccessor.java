package me.matyyy.kscore.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;

import org.bukkit.entity.Player;

public class PacketAccessor {

    private static String version = "v1_8_R3";
    private static boolean LEGACY_SERVER = true;
    
    static Field MEMBERS;
    static Field PREFIX;
    static Field SUFFIX;
    static Field TEAM_NAME;
    static Field PARAM_INT;
    static Field PACK_OPTION;
    static Field DISPLAY_NAME;
    static Field TEAM_COLOR;
    static Field PUSH;
    static Field VISIBILITY;

    private static Method getHandle;
    private static Method sendPacket;
    private static Field playerConnection;

    private static Class<?> packetClass;

    static {

        try {
            
            Class<?> typeCraftPlayer = Class.forName("org.bukkit.craftbukkit." + version + ".entity.CraftPlayer");
            getHandle = typeCraftPlayer.getMethod("getHandle");
            
            packetClass = Class.forName("net.minecraft.server." + version + ".PacketPlayOutScoreboardTeam");
            Class<?> typeNMSPlayer = Class.forName("net.minecraft.server." + version + ".EntityPlayer");
            Class<?> typePlayerConnection = Class.forName("net.minecraft.server." + version + ".PlayerConnection");
            playerConnection = typeNMSPlayer.getField("playerConnection");
            sendPacket = typePlayerConnection.getMethod("sendPacket", Class.forName("net.minecraft.server." + version + ".Packet"));

            PacketData currentVersion = null;
            for (PacketData packetData : PacketData.values()) {
                if (version.contains(packetData.name())) {
                    currentVersion = packetData;
                }
            }
            
            if (currentVersion != null) {
                PREFIX = getNMS(currentVersion.getPrefix());
                SUFFIX = getNMS(currentVersion.getSuffix());
                MEMBERS = getNMS(currentVersion.getMembers());
                TEAM_NAME = getNMS(currentVersion.getTeamName());
                PARAM_INT = getNMS(currentVersion.getParamInt());
                PACK_OPTION = getNMS(currentVersion.getPackOption());
                DISPLAY_NAME = getNMS(currentVersion.getDisplayName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isLegacyVersion() {
        return PacketAccessor.LEGACY_SERVER;
    }
    
    static Object createPacket() {
        try {
            return PacketAccessor.packetClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static void sendPacket(Collection<? extends Player> players, Object packet) {
        for (Player player : players) {
            sendPacket(player, packet);
        }
    }

    static void sendPacket(Player player, Object packet) {
        try {
            Object nmsPlayer = getHandle.invoke(player);
            Object connection = playerConnection.get(nmsPlayer);
            sendPacket.invoke(connection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Field getNMS(final String path) throws Exception {
        final Field field = PacketAccessor.packetClass.getDeclaredField(path);
        field.setAccessible(true);
        return field;
    }
    
}
