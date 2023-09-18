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
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class Player extends GameObject implements EventHandler<Event> {
    private static final double DEFAULT_POSITION_XTION_X = 0;
    private static final double DEFAULT_POSITION_Y = 0;
    private static final double DEFAULT_POSITION_Z = 0;

    public static final double NEAR_CLIP = 0.1;
    public static final double FAR_CLIP = 10_000;
    public static final double FIELD_OF_VIEW = 60;
    private static final double CAMERA_ROTATION_DIFF = 1.0;
    private static final double CAMERA_ROTATION_BOUND = 15.0;
    private static final double MAX_LIVES = 3;
    private static final double RADIUS = 30;

    private PerspectiveCamera camera;
    private Box shape;
    private Sphere sphere;
    private int lane = 1;
    private double current_camera_rotate = 0;

    private boolean jumping = false;
    private int lives = 1;
    private int jumps = 0;
    private double y;

    private Timeline timeline;

    public Player(Position position){
        super(position);

        shape = new Box(30.0 , 30.0, 30.0);
        shape.setVisible(false);

        sphere = new Sphere(30);
        sphere.setVisible(false);

        camera = new PerspectiveCamera(true);
        camera.setNearClip(NEAR_CLIP);
        camera.setFarClip(FAR_CLIP);
        camera.setFieldOfView(FIELD_OF_VIEW);

        this.setTranslateY(position.getY());

        y = this.getTranslateY();

        this.getChildren().addAll(shape, camera, sphere);

    }

    public static Player InstantiatePlayer(){
        return new Player(new Position(DEFAULT_POSITION_XTION_X, DEFAULT_POSITION_Y, DEFAULT_POSITION_Z));
    }

    @Override
    public void handle(Event event){
        if(event instanceof KeyEvent){
            KeyEvent keyEvent = (KeyEvent) event;
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
                else if(keyEvent.getCode() == KeyCode.L && keyEvent.getEventType() == KeyEvent.KEY_PRESSED){
                    HelloApplication.toggleLight();
                }
                else if(keyEvent.getCode() == KeyCode.R && keyEvent.getEventType() == KeyEvent.KEY_PRESSED){
                    rotateCamera(-CAMERA_ROTATION_DIFF);
                }
                else if(keyEvent.getCode() == KeyCode.T && keyEvent.getEventType() == KeyEvent.KEY_PRESSED){
                    rotateCamera(CAMERA_ROTATION_DIFF);
                }
                else if((keyEvent.getCode() == KeyCode.S || keyEvent.getCode() == KeyCode.DOWN) && keyEvent.getEventType() == KeyEvent.KEY_PRESSED){
                    forceLand();
                }
            }
        }
    }

    public Bounds getParentBounds(){
        return shape.getBoundsInParent();
    }

    public Bounds getSphereBounds() { return sphere.getBoundsInParent(); }

    public double getCenterX() { return sphere.localToParent(sphere.getTranslateX(),0).getX(); }

    public double getCenterY() { return sphere.localToParent(0, sphere.getTranslateY()).getY(); }

    public double getCenterZ() { return sphere.localToParent(0, 0, sphere.getTranslateZ()).getZ(); }

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
        jumps++;
        if(jumps == 2) jumping = true;
        timeline = new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new KeyValue(this.translateYProperty(), this.getTranslateY(), Interpolator.LINEAR)
                ),
                new KeyFrame(
                        Duration.seconds(0.5),
                        new KeyValue(this.translateYProperty(), this.getTranslateY() - 1.2*shape.getHeight(), Interpolator.LINEAR)
                ),

                new KeyFrame(
                        Duration.seconds(1),
                        new KeyValue(this.translateYProperty(), y, Interpolator.LINEAR)
                )
        );

        timeline.setOnFinished(event -> {
           jumps--;
           if(jumps == 0){
               jumping = false;
           }
        });

        timeline.play();
    }

    private void rotateCamera(double d){
        current_camera_rotate += d;
        if(current_camera_rotate >= CAMERA_ROTATION_BOUND || current_camera_rotate <= -CAMERA_ROTATION_BOUND){
            return;
        }
        camera.getTransforms().addAll(new Rotate(d, Rotate.X_AXIS));
    }

    public int getLives(){
        return lives;
    }

    public void decrementLives(){
        lives -=1;
    }
    public void incrementLives() {
        if(lives == MAX_LIVES) return;
        lives += 1;
    }

    private void forceLand(){
        jumping = false;
        jumps =  0;
        timeline.stop();
        this.setTranslateY(y);
    }
}
