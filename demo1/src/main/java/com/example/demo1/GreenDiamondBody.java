package com.example.demo1;

import javafx.scene.Camera;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.scene.*;
public class GreenDiamondBody extends TokenBody{


    GreenDiamondBody(Position position){
        super(position);


    }

    @Override
    protected void createTokenBody() {
        Pyramid top = new Pyramid(position);
        Pyramid bottom = new Pyramid(position);

        top.getTransforms().addAll(new Translate(0, -2*getTokenHeight()/3));
        bottom.getTransforms().addAll(new Rotate(180, Rotate.X_AXIS));

        top.setColors(Color.GREEN, Color.LIGHTGREEN);
        bottom.setColors(Color.GREEN, Color.LIGHTGREEN);


        super.getChildren().addAll(top, bottom);
    }

    @Override
    public double getTokenHeight(){
        return 2*Pyramid.getHeight();
    }




}
