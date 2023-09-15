package com.example.demo1;

public abstract class TokenBody extends GameObject{
    TokenBody(Position position) {
        super(position);
        createTokenBody();
    }

    protected abstract void createTokenBody();

    public abstract double getTokenHeight();
}
