package com.example.demo1;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;

public class Points extends AnimationTimer {
    private Label label;
    private long startTime;
    private boolean stop = false;
    private boolean yellowEffect = false;

    private long yellowTime = 0;
    private long inYellowTime = 0;
    long bonus = 0;
    long elapsed = 0;

    public Points(Label label){
        this.label = label;
        startTime = System.nanoTime();
    }
    @Override
    public void handle(long now){
        if(stop) return;

        if(yellowEffect) {
            long d = (now - yellowTime) / 1_000_000_000;
            if(d >= 1){
                inYellowTime++;
                yellowTime = now;
                startTime = now;
            }

            elapsed += d*2;
            label.setText("Score: " + elapsed);
            if(inYellowTime == 10){
                yellowEffect = false;
            }
            return;
        }
        long d = (now - startTime)/1_000_000_000;
        elapsed += d;
        if(d >= 1){
            startTime = now;
        }

        label.setText("Score: " + elapsed);


    }

    public void stopCounter(){
        stop = true;
    }

    public void greenDiamondEffect(){
        ++elapsed;
        label.setText("Score: " + elapsed + bonus);
    }

    public void yellowDiamondEffectStart(){
        yellowEffect = true;
        inYellowTime = 0;
        yellowTime = System.nanoTime();

    }


}
