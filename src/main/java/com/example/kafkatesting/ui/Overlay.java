package com.example.kafkatesting.ui;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.Random;

public class Overlay {

  private final Window target;
  private OverlayRenderer renderer;
  private Lwjgl3Application application;

  private static final String title = String.valueOf(new Random().nextLong());

  public Overlay(Window target) {
    this.target = target;
  }

  public final boolean display() {
    if (renderer != null)
      return true;

    System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");

    Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
    Rectangle bounds = target.getBounds();
    while (bounds == null)
      return false;

    config.setWindowedMode(bounds.width, bounds.height);
    config.setTitle(title);
    config.setWindowPosition(bounds.x,bounds.y);

    //config.useOpenGL3(true, 4, 6);
    config.setOpenGLEmulation(Lwjgl3ApplicationConfiguration.GLEmulation.GL30, 4, 6);
    config.setDecorated(false);
    config.useVsync(false);
    config.disableAudio(true);
    config.setResizable(false);

    GLFW.glfwSwapInterval(0);
    GLFW.glfwWindowHint(GLFW.GLFW_DOUBLEBUFFER, GLFW.GLFW_FALSE);

    config.setForegroundFPS(144);
    config.setIdleFPS(144);
    int antialiasingSamples = 0;//4
    // note: we don't need any bits for the back buffer besides just 1 for alpha
    config.setBackBufferConfig(0, 0, 0, 1, 0, 0, antialiasingSamples);

    System.out.println("Not created");

    application = new Lwjgl3Application(renderer = new OverlayRenderer(this, title), config);


    return true;
  }

  public final Window getTarget() {
    return this.target;
  }

  public final OverlayRenderer getRenderer() {
    return this.renderer;
  }


  public final void close() {
    renderer = null;
    this.application.exit();
  }
}
