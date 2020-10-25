package me.matyyy.kscore.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class NametagManager {

    private final Map<String, FakeTeam> TEAMS = new HashMap<>();
    private final Map<String, FakeTeam> CACHED_FAKE_TEAMS = new HashMap<>();

    private FakeTeam getFakeTeam(String prefix, String suffix) {
        for (FakeTeam fakeTeam : this.TEAMS.values()) {
            if (fakeTeam.isSimilar(prefix, suffix)) {
                return fakeTeam;
            }
        }

        return null;
    }

    private void addPlayerToTeam(String player, String prefix, String suffix, int sortPriority, boolean playerTag) {
        FakeTeam previous = this.getFakeTeam(player);

        if (previous != null && previous.isSimilar(prefix, suffix)) {
            return;
        }

        this.reset(player);

        FakeTeam joining = getFakeTeam(prefix, suffix);
        if (joining != null) {
            joining.addMember(player);
        } else {
            joining = new FakeTeam(prefix, suffix, sortPriority, playerTag);
            joining.addMember(player);
            this.TEAMS.put(joining.getName(), joining);
            this.addTeamPackets(joining);
        }
        
        Player adding = Bukkit.getPlayerExact(player);
        if (adding != null) {
        	this.addPlayerToTeamPackets(joining, adding.getName());
        	this.cache(adding.getName(), joining);
        } else {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(player);
            this.addPlayerToTeamPackets(joining, offlinePlayer.getName());
            this.cache(offlinePlayer.getName(), joining);
        }

    }

    public FakeTeam reset(String player) {
        return this.reset(player, decache(player));
    }

    private FakeTeam reset(String player, FakeTeam fakeTeam) {
        if (fakeTeam != null && fakeTeam.getMembers().remove(player)) {
            boolean delete;
            Player removing = Bukkit.getPlayerExact(player);
            if (removing != null) {
                delete = this.removePlayerFromTeamPackets(fakeTeam, removing.getName());
            } else {
                OfflinePlayer toRemoveOffline = Bukkit.getOfflinePlayer(player);
                delete = this.removePlayerFromTeamPackets(fakeTeam, toRemoveOffline.getName());
            }

            if (delete) {
            	this.removeTeamPackets(fakeTeam);
                this.TEAMS.remove(fakeTeam.getName());
            }
        }

        return fakeTeam;
    }

    private FakeTeam decache(String player) {
        return this.CACHED_FAKE_TEAMS.remove(player);
    }

    public FakeTeam getFakeTeam(String player) {
        return this.CACHED_FAKE_TEAMS.get(player);
    }

    private void cache(String player, FakeTeam fakeTeam) {
        this.CACHED_FAKE_TEAMS.put(player, fakeTeam);
    }

    public void setNametag(String player, String prefix, String suffix) {
    	this.setNametag(player, prefix, suffix, -1);
    }

    void setNametag(String player, String prefix, String suffix, int sortPriority) {
    	this.setNametag(player, prefix, suffix, sortPriority, false);
    }

    void setNametag(String player, String prefix, String suffix, int sortPriority, boolean playerTag) {
    	this.addPlayerToTeam(player, prefix != null ? prefix : "", suffix != null ? suffix : "", sortPriority, playerTag);
    }

    public void sendTeams(Player player) {
        for (FakeTeam fakeTeam : TEAMS.values()) {
            new PacketWrapper(fakeTeam.getName(), fakeTeam.getPrefix(), fakeTeam.getSuffix(), 0, fakeTeam.getMembers()).send(player);
        }
    }

    void reset() {
        for (FakeTeam fakeTeam : TEAMS.values()) {
        	this.removePlayerFromTeamPackets(fakeTeam, fakeTeam.getMembers());
        	this.removeTeamPackets(fakeTeam);
        }
        this.CACHED_FAKE_TEAMS.clear();
        this.TEAMS.clear();
    }

    // ==============================================================
    // Below are private methods to construct a new Scoreboard packet
    // ==============================================================
    private void removeTeamPackets(FakeTeam fakeTeam) {
        new PacketWrapper(fakeTeam.getName(), fakeTeam.getPrefix(), fakeTeam.getSuffix(), 1, new ArrayList<>()).send();
    }

    private boolean removePlayerFromTeamPackets(FakeTeam fakeTeam, String... players) {
        return this.removePlayerFromTeamPackets(fakeTeam, Arrays.asList(players));
    }

    private boolean removePlayerFromTeamPackets(FakeTeam fakeTeam, List<String> players) {
        new PacketWrapper(fakeTeam.getName(), 4, players).send();
        fakeTeam.getMembers().removeAll(players);
        return fakeTeam.getMembers().isEmpty();
    }

    private void addTeamPackets(FakeTeam fakeTeam) {
        new PacketWrapper(fakeTeam.getName(), fakeTeam.getPrefix(), fakeTeam.getSuffix(), 0, fakeTeam.getMembers()).send();
    }

    private void addPlayerToTeamPackets(FakeTeam fakeTeam, String player) {
        new PacketWrapper(fakeTeam.getName(), 3, Collections.singletonList(player)).send();
    }
}
