package com.bkbklim.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.bkbklim.Helpers.InputHandler;
import com.bkbklim.Helpers.ThirdPartyController;
import com.bkbklim.Logic.GameRenderer;
import com.bkbklim.Logic.GameWorld;

/**
 * Created by bklim on 29/11/15.
 */
public class GameScreen implements Screen {
    private GameWorld world;
    private GameRenderer renderer;
    private float runTime = 0;
    private ThirdPartyController controller;

    public GameScreen(ThirdPartyController controller) {
        this.controller = controller;

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float gameWidth = 136;

        float scaleFactorX = (screenWidth/gameWidth);
        float gameHeight = screenHeight / scaleFactorX;
        float scaleFactorY = (screenHeight/gameHeight);


        int midPointY = (int) (gameHeight / 2);

        world = new GameWorld(midPointY, controller);
        Gdx.input.setInputProcessor(new InputHandler(world, scaleFactorX, scaleFactorY));
        renderer = new GameRenderer(world, (int) gameHeight, midPointY);
        world.setRenderer(renderer);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        runTime += delta;
        world.update(delta);
        renderer.update(delta, runTime);

    }

    @Override
    public void resize(int width, int height) {

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

    }
}
