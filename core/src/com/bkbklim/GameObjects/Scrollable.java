package com.bkbklim.GameObjects;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by bklim on 02/12/15.
 */
public class Scrollable {

    protected Vector2 position;
    protected Vector2 velocity;
    protected int width;
    protected int height;
    protected boolean isScrolledLeft;

    public Scrollable(float x, float y, int width, int height, float scrollSpeed) {

        this.width = width;
        this.height = height;
        velocity = new Vector2(scrollSpeed, 0);
        position = new Vector2(x, y);
        isScrolledLeft = false;
    }

    public void setScrollSpeed(float scrollSpeed) {
        velocity.x = scrollSpeed;

    }

    public void update(float delta) {
        position.add(velocity.cpy().scl(delta));

        if (position.x + width < 0) {
            isScrolledLeft = true;
        }
    }

    public void reset(float newX) {
        position.x = newX;
        isScrolledLeft = false;
    }

    public void stop() {
        velocity.x = 0;
    }

    public boolean isScrolledLeft() {
        return isScrolledLeft;
    }

    public float getTailX() {
        return position.x + width;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
