package com.bkbklim.GameObjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.bkbklim.Helpers.AssetLoader;

/**
 * Created by bklim on 29/11/15.
 */
public class Bird {
    private Vector2 position, velocity, acceleration;
    private int width, height, rotation;
    private float originalY;
    private Circle boundingCircle;
    private boolean isAlive;
    private String birdThought;

    public Bird(int x, int y, int width, int height) {
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, 460);

        this.width = width;
        this.height = height;
        originalY = y;

        boundingCircle = new Circle();
        isAlive = true;

        birdThought = "+";
    }

    public void onClick() {
        if (isAlive) {
            velocity.y = -140;
            AssetLoader.flap.play();
        }
    }

    public void update(float delta) {
        velocity.add(acceleration.cpy().scl(delta));

        //maximum speed check
        if (velocity.y > 200) {
            velocity.y = 200;
        }

        //ceiling check / hit the wood banner
        if (position.y < 10) {
            position.y = 10;
        }

        //ground check
        if (position.y >= originalY + 86) {
            position.y = originalY + 86;
            rotation -= 600 * delta;
            if (rotation < -20) {
                rotation = -20;
            }
        }

        position.add(velocity.cpy().scl(delta));

        // Set the circle's center to be (9, 6) with respect to the bird.
        // Set the circle's radius to be 6.5f;
        boundingCircle.set(position.x + 9, position.y + 6, 6.5f);

        // Rotate counterclockwise
        if (velocity.y < 0) {
            rotation -= 600 * delta;

            if (rotation < -20) {
                rotation = -20;
            }
        }

        // Rotate clockwise
        if (isFalling() || !isAlive) {
            rotation += 480 * delta;
            if (rotation > 90) {
                rotation = 90;
            }

        }

    }

    public boolean shouldntFlap() {
        return velocity.y > 70 || !isAlive;
    }

    public void die() {
        isAlive = false;
        velocity.y = 0;
    }

    public void decelerate() {
        acceleration.y = 0;
    }

    public void onRestart(int y) {
        rotation = 0;
        position.y = y;
        velocity.x = 0;
        velocity.y = 0;
        acceleration.x = 0;
        acceleration.y = 0;
        isAlive = true;
    }

    public void updateReady(float runTime, float offset) {
        position.y = 2 * (float) Math.sin(7 * runTime) + originalY - offset;
        updateBirdThought(runTime);
    }

    public boolean isFalling() {
        return velocity.y > 110;
    }


    public Circle getBoundingCircle() {
        return boundingCircle;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public String getBirdThought() {
        return birdThought;
    }

    public void updateBirdThought(float runTime) {
        int operator = (int) (runTime % 4);

        switch (operator) {
            case 0:
                birdThought = "+";
                break;

            case 1:
                birdThought = "-";
                break;

            case 2:
                birdThought = "x";
                break;

            case 3:
                birdThought = "/";
                break;

        }
    }

    public boolean isAlive() {
        return isAlive;
    }


}
