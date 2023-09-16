package com.example.demo1;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Translate;

public class TallHurdle extends ObstacleBody{
    private static final Color DEFAULT_OBSTACLE_COLOR = Color.WHITESMOKE;
    private static final PhongMaterial OBSTACLE_MATERIAL = new PhongMaterial(DEFAULT_OBSTACLE_COLOR);
    //private static final Image textureImage = new Image("C:\\Users\\core I7\\Desktop\\3D_domaci\\3D_game\\demo1\\images\\stripes.jpg");
    private static final PhongMaterial RED_MATERIAL = new PhongMaterial(Color.RED);
    private static final double DEFAULT_OBSTACLE_DIMENSION_X = 65.0;
    private static final double DEFAULT_OBSTACLE_DIMENSION_Y = 3.0;
    private static final double DEFAULT_OBSTACLE_DIMENSION_Z = 3.0;
    private static final double DEFAULT_OLD_X = 28.0;

    private Group body;

    public TallHurdle(Position position){
        super(position);
    }

    @Override
    protected void createObstacleBody(){
        body = new Group();

        Box box1 = new Box(DEFAULT_OBSTACLE_DIMENSION_Y, DEFAULT_OBSTACLE_DIMENSION_X*0.6, DEFAULT_OBSTACLE_DIMENSION_Z);
        Box box2 = new Box(DEFAULT_OLD_X - DEFAULT_OBSTACLE_DIMENSION_Y*2, DEFAULT_OBSTACLE_DIMENSION_Y, DEFAULT_OBSTACLE_DIMENSION_Z);
        Box box3 = new Box(DEFAULT_OBSTACLE_DIMENSION_Y, DEFAULT_OBSTACLE_DIMENSION_X*0.6, DEFAULT_OBSTACLE_DIMENSION_Z);

        box1.getTransforms().addAll(new Translate(-DEFAULT_OLD_X/2 + DEFAULT_OBSTACLE_DIMENSION_Y/2, 0));
        box2.getTransforms().addAll(new Translate(0,  -18.0*DEFAULT_OBSTACLE_DIMENSION_Y/3));
        box3.getTransforms().addAll(new Translate(DEFAULT_OLD_X/2 - DEFAULT_OBSTACLE_DIMENSION_Y/2, 0));



        box1.setMaterial(OBSTACLE_MATERIAL);
        box2.setMaterial(RED_MATERIAL);
        box3.setMaterial(OBSTACLE_MATERIAL);

        body.getChildren().addAll(box2, box1, box3);
        this.getChildren().addAll(body);


    }

    @Override
    public double getObstacleHeight() {
        return DEFAULT_OBSTACLE_DIMENSION_X;
    }
}
