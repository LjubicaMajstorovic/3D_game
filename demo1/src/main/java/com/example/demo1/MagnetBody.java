package com.example.demo1;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Translate;

public class MagnetBody extends TokenBody{

    private static final double MAGNET_HEIGHT_HALF = 12.0;
    private static final double MAGNET_WIDTH = 5.0;
    private static final double MAGNET_DEPTH = 5.0;
    MagnetBody(Position position) {
        super(position);
        createTokenBody();
    }

    protected void createTokenBody(){
        Box north = new Box(MAGNET_WIDTH, MAGNET_HEIGHT_HALF, MAGNET_DEPTH);
        PhongMaterial phongMaterial1 = new PhongMaterial(Color.BLUE);
        phongMaterial1.setSpecularColor(Color.BLUE);
        north.setMaterial(phongMaterial1);

        Box south = new Box(MAGNET_WIDTH, MAGNET_HEIGHT_HALF, MAGNET_DEPTH);
        PhongMaterial phongMaterial2 = new PhongMaterial(Color.RED);
        phongMaterial2.setSpecularColor(Color.RED);
        south.setMaterial(phongMaterial2);

        south.getTransforms().addAll(new Translate(0,-MAGNET_HEIGHT_HALF));

        super.getChildren().addAll(north, south);



    }

    public double getTokenHeight(){
        return 2*MAGNET_HEIGHT_HALF;
    }
}
