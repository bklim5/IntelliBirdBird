package com.bkbklim.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bkbklim.Helpers.AssetLoader;
import com.bkbklim.Helpers.ThirdPartyController;
import com.bkbklim.IntelliBirdBird;
import com.bkbklim.TweenAccessor.SpriteAccessor;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

/**
 * Created by bklim on 29/11/15.
 */
public class SplashScreen implements Screen{

    private IntelliBirdBird birdGame;
    private TweenManager manager;
    private Sprite sprite, birdSprite;
    private TextureRegion bird;
    private SpriteBatch batcher;
    private float rotation, x, y;
    private float width, height;
    private float birdScale;
    private ThirdPartyController controller;

    public SplashScreen(IntelliBirdBird birdGame, ThirdPartyController controller) {
        this.birdGame = birdGame;
        this.controller = controller;
    }


    @Override
    public void show() {

        sprite = new Sprite(AssetLoader.logo);
        sprite.setColor(1,1,1,0);

        birdSprite = new Sprite(AssetLoader.bird);
        birdSprite.setColor(1,1,1,0);


        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        float desiredWidth = width * 0.8f;
        float scale = desiredWidth / sprite.getWidth();



        //scale and center sprite
        sprite.setSize(sprite.getWidth() * scale, sprite.getHeight() * scale);
        sprite.setPosition(width / 2 - sprite.getWidth() / 2, height / 2 + sprite.getHeight() / 2);



        // roll bird under logo!
        bird = AssetLoader.bird;
        birdScale = bird.getRegionWidth() / (width * 0.183f);

        birdSprite.setSize(bird.getRegionWidth() / birdScale, bird.getRegionHeight() / birdScale);
        birdSprite.setPosition(width / 2 - (bird.getRegionWidth() / birdScale) / 2, height / 2);
        birdSprite.setOrigin((bird.getRegionWidth() / birdScale) / 2, (bird.getRegionHeight() / birdScale) / 2);

        setupTween();

        rotation = 0;
        x = -100;
        y = height/2 - 10;

        batcher = new SpriteBatch();

    }

    private void setupTween() {
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());
        manager = new TweenManager();

        TweenCallback cb = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                birdGame.setScreen(new GameScreen(controller));

            }
        };

        Tween.to(sprite, SpriteAccessor.ALPHA, 1f)
                .target(1)
                .ease(TweenEquations.easeInOutQuad)
                .repeatYoyo(1, 1f)
                .setCallback(cb)
                .setCallbackTriggers(TweenCallback.COMPLETE)
                .start(manager);

        Tween.to(birdSprite, SpriteAccessor.ALPHA, 1f)
                .target(1)
                .ease(TweenEquations.easeInOutQuad)
                .repeatYoyo(1, 1f)
                .start(manager);


    }

    @Override
    public void render(float delta) {
        manager.update(delta);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batcher.begin();

        sprite.draw(batcher);
        birdSprite.draw(batcher);

//        batcher.draw(bird, width / 2 - (bird.getRegionWidth() / birdScale) / 2 , height / 2, (bird.getRegionWidth() / birdScale) / 2, (bird.getRegionHeight() / birdScale) / 2, bird.getRegionWidth() / birdScale, bird.getRegionHeight() / birdScale, 1, 1, rotation);
        rotation += 8;
        birdSprite.setRotation(rotation);
        batcher.end();

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
