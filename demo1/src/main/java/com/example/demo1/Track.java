package com.example.demo1;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

import java.io.File;
import java.util.Random;

public class Track extends Group {
    public static final double LANE_HEIGHT = 0.5;
    public static final double LANE_WIDTH = 30.0;
    public static final double LANE_LENGTH = 10000.0;

    public static final double DEFAULT_TRANSLATE_Y = 30.0;
    public static final double DEFAULT_DISTANCE_BETWEEN_LANES = 2.0;

    private static final Color DEFAULT_TRACK_COLOR = Color.CORAL;

    static File file = new File("images/tartan.jpg");

    // Pretvaranje File objekta u apsolutnu putanju
    static String absolutePath = file.getAbsolutePath();
    private final Image imageTrack = new Image(getClass().getResource("images/tartan.jpg").toString());
    private static final PhongMaterial TRACK_MATERIAL = new PhongMaterial(DEFAULT_TRACK_COLOR);

    private static final Color DEFAULT_LINE_COLOR = Color.WHITE;
    private static final PhongMaterial LINE_MATERIAL = new PhongMaterial(DEFAULT_LINE_COLOR);

    static final Color DEFAULT_GRASS_COLOR = Color.GREEN;

    static File file1 = new File("grass.jpg");
    static String absolutePath1 = file1.getAbsolutePath();
    private final Image imageGrass = new Image(getClass().getResource("images/grass.jpg").toString());
    private static final PhongMaterial GRASS_MATERIAL = new PhongMaterial(DEFAULT_GRASS_COLOR);

    private Box lanes[];


    public Track(){
        lanes = new Box[3];
        for(int i = 0; i < lanes.length; i++){
            lanes[i] = CreateLane(i);
        }

        Box lines = new Box(LANE_WIDTH * 2, LANE_HEIGHT, LANE_LENGTH);
        lines.setMaterial(LINE_MATERIAL);
        lines.setTranslateX(lanes[1].getTranslateX());
        lines.setTranslateY(DEFAULT_TRANSLATE_Y + 1);

        Box grass = new Box(LANE_WIDTH * 8, LANE_HEIGHT, LANE_LENGTH);


        GRASS_MATERIAL.setDiffuseColor(DEFAULT_GRASS_COLOR);
        GRASS_MATERIAL.setDiffuseMap(imageGrass);

        grass.setMaterial(GRASS_MATERIAL);
        grass.setTranslateX(lanes[1].getTranslateX());
        grass.setTranslateY(DEFAULT_TRANSLATE_Y + 2);


        this.getChildren().addAll(lanes);
        this.getChildren().addAll(lines, grass);
    }

    public Box CreateLane(int i){
        Box lane = new Box(LANE_WIDTH, LANE_HEIGHT, LANE_LENGTH);
        /*ImageView imageView2 = new ImageView(imageTrack);
        imageView2.setSmooth(true);
        imageView2.setCache(false);*/
        TRACK_MATERIAL.setDiffuseColor(DEFAULT_TRACK_COLOR);
        TRACK_MATERIAL.setDiffuseMap(imageTrack);
        lane.setMaterial(TRACK_MATERIAL);
        lane.setTranslateY(DEFAULT_TRANSLATE_Y);
        lane.setTranslateX((-1 + i) * (LANE_WIDTH + DEFAULT_DISTANCE_BETWEEN_LANES));
        return lane;
    }


    public double getY()
    {
        return lanes[0].getTranslateY();
    }

    public double getRandomX()
    {
        return lanes[new Random().nextInt(3)].getTranslateX();
    }

}
