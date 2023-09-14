package com.example.demo1;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;

public class Hurdle extends ObstacleBody{
    private static final Color DEFAULT_OBSTACLE_COLOR = Color.WHITESMOKE;
    private static final PhongMaterial OBSTACLE_MATERIAL = new PhongMaterial(DEFAULT_OBSTACLE_COLOR);
    private static final Image textureImage = new Image("C:\\Users\\core I7\\Desktop\\3D_domaci\\3D_game\\demo1\\images\\stripes.jpg");
    private static final PhongMaterial STRIPED_MATERIAL = new PhongMaterial();
    private static final double DEFAULT_OBSTACLE_DIMENSION_X = 28.0;
    private static final double DEFAULT_OBSTACLE_DIMENSION_Y = 3.0;
    private static final double DEFAULT_OBSTACLE_DIMENSION_Z = 3.0;

    private Group body;

    public Hurdle(Position position){
        super(position);
    }

    @Override
    protected void createObstacleBody(){
        body = new Group();

        Box box1 = new Box(DEFAULT_OBSTACLE_DIMENSION_Y, DEFAULT_OBSTACLE_DIMENSION_X*0.6, DEFAULT_OBSTACLE_DIMENSION_Z);
        Box box2 = new Box(DEFAULT_OBSTACLE_DIMENSION_X - DEFAULT_OBSTACLE_DIMENSION_Y*2, DEFAULT_OBSTACLE_DIMENSION_Y, DEFAULT_OBSTACLE_DIMENSION_Z);
        Box box3 = new Box(DEFAULT_OBSTACLE_DIMENSION_Y, DEFAULT_OBSTACLE_DIMENSION_X*0.6, DEFAULT_OBSTACLE_DIMENSION_Z);

        box1.getTransforms().addAll(new Translate(-DEFAULT_OBSTACLE_DIMENSION_X/2 + DEFAULT_OBSTACLE_DIMENSION_Y/2, 0));
        box2.getTransforms().addAll(new Translate(0,  -6.9*DEFAULT_OBSTACLE_DIMENSION_Y/3));
        box3.getTransforms().addAll(new Translate(DEFAULT_OBSTACLE_DIMENSION_X/2 - DEFAULT_OBSTACLE_DIMENSION_Y/2, 0));

        STRIPED_MATERIAL.setDiffuseMap(textureImage);

        box1.setMaterial(OBSTACLE_MATERIAL);
        box2.setMaterial(STRIPED_MATERIAL);
        box3.setMaterial(OBSTACLE_MATERIAL);

        body.getChildren().addAll(box2, box1, box3);
        this.getChildren().addAll(body);


    }

    @Override
    public double getObstacleHeight() {
        return DEFAULT_OBSTACLE_DIMENSION_X;
    }
}
