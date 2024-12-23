package io.github.basic3d.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.basic3d.Main;

public class PlaneScreen implements Screen {
    public ModelBatch modelBatch;
    public Model model;
    public ModelInstance instance;
    public Environment environment;
    public CameraInputController camController;

    final Main game;
    public PlaneScreen(final Main getGame) {
        this.game = getGame;

        modelBatch = new ModelBatch();

        // Creates an environment for lighting data
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        // Creates model builder used for making primitive models
        ModelBuilder modelBuilder = new ModelBuilder();

        // Creates a cyan box model
        model = modelBuilder.createBox(5f, 5f, 5f,
                new Material(ColorAttribute.createDiffuse(Color.CYAN)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);

        // Makes an instance of the box model
        instance = new ModelInstance(model);

        camController = new CameraInputController(game.cam);
        Gdx.input.setInputProcessor(camController);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // Clears screen
        ScreenUtils.clear(1, 1, 1, 1, true);
        game.cam.update();

        modelBatch.begin(game.cam);
        modelBatch.render(instance, environment);
        modelBatch.end();

        // Change screen
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            game.setScreen(new ModelLoading(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        game.cam.viewportWidth = width;
        game.cam.viewportHeight = height;
        game.cam.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        modelBatch.dispose();
        model.dispose();
    }
}
