package com.example.jsonparser.model;

import lombok.Getter;

import java.text.DecimalFormat;
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
        if (!this.stats.containsKey(player) || this.equals(player)) {
            return -1.0;
        } else {
            Stats s = this.stats.get(player);
            double p = Double.valueOf(s.getWin()) / Double.valueOf(s.getTotal()) * 100;
            DecimalFormat df = new DecimalFormat("#.00");
            String formatted_p = df.format(p);
            return Double.parseDouble(formatted_p);
        }
    }

    public Double getTotalWinRate() {
        double winCount = 0.0;
        double totalCount = 0.0;
        for (Stats s : this.getStats().values()) {
            winCount += s.getWin();
            totalCount += s.getTotal();
        }
        double ans = Double.valueOf(winCount) / Double.valueOf(totalCount) * 100;
        DecimalFormat df = new DecimalFormat("#.00");
        String formatted_ans = df.format(ans);
        return Double.parseDouble(formatted_ans);
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
