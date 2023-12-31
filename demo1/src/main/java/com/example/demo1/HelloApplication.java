package com.example.demo1;

import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.util.Random;

public class HelloApplication extends Application {

    private static final double WINDOW_WIDTH = 720.0;
    private static final double WINDOW_HEIGHT = 400.0;
    private static final Color DEFAULT_BACKGROUND_COLOR = Color.CADETBLUE;
    private static final int DEFAULT_OBSTACLE_TARGET_COUNT = 5;
    private static final double OBSTACLE_SPAWN_DEPTH = 1200.0;
    private static final long DEFAULT_OBSTACLE_CREATION_SPEED = 1500000000l;
    private static final double DEFAULT_OBSTACLE_CREATION_INCREMENT = 1000000;
    private static final double MAX_SPEED_CREATION = 500000000l;
    private Stage stage;
    private static SubScene scene3D;
    private Scene mainScene;
    private Group objects;
    private Player player;
    private Track track;
    private Timer clock;
    private Points pointCounter;
    private static PointLight pointLight;
    private LivesDisplay livesDisplay;
    private Group root;

    private int obstacleCount = 0;
    private long lastObstacleCreatedTime = 0;
    private int targetObstacleCount = DEFAULT_OBSTACLE_TARGET_COUNT;
    private double obstacleCreationSpeed = DEFAULT_OBSTACLE_CREATION_SPEED;
    private int tokenCount = 0;
    private double yOriginal = 0;




    public static boolean isGameActive = true;
    private static boolean isLightOn = false;
    private boolean magnetOn = false;
    private boolean nozzleOn = false;
    private boolean heightSetting = false;


    private final UpdateTimer timer = new UpdateTimer();

    private class UpdateTimer extends AnimationTimer
    {
        @Override
        public void handle(long now)
        {
            updateObstacles(now);
        }
    }

    private UpdateMagnet updateMagnet = new UpdateMagnet();

    private class UpdateMagnet extends AnimationTimer
    {
        @Override
        public void handle(long now)
        {
            magnetAttraction();
        }
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        setupScene();
        showStage();
    }

    private void setupScene(){
        mainScene = new Scene(root = new Group(), WINDOW_WIDTH, WINDOW_HEIGHT, false);
        scene3D = new SubScene( objects = new Group(), WINDOW_WIDTH, WINDOW_HEIGHT, true, SceneAntialiasing.BALANCED);
        root.getChildren().addAll(scene3D);
        scene3D.setFill(DEFAULT_BACKGROUND_COLOR);
        mainScene.setCursor(Cursor.NONE);
        player = Player.InstantiatePlayer();
        yOriginal = player.getY();
        scene3D.setCamera(player.getCamera());

        mainScene.setOnMouseMoved(player);
        mainScene.setOnKeyPressed(player);
        mainScene.setOnKeyReleased(player);

        track = new Track();

        AmbientLight ambientLight = new AmbientLight(Color.WHITE);
        ambientLight.setOpacity(0.2);
        ambientLight.setTranslateZ(-1000);
        ambientLight.setBlendMode(BlendMode.SOFT_LIGHT);

        pointLight = new PointLight();
        pointLight.setColor(Color.TRANSPARENT);
        pointLight.getTransforms().addAll(new Translate(-250,-100, -10000));


        objects.getChildren().addAll(player, track, ambientLight, pointLight);


        Label labelTimer = new Label("00:00");
        labelTimer.setStyle("-fx-text-fill: black; -fx-font-size: 20px; -fx-font-weight: bold;");
        labelTimer.getTransforms().addAll(new Translate(WINDOW_WIDTH*0.9, 5));


        clock = new Timer(labelTimer);
        clock.start();


        Label labelPoints = new Label("Score: 0");
        labelPoints.setStyle("-fx-text-fill: black; -fx-font-size: 20px; -fx-font-weight: bold;");
        labelPoints.getTransforms().addAll(new Translate(10, 5));

        livesDisplay = new LivesDisplay();

        livesDisplay.getTransforms().addAll(new Translate(20, 35));

        pointCounter = new Points(labelPoints);
        pointCounter.start();

        updateMagnet.stop();

        root.getChildren().addAll(labelPoints, labelTimer, livesDisplay);


    }

    private void showStage(){
        stage.setTitle("Trka sa preprekama");
        stage.setScene(mainScene);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();

        timer.start();
    }

