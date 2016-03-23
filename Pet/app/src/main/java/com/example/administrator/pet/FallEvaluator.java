package com.example.administrator.pet;

import android.animation.TypeEvaluator;
import android.view.WindowManager;

/**
 * Created by Administrator on 2016/3/19.
 */
public class FallEvaluator implements TypeEvaluator {
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        float startY = (float)startValue;
        float endY = (float)endValue;
        float now ;
        now = (int)(startY + fraction * (endY - startY));

        return now;


    }
}
