package com.example.kafkatesting.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class GameMemory {

    @Getter
    @Setter
    private GameRender gameRender;

    @Getter
    @Setter
    private List<Entity> entities;


    private GameMemory instance;

    private static class InstanceHolder {
        public static GameMemory instance = new GameMemory();
    }

    public static GameMemory getInstance() {
        return GameMemory.InstanceHolder.instance;
    }

    private GameMemory() {
        gameRender = new GameRender();
        entities = new ArrayList<>();
    }
}
