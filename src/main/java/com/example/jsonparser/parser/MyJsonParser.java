package com.example.jsonparser.parser;

import com.example.jsonparser.exception.CustomParseException;
import com.example.jsonparser.model.Player;
import com.example.jsonparser.model.Stats;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class MyJsonParser {

    private static final String TARGET_FOLDER_PATH = "src/main/resources/files";
    private static final String JSON_EXT = ".json";
    private static final String DATA = "data";
    private static final String COMPETITORS = "Competitors";
    private static final String TEAM = "Team";
    private static final String PLAYERS = "Players";
    private static final String DISPLAY_NAME = "DisplayName";
    private static final String USERNAME = "Username";
    private static final String RESULT_STRING = "ResultString";
    private static final String BYE = "bye";

    public List<Player> parseTable() throws CustomParseException {
        List<Player> players = new ArrayList<>();
        File folder = new File(TARGET_FOLDER_PATH);
        File[] files = folder.listFiles((dir, name) -> name.endsWith(JSON_EXT));
        if (Objects.nonNull(files)) {
            for (File file : files) {
                readJsonFile(file, players);
            }
        }
        return players;
    }

    /*stats of the player against the opponent*/
    private void addOpponentAndStatsToPlayer(Player player, Player opponent, Stats stats) {
        Map<Player, Stats> playerStatsMap = player.getStats();
        if (playerStatsMap.containsKey(opponent)) {
            Stats old = playerStatsMap.get(opponent);
            if (stats.getWin().equals(stats.getLose())) {
                old.addDraws(1);
            } else if (stats.getWin() > stats.getLose()) {
                old.addWins(1);
            } else {
                old.addLoses(1);
            }
        } else {
            Stats initSt = new Stats();
            if (stats.getWin().equals(stats.getLose())) {
                initSt.addDraws(1);
            } else if (stats.getWin() > stats.getLose()) {
                initSt.addWins(1);
            } else {
                initSt.addLoses(1);
            }
            playerStatsMap.put(opponent, initSt);
        }
    }

    private void updatePlayerAndOppStats(Player player, Player opponent, Stats stats, List<Player> players) {
        if (players.contains(player)) {
            Player existing = players.get(players.indexOf(player));
            addOpponentAndStatsToPlayer(existing, opponent, stats);
        } else {
            addOpponentAndStatsToPlayer(player, opponent, stats);
            players.add(player);
        }
    }

    private void readJsonFile(File file, List<Player> players) throws CustomParseException {
        JsonFactory jsonFactory = new JsonFactory();
        try (JsonParser jsonParser = jsonFactory.createParser(file)) {
            while (!jsonParser.isClosed()) {
                JsonToken token = jsonParser.nextToken();
                if (JsonToken.FIELD_NAME.equals(token)) {
                    String fieldName = jsonParser.getText();
                    if (DATA.equals(fieldName)) {
                        jsonParser.nextToken();
                        Player player1 = new Player();
                        Player player2 = new Player();
                        String displayName = "";
                        String username = "";
                        String resultString = "";
                        while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                            while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                                boolean first = true;
                                if (COMPETITORS.equals(jsonParser.getText())) {
                                    jsonParser.nextToken();
                                    while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                                        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                                            if (TEAM.equals(jsonParser.getText())) {
                                                jsonParser.nextToken();
                                                while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                                                    if (PLAYERS.equals(jsonParser.getText())) {
                                                        jsonParser.nextToken();
                                                        while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                                                            while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                                                                if (DISPLAY_NAME.equals(jsonParser.getText())) {
                                                                    jsonParser.nextToken();
                                                                    displayName = jsonParser.getText();
                                                                } else if (USERNAME.equals(jsonParser.getText())) {
                                                                   jsonParser.nextToken();
                                                                   username = jsonParser.getText();
                                                                }
                                                            }
                                                            if (first) {
                                                                player1 = new Player();
                                                                player1.setDisplayName(displayName);
                                                                player1.setUsername(username);
                                                                first = false;
                                                            } else {
                                                                player2 = new Player();
                                                                player2.setDisplayName(displayName);
                                                                player2.setUsername(username);
                                                                first = true;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                if (RESULT_STRING.equals(jsonParser.getText())) {
                                    jsonParser.nextToken();
                                    resultString = jsonParser.getText();
                                }
                            }
                            String[] strings = resultString.split(" ");
                            String[] wdl;
                            if (strings.length == 2) {
                                wdl = strings[0].split("-");
                            } else if (strings[strings.length - 1].equals(BYE)) {
                                continue;
                            } else {
                                wdl = strings[strings.length - 1].split("-");
                            }
                            Stats s = new Stats(Integer.parseInt(wdl[0]), Integer.parseInt(wdl[2]), Integer.parseInt(wdl[1]));
                            Stats reverse = new Stats(Integer.parseInt(wdl[1]), Integer.parseInt(wdl[2]), Integer.parseInt(wdl[0]));
                            if (resultString.startsWith(player1.getDisplayName())) {
                                updatePlayerAndOppStats(player1, player2, s, players);
                                updatePlayerAndOppStats(player2, player1, reverse, players);
                            } else {
                                updatePlayerAndOppStats(player2, player1, s, players);
                                updatePlayerAndOppStats(player1, player2, reverse, players);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("bad parse");
            throw new CustomParseException("Something went wrong while parsing json");
        }
    }

}
