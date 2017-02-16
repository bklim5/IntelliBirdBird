package com.bkbklim.TweenAccessor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;

import aurelienribon.tweenengine.TweenAccessor;

/**
 * Created by bklim on 07/12/15.
 */
public class BitmapFontCacheAccessor implements TweenAccessor<BitmapFontCache>{

    public static final int ALPHA = 1;
    public static final int POSITION_Y = 2;

    @Override
    public int getValues(BitmapFontCache target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case ALPHA:
                returnValues[0] = target.getColor().a;
                return 1;

            case POSITION_Y:
                returnValues[0] = target.getY();
                return 1;

            default:
                return 0;

        }
    }

    @Override
    public void setValues(BitmapFontCache target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case (ALPHA):
                target.setAlphas(newValues[0]);
                Color c = target.getColor();
                c.a = newValues[0];
                target.setColor(c);
                break;

            case (POSITION_Y):
                target.setPosition(target.getX(), newValues[0]);
                break;

        }
    }

}