    private void updateObstacles(long now){

        if(!isGameActive) return;
        List<Node> children = objects.getChildren();

        if(obstacleCreationSpeed > MAX_SPEED_CREATION){
            obstacleCreationSpeed -= DEFAULT_OBSTACLE_CREATION_INCREMENT;
        }

        for(int i = 0; i < objects.getChildren().size(); i++){
            Node child = children.get(i);
            if(child instanceof Token){
                Token token = (Token) child;
                if(child.getBoundsInParent().intersects((player.localToScene(player.getParentBounds())))){
                    if(token.getTokenBody() instanceof HealthBody){
                       player.incrementLives();
                       livesDisplay.collectedLife();
                    }
                    else if(token.getTokenBody() instanceof GreenDiamondBody){
                        pointCounter.greenDiamondEffect();
                    } else if(token.getTokenBody() instanceof YellowDiamondBody){
                        pointCounter.yellowDiamondEffectStart();
                    } else if(token.getTokenBody() instanceof MagnetBody){
                        clock.startMagnetCount();
                        updateMagnet.start();
                        magnetOn = true;
                    } else if(token.getTokenBody() instanceof NozzleBody){
                        clock.startNozzleCount();
                        if(!nozzleOn) {
                            nozzleOn = true;
                            elevatePlayerAndTokens();
                        }
                    }
                    objects.getChildren().remove(child);
                    tokenCount--;
                }

                if(magnetOn){
                    if(!clock.isMagnetTimeOn()){
                        magnetOn = false;
                        updateMagnet.stop();

                    }
                } else if (nozzleOn){
                    if(!clock.isNozzleTimeOn()){
                        nozzleOn = false;
                        descendPlayersAndTokens();
                    }
                }

                if (tokenCount > 0 && !((Token)child).move())
                {
                    tokenCount--;
                    objects.getChildren().remove(child);
                } else{
                    token.rotate();
                }
            }
            else if(child instanceof Obstacle){
                Obstacle obstacle = (Obstacle) child;
                if(child.getBoundsInParent().intersects((player.localToScene(player.getParentBounds()))) && !obstacle.isHit() && !heightSetting){
                    obstacle.hit();
                    player.decrementLives();
                    livesDisplay.takeLife();
                    if(player.getLives() == 0){
                        pointLight.setColor(Color.RED);
                        isGameActive = false;
                        clock.stopTimer();
                        pointCounter.stopCounter();
                        return;
                    }

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
            Position positionObstacle = new Position(track.getRandomX(), track.getY(), OBSTACLE_SPAWN_DEPTH);
            objects.getChildren().add(new Obstacle(positionObstacle));
            obstacleCount++;
            Position positionToken = new Position(track.getRandomX(), track.getY(), OBSTACLE_SPAWN_DEPTH);
            while(positionToken.getX() == positionObstacle.getX()){
                positionToken = new Position(track.getRandomX(), track.getY(), OBSTACLE_SPAWN_DEPTH);
            }

            tokenCount++;
            Random random = new Random();
            double probability = random.nextDouble();
            Token token;
            if(probability < 0.5){
                objects.getChildren().add(token = new Token( positionToken, new GreenDiamondBody(positionToken)));
            } else if(probability < 0.6){
                objects.getChildren().add(token = new Token( positionToken, new YellowDiamondBody(positionToken)));
            } else if (probability < 0.8){
                objects.getChildren().add(token = new Token( positionToken, new HealthBody(positionToken)));
            } else if(probability < 0.9) {
                if(magnetOn || nozzleOn){
                    objects.getChildren().add(token = new Token( positionToken, new GreenDiamondBody(positionToken)));
                } else {
                    objects.getChildren().add(token = new Token(positionToken, new MagnetBody(positionToken)));
                }
            } else {
                if(magnetOn || nozzleOn){
                    objects.getChildren().add(token = new Token( positionToken, new YellowDiamondBody(positionToken)));
                } else {
                    objects.getChildren().add(token = new Token(positionToken, new NozzleBody(positionToken)));
                }

            }

            if(nozzleOn){
                elevateToken(token);
            }

        }

    }

    private void magnetAttraction(){
        for(int i = 0; i < objects.getChildren().size(); i++){
            Node child = objects.getChildren().get(i);
            if(child instanceof Token){
                Token token = (Token) child;
                if((token.getTokenBody() instanceof GreenDiamondBody || token.getTokenBody() instanceof YellowDiamondBody) && child.intersects(player.getSphereBounds())){
                    double magnetX =player.localToParent(player.getTranslateX(), 0).getX()/2;
                    double particleX = child.getTranslateX();
                    double deltaX = magnetX - particleX;



                    double attractionFactor = 0.5;


                    child.setTranslateX(particleX + deltaX * attractionFactor);
                }
            }

        }
    }

    public static void toggleLight(){
        if(isLightOn){
            pointLight.setColor(Color.TRANSPARENT);
        } else {
            pointLight.setColor(Color.WHITE);
        }
        isLightOn = !isLightOn;
    }


    private void elevatePlayerAndTokens(){
        heightSetting = true;
        for(int i = 0; i < objects.getChildren().size(); i++){
            Node child = objects.getChildren().get(i);
            if(child instanceof Token || child instanceof Player){
                Timeline timeline = new Timeline(
                        new KeyFrame(
                                javafx.util.Duration.ZERO,
                                new KeyValue(child.translateYProperty(), child.getTranslateY(), Interpolator.LINEAR)
                        ),
                        new KeyFrame(
                                Duration.seconds(1),
                                new KeyValue(child.translateYProperty(), -55, Interpolator.LINEAR)
                        )
                );

                timeline.play();
                if(child instanceof Player){
                    timeline.setOnFinished(event -> {
                        heightSetting = false;
                    });
                }
            }

        }
    }

    private void descendPlayersAndTokens(){
        heightSetting = true;
        for(int i = 0; i < objects.getChildren().size(); i++){
            Node child = objects.getChildren().get(i);
            if(child instanceof Token || child instanceof Player){
                Timeline timeline = new Timeline(
                        new KeyFrame(
                                javafx.util.Duration.ZERO,
                                new KeyValue(child.translateYProperty(), child.getTranslateY(), Interpolator.LINEAR)
                        ),
                        new KeyFrame(
                                Duration.seconds(1),
                                new KeyValue(child.translateYProperty(), yOriginal, Interpolator.LINEAR)
                        )
                );

                timeline.play();
                if(child instanceof Player){
                    timeline.setOnFinished(event -> {
                        heightSetting = false;
                    });
                }
            }
        }
    }

    private void elevateToken(Token token){
        token.setTranslateY(-55);
    }

    public static void setCamera(Camera cameraToSet){
        scene3D.setCamera(cameraToSet);
    }


    public static void main(String[] args) {
        launch();
    }
}