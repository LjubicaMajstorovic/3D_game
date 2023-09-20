package com.example.demo1;

import javafx.animation.*;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.util.Random;

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
    private static final double RADIUS = 64;

    private PerspectiveCamera defaultCamera;
    private PerspectiveCamera alternativeCamera;
    private PerspectiveCamera camera;
    private Group shape;
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

        PhongMaterial phongMaterial = new PhongMaterial(Color.LIGHTGRAY);
        phongMaterial.setSpecularColor(Color.WHITE);

        Box box = new Box(30.0 , 30.0, 30.0);
        box.getTransforms().addAll(new Translate(0, 10));
        box.setMaterial(new PhongMaterial(Color.TRANSPARENT));
        Sphere head = new Sphere(3);
        head.setMaterial(phongMaterial);
        Box body = new Box(10, 7, 10);
        body.setMaterial(phongMaterial);
        Box neck = new Box(2, 3, 1);
        neck.setMaterial(phongMaterial);

        Box leftArm = new Box(2, 7, 2);
        leftArm.setMaterial(phongMaterial);
        Box rightArm = new Box(2, 7, 2);
        rightArm.setMaterial(phongMaterial);

        Sphere leftHand = new Sphere(1);
        leftHand.setMaterial(phongMaterial);
        Sphere rightHand = new Sphere(1);
        rightHand.setMaterial(phongMaterial);

        Box leftLeg = new Box(2.5, 10, 2.5);
        leftLeg.setMaterial(phongMaterial);
        Box rightLeg = new Box(2.5, 10, 2.5);
        rightLeg.setMaterial(phongMaterial);

        Sphere leftFoot = new Sphere(1.5);
        leftFoot.setMaterial(phongMaterial);
        Sphere rightFoot = new Sphere(1.5);
        rightFoot.setMaterial(phongMaterial);

        head.getTransforms().addAll(new Translate(0, -14));

        body.getTransforms().addAll(new Translate(0, -5));

        neck.getTransforms().addAll(new Translate(0, -10));

        leftArm.getTransforms().addAll(new Translate(-6, -5), new Rotate(30, Rotate.Z_AXIS));
        rightArm.getTransforms().addAll(new Translate(6, -5), new Rotate(-30, Rotate.Z_AXIS));

        leftHand.getTransforms().addAll(new Translate(-8, -2));
        rightHand.getTransforms().addAll(new Translate(8, -2));

        leftLeg.getTransforms().addAll(new Translate(-3,0));
        rightLeg.getTransforms().addAll(new Translate(3,0));

        leftFoot.getTransforms().addAll(new Translate(-3, 5.5));
        rightFoot.getTransforms().addAll(new Translate(3, 5.5));

        Group legLeftGroup = new Group(leftLeg, leftFoot);
        Group legRightGroup = new Group(rightLeg, rightFoot);

        Rotate left;
        Rotate right;
        legLeftGroup.getTransforms().addAll(left = new Rotate(0, Rotate.X_AXIS));
        legRightGroup.getTransforms().addAll(right = new Rotate(0, Rotate.X_AXIS));

        Timeline walking = new Timeline(new KeyFrame(
                Duration.ZERO,
                new KeyValue(left.angleProperty(), legLeftGroup.getRotate()),
                new KeyValue(right.angleProperty(), -60)
        ),
                new KeyFrame(
                        Duration.seconds(1),
                        new KeyValue(left.angleProperty(), -60),
                        new KeyValue(right.angleProperty(), 0)
                )
        );

        walking.setAutoReverse(true);
        walking.setCycleCount(Animation.INDEFINITE);
        walking.play();



        //shape.setMaterial(phongMaterial);

        shape = new Group();

        shape.getChildren().addAll(leftArm, rightArm, body, legLeftGroup, legRightGroup, head, neck, leftHand, rightHand, box);
        shape.setVisible(false);


        sphere = new Sphere(RADIUS);
        sphere.setVisible(false);

        defaultCamera = new PerspectiveCamera(true);
        defaultCamera.setNearClip(NEAR_CLIP);
        defaultCamera.setFarClip(FAR_CLIP);
        defaultCamera.setFieldOfView(FIELD_OF_VIEW);



        alternativeCamera = new PerspectiveCamera(true);
        alternativeCamera.setNearClip(NEAR_CLIP);
        alternativeCamera.setFarClip(FAR_CLIP);
        alternativeCamera.setFieldOfView(FIELD_OF_VIEW);

        alternativeCamera.setTranslateY(-30);
        alternativeCamera.setTranslateZ(-70);

        camera = defaultCamera;

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
                    if(timeline != null)forceLand();
                }
                else if(keyEvent.getCode() == KeyCode.DIGIT2 && keyEvent.getEventType() == KeyEvent.KEY_PRESSED){
                    shape.setVisible(true);
                    camera = alternativeCamera;
                    HelloApplication.setCamera(alternativeCamera);
                } else if(keyEvent.getCode() == KeyCode.DIGIT1 && keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
                    shape.setVisible(false);
                    camera = defaultCamera;
                    HelloApplication.setCamera(defaultCamera);
                }
            }
        }
    }

    public double getY(){
        return y;
    }

    public Bounds getParentBounds(){
        return shape.getBoundsInParent();
    }

    public Bounds getSphereBounds() { return sphere.getBoundsInParent(); }

    public double getCenterX() { return shape.localToParent(shape.getTranslateX(),0).getX(); }

    public double getCenterY() { return shape.localToParent(0, shape.getTranslateY()).getY(); }

    public double getCenterZ() { return shape.localToParent(0, 0, shape.getTranslateZ()).getZ(); }

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
                        new KeyValue(this.translateYProperty(), this.getTranslateY() - 1.2*40, Interpolator.LINEAR)
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
