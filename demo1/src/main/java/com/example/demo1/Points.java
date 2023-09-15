package com.example.demo1;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;

public class Points extends AnimationTimer {
    private Label label;
    private long startTime;
    private boolean stop = false;
    long bonus = 0;
    long elapsed = 0;

    public Points(Label label){
        this.label = label;
        startTime = System.nanoTime();
    }
    @Override
    public void handle(long now){
        if(stop) return;
        elapsed = (now - startTime)/1_000_000_000 + bonus;
        label.setText("Score: " + elapsed);
    }

    public void stopCounter(){
        stop = true;
    }

    public void greenDiamondEffect(){
        ++bonus;
        label.setText("Score: " + elapsed + bonus);
    }
}
