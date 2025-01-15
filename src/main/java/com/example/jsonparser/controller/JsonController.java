package com.example.jsonparser.controller;

import com.example.jsonparser.model.Player;
import com.example.jsonparser.model.Stats;
import com.example.jsonparser.parser.MyJsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@Controller
public class JsonController {

    private static final String STATS = "stats";
    private static final String PERCENTS = "percents";
    private final MyJsonParser myJsonParser;

    @Autowired
    public JsonController(final MyJsonParser myJsonParser) {
        this.myJsonParser = myJsonParser;
    }

    @GetMapping(value = "/api/" + STATS, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String getPlayers(Model model) {
        List<Player> ans = myJsonParser.parseTable();
        //todo подумать о выводе статы посчитанной в tymeleaf
        List<List<Double>> percents = new ArrayList<>();
        List<Double> buff;
        for (Player player : ans) {
            buff = new ArrayList<>();
            for (Player nested : ans) {
                if (player.equals(nested)) {
                    buff.add(-1.0);
                } else {
                    if (!player.getStats().containsKey(nested)) {
                        buff.add(-1.0);
                    } else {
                        Stats stats = player.getStats().get(nested);
                        buff.add(Double.parseDouble(String.valueOf((stats.getWin() / stats.getTotal()))) * 100.0);
                    }
                }
            }
            percents.add(buff);
            //buff.clear();
        }
        System.out.println("per: " + percents);
        model.addAttribute(PERCENTS, percents);
        System.out.println("ans: " + ans);
        model.addAttribute(STATS, ans);
        return STATS;
    }

}
