package com.example.demo1;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Camera;
import javafx.scene.PerspectiveCamera;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Box;
import javafx.util.Duration;

public class Player extends GameObject implements EventHandler<Event> {
    private static final double DEFAULT_POSTION_X = 0;
    private static final double DEFAULT_POSITION_Y = 0;
    private static final double DEFAULT_POSITION_Z = 0;

    public static final double NEAR_CLIP = 0.1;
    public static final double FAR_CLIP = 10_000;
    public static final double FIELD_OF_VIEW = 60;

    private PerspectiveCamera camera;
    private Box shape;

    private int lane = 1;

    private boolean jumping = false;

    public Player(Position position){
        super(position);

        shape = new Box(30.0 , 30.0, 30.0);
        shape.setVisible(false);

        camera = new PerspectiveCamera(true);
        camera.setNearClip(NEAR_CLIP);
        camera.setFarClip(FAR_CLIP);
        camera.setFieldOfView(FIELD_OF_VIEW);

        this.setTranslateY(position.getY());

        this.getChildren().addAll(shape, camera);

    }

    public static Player InstantiatePlayer(){
        return new Player(new Position(DEFAULT_POSTION_X, DEFAULT_POSITION_Y, DEFAULT_POSITION_Z));
    }

    @Override
    public void handle(Event evnet){
        if(evnet instanceof KeyEvent){
            KeyEvent keyEvent = (KeyEvent) evnet;
            if(keyEvent.getCode() == KeyCode.ESCAPE && keyEvent.getEventType() == KeyEvent.KEY_PRESSED){
                System.exit(0);
            } else {
                if(!HelloApplication.isGameActive){
                    return;
                }
                if((keyEvent.getCode() == KeyCode.A || keyEvent.getCode() == KeyCode.LEFT) && keyEvent.getEventType() == KeyEvent.KEY_PRESSED){
                    moveLeft();
                }
                else if((keyEvent.getCode() == KeyCode.D || keyEvent.getCode() == KeyCode.RIGHT) && keyEvent.getEventType() == KeyEvent.KEY_PRESSED){
                    moveRight();
                }
                else if((keyEvent.getCode() == KeyCode.W || keyEvent.getCode() == KeyCode.UP) && keyEvent.getEventType() == KeyEvent.KEY_PRESSED){
                    jump();
                }
            }
        }
    }

    public Bounds getParentBounds(){
        return shape.getBoundsInParent();
    }

    public Camera getCamera(){
        return camera;
    }

    private void moveLeft(){
        if(lane == 0){
            return;
        }
        lane--;
        this.setTranslateX(this.getTranslateX() - Track.LANE_WIDTH);
    }

    private void moveRight(){
        if(lane == 2){
            return;
        }
        lane++;
        this.setTranslateX(this.getTranslateX() + Track.LANE_WIDTH);
    }

    private void jump(){
        if(jumping){
            return;
        }
        jumping = true;
        double oldY = this.getTranslateY();
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new KeyValue(this.translateYProperty(), this.getTranslateY(), Interpolator.LINEAR)
                ),
                new KeyFrame(
                        Duration.seconds(0.5),
                        new KeyValue(this.translateYProperty(), this.getTranslateY() - 1.5*shape.getHeight(), Interpolator.LINEAR)
                ),

                new KeyFrame(
                        Duration.seconds(1),
                        new KeyValue(this.translateYProperty(), oldY, Interpolator.LINEAR)
                )
        );

        timeline.setOnFinished(event -> {
            jumping = false;
        });

        timeline.play();
    }
}
