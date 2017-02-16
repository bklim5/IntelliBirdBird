package com.bkbklim.Logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.bkbklim.GameObjects.Bird;
import com.bkbklim.GameObjects.ChristmasBall;
import com.bkbklim.GameObjects.ScrollHandler;
import com.bkbklim.GameObjects.Tree;
import com.bkbklim.Helpers.AssetLoader;
import com.bkbklim.Helpers.Constant;
import com.bkbklim.Helpers.InputHandler;
import com.bkbklim.Helpers.SimpleButton;
import com.bkbklim.Helpers.ToggleButton;
import com.bkbklim.TweenAccessor.BitmapFontCacheAccessor;
import com.bkbklim.TweenAccessor.SpriteAccessor;
import com.bkbklim.TweenAccessor.Value;
import com.bkbklim.TweenAccessor.ValueAccessor;

import java.util.ArrayList;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;


/**
 * Created by bklim on 29/11/15.
 */
public class GameRenderer {

    private GameWorld world;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;

    private SpriteBatch batcher;
    private int midPointY;

    //Game Objects
    private Bird bird;
    private ArrayList<ChristmasBall> balls;
    private Tree tree;

    //Game Assets
    private Animation birdAnimation;
    private TextureRegion woodBanner, christmasBall, treeRegion, birdTextureRegion;
    private TextureRegion bg, cloudThought;
    private TextureRegion medalLevel1, medalLevel1PlaceHolder, medalLevel10, medalLevel10PlaceHolder, medalLevel30, medalLevel30PlaceHolder, medalLevel50, medalLevel50PlaceHolder;
    private ScrollHandler scroller;

    private QuestionGenerator generator;

    private Color transitionColor;
    private TweenManager manager, manager2;
    private Value alpha;

    private SimpleButton playButton, continueButton, retryButton, mainMenuButton, nextLevelButton, facebookButton;
    private ToggleButton volumeButton;

    private BitmapFontCache whiteFontCache, shadowFontCache, titleWhiteFontCache, titleShadowFontCache, smallWhiteFontCache, smallShadowFontCache;
    private GlyphLayout tempLayout;


    public GameRenderer(GameWorld world, int gameHeight, int midPointY) {
        this.world = world;
        this.midPointY = midPointY;

        cam = new OrthographicCamera();
        cam.setToOrtho(true, 136, gameHeight);

        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);

        initGameObjects();
        initAssets();

        alpha = new Value();
        transitionColor = new Color();

        manager = new TweenManager();
        prepareTransition(0, 0, 0, 1f);

