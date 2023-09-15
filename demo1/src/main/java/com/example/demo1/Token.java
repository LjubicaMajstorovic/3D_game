package com.example.demo1;

import javafx.geometry.Pos;
import javafx.scene.DirectionalLight;
import javafx.scene.PointLight;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class Token extends GameObject{

    private static final double TOKEN_SPEED = 4.0;
    private static final double ROTATION_SPEED = 3;


    private TokenBody tokenBody;

    private Rotate rotate;
    private DirectionalLight directionalLight;

    Token(Position position, TokenBody tokenBody){
        super(position);
        this.tokenBody = tokenBody;

        this.setTranslateX(position.getX());
        this.setTranslateY(position.getY() - tokenBody.getTokenHeight() / 2);
        this.setTranslateZ(position.getZ());

        tokenBody.getTransforms().addAll(rotate = new Rotate(0.0, Rotate.Y_AXIS));
        PointLight pointLight = new PointLight(Color.WHITE);

        directionalLight = new DirectionalLight();
        directionalLight.setColor(Color.WHITE);
        directionalLight.getTransforms().addAll(new Translate(500, 100));
        directionalLight.getScope().addAll(tokenBody);

        this.getChildren().addAll(tokenBody, directionalLight);

    }

    public boolean move()
    {
        this.setTranslateZ(this.getTranslateZ() - TOKEN_SPEED);
        return isOnTrack();

    }

    public boolean isOnTrack()
    {
        return this.getTranslateZ() > 0;
    }

    public void rotate(double ds){
        double odlAngle = this.rotate.getAngle();
        this.rotate.setAngle(odlAngle +  ROTATION_SPEED);
    }

    public TokenBody getTokenBody(){
        return tokenBody;
    }

}
