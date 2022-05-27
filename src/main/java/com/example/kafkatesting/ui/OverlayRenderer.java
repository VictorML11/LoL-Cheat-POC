package com.example.kafkatesting.ui;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.example.kafkatesting.model.Entity;
import com.example.kafkatesting.model.GameMemory;
import com.example.kafkatesting.model.GameRender;
import com.example.kafkatesting.model.Vec2f;
import com.example.kafkatesting.model.Vec3f;
import com.example.kafkatesting.ui.transparency.TransparencyApplication;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import static com.badlogic.gdx.Gdx.gl;
import static com.badlogic.gdx.graphics.GL20.GL_BLEND;
import static org.lwjgl.opengl.GL11C.*;

@Log4j2
public class OverlayRenderer extends ApplicationAdapter {
    private Window window;

    public Window getWindow() {
        return window;
    }

    public Overlay getOverlay() {
        return overlay;
    }

    private final OverlayEvent event;
    private final Overlay overlay;

    private OrthographicCamera camera;

    private ShapeRenderer renderer;
    @Getter
    private BitmapFont textRenderer;

    @Getter
    private BitmapFont smallText;

    private Batch batch;
    private SpriteBatch spriteBatch;
    private String title;


    @Getter
    private Stage stage;
    private Viewport viewPort;



    public OverlayRenderer getInstance() {
        return this;
    }


    OverlayRenderer(Overlay overlay, String title) {
        this.title = title;
        this.overlay = overlay;
        this.event = new OverlayEvent(batch);


    }

    @Override
    public final void create() {
        System.out.println("Created");
        while (window == null) {
            window = Window.get(title);
            System.out.println("not got");
        }

        if (!OverlaySetup.setup(this)) {
            System.out.println("FILD");
        }

        TransparencyApplication.systemDefault.accept(window.getHWnd());
        update();
    }


    public void worldToScreen(final float x, final float y, final float z, Vec2f ref) {
        GameRender gameRender = GameMemory.getInstance().getGameRender();
        List<Float> vpm = new ArrayList<>();
        gameRender.getViewProjMatrix().forEach(f -> {
            vpm.add(f);
        });
        final var w = gameRender.getWidth();
        final var h = gameRender.getHeight();

        float clip_coords_x = x * vpm.get(0) + vpm.get(4) * y + vpm.get(8) * z + vpm.get(12);
        float clip_coords_y = x * vpm.get(1) + vpm.get(5) * y + vpm.get(9) * z + vpm.get(13);
        float clip_coords_z = x * vpm.get(2) + vpm.get(6) * y + vpm.get(10) * z + vpm.get(14);
        float clip_coords_w = x * vpm.get(3) + vpm.get(7) * y + vpm.get(11) * z + vpm.get(15);

        if (clip_coords_w < 1.0f) {
            clip_coords_w = 1.f;
        }

        float M_x = clip_coords_x / clip_coords_w;
        float M_y = clip_coords_y / clip_coords_w;
        float M_Z = clip_coords_z / clip_coords_w;

        float out_x = (w / 2.f * M_x) + (M_x + w / 2.f);
        float out_y = -(h / 2.f * M_y) + (M_y + h / 2.f);

        ref.setX(out_x);
        ref.setY(out_y);

    }

    Vec2f vec2f = new Vec2f(0,0);
    Vec2f circleDraw = new Vec2f(0,0);

