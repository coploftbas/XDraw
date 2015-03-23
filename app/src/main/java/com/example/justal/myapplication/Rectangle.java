package com.example.justal.myapplication;

/**
 * Created by Justal on 23/03/2015.
 */
public class Rectangle {
    private float x1;
    private float x2;
    private float y1;
    private float y2;

    public Rectangle(float x1,float y1,float x2,float y2) {
        if(x1<x2) {
            this.x1=x1;
            this.x2=x2;
        } else {
            this.x1=x2;
            this.x2=x1;
        }
        if(y1<y2) {
            this.y1=y1;
            this.y2=y2;
        } else {
            this.y1=y2;
            this.y2=y1;
        }
    }

    public boolean inside(float x,float y) {
        if(x>=x1 && x<=x2 && y>=y1 && y<=y2) {
            return true;
        }
        return false;
    }

    public float getX1() {
        return x1;
    }

    public float getX2() {
        return x2;
    }

    public float getY1() {
        return y1;
    }

    public float getY2() {
        return y2;
    }
}
