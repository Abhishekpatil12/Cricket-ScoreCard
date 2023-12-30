package com.example.cricketscorecalculator;

import java.io.Serializable;

public class Cricket implements Serializable {

    int score;
    int out;
    String over;
    String action;
    double perover;

    public Cricket(int score, int out, String over, String action, double perover) {
        this.score = score;
        this.out = out;
        this.over = over;
        this.action = action;
        this.perover = perover;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getOut() {
        return out;
    }

    public void setOut(int out) {
        this.out = out;
    }

    public String getOver() {
        return over;
    }

    public void setOver(String over) {
        this.over = over;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public double getPerover() {
        return perover;
    }

    public void setPerover(double perover) {
        this.perover = perover;
    }
}
