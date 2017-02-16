package com.bkbklim.GameObjects;

/**
 * Created by bklim on 02/12/15.
 */
public class Ground extends Scrollable {
    public Ground(float x, float y, int width, int height, float scrollSpeed) {
        super(x, y, width, height, scrollSpeed);
    }

    public void onRestart(float x, float scrollSpeed) {
        position.x = x;
        velocity.x = scrollSpeed;
    }

}
