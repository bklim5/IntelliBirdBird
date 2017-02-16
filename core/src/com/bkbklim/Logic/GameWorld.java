package com.bkbklim.Logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.utils.ScreenUtils;
import com.bkbklim.GameObjects.Bird;
import com.bkbklim.GameObjects.ScrollHandler;
import com.bkbklim.Helpers.AssetLoader;
import com.bkbklim.Helpers.ThirdPartyController;

import java.nio.ByteBuffer;

/**
 * Created by bklim on 29/11/15.
 */
public class GameWorld {

    public enum GameState {
        READY, RUNNING, GAMEOVER, MENU, LEVELUP, LASTLEVEL
    }

    private Bird bird;
    private int midPointY;
    private int score, life;
    private int gameLevel, highestLevel;
    private boolean gameComplete;
    private int ballCollision;
    private GameState currentState;
    private GameRenderer renderer;
    private ScrollHandler scroller;
    private float runTime = 0;

    private QuestionGenerator generator;
    private ThirdPartyController controller;

    public GameWorld(int midPointY, ThirdPartyController controller) {
        currentState = GameState.MENU;
        //currentState = GameState.RUNNING;
        this.midPointY = midPointY;
        bird = new Bird(33, midPointY - 10, 25, 20);

        scroller = new ScrollHandler(this, midPointY + 86);
        generator = new QuestionGenerator(); // default
        highestLevel = AssetLoader.getLevel();
        gameComplete = AssetLoader.getGameCompleted();

        this.controller = controller;
        AssetLoader.playMusic();
    }

    public void update(float delta) {
        runTime += delta;

        switch (currentState) {
            case MENU:
                updateReady(delta, 0);
                break;

            case READY:
                updateReady(delta, 10f);
                break;

            case RUNNING:
                updateRunning(delta);
                break;

            case LEVELUP:
                break;

            case GAMEOVER:
                break;
        }
    }

    public void updateRunning(float delta) {

        bird.update(delta);
        scroller.update(delta);


        //collide with one of the ball, check answer and reset question
        ballCollision = scroller.collides(bird);
        if (ballCollision != -1) {

            //check answer
            if (ballCollision == generator.getAnswerLocation()) {
                AssetLoader.score.play();
                score++;
            } else {
                reduceLife();
            }

            newQuestion();

            //reset ball position
            scroller.reset(180);
        }

        if (score == 10) {
            levelup();
        }

        if (life == 0) {
            gameOver();
        }
    }

    public void reduceLife() {
        life--;
        if (life > 0) {
            AssetLoader.hungry.play(0.7f);
        }

    }

    public void updateReady(float delta, float offset) {
        bird.updateReady(runTime, offset);
        scroller.updateReady(delta);
    }

    public void restart() {
        ready();
    }

    public int getMidPointY() {
        return midPointY;
    }

    public Bird getBird() {
        return bird;
    }

    public ScrollHandler getScroller() {
        return scroller;
    }

    public QuestionGenerator getQuestionGenerator() {
        return generator;
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public int getScore() {
        return score;
    }

    public int getLife() {
        return life;
    }

    public void menu() {
        currentState = GameState.MENU;
        renderer.prepareTransition(0, 0, 0, 1f);
        if (!controller.isAdShown()) {
            controller.showBannerAd();
        }

        renderer.prepareMenuTween();


    }

    public void start() {
        newQuestion();
        currentState = GameState.RUNNING;
        bird.onClick();
        score = 0;
        life = 4;
        scroller.reset(210);

        controller.hideBannerAd();

    }

    public void ready() {
        currentState = GameState.READY;
        renderer.prepareTransition(0, 0, 0, 1f);
        bird.setRotation(0);

        controller.hideBannerAd();
    }

    public void levelup() {
        AssetLoader.levelup.play();

        if (gameLevel == 50) {
            currentState = GameState.LASTLEVEL;
            gameComplete = true;
            AssetLoader.setCompleted(true);
        } else {
            currentState = GameState.LEVELUP;
            setGameLevel(++gameLevel);

            if (gameLevel > highestLevel) {
                highestLevel = gameLevel;
                AssetLoader.setHighestLevel(highestLevel);
            }
        }

        renderer.resetAssetAlphas();
        renderer.prepareAssetFade(0.75f);

        if (!controller.isAdShown()) {
            controller.showBannerAd();
        }
    }

    public void gameOver() {
        AssetLoader.die.play();
        currentState = GameState.GAMEOVER;

        renderer.resetAssetAlphas();
        renderer.prepareAssetFade(0.75f);

        if (!controller.isAdShown()) {
            controller.showBannerAd();
        }
    }

    public void postFacebook() {
        String path = saveScreenshot();
        controller.postFacebook(getGameLevel(), path);
    }

    private String saveScreenshot() {
        try{
            FileHandle fh;
            fh = new FileHandle(Gdx.files.getLocalStoragePath() + "intellibirdbird_screenshot" + ".png");
            Pixmap pixmap = getScreenshot(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
            PixmapIO.writePNG(fh, pixmap);
            pixmap.dispose();
            return fh.toString();

        }catch(Exception e) {
            Gdx.app.log("screenshot", e.toString());
        }

        return "";
    }
    private Pixmap getScreenshot(int x, int y, int w, int h, boolean yDown){

        final Pixmap pixmap = ScreenUtils.getFrameBufferPixmap(x, y, w, h);
        w = pixmap.getWidth();
        h = pixmap.getHeight();
        if(yDown) {
            ByteBuffer pixels = pixmap.getPixels();
            int numBytes = w * h * 4;
            byte[] lines = new byte[numBytes];
            int numBytesPerLine = w * 4;
            for (int i = 0; i < h; i++) {
                pixels.position((h - i - 1) * numBytesPerLine);
                pixels.get(lines, i * numBytesPerLine, numBytesPerLine);
            }

            pixels.clear();
            pixels.put(lines);


        }
        return pixmap;
    }

    public boolean isReady() {
        return currentState == GameState.READY;
    }

    public boolean isRunning() {
        return currentState == GameState.RUNNING;
    }

    public boolean isGameOver() {
        return currentState == GameState.GAMEOVER;
    }

    public boolean isMenu() {
        return currentState == GameState.MENU;
    }

    public boolean isLevelUp() { return currentState == GameState.LEVELUP; }

    public boolean isLastLevel() { return currentState == GameState.LASTLEVEL; }

    public void setRenderer(GameRenderer renderer) {
        this.renderer = renderer;
    }

    public int getGameLevel() {
        return gameLevel;
    }

    public void setGameLevel(int level) {
        gameLevel = level;
        generator.setLevel(gameLevel);
//        scroller.updateBallNumber(generator.getNumOfAnswer());
    }

    public void newQuestion() {
        generator.generateQuestion();
//        scroller.updateBallText(generator.getAnswers());
        scroller.updateBalls(generator.getAnswers());
    }

    public int getHighestLevel() {
        return highestLevel;
    }

    public boolean getGameComplete() {
        return gameComplete;
    }





}
