package com.example.kafkatesting.model;


import lombok.Data;
import org.apache.commons.lang3.tuple.ImmutablePair;

@Data
public class Vec2f {

    private float x;
    private float y;


    public Vec2f(final float x, final float y) {
        this.x = x;
        this.y = y;
    }

    public Vec2f(ImmutablePair<Float,Float> sc) {
        this.x = sc.getLeft();
        this.y = sc.getRight();
    }

    public void updateValues(float x, float y){
        this.x = x;
        this.y = y;
    }

    public static Vec2f copyOf(Vec2f vec2){
        return new Vec2f(vec2.getX(), vec2.getY());
    }

    public Vec2f clone(){
        return new Vec2f(this.x, this.y);
    }

    public Vec2f add(Vec2f o) {
        return new Vec2f(x + o.x, y + o.y);
    }

    public void addVec(Vec2f o) {
        this.addToX(o.getX());
        this.addToY(o.getY());
    }

    public Vec2f sub(Vec2f o) {
        return new Vec2f(x - o.x, y - o.y);
    }

    public int getIntX(){
        return (int) this.getX();
    }

    public int getIntY(){
        return (int) this.getY();
    }
    public void addToX(float addX){
        this.x += addX;
    }

    public void addToY(float addY){
        this.y += addY;
    }


    public void subToX(float subX){
        this.x -= subX;
    }

    public void subToY(float subY){
        this.y -= subY;
    }


    @Override
    public String toString() {
        return "X >> " + this.x + " Y >> " + this.y;
    }
}
