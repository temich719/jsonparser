package com.example.jsonparser.controller;

import com.example.jsonparser.exception.CustomParseException;
import com.example.jsonparser.model.Player;
import com.example.jsonparser.parser.MyJsonParser;
import com.example.jsonparser.util.TopLadder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class JsonController {

    private static final String STATS = "stats";
    private final MyJsonParser myJsonParser;

    @Autowired
    public JsonController(final MyJsonParser myJsonParser) {
        this.myJsonParser = myJsonParser;
    }

    @GetMapping(value = "/api/" + STATS, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String getPlayers(Model model) throws CustomParseException {
        List<Player> ans = myJsonParser.parseTable();
        List<Player> topLadderPlayers = ans.stream()
                                            .filter(this::isTopLadder)
                                            .collect(Collectors.toList());
        model.addAttribute(STATS, topLadderPlayers);
        return STATS;
    }

    private boolean isTopLadder(Player player) {
        for (TopLadder t : TopLadder.values()) {
            if (t.getDisplayName().equals(player.getDisplayName())) {
                return true;
            }
        }
        return false;
    }

}
