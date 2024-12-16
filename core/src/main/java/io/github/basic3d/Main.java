package io.github.basic3d;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import io.github.basic3d.screen.PlaneScreen;

public class Main extends Game {
    public PerspectiveCamera cam;

    @Override
    public void create() {
        Gdx.graphics.setTitle("Basic3D");

        // Creates the camera
        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(10f, 10f, 10f);
        cam.lookAt(0, 0, 0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();

        // Changes screen
        this.setScreen(new PlaneScreen(this));
    }
}
