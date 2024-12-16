package io.github.basic3d.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.basic3d.Main;

public class ModelLoading implements Screen {
    public ModelBatch modelBatch;
    public Environment environment;
    public Model model;
    public ModelInstance instance;
    public CameraInputController camController;
    public AssetManager assets;
    public Array<ModelInstance> instances = new Array<>();
    public boolean loading;

    final Main game;
    public ModelLoading(final Main getGame) {
        this.game = getGame;

        modelBatch = new ModelBatch();
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        game.cam.position.set(40f, 40f, 40f);
        camController = new CameraInputController(game.cam);
        Gdx.input.setInputProcessor(camController);

        assets = new AssetManager();
        assets.load("Lowpoly_tree_sample.obj", Model.class);
        loading = true;
    }

    private void doneLoading() {
        Model tree = assets.get("Lowpoly_tree_sample.obj", Model.class);
        for (float x = -20f; x < 20f; x += 10f) {
            for (float z = -20f; z < 20f; z += 10f) {
                ModelInstance instance = new ModelInstance(tree);
                instance.transform.setToTranslation(x, 0, z);
                instances.add(instance);
            }
        }
        loading = false;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (loading && assets.update()) {
            doneLoading();
        }
        camController.update();

        ScreenUtils.clear(1, 1, 1, 1, true);

        modelBatch.begin(game.cam);
        modelBatch.render(instances, environment);
        modelBatch.end();

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
        assets.dispose();
        instances.clear();
        instances = null;
        model = null;
        instance = null;
        environment = null;
    }
}
