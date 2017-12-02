package com.larsen.magnus.guessthenumber;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by Magnus on 04.04.2016.
 */

public class Level implements Serializable {

    private String levelName;
    private long timePressure;
    private int numberOfGuesses;
    private int highestNum;

    public Level(String levelName, long timePressure, int numberOfGuesses, int highestNum) {
        this.levelName = levelName;
        this.timePressure = timePressure;
        this.numberOfGuesses = numberOfGuesses;
        this.highestNum = highestNum;
    }

    public int getHighestNum() {
        return highestNum;
    }

    public void setHighestNum(int highestNum) {
        this.highestNum = highestNum;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public long getTimePressure() {
        return timePressure;
    }

    public void setTimePressure(int timePressure) {
        this.timePressure = timePressure;
    }

    public int getNumberOfGuesses() {
        return numberOfGuesses;
    }

    public void setNumberOfGuesses(int numberOfGuesses) {
        this.numberOfGuesses = numberOfGuesses;
    }
}
