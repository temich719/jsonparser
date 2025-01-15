package com.example.jsonparser.model;

import lombok.Getter;

@Getter
public class Stats {

    private Integer win;
    private Integer draw;
    private Integer lose;

    public Stats() {
        this.win = 0;
        this.draw = 0;
        this.lose = 0;
    }

    public Stats(final Integer win, final Integer draw, final Integer lose) {
        this.win = win;
        this.draw = draw;
        this.lose = lose;
    }

    @Override
    public String toString() {
        return "{win: " + win + ", draw: " + draw + ", lose: " + lose + "}";
    }

    public Integer getTotal() {
        return this.win + this.draw + this.lose;
    }

    public void addWins(Integer count) {
        this.win += count;
    }

    public void addDraws(Integer count) {
        this.draw += count;
    }

    public void addLoses(Integer count) {
        this.lose += count;
    }

}
