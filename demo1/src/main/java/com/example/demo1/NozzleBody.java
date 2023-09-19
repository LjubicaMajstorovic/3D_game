package com.example.demo1;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Translate;

public class NozzleBody extends TokenBody{
    private static final double NOZZLE_HEIGHT = 24.0;
    private static final double NOZLLE_WIDTH = 5.0;

    NozzleBody(Position position) {
        super(position);
        createTokenBody();
    }

    protected void createTokenBody(){
        Cylinder nozzle = new Cylinder(NOZLLE_WIDTH, NOZZLE_HEIGHT);
        PhongMaterial phongMaterial1 = new PhongMaterial();
        phongMaterial1.setDiffuseColor(Color.CYAN);
        phongMaterial1.setSpecularColor(Color.WHITE);
        nozzle.setMaterial(phongMaterial1);


        super.getChildren().addAll(nozzle);

    }

    public double getTokenHeight(){
        return NOZZLE_HEIGHT;
    }
}
