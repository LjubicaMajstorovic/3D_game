package com.example.demo1;

import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class YellowDiamondBody extends TokenBody{
    YellowDiamondBody(Position position){
        super(position);


    }

    @Override
    protected void createTokenBody() {
        Pyramid top = new Pyramid(position);
        Pyramid bottom = new Pyramid(position);

        top.getTransforms().addAll(new Translate(0, -2*getTokenHeight()/3));
        bottom.getTransforms().addAll(new Rotate(180, Rotate.X_AXIS));

        top.setColors(Color.YELLOW, Color.rgb(255, 255, 150));
        bottom.setColors(Color.YELLOW, Color.rgb(255, 255, 150));


        super.getChildren().addAll(top, bottom);
    }

    @Override
    public double getTokenHeight(){
        return 2*Pyramid.getHeight();
    }

}
