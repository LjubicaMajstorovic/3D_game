package com.example.demo1;

import java.util.Random;

public class Obstacle extends GameObject{

    private static final double OBSTACLE_SPEED = 4.0;
    private ObstacleBody obstacleBody;
    private boolean isHit = false;

    public Obstacle(Position position){
        super(position);
        Random random = new Random();
        double probability = random.nextDouble();
        if(probability < 0.5){
            obstacleBody = new Hurdle(position);
        } else {
            obstacleBody = new TallHurdle(position);
        }



        this.setTranslateX(position.getX());
        this.setTranslateY(position.getY() - obstacleBody.getObstacleHeight() / 2);
        this.setTranslateZ(position.getZ());

        this.getChildren().add(obstacleBody);

    }

    public boolean move()
    {
        this.setTranslateZ(this.getTranslateZ() - OBSTACLE_SPEED);
        return isOnTrack();
    }

    public boolean isOnTrack()
    {
        return this.getTranslateZ() > 0;
    }

    public boolean isHit() { return this.isHit; }

    public void hit() { this.isHit = true; }


}
