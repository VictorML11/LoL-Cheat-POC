package com.example.kafkatesting.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


public class OverlayEvent extends Event {
  private Batch batch;
  private ShapeRenderer renderer;
  private BitmapFont fontRenderer;

  public OverlayEvent(Batch batchIn) {
    this.batch = batchIn;
  }

  public OverlayEvent setBatch(Batch batchIn) {
    this.batch = batchIn;
    return this;
  }
  public OverlayEvent setRenderer(ShapeRenderer rendererIn) {
    this.renderer = rendererIn;
    return this;
  }
  public OverlayEvent setTextRenderer(BitmapFont fontRendererIn) {
    this.fontRenderer = fontRendererIn;
    return this;
  }

  public Batch getBatch() {
    return this.batch;
  }

  public ShapeRenderer getRenderer() {
    return this.renderer;
  }

  public BitmapFont getFontRenderer() {
    return this.fontRenderer;
  }
}
