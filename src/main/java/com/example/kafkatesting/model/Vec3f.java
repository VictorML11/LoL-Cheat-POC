package com.example.kafkatesting.model;


import lombok.Data;

import java.util.Objects;

@Data
public class Vec3f implements Cloneable{

    private float x;
    private float y;
    private float z;

    public Vec3f() {
    }

    public Vec3f(final float x, final float y, final float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }



    public static Vec3f copyOf(Vec3f vec3){
        return new Vec3f(vec3.getX(), vec3.getY(), vec3.getZ());
    }

    public void setPos(Vec3f vec3){
        this.setX(vec3.getX());
        this.setY(vec3.getY());
        this.setZ(vec3.getZ());
    }

    public Vec3f clone(){
        Vec3f vec3f = null;
        try{
            vec3f = (Vec3f) super.clone();

        }catch (CloneNotSupportedException ex){}
        //return new Vec3f(this.x, this.y, this.z);
        return vec3f;
    }

    public Vec3f add(Vec3f o) {
        return new Vec3f(x + o.x, y + o.y, z + o.z);
    }

    public void addVec(Vec3f o) {
        this.addToX(o.getX());
        this.addToY(o.getY());
        this.addToZ(o.getZ());
    }

    public Vec3f sub(Vec3f o) {
        return new Vec3f(x - o.x, y - o.y, z - o.z);
    }

    public void addToX(float addX){
        this.x += addX;
    }

    public void addToY(float addY){
        this.y += addY;
    }

    public void addToZ(float addZ){
        this.z += addZ;
    }

    public void subToX(float subX){
        this.x -= subX;
    }

    public void subToY(float subY){
        this.y -= subY;
    }

    public void subToZ(float subZ){
        this.z -= subZ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vec3f)) return false;
        Vec3f vec3f = (Vec3f) o;
        return Float.compare(vec3f.getX(), getX()) == 0 && Float.compare(vec3f.getY(), getY()) == 0 && Float.compare(vec3f.getZ(), getZ()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY(), getZ());
    }

    @Override
    public String toString() {
        return "X >> " + this.x + " Y >> " + this.y + " Z >> " + this.z;
    }
}

