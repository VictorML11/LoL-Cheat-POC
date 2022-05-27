package com.example.kafkatesting.model;

import lombok.Data;

import java.util.List;

@Data
public class GameRender {

    private float gameTime;

    private int height;
    private int width;

    private List<Float> viewProjMatrix;

    private Vec2f minimapHudPos;
    private Vec2f minimapHudSize;
}
