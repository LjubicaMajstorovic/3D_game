package com.example.demo1;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.DirectionalLight;
import javafx.scene.PointLight;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class Token extends GameObject{

    private static double TOKEN_SPEED = 4.0;
    private static final double ROTATION_SPEED = 3;

    private static final double SPEED_INCREMENT = 0.001;
    private static final double MAX_SPEED = 20;
    private boolean takenByMagnet = false;


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
        if(TOKEN_SPEED < MAX_SPEED){
            TOKEN_SPEED += SPEED_INCREMENT;
        }
        this.setTranslateZ(this.getTranslateZ() - TOKEN_SPEED);
        return isOnTrack();

    }

    public boolean isOnTrack()
    {
        return this.getTranslateZ() > 0;
    }

    public void rotate(){
        double odlAngle = this.rotate.getAngle();
        this.rotate.setAngle(odlAngle +  ROTATION_SPEED);
    }

    public TokenBody getTokenBody(){
        return tokenBody;
    }

    public void draggedByMagnet(double x, double y, double z){
        if(takenByMagnet) return;
        Timeline timeline = new Timeline(
                new KeyFrame(
                        javafx.util.Duration.ZERO,
                        new KeyValue(this.translateZProperty(), this.getTranslateZ(), Interpolator.LINEAR)
                ),
                new KeyFrame(
                        javafx.util.Duration.seconds(1),
                        new KeyValue(this.translateZProperty(), z, Interpolator.LINEAR))

        );

        takenByMagnet = true;

        timeline.play();

    }

}
