package com.example.demo1;

public class Obstacle extends GameObject{

    private static final double OBSTACLE_SPEED = 4.0;
    private ObstacleBody obstacleBody;

    public Obstacle(Position position){
        super(position);

        obstacleBody = new Hurdle(position);

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


}
