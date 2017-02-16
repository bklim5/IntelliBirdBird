package com.bkbklim.GameObjects;

import com.bkbklim.Logic.GameWorld;

import java.util.ArrayList;

/**
 * Created by bklim on 02/12/15.
 */
public class ScrollHandler {

    private ChristmasBall ball1, ball2;
    private ArrayList<ChristmasBall> balls;
    private Tree tree;
    private Ground frontFloorGround, backFloorGround;
    private GameWorld gameWorld;
    private float groundY;
    private int scrollSpeed = -60;

    public ScrollHandler(GameWorld gameWorld, float groundY) {
        this.gameWorld = gameWorld;
        this.groundY = groundY;
        ball1 = new ChristmasBall(210, 25, 30, 30, scrollSpeed);
        ball2 = new ChristmasBall(210, 100, 30, 30, scrollSpeed);

        frontFloorGround = new Ground(0, groundY, 143, 20, scrollSpeed);
        backFloorGround = new Ground(frontFloorGround.getTailX(), groundY, 143, 20, scrollSpeed);

        balls = new ArrayList<ChristmasBall>();

        //tree width 50, y coordinates below woodBanner
        tree = new Tree(220, 25, 30, (int) groundY - 22, scrollSpeed);
    }

    public void updateBallText(ArrayList<Integer> options) {
        for (int i = 0 ; i < balls.size() ; i++ ) {
            balls.get(i).setAnswerText(Integer.toString(options.get(i)));
        }
    }

    public void updateBallNumber(int number) {
        balls.clear();

        float availableSpace = groundY - 22;
        float gap = (availableSpace - number * 30) / number;

        float positionY = 30;
        for (int i = 0; i < number ; i++) {
            ChristmasBall ball = new ChristmasBall(210, positionY, 30, 30, scrollSpeed);
            balls.add(ball);
            positionY = positionY + gap + ball.getHeight();
        }
    }

    public void updateBalls(ArrayList<Integer> options) {
        int numberOfBall = options.size();
        balls.clear();

        float availableSpace = groundY - 22;
        float gap = (availableSpace - numberOfBall * 30) / numberOfBall;

        float positionY = 27;
        for (int i = 0; i < numberOfBall ; i++) {
            ChristmasBall ball = new ChristmasBall(210, positionY, 30, 30, scrollSpeed);
            ball.setAnswerText(Integer.toString(options.get(i)));
            balls.add(ball);
            positionY = positionY + gap + ball.getHeight();
        }

    }

    public void setScrollSpeed(int scrollSpeed) {
        this.scrollSpeed = scrollSpeed;
        for (ChristmasBall ball : balls) {
            ball.setScrollSpeed(scrollSpeed);
        }

        frontFloorGround.setScrollSpeed(scrollSpeed);
        backFloorGround.setScrollSpeed(scrollSpeed);
    }

    public void updateReady(float delta) {
        updateGround(delta);

    }

    public void update(float delta) {


        tree.update(delta);

        for (ChristmasBall ball : balls) {
            ball.update(delta);
        }

        //the balls past the left screen
        if (balls.get(0).isScrolledLeft()) {
            gameWorld.reduceLife();
            reset(210);
            gameWorld.newQuestion();
        }


        updateGround(delta);
    }

    private void updateGround(float delta) {
        frontFloorGround.update(delta);
        backFloorGround.update(delta);

        if (frontFloorGround.isScrolledLeft()) {
            frontFloorGround.reset(backFloorGround.getX());

        } else if (backFloorGround.isScrolledLeft()) {
            backFloorGround.reset(frontFloorGround.getX());
        }
    }

    public int collides(Bird bird) {
        for (int i = 0 ; i < balls.size() ; i++) {
            if (balls.get(i).collides(bird)) {
                return i;
            }
        }

        return -1;
    }

    public void reset(float x) {

        for (ChristmasBall ball : balls) {
            ball.reset(x);
        }
        tree.reset(x);
    }


    public void stop() {
        for (ChristmasBall ball : balls) {
            ball.stop();
        }
        frontFloorGround.stop();
        backFloorGround.stop();

    }

    public ChristmasBall getBall1() {
        return ball1;
    }


    public ChristmasBall getBall2() {
        return ball2;
    }

    public ArrayList<ChristmasBall> getBalls() {
        return balls;
    }

    public Tree getTree() {
        return tree;
    }


}
