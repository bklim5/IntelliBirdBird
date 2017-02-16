package com.bkbklim.GameObjects;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.bkbklim.Helpers.AssetLoader;

/**
 * Created by bklim on 02/12/15.
 */
public class ChristmasBall extends Scrollable {

    private Circle circle;
    private String text;
    private Vector2 answerPosition;
    private GlyphLayout answerTextLayout;

    public ChristmasBall(float x, float y, int width, int height, float scrollSpeed) {
        super(x, y, width, height, scrollSpeed);
        circle = new Circle();
        answerPosition = new Vector2(0, 0);
        answerTextLayout = new GlyphLayout();
        setAnswerText("100");
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        circle.set(position.x + width / 2, position.y + height / 2, (width )/ 3.0f);
        answerPosition.x = position.x + width / 2 - (answerTextLayout.width / 2) ;
        answerPosition.y = position.y + height / 2  +  answerTextLayout.height / 2 ; //answerTextLayout height is negative
    }

    public void onRestart(float x, float scrollSpeed) {
        velocity.x = scrollSpeed;
        reset(x);
    }

    public Circle getBallCircle() {
        return circle;
    }

    public void setAnswerText(String answer) {
        text = answer;
        answerTextLayout.setText(AssetLoader.smallWhiteFont, text);
        answerPosition.x = position.x + width / 2 - answerTextLayout.width / 2;
        answerPosition.y = position.y + height / 2 - answerTextLayout.height / 2;

    }

    public String getAnswerText() {
        return text;
    }

    public GlyphLayout getAnswerTextLayout() {
        return answerTextLayout;
    }

    public Vector2 getAnswerPosition() {
        return answerPosition;
    }

    public boolean collides(Bird bird) {

        //only need to check when the ball position is less than or touch the bird
        if (position.x < bird.getX() + bird.getWidth()) {
            return Intersector.overlaps(bird.getBoundingCircle(), circle);
        }
        return false;
    }

}
