package com.example.demo1;

import javafx.scene.Group;


public class GameObject extends Group {
    protected Position position;

    public GameObject(Position position){
        this.position = position;
    }
}
