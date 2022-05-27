package com.example.kafkatesting.model;

import lombok.Data;

@Data
public class Entity implements Cloneable{

    private String name;

    private int address;

    private float health;

    private boolean visible;


    private Vec3f position = new Vec3f(0f,0f,0f);

    @Override
    public Entity clone(){
        Entity e = null;
        try{
            e = (Entity) super.clone();
            e.setPosition(this.position.clone());
        }catch (CloneNotSupportedException ex){

        }
        return e;
    }

}
