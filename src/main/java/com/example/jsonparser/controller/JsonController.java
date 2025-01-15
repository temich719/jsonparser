package com.example.jsonparser.controller;

import com.example.jsonparser.model.Player;
import com.example.jsonparser.parser.MyJsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

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
    public String getPlayers(Model model) {
        List<Player> ans = myJsonParser.parseTable();
        model.addAttribute(STATS, ans);
        return STATS;
    }

}
