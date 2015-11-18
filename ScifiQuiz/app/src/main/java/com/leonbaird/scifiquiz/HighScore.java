package com.leonbaird.scifiquiz;

public class HighScore {

    public String playerName;
    public int score;

    public HighScore(String name, int score) {
        playerName = name;
        this.score = score;
    }

    @Override
    public String toString() {
        return playerName+" - "+score;
    }
}
