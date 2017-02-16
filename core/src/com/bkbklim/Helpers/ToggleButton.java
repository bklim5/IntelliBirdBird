package com.bkbklim.Helpers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by bklim on 12/12/15.
 */
public class ToggleButton extends SimpleButton {
    private boolean isOn;
    public ToggleButton(float x, float y, float width, float height, TextureRegion buttonOn, TextureRegion buttonOff) {
        super(x, y, width, height, buttonOn, buttonOff);
        isOn = true;
    }

//    public void setButtonTexture(TextureRegion regionUp, TextureRegion regionDown) {
//        buttonUp.setRegion(regionUp);
//        buttonDown.setRegion(regionDown);
//    }

    public void drawButton(SpriteBatch batcher) {
        if (isOn) {
            batcher.draw(buttonUp, x, y, width, height);
        } else {
            batcher.draw(buttonDown, x, y, width, height);
        }
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean onOrOff) {
        isOn = onOrOff;
    }


}
