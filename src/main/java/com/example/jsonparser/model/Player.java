package com.example.jsonparser.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Player {

    private String displayName;
    private String username;
    private final Map<Player, Stats> stats;

    public Player(final String displayName, final String username) {
        this.username = username;
        this.displayName = displayName;
        this.stats = new HashMap<>();
    }

    public Double getPercentAgainstPlayer(Player player) {
        System.out.println();
        System.out.println();
        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&");
        System.out.println(this.toString());
        System.out.println(player.toString());
        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&");
        if (!this.stats.containsKey(player) || this.equals(player)) {
            System.out.println("-1");
            return -1.0;
        } else {
            Stats s = this.stats.get(player);
            System.out.println("&&&&&&&&&");
            System.out.println(s.toString());
            return Double.valueOf(s.getWin()) / Double.valueOf(s.getTotal()) * 100;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Player player = (Player) obj;
        return this.username.equals(player.username) && this.displayName.equals(player.displayName);
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + displayName.hashCode();
        result = 31 * result + username.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "{" +
                    "displayName: " + displayName + "," +
                    "username: " + username + "," +
                "}";
    }

    public Player() {
        this.stats = new HashMap<>();
    }

    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public void addStat(final Player player, final Stats stats) {
        this.stats.put(player, stats);
    }

}
