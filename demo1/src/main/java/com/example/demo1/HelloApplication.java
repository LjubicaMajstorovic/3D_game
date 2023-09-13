package com.example.demo1;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class HelloApplication extends Application {

    private static final double WINDOW_WIDTH = 720.0;
    private static final double WINDOW_HEIGHT = 400.0;
    private static final Color DEFAULT_BACKGROUND_COLOR = Color.CADETBLUE;
    private static final int DEFAULT_OBSTACLE_TARGET_COUNT = 5;
    private static final double OBSTACLE_SPAWN_DEPTH = 1200.0;
    private static final long DEFAULT_OBSTACLE_CREATION_SPEED = 1500000000l;
    private Stage stage;
    private Scene scene;
    private Group objects;
    private Player player;
    private Track track;


    private int obstacleCount = 0;
    private long lastObstacleCreatedTime = 0;
    private int targetObstacleCount = DEFAULT_OBSTACLE_TARGET_COUNT;
    private long obstacleCreationSpeed = DEFAULT_OBSTACLE_CREATION_SPEED;




    public static boolean isGameActive = true;


    private final UpdateTimer timer = new UpdateTimer();

    private class UpdateTimer extends AnimationTimer
    {
        @Override
        public void handle(long now)
        {
            updateObstacles(now);
        }
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        setupSecne();
        showStage();
    }

    private void setupSecne(){
        scene = new Scene(objects = new Group(), WINDOW_WIDTH, WINDOW_HEIGHT, true, SceneAntialiasing.BALANCED);
        scene.setFill(DEFAULT_BACKGROUND_COLOR);
        scene.setCursor(Cursor.NONE);
        player = Player.InstantiatePlayer();
        scene.setCamera(player.getCamera());

        scene.setOnMouseMoved(player);
        scene.setOnKeyPressed(player);
        scene.setOnKeyReleased(player);

        track = new Track();

        AmbientLight ambientLight = new AmbientLight(Color.WHITE);
        ambientLight.setOpacity(0.2);
        ambientLight.setTranslateZ(-1000);
        ambientLight.setBlendMode(BlendMode.SOFT_LIGHT);

        objects.getChildren().addAll(player, track, ambientLight);
    }

    private void showStage(){
        stage.setTitle("Trka sa preprekama");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();

        timer.start();
    }

    private void updateObstacles(long now){
        List<Node> children = objects.getChildren();

        for(int i = 0; i < objects.getChildren().size(); i++){
            Node child = children.get(i);
            if(child instanceof Obstacle){
                if(child.getBoundsInParent().intersects((player.localToScene(player.getParentBounds())))){
                    isGameActive = false;
                    return;
                }

                if (obstacleCount > 0 && !((Obstacle)child).move())
                {
                    obstacleCount--;
                    objects.getChildren().remove(child);
                }
            }
        }

        if (obstacleCount < targetObstacleCount && now > lastObstacleCreatedTime + obstacleCreationSpeed)
        {
            lastObstacleCreatedTime = now;
            objects.getChildren().add(new Obstacle(new Position(track.getRandomX(), track.getY(), OBSTACLE_SPAWN_DEPTH)));
            obstacleCount++;
        }

    }
    public static void main(String[] args) {
        launch();
    }
}