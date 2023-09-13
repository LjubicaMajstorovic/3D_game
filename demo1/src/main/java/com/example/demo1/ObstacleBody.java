package com.example.demo1;

public abstract class ObstacleBody extends GameObject{

    public ObstacleBody(Position position){
        super(position);
        createObstacleBody();
    }

    protected abstract void createObstacleBody();

    public abstract double getObstacleHeight();
}
