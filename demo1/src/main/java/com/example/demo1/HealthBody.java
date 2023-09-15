package com.example.demo1;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

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


        super.getChildren().addAll(horizontal, vertical);
    }

    @Override
    public double getTokenHeight(){
        return 2*Pyramid.getHeight();
    }

}
