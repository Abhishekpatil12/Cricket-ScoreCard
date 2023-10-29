package com.example.cricketscorecalculator;

public class Cricket {

    int run;
    int ball;
    double runrate;
    double over;

    public int getRun() {
        return run;
    }

    public void setRun(int run) {
        this.run = run;
    }

    public int getBall() {
        return ball;
    }

    public void setBall(int ball) {
        this.ball = ball;
    }

    public double getRunrate() {
        return runrate;
    }

    public void setRunrate(double runrate) {
        this.runrate = runrate;
    }

    public double getOver() {
        return over;
    }

    public void setOver(double over) {
        this.over = over;
    }

    public Cricket(int run, int ball, double runrate, double over) {
        this.run = run;
        this.ball = ball;
        this.runrate = runrate;
        this.over = over;
    }
}
