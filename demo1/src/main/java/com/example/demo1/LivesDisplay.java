package com.example.demo1;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.transform.Translate;

public class LivesDisplay extends Group {
    private Group health1;
    private Group health2;
    private Group health3;

    public LivesDisplay(){
        health1 = new Group();
        Line line1 = new Line(0, 0, 0, 10);
        Line line2 = new Line(-5, 5, 5, 5);

        line1.setStroke(Color.GREEN);
        line2.setStroke(Color.GREEN);

        health1.getChildren().addAll(line1, line2);

        health2 = new Group();
        Line line3 = new Line(0, 0, 0, 10);
        Line line4 = new Line(-5, 5, 5, 5);

        line3.setStroke(Color.RED);
        line4.setStroke(Color.RED);

        health2.getChildren().addAll(line3, line4);
        health2.getTransforms().addAll(new Translate(15, 0));

        health3 = new Group();
        Line line5 = new Line(0, 0 , 0, 10);
        Line line6 = new Line (-5, 5, 5, 5);

        line5.setStroke(Color.RED);
        line6.setStroke(Color.RED);

        health3.getChildren().addAll(line5, line6);
        health3.getTransforms().addAll(new Translate(30, 0));

        super.getChildren().addAll(health1, health2, health3);
    }


}
