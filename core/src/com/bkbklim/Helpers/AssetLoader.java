package com.bkbklim.Helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by bklim on 28/11/15.
 */
public class AssetLoader {

    public static Texture birdUpTexture, birdDownTexture, birdTexture;
    public static Texture texture, logoTexture;

    public static TextureRegion logo;
    public static TextureRegion birdUp, birdDown, bird;
    public static TextureRegion playButtonUp, playButtonDown,
                                continueButtonUp, continueButtonDown,
                                mainMenuButtonUp, mainMenuButtonDown,
                                retryButtonUp, retryButtonDown,
                                nextLevelButtonUp, nextLevelButtonDown,
                                volumeButton, mutedButton,
                                facebookButtonUp, facebookButtonDown;
    public static TextureRegion woodBanner, christmasBall, tree;
    public static TextureRegion bg, cloudThought;
    public static TextureRegion medalLevel1, medalLevel10, medalLevel30, medalLevel50;
    public static TextureRegion medalLevel1PlaceHolder, medalLevel10PlaceHolder, medalLevel30PlaceHolder, medalLevel50PlaceHolder;

    public static Animation birdAnimation;

    public static BitmapFont font, shadow, whiteFont, smallWhiteFont, smallShadowFont, mediumGreenFont, mediumShadowFont, smallGreenFont;

    public static Sound flap, score, levelup, die, hungry;

    public static Music bgMusic;

    private static Preferences prefs;


