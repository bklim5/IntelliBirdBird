package com.bkbklim.Helpers;

import com.badlogic.gdx.InputProcessor;
import com.bkbklim.GameObjects.Bird;
import com.bkbklim.Logic.GameWorld;

/**
 * Created by bklim on 29/11/15.
 */
public class InputHandler implements InputProcessor{

    private GameWorld world;
    private Bird bird;
    private float scaleFactorX, scaleFactorY;
    private SimpleButton playButton, continueButton, mainMenuButon, retryButton, nextLevelButton, facebookButton;
    private ToggleButton volumeButton;

    public InputHandler(GameWorld world, float scaleFactorX, float scaleFactorY) {
        this.world = world;
        this.scaleFactorX = scaleFactorX;
        this.scaleFactorY = scaleFactorY;

        bird = world.getBird();

        int midPointY = world.getMidPointY();

        playButton = new SimpleButton(136/4 - (AssetLoader.playButtonUp.getRegionWidth() / 2), midPointY + 55, 29 ,16, AssetLoader.playButtonUp, AssetLoader.playButtonDown);
        continueButton = new SimpleButton(136*3/4 - (AssetLoader.continueButtonUp.getRegionWidth() / 2), midPointY + 55, 43 ,16, AssetLoader.continueButtonUp, AssetLoader.continueButtonDown);
        nextLevelButton = new SimpleButton(136/4 - (AssetLoader.nextLevelButtonUp.getRegionWidth() / 2), midPointY + 30, 43, 16, AssetLoader.nextLevelButtonUp, AssetLoader.nextLevelButtonDown);
        mainMenuButon = new SimpleButton(136*3/4 - (AssetLoader.mainMenuButtonUp.getRegionWidth() / 2), midPointY + 30, 43, 16, AssetLoader.mainMenuButtonUp, AssetLoader.mainMenuButtonDown);
        retryButton = new SimpleButton(136/4 - (AssetLoader.retryButtonUp.getRegionWidth() / 2), midPointY + 30, 31, 16, AssetLoader.retryButtonUp, AssetLoader.retryButtonDown);
        volumeButton = new ToggleButton(136*7/ 8 - (AssetLoader.volumeButton.getRegionWidth() / 2), 5, 18, 18, AssetLoader.volumeButton, AssetLoader.mutedButton);
        facebookButton = new SimpleButton(136/2 - (AssetLoader.facebookButtonUp.getRegionWidth() /2) , midPointY + 50, 43, 16, AssetLoader.facebookButtonUp, AssetLoader.facebookButtonDown);
    }

    public SimpleButton getPlayButton() {
        return playButton;
    }
    public SimpleButton getRetryButton() {
        return retryButton;
    }
    public SimpleButton getContinueButton() {
        return continueButton;
    }

    public SimpleButton getNextLevelButton() {
        return nextLevelButton;
    }

    public SimpleButton getMainMenuButon() {
        return mainMenuButon;
    }

    public ToggleButton getVolumeButton() {
        return volumeButton;
    }

    public SimpleButton getFacebookButton() { return facebookButton; }

    private int scaleX(int screenX) {
        return (int) (screenX / scaleFactorX);
    }

    private int scaleY(int screenY) {
        return (int) (screenY / scaleFactorY);
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);

        if (world.isMenu()) {
            playButton.isTouchDown(screenX, screenY);
            continueButton.isTouchDown(screenX, screenY);
            volumeButton.isTouchDown(screenX, screenY);


        } else if (world.isRunning()) {
            bird.onClick();

        } else if (world.isReady()) {
            world.start();

        } else if (world.isLevelUp()) {
            nextLevelButton.isTouchDown(screenX, screenY);
            mainMenuButon.isTouchDown(screenX, screenY);
            facebookButton.isTouchDown(screenX, screenY);


        } else if (world.isGameOver() || world.isLastLevel()) {
            retryButton.isTouchDown(screenX, screenY);
            mainMenuButon.isTouchDown(screenX, screenY);

        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);

        if (world.isMenu()) {
            if (playButton.isTouchUp(screenX, screenY)) {
                world.ready();
                world.setGameLevel(1);
                return true;

            } else if (continueButton.isTouchUp(screenX, screenY)) {
                //if button is clicked, set game level to highest level
                world.ready();
                world.setGameLevel(world.getHighestLevel());
                return true;

            } else if (volumeButton.isTouchUp(screenX, screenY)) {
                //volume is on , mute it
                if (volumeButton.isOn()) {
                    volumeButton.setOn(false);
                    AssetLoader.muteMusic();
                } else {
                    volumeButton.setOn(true);
                    AssetLoader.playMusic();
                }
            }

        } else if (world.isLevelUp()) {
            if (mainMenuButon.isTouchUp(screenX, screenY)) {
                world.menu();

            } else if (nextLevelButton.isTouchUp(screenX, screenY)) {
                world.ready();
                return true;

            } else if (facebookButton.isTouchUp(screenX, screenY)) {
                //do facebook sharing action here
                world.postFacebook();

            }

        } else if (world.isGameOver() || world.isLastLevel()) {
            if (mainMenuButon.isTouchUp(screenX, screenY)) {
                world.menu();

            } else if (retryButton.isTouchUp(screenX, screenY)) {
                world.ready();
                return true;

            }

        }
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
