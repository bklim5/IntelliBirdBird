package com.bkbklim.Helpers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by bklim on 01/12/15.
 */
public class SimpleButton {

    protected float x, y, height, width;
    protected TextureRegion buttonUp;
    protected TextureRegion buttonDown;
    private Rectangle bounds;
    private boolean isPressed;
    private float alpha;
    protected Sprite buttonUpSprite, buttonDownSprite;

    public SimpleButton(float x, float y, float width, float height, TextureRegion buttonUp, TextureRegion buttonDown) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.buttonUp = buttonUp;
        this.buttonDown = buttonDown;
        isPressed = false;
        bounds = new Rectangle(x, y, width, height);
        buttonUpSprite = new Sprite(buttonUp);
        buttonUpSprite.setPosition(x, y);
        buttonUpSprite.setSize(width, height);

    }

    public boolean isClicked(int screenX, int screenY) {
        return bounds.contains(screenX, screenY);
    }

    public void drawButton(SpriteBatch batcher) {
        if (isPressed) {
            batcher.draw(buttonDown, x, y, width, height);
        } else {
            batcher.draw(buttonUp, x, y, width, height);
        }
    }

    public void drawButtonAlpha(SpriteBatch batcher) {
        if (isPressed) {
            batcher.draw(buttonDown, x, y, width, height);
        } else {
            buttonUpSprite.draw(batcher, buttonUpSprite.getColor().a);
        }
    }

    public boolean isTouchDown(int screenX, int screenY) {
        if (bounds.contains(screenX, screenY)) {
            isPressed = true;
            return true;
        }

        return false;
    }

    public boolean isTouchUp(int screenX, int screenY) {

        //only if it is in pressed state, we change it back to false
        if (bounds.contains(screenX, screenY) && isPressed) {
            isPressed = false;
            return true;
        }

        isPressed = false;
        return false;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        Color c = buttonUpSprite.getColor();
        c.a = alpha;
        buttonUpSprite.setColor(c);
    }

    public Sprite getButtonUpSprite() {
        return buttonUpSprite;
    }
}