    public static void load() {

        texture = new Texture("img/texture.png");

        //menu play buttons
        playButtonUp = new TextureRegion(texture, 0, 57, 29, 16);
        playButtonDown = new TextureRegion(texture, 29, 57, 29, 16);
        playButtonUp.flip(false, true);
        playButtonDown.flip(false, true);

        //continue to highest level button
        continueButtonUp = new TextureRegion(texture, 58, 57, 43, 16);
        continueButtonDown = new TextureRegion(texture, 101, 57, 43, 16);
        continueButtonUp.flip(false, true);
        continueButtonDown.flip(false, true);

        //retry button
        retryButtonUp = new TextureRegion(texture, 0, 73, 31, 16);
        retryButtonDown = new TextureRegion(texture, 31, 73, 31, 16);
        retryButtonUp.flip(false, true);
        retryButtonDown.flip(false, true);

        //back to main menu button
        mainMenuButtonUp = new TextureRegion(texture, 62, 73, 43, 16);
        mainMenuButtonDown = new TextureRegion(texture, 105, 73, 43, 16);
        mainMenuButtonUp.flip(false, true);
        mainMenuButtonDown.flip(false, true);

        //next level button
        nextLevelButtonUp = new TextureRegion(texture, 0, 89, 43, 16);
        nextLevelButtonDown = new TextureRegion(texture, 43, 89, 43, 16);
        nextLevelButtonUp.flip(false, true);
        nextLevelButtonDown.flip(false, true);

        //volume button
        mutedButton = new TextureRegion(texture, 0, 105, 23, 23);
        volumeButton = new TextureRegion(texture, 23, 105, 23, 23);
        mutedButton.flip(false, true);
        volumeButton.flip(false, true);

        //facebook share button
        facebookButtonUp = new TextureRegion(texture, 136, 0, 43, 16);
        facebookButtonDown = new TextureRegion(texture, 179, 0, 43, 16);
        facebookButtonUp.flip(false, true);
        facebookButtonDown.flip(false, true);



        //logo texture
//        logoTexture = new Texture("logo.png");
//        logo = new TextureRegion(logoTexture, 0, 0, 512, 114);

        logoTexture = new Texture("img/smileProduction.png");
        logo = new TextureRegion(logoTexture, 0, 0, 760, 300);

        //background
        bg = new TextureRegion(new Texture("bg/bg.jpg"));
        bg.flip(false, true);

        //bird thought
        cloudThought = new TextureRegion(texture, 86, 90, 22, 11);
        cloudThought.flip(false, true);

        //medals
        medalLevel1 = new TextureRegion(texture, 48, 105, 15, 20);
        medalLevel10 = new TextureRegion(texture, 63, 105, 15, 20);
        medalLevel30 = new TextureRegion(texture, 78, 105, 15, 20);
        medalLevel50 = new TextureRegion(texture, 93, 105, 15, 20);
        medalLevel1.flip(false, true);
        medalLevel10.flip(false, true);
        medalLevel30.flip(false, true);
        medalLevel50.flip(false, true);

        medalLevel1PlaceHolder = new TextureRegion(texture, 108, 105, 15, 20);
        medalLevel10PlaceHolder = new TextureRegion(texture, 123, 105, 15, 20);
        medalLevel30PlaceHolder = new TextureRegion(texture, 138, 105, 15, 20);
        medalLevel50PlaceHolder = new TextureRegion(texture, 153, 105, 15, 20);
        medalLevel1PlaceHolder.flip(false, true);
        medalLevel10PlaceHolder.flip(false, true);
        medalLevel30PlaceHolder.flip(false, true);
        medalLevel50PlaceHolder.flip(false, true);

        //game objects

        //bird textures and animation
        birdTexture = new Texture("img/christmasBirdNeutral.png");
        birdUpTexture = new Texture("img/christmasBirdUp.png");
        birdDownTexture = new Texture("img/christmasBirdDown.png");

        bird = new TextureRegion(birdTexture);
        birdUp = new TextureRegion(birdUpTexture);
        birdDown = new TextureRegion(birdDownTexture);
        bird.flip(false, true);
        birdUp.flip(false, true);
        birdDown.flip(false, true);

        TextureRegion[] birds = {birdDown, bird, birdUp};
        birdAnimation = new Animation(0.1f, birds);
        birdAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        //wood banner for question
        woodBanner = new TextureRegion(texture, 136, 21, 50, 22);

        //christmas ball for answer option
//        christmasBall = new TextureRegion(texture, 189, 21, 22, 22);
        christmasBall = new TextureRegion(new Texture("img/ball1.png"));
        christmasBall.flip(false, true);

        //tree to hold christmas ball
        tree = new TextureRegion(new Texture("img/tree.png"));
        tree.flip(false, true);


        // font
        font = new BitmapFont(Gdx.files.internal("text/text.fnt"));
        shadow = new BitmapFont(Gdx.files.internal("text/shadow.fnt"));
        whiteFont = new BitmapFont(Gdx.files.internal("text/whitetext.fnt"));
        smallWhiteFont = new BitmapFont(Gdx.files.internal("text/whitetext.fnt"));
        smallShadowFont = new BitmapFont(Gdx.files.internal("text/shadow.fnt"));
        mediumShadowFont = new BitmapFont(Gdx.files.internal("text/shadow.fnt"));
        mediumGreenFont = new BitmapFont(Gdx.files.internal("text/text.fnt"));
        smallGreenFont = new BitmapFont(Gdx.files.internal("text/text.fnt"));
        font.getData().setScale(.25f, -.25f);
        shadow.getData().setScale(.25f, -.25f);
        whiteFont.getData().setScale(.25f, -.25f);
        smallWhiteFont.getData().setScale(.15f, -.15f);
        smallShadowFont.getData().setScale(.15f, -.15f);
        mediumGreenFont.getData().setScale(.2f, -.2f);
        mediumShadowFont.getData().setScale(.2f, -.2f);
        smallGreenFont.getData().setScale(.15f, -.15f);

        //sound
        flap = Gdx.audio.newSound(Gdx.files.internal("music/flap.wav"));
        hungry = Gdx.audio.newSound(Gdx.files.internal("music/hungry.mp3"));
        levelup = Gdx.audio.newSound(Gdx.files.internal("music/levelup.mp3"));
        die = Gdx.audio.newSound(Gdx.files.internal("music/die.wav"));
        score = Gdx.audio.newSound(Gdx.files.internal("music/score.wav"));

        bgMusic = Gdx.audio.newMusic(Gdx.files.internal("music/cnymedley.mp3"));
        bgMusic.setLooping(true);
        bgMusic.setVolume(0.35f);


        prefs = Gdx.app.getPreferences("com.bkbklim.IntelliBirdBird");

        if (!prefs.contains("level")) {
            prefs.putInteger("level", 1);
        }

        if (!prefs.contains("completed")) {
            prefs.putBoolean("completed", false);
        }

    }

    public static void muteMusic() {
        bgMusic.stop();
    }

    public static void playMusic() {
        bgMusic.play();
    }

    public static void setHighestLevel(int level) {
        prefs.putInteger("level", level);
        prefs.flush();
    }

    public static void setCompleted(boolean completed) {
        prefs.putBoolean("completed", completed);
        prefs.flush();
    }

    public static int getLevel() {
        return prefs.getInteger("level", 1);
    }

    public static boolean getGameCompleted() {
        return prefs.getBoolean("completed", false);
    }

    public static void dispose() {
        logoTexture.dispose();
        font.dispose();
        shadow.dispose();
        whiteFont.dispose();
        bgMusic.dispose();

    }
}