    @Override
    public final void render() {

        gl.glClearColor(0.0F, 0.0F, 0.0F, 0F);
        gl.glClear(GL_COLOR_BUFFER_BIT);


        if (overlay.getTarget().isVisible()) {
            gl.glEnable(GL_BLEND);

            gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            //System.out.println("Visible");
            GameRender gameRender = GameMemory.getInstance().getGameRender();
            if(gameRender != null){
                spriteBatch.begin();
                textRenderer.setColor(Color.RED);
                textRenderer.draw(spriteBatch, " FPS >> " + Gdx.graphics.getFramesPerSecond(), 10, 5);
                textRenderer.draw(spriteBatch, " Resolution >> " + gameRender.getWidth() + "x" + gameRender.getHeight(), 10, 20);
                textRenderer.draw(spriteBatch, " GameTime >> " + gameRender.getGameTime(), 10, 35);
                if(gameRender.getViewProjMatrix() != null){
                    textRenderer.draw(spriteBatch, " Matrix >> " + gameRender.getViewProjMatrix().toString(), 10, 300);

                }
                spriteBatch.end();

                int i = 50;
                List<Entity> ents = GameMemory.getInstance().getEntities().stream().map(Entity::clone).collect(Collectors.toList());
                for (Entity entity : ents) {
                    spriteBatch.begin();

                    textRenderer.setColor(Color.GREEN);
                    textRenderer.draw(spriteBatch, " Entity Pos >> " + entity.getPosition().toString(), 10, i);
                    i += 20;
                    worldToScreen(entity.getPosition().getX(),entity.getPosition().getY(),entity.getPosition().getZ(),vec2f);
                    textRenderer.draw(spriteBatch, " Ashe ", (int) vec2f.getX(), (int) vec2f.getY());
                    spriteBatch.end();

                    this.optimizedDraw(entity.getPosition(), 665, 100, 2, Color.GREEN, circleDraw);

                    //textRenderer.draw(spriteBatch, entity.getPosition().toString(), (int) vec2f.getX(), (int) vec2f.getY());

                }


            }

            gl.glDisable(GL_BLEND);


        }

    }

    public void optimizedDraw(Vec3f position, float range, int points, int thikness, Color color, Vec2f screenPosRef) {
        float step = 6.2831f / points;
        float theta = 0.f;
        float[] positions = new float[points * 2];

        for (int i = 0; i < points; i++, theta += step) {
            float worldX = (float) (position.getX() + range * Math.cos(theta));
            float worldY = position.getY();
            float worldZ = (float) (position.getZ() - range * Math.sin(theta));
            //Vec2f screenPos = GameRenderer.worldToScreen(worldX, worldY, worldZ);
            this.worldToScreen(worldX, worldY, worldZ, screenPosRef);
            positions[i * 2] = screenPosRef.getX();
            positions[(i * 2) + 1] = screenPosRef.getY();
        }

        gl.glLineWidth(thikness);
        renderer.begin();
        renderer.setColor(color);
        renderer.polygon(positions);
        renderer.end();

    }



    @Override
    public final void dispose() {
        batch.dispose();
        stage.dispose();
    }


    private void update() {
        System.out.println("Updating");
        Rectangle bounds = overlay.getTarget().getBounds();
        if (bounds == null)
            return;

        Window overlay = this.getWindow();
        if (overlay != null)
            overlay.setBounds(bounds);

        if (camera == null)
            camera = new OrthographicCamera((float) bounds.getWidth(), (float) bounds.getHeight());
        camera.setToOrtho(true, (float) bounds.getWidth(), (float) bounds.getHeight());

        if (batch == null)
            batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);

        if (spriteBatch == null) {
            spriteBatch = new SpriteBatch();

        }
        spriteBatch.setProjectionMatrix(camera.combined);


        if (renderer == null)
            renderer = new ShapeRenderer();
        renderer.setProjectionMatrix(camera.combined);
        renderer.setAutoShapeType(true);

        if (textRenderer == null){
            textRenderer = new BitmapFont(Gdx.files.local("./data/b.fnt"),true);
        }

        if (smallText == null){
            smallText = new BitmapFont(Gdx.files.local("./data/a.fnt"),true);
        }

        if (stage == null){
            stage = new Stage();
            viewPort =  new FitViewport(camera.viewportWidth, camera.viewportHeight, camera);
            stage.setViewport(viewPort);
        }


        //new BitmapFont(Gdx.files.internal("data/font.png"),Gdx.files.internal("data/font.png"), true);

    }
}