        prepareMenuTween();

    }

    public void initGameObjects() {
        bird = world.getBird();
        playButton = ((InputHandler) Gdx.input.getInputProcessor()).getPlayButton();
        retryButton = ((InputHandler) Gdx.input.getInputProcessor()).getRetryButton();
        continueButton = ((InputHandler) Gdx.input.getInputProcessor()).getContinueButton();
        nextLevelButton = ((InputHandler) Gdx.input.getInputProcessor()).getNextLevelButton();
        mainMenuButton = ((InputHandler) Gdx.input.getInputProcessor()).getMainMenuButon();
        volumeButton = ((InputHandler) Gdx.input.getInputProcessor()).getVolumeButton();
        facebookButton = ((InputHandler) Gdx.input.getInputProcessor()).getFacebookButton();
        generator = world.getQuestionGenerator();
        scroller = world.getScroller();
        balls = scroller.getBalls();
        tree = scroller.getTree();
    }

    public void initAssets() {
        bg = AssetLoader.bg;
        treeRegion = AssetLoader.tree;
        birdAnimation = AssetLoader.birdAnimation;
        woodBanner = AssetLoader.woodBanner;
        christmasBall = AssetLoader.christmasBall;
        birdTextureRegion = AssetLoader.bird;
        cloudThought = AssetLoader.cloudThought;
        whiteFontCache = new BitmapFontCache(AssetLoader.whiteFont, true);
        shadowFontCache = new BitmapFontCache(AssetLoader.shadow, true);
        smallWhiteFontCache = new BitmapFontCache(AssetLoader.smallWhiteFont, true);
        smallShadowFontCache = new BitmapFontCache(AssetLoader.smallShadowFont, true);
        titleShadowFontCache = new BitmapFontCache(AssetLoader.shadow, true);
        titleWhiteFontCache = new BitmapFontCache(AssetLoader.whiteFont, true);
        medalLevel1 = AssetLoader.medalLevel1;
        medalLevel10 = AssetLoader.medalLevel10;
        medalLevel30 = AssetLoader.medalLevel30;
        medalLevel50 = AssetLoader.medalLevel50;
        medalLevel1PlaceHolder = AssetLoader.medalLevel1PlaceHolder;
        medalLevel10PlaceHolder = AssetLoader.medalLevel10PlaceHolder;
        medalLevel30PlaceHolder = AssetLoader.medalLevel30PlaceHolder;
        medalLevel50PlaceHolder = AssetLoader.medalLevel50PlaceHolder;
        tempLayout = new GlyphLayout();

        resetAssetAlphas();
    }

    public void update(float delta, float runTime) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batcher.begin();
        batcher.draw(bg, 0, 0, 136, midPointY * 2);

        if (world.isRunning()) {
            drawBanner();
            drawChristmasBall();
            drawBird(runTime);
            drawScore();
            drawLife();

        } else if (world.isMenu()) {
            drawGameTitle(delta);
            drawBirdCentered(runTime);
            drawMenuUI();

        } else if (world.isReady()) {
            drawReady();
            drawBird(runTime);

        } else if (world.isLevelUp()) {
            drawLevelUp(delta, runTime);

        } else if (world.isGameOver()) {
            drawGameOver(delta);

        } else if (world.isLastLevel()) {
            drawLastLevel(delta);
        }

        batcher.end();

        drawTransition(delta);
    }


    // menu state
    public void drawGameTitle(float delta) {

        tempLayout.setText(titleShadowFontCache.getFont(), Constant.gameTitle);
        titleShadowFontCache.draw(batcher);

        titleWhiteFontCache.draw(batcher);

        tempLayout.setText(smallShadowFontCache.getFont(), Constant.gameEdition);
        smallShadowFontCache.setText(tempLayout, 136 / 2 - tempLayout.width / 2, midPointY - 40);
        smallShadowFontCache.draw(batcher, smallShadowFontCache.getColor().a);

        smallWhiteFontCache.setText(tempLayout, 136 / 2 - tempLayout.width / 2 + 1, midPointY - 41);
        smallWhiteFontCache.draw(batcher, smallWhiteFontCache.getColor().a);

        manager2.update(delta);


    }

    public void drawMenuUI() {
        playButton.drawButton(batcher);
        continueButton.drawButton(batcher);
        volumeButton.drawButton(batcher);

        //draw medals
        if (world.getHighestLevel() > 1) {
            batcher.draw(medalLevel1, 136 / 8 - medalLevel1.getRegionWidth() / 2, midPointY + 25, medalLevel1.getRegionWidth(), medalLevel1.getRegionHeight());
        } else {
            batcher.draw(medalLevel1PlaceHolder, 136 / 8 - medalLevel1PlaceHolder.getRegionWidth() / 2, midPointY + 25, medalLevel1PlaceHolder.getRegionWidth(), medalLevel1PlaceHolder.getRegionHeight());
        }


        if (world.getHighestLevel() > 10) {
            batcher.draw(medalLevel10, 136 * 3/ 8 - medalLevel10.getRegionWidth() / 2, midPointY + 25, medalLevel10.getRegionWidth(), medalLevel10.getRegionHeight());
        } else {
            batcher.draw(medalLevel10PlaceHolder, 136 * 3 / 8 - medalLevel10PlaceHolder.getRegionWidth() / 2, midPointY + 25, medalLevel10PlaceHolder.getRegionWidth(), medalLevel10PlaceHolder.getRegionHeight());

        }


        if (world.getHighestLevel() > 30) {
            batcher.draw(medalLevel30, 136 * 5/ 8 - medalLevel30.getRegionWidth() / 2, midPointY + 25, medalLevel30.getRegionWidth(), medalLevel30.getRegionHeight());

        } else {
            batcher.draw(medalLevel30PlaceHolder, 136 * 5/ 8 - medalLevel30PlaceHolder.getRegionWidth() / 2, midPointY + 25, medalLevel30PlaceHolder.getRegionWidth(), medalLevel30PlaceHolder.getRegionHeight());

        }

        if (world.getGameComplete()) {
            batcher.draw(medalLevel50, 136 * 7/ 8 - medalLevel50.getRegionWidth() / 2, midPointY + 25, medalLevel50.getRegionWidth(), medalLevel50.getRegionHeight());

        } else {
            batcher.draw(medalLevel50PlaceHolder, 136 * 7/ 8 - medalLevel50PlaceHolder.getRegionWidth() / 2, midPointY + 25, medalLevel50PlaceHolder.getRegionWidth(), medalLevel50PlaceHolder.getRegionHeight());

        }


    }

    public void drawBirdCentered(float runTime) {
        batcher.draw(birdAnimation.getKeyFrame(runTime), 50, bird.getY() + 10,
                bird.getWidth() / 2.0f, bird.getHeight() / 2.0f,
                bird.getWidth(), bird.getHeight(), 1, 1, 0);

        batcher.draw(cloudThought, 75, bird.getY() + 5, cloudThought.getRegionWidth() /3, cloudThought.getRegionHeight() / 3);
        batcher.draw(cloudThought, 80, bird.getY(), cloudThought.getRegionWidth() /2, cloudThought.getRegionHeight() / 2);
        batcher.draw(cloudThought, 85, bird.getY() - 10, cloudThought.getRegionWidth(), cloudThought.getRegionHeight());


        AssetLoader.smallShadowFont.draw(batcher, bird.getBirdThought(), 85 + cloudThought.getRegionWidth() / 2 - 2, bird.getY() - 8);
        AssetLoader.smallWhiteFont.draw(batcher, bird.getBirdThought(), 85 + cloudThought.getRegionWidth() / 2 - 1, bird.getY() - 9);

    }

    //ready state
    public void drawReady() {
        tempLayout.setText(AssetLoader.shadow, "LEVEL " + world.getGameLevel());
        AssetLoader.shadow.draw(batcher, "LEVEL " + world.getGameLevel(), 136 / 2 - tempLayout.width / 2, midPointY - 50);
        AssetLoader.whiteFont.draw(batcher, "LEVEL " + world.getGameLevel(), 136 / 2 - tempLayout.width / 2 + 1, midPointY - 51);

        if (world.getGameLevel() == 1) {
            // draw instruction
            tempLayout.setText(AssetLoader.smallShadowFont, "1. Tap screen to fly");
            AssetLoader.smallShadowFont.draw(batcher, tempLayout, 5, midPointY + 10);
            AssetLoader.smallWhiteFont.draw(batcher, tempLayout, 6, midPointY + 9);

            tempLayout.setText(AssetLoader.smallShadowFont, "2. Solve the question");
            AssetLoader.smallShadowFont.draw(batcher, tempLayout, 5, midPointY + 30);
            AssetLoader.smallWhiteFont.draw(batcher, tempLayout, 6, midPointY + 29);

            tempLayout.setText(AssetLoader.smallShadowFont, "3. Bump Yuan Bao");
            AssetLoader.smallShadowFont.draw(batcher, tempLayout, 5, midPointY + 50);
            AssetLoader.smallWhiteFont.draw(batcher, tempLayout, 6, midPointY + 49);

            tempLayout.setText(AssetLoader.smallShadowFont, "  with correct answer");
            AssetLoader.smallShadowFont.draw(batcher, tempLayout, 5, midPointY + 65);
            AssetLoader.smallWhiteFont.draw(batcher, tempLayout, 6, midPointY + 64);

            tempLayout.setText(AssetLoader.smallShadowFont, "4. Win medals!");
            AssetLoader.smallShadowFont.draw(batcher, tempLayout, 5, midPointY + 85);
            AssetLoader.smallWhiteFont.draw(batcher, tempLayout, 6, midPointY + 84);


        }
    }

    //running state
    public void drawBird(float runTime) {
        batcher.draw(birdAnimation.getKeyFrame(runTime), bird.getX(), bird.getY(),
                bird.getWidth() / 2.0f, bird.getHeight() / 2.0f,
                bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
    }

    public void drawBanner() {
        GlyphLayout questionLayout = generator.getQuestionTextLayout();
        batcher.draw(woodBanner, 0, 0, 136, woodBanner.getRegionHeight());

        if (generator.isLongQuestion() == 0) {
            AssetLoader.shadow.draw(batcher, questionLayout, 136 / 2 - questionLayout.width / 2, 3);
            AssetLoader.font.draw(batcher, questionLayout, 136 / 2 - questionLayout.width / 2, 2);
        } else if (generator.isLongQuestion() == 1) {
            AssetLoader.mediumShadowFont.draw(batcher, questionLayout, 136 / 2 - questionLayout.width / 2, 5);
            AssetLoader.mediumGreenFont.draw(batcher, questionLayout, 136 / 2 - questionLayout.width / 2, 4);
        } else {
            AssetLoader.smallShadowFont.draw(batcher, questionLayout, 136 / 2 - questionLayout.width / 2, 6);
            AssetLoader.smallGreenFont.draw(batcher, questionLayout, 136 / 2 - questionLayout.width / 2, 5);
        }


    }

    public void drawChristmasBall() {
        batcher.draw(treeRegion, tree.getX(), tree.getY(), tree.getWidth(), tree.getHeight());
        for (ChristmasBall ball : balls) {
            batcher.draw(christmasBall, ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight());
            AssetLoader.smallShadowFont.draw(batcher, ball.getAnswerTextLayout(), ball.getAnswerPosition().x - 1, ball.getAnswerPosition().y + 1);
            AssetLoader.smallWhiteFont.draw(batcher, ball.getAnswerTextLayout(), ball.getAnswerPosition().x, ball.getAnswerPosition().y);
        }

    }

    public void drawScore() {
        float rectWidth = 1.5f;
        if (world.getScore() != 0) {
            rectWidth = (136 / 4 + 136 / 10f) * (world.getScore() / 10f);
        }

        batcher.draw(christmasBall, 136/2 + 136/8 - 15, midPointY + 64, 12, 12);
        batcher.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(42 / 255.0f, 187 / 255.0f, 155 / 255.0f, 1);
        shapeRenderer.rect(136 / 2 + 136 / 8, midPointY + 70, rectWidth, 6);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0 / 255.0f, 0 / 255.0f, 0 / 255.0f, 1);
        shapeRenderer.rect(136 / 2 + 136 / 8, midPointY + 70, 136 / 4 + 136 / 10f, 6);
        shapeRenderer.end();

        batcher.begin();
    }

    public void drawLife() {
        float rectWidth = (world.getLife() / 4f) * (136 / 4 + 136 / 10f);
        batcher.draw(birdTextureRegion, 136/2 + 136/8 - 15, midPointY + 79, 12, 12);
        batcher.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(249 / 255.0f, 108 / 255.0f, 108 / 255.0f, 1);
        shapeRenderer.rect(136 / 2 + 136 / 8, midPointY + 85, rectWidth, 6);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0 / 255.0f, 0 / 255.0f, 0 / 255.0f, 1);
        shapeRenderer.rect(136 / 2 + 136 / 8, midPointY + 85, 136 / 4 + 136 / 10f, 6);
        shapeRenderer.end();

        batcher.begin();
    }


    //level up
    public void drawLevelUp(float delta, float runTime) {
        int gameLevel = world.getGameLevel();
        String levelStr = Integer.toString(gameLevel - 1);

        batcher.draw(birdAnimation.getKeyFrame(runTime), 136 / 2 - bird.getWidth() / 2, midPointY - 85,
                bird.getWidth() / 2.0f, bird.getHeight() / 2.0f,
                bird.getWidth(), bird.getHeight(), 1, 1, 0);

        tempLayout.setText(shadowFontCache.getFont(), "LEVEL " + levelStr);

        shadowFontCache.setText(tempLayout, 136 / 2 - tempLayout.width / 2, midPointY - 50);
        shadowFontCache.draw(batcher, shadowFontCache.getColor().a);

        whiteFontCache.setText(tempLayout, 136 / 2 - tempLayout.width / 2 + 1, midPointY - 51);
        whiteFontCache.draw(batcher, whiteFontCache.getColor().a);


        tempLayout.setText(shadowFontCache.getFont(), "Completed");

        shadowFontCache.setText(tempLayout, 136 / 2 - tempLayout.width / 2, midPointY - 30);
        shadowFontCache.draw(batcher, shadowFontCache.getColor().a);

        whiteFontCache.setText(tempLayout, 136 / 2 - tempLayout.width / 2 + 1, midPointY - 31);
        whiteFontCache.draw(batcher, whiteFontCache.getColor().a);


        if (gameLevel - 1 == 30) {
            batcher.draw(medalLevel30, 136 / 2 - medalLevel30.getRegionWidth() / 2, midPointY - 10);
            tempLayout.setText(AssetLoader.smallShadowFont, "Medal collected!");
            AssetLoader.smallShadowFont.draw(batcher, tempLayout, 136 / 2 - tempLayout.width / 2, midPointY + 13);
            AssetLoader.smallWhiteFont.draw(batcher, tempLayout, 136 / 2 - tempLayout.width / 2, midPointY + 12);

        } else if (gameLevel - 1 == 10) {
            batcher.draw(medalLevel10, 136 / 2 - medalLevel10.getRegionWidth() / 2, midPointY - 10);
            tempLayout.setText(AssetLoader.smallShadowFont, "Medal collected!");
            AssetLoader.smallShadowFont.draw(batcher, tempLayout, 136 / 2 - tempLayout.width / 2, midPointY + 13);
            AssetLoader.smallWhiteFont.draw(batcher, tempLayout, 136 / 2 - tempLayout.width / 2, midPointY + 12);

        } else if (gameLevel - 1 == 1) {
            batcher.draw(medalLevel1, 136 / 2 - medalLevel1.getRegionWidth() / 2, midPointY - 10);
            tempLayout.setText(AssetLoader.smallShadowFont, "Medal collected!");
            AssetLoader.smallShadowFont.draw(batcher, tempLayout, 136 / 2 - tempLayout.width / 2, midPointY + 13);
            AssetLoader.smallWhiteFont.draw(batcher, tempLayout, 136 / 2 - tempLayout.width / 2, midPointY + 12);

        }

        //draw buttons
        nextLevelButton.drawButtonAlpha(batcher);
        mainMenuButton.drawButtonAlpha(batcher);
        facebookButton.drawButtonAlpha(batcher);


        manager2.update(delta);

    }

    public void drawGameOver(float delta) {
        tempLayout.setText(shadowFontCache.getFont(), "Game Over");
        shadowFontCache.setText(tempLayout, 136 / 2 - tempLayout.width / 2, midPointY - 50);
        shadowFontCache.draw(batcher, shadowFontCache.getColor().a);

        whiteFontCache.setText(tempLayout, 136 / 2 - tempLayout.width / 2 + 1, midPointY - 51);
        whiteFontCache.draw(batcher, whiteFontCache.getColor().a);

        //draw buttons
        retryButton.drawButtonAlpha(batcher);
        mainMenuButton.drawButtonAlpha(batcher);

        manager2.update(delta);
    }

    public void drawLastLevel(float delta) {
        tempLayout.setText(smallShadowFontCache.getFont(), "Congratulations!");

        smallShadowFontCache.setText(tempLayout, 136 / 2 - tempLayout.width / 2, midPointY - 70);
        smallShadowFontCache.draw(batcher, smallShadowFontCache.getColor().a);

        smallWhiteFontCache.setText(tempLayout, 136 / 2 - tempLayout.width / 2 + 1, midPointY - 71);
        smallWhiteFontCache.draw(batcher, smallWhiteFontCache.getColor().a);

        tempLayout.setText(smallShadowFontCache.getFont(), "This is the last level");

        smallShadowFontCache.setText(tempLayout, 136 / 2 - tempLayout.width / 2, midPointY - 50);
        smallShadowFontCache.draw(batcher, smallShadowFontCache.getColor().a);

        smallWhiteFontCache.setText(tempLayout, 136 / 2 - tempLayout.width / 2 + 1, midPointY - 51);
        smallWhiteFontCache.draw(batcher, smallWhiteFontCache.getColor().a);

        tempLayout.setText(smallShadowFontCache.getFont(), "Hope you enjoyed &");

        smallShadowFontCache.setText(tempLayout, 136 / 2 - tempLayout.width / 2, midPointY - 30);
        smallShadowFontCache.draw(batcher, smallShadowFontCache.getColor().a);

        smallWhiteFontCache.setText(tempLayout, 136 / 2 - tempLayout.width / 2 + 1, midPointY - 31);
        smallWhiteFontCache.draw(batcher, smallWhiteFontCache.getColor().a);

        tempLayout.setText(smallShadowFontCache.getFont(), "thanks for playing");

        smallShadowFontCache.setText(tempLayout, 136 / 2 - tempLayout.width / 2, midPointY - 10);
        smallShadowFontCache.draw(batcher, smallShadowFontCache.getColor().a);

        smallWhiteFontCache.setText(tempLayout, 136 / 2 - tempLayout.width / 2 + 1, midPointY - 11);
        smallWhiteFontCache.draw(batcher, smallWhiteFontCache.getColor().a);

        tempLayout.setText(smallShadowFontCache.getFont(), "IntelliBirdBird!");

        smallShadowFontCache.setText(tempLayout, 136 / 2 - tempLayout.width / 2, midPointY + 10);
        smallShadowFontCache.draw(batcher, smallShadowFontCache.getColor().a);

        smallWhiteFontCache.setText(tempLayout, 136 / 2 - tempLayout.width / 2 + 1, midPointY + 9);
        smallWhiteFontCache.draw(batcher, smallWhiteFontCache.getColor().a);


        //draw buttons
        retryButton.drawButtonAlpha(batcher);
        mainMenuButton.drawButtonAlpha(batcher);

        manager2.update(delta);
    }

    public void prepareTransition(int r, int g, int b, float duration) {
        alpha.setValue(1);
        transitionColor.set(r / 255f, g / 255f, b / 255f, 1);
        Tween.registerAccessor(Value.class, new ValueAccessor());
        manager = new TweenManager();

        Tween.to(alpha, -1, duration).target(0)
                .ease(TweenEquations.easeOutQuad).start(manager);
    }

    public void drawTransition(float delta) {
        if (alpha.getValue() > 0) {
            manager.update(delta);
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(transitionColor.r, transitionColor.g,
                    transitionColor.b, alpha.getValue());
            shapeRenderer.rect(0, 0, 136, 300);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
    }

    public void prepareMenuTween() {
        Tween.registerAccessor(BitmapFontCache.class, new BitmapFontCacheAccessor());
        manager2 = new TweenManager();

        smallWhiteFontCache.setAlphas(0);
        Color c = smallWhiteFontCache.getColor();
        c.a = 0;
        smallWhiteFontCache.setColor(c);

        smallShadowFontCache.setAlphas(0);
        c = smallShadowFontCache.getColor();
        c.a = 0;
        smallShadowFontCache.setColor(c);

        TweenCallback cb = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                Tween.to(smallShadowFontCache, BitmapFontCacheAccessor.ALPHA, 0.75f)
                        .target(1.0f)
                        .ease(TweenEquations.easeOutQuad)
                        .start(manager2);

                Tween.to(smallWhiteFontCache, BitmapFontCacheAccessor.ALPHA, 0.75f)
                        .target(1.0f)
                        .ease(TweenEquations.easeOutQuad)
                        .start(manager2);
            }
        };

        tempLayout.setText(titleShadowFontCache.getFont(), Constant.gameTitle);

        titleShadowFontCache.setText(tempLayout, 136 / 2 - tempLayout.width / 2, midPointY - 59);
        titleWhiteFontCache.setText(tempLayout, 136 / 2 - tempLayout.width / 2, midPointY - 60);

        Tween.from(titleShadowFontCache, BitmapFontCacheAccessor.POSITION_Y, 1.0f)
                .target(midPointY)
                .ease(TweenEquations.easeOutQuad)
                .setCallback(cb)
                .setCallbackTriggers(TweenCallback.COMPLETE)
                .start(manager2);

        Tween.from(titleWhiteFontCache, BitmapFontCacheAccessor.POSITION_Y, 1.0f)
                .target(midPointY)
                .ease(TweenEquations.easeOutQuad)
                .start(manager2);



    }

    public void prepareAssetFade(float duration) {
        Tween.registerAccessor(BitmapFontCache.class, new BitmapFontCacheAccessor());
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());
        manager2 = new TweenManager();

        Tween.to(whiteFontCache, BitmapFontCacheAccessor.ALPHA, duration)
                .target(1.0f)
                .ease(TweenEquations.easeOutQuad)
                .start(manager2);

        Tween.to(shadowFontCache, BitmapFontCacheAccessor.ALPHA, duration)
                .target(1.0f)
                .ease(TweenEquations.easeOutQuad)
                .start(manager2);

        Tween.to(smallShadowFontCache, BitmapFontCacheAccessor.ALPHA, duration)
                .target(1.0f)
                .ease(TweenEquations.easeOutQuad)
                .start(manager2);

        Tween.to(smallWhiteFontCache, BitmapFontCacheAccessor.ALPHA, duration)
                .target(1.0f)
                .ease(TweenEquations.easeOutQuad)
                .start(manager2);


        Tween.to(nextLevelButton.getButtonUpSprite(), SpriteAccessor.ALPHA, duration)
                .target(1.0f)
                .ease(TweenEquations.easeOutQuad)
                .start(manager2);

        Tween.to(mainMenuButton.getButtonUpSprite(), SpriteAccessor.ALPHA, duration)
                .target(1.0f)
                .ease(TweenEquations.easeOutQuad)
                .start(manager2);

        Tween.to(retryButton.getButtonUpSprite(), SpriteAccessor.ALPHA, duration)
                .target(1.0f)
                .ease(TweenEquations.easeOutQuad)
                .start(manager2);

        Tween.to(facebookButton.getButtonUpSprite(), SpriteAccessor.ALPHA, duration)
                .target(1.0f)
                .ease(TweenEquations.easeOutQuad)
                .start(manager2);
    }

    public void resetAssetAlphas() {
        whiteFontCache.setAlphas(0);
        Color c = whiteFontCache.getColor();
        c.a = 0;
        whiteFontCache.setColor(c);

        shadowFontCache.setAlphas(0);
        c = shadowFontCache.getColor();
        c.a = 0;
        shadowFontCache.setColor(c);

        smallWhiteFontCache.setAlphas(0);
        c = smallWhiteFontCache.getColor();
        c.a = 0;
        smallWhiteFontCache.setColor(c);

        smallShadowFontCache.setAlphas(0);
        c = smallShadowFontCache.getColor();
        c.a = 0;
        smallShadowFontCache.setColor(c);


        nextLevelButton.setAlpha(0);
        mainMenuButton.setAlpha(0);
        retryButton.setAlpha(0);
        facebookButton.setAlpha(0);
    }



}
