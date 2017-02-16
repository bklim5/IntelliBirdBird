package com.bkbklim.TweenAccessor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;

import aurelienribon.tweenengine.TweenAccessor;

/**
 * Created by bklim on 29/11/15.
 */
public class SpriteAccessor implements TweenAccessor<Sprite>{

    public static final int ALPHA = 1;

    @Override
    public void setValues(Sprite target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case (ALPHA):
//                target.setColor(1,1,1, newValues[0]);
                target.setAlpha(newValues[0]);
                Color c = target.getColor();
                c.a = newValues[0];
                target.setColor(c);

                break;

        }
    }

    @Override
    public int getValues(Sprite target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case ALPHA:
                returnValues[0] = target.getColor().a;
                return 1;

            default:
                return 0;

        }

    }
}
