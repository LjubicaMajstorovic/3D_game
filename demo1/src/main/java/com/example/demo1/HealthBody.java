package com.example.demo1;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.PointLight;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class HealthBody extends TokenBody{

    private static final double HEIGHT = 28.0;
    private static final double WIDTH = 18.0;
    private static final double DEPTH = 5.0;
    HealthBody(Position position){
        super(position);

    }

    @Override
    protected void createTokenBody() {
        Box horizontal = new Box(DEPTH, HEIGHT, DEPTH);
        Box vertical = new Box(WIDTH, DEPTH, DEPTH);


        vertical.getTransforms().addAll(new Translate(0, -DEPTH));

        PhongMaterial phongMaterial = new PhongMaterial(Color.RED);
        vertical.setMaterial(phongMaterial);
        horizontal.setMaterial(phongMaterial);


        PointLight pointLight = new PointLight(Color.RED);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.5), event->{
                    super.getChildren().addAll(pointLight);
                }),
                new KeyFrame(Duration.seconds(1), event->{
                    super.getChildren().remove(pointLight);
                })
        );

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();


        super.getChildren().addAll(horizontal, vertical);
    }

    @Override
    public double getTokenHeight(){
        return 2*Pyramid.getHeight();
    }

}
