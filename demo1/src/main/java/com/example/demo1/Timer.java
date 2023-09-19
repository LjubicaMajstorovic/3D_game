package com.example.demo1;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class Timer extends AnimationTimer{
    private Label label;
    private long startTime;
    private boolean stop = false;

    private boolean magnetTime = false;
    private long magnetTimeElapsed = 0;
    private long magnetStartTime = 0;

    private boolean nozzleTime = false;
    private long nozzleTimeElapsed = 0;
    private long nozzleStartTime = 0;

    public Timer(Label label){
        this.label = label;
        startTime = System.nanoTime();
    }
    @Override
    public void handle(long now){
        if(stop) return;
        long elapsed = (now - startTime)/1_000_000_000;
        long seconds = elapsed%60;
        long minutes = elapsed/60;
        String min;
        String sec;
        if(minutes < 10){
            min = "0" + minutes;
        } else {
            min = "" + minutes;
        }
        if(seconds < 10){
            sec = "0" +seconds;
        } else{
            sec = "" +seconds;
        }

        label.setText("" + min + ":" + sec);
        if(magnetTime){
            magnetTimeElapsed = (now - magnetStartTime)/1_000_000_000;
            if(magnetTimeElapsed >= 10){
                magnetTime = false;
                label.setTextFill(Color.BLACK);
            }
        } else if(nozzleTime){
            nozzleTimeElapsed = (now - nozzleStartTime)/1_000_000_000;
            if(nozzleTimeElapsed >= 10){
                nozzleTime = false;
                label.setTextFill(Color.BLACK);
            }
        }
    }

    public void stopTimer(){
        stop = true;
    }

    public void startMagnetCount(){
        magnetTime = true;
        magnetStartTime = System.nanoTime();
        label.setTextFill(Color.RED);
    }

    public void startNozzleCount(){
        nozzleTime = true;
        nozzleStartTime = System.nanoTime();
        label.setTextFill(Color.PURPLE);
    }


    public boolean isMagnetTimeOn(){
        return magnetTime;
    }

    public boolean isNozzleTimeOn(){
        return nozzleTime;
    }
}
