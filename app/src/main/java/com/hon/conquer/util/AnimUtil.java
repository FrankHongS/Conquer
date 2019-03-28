package com.hon.conquer.util;

import android.animation.ValueAnimator;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

/**
 * Created by Frank on 2018/2/4.
 * E-mail:frank_hon@foxmail.com
 */

public class AnimUtil {
    private ValueAnimator mHideBottomViewValueAnimator;
    private ValueAnimator mShowBottomViewValueAnimator;

    private int mHideCount = 0;
    private int mShowCount = 0;

    public void hideBottomNavigationView(BottomNavigationView view, long animTime) {
        if (mHideBottomViewValueAnimator == null)
            mHideBottomViewValueAnimator = ValueAnimator.ofFloat(view.getY(),
                    view.getY() + view.getHeight());
        if (!mHideBottomViewValueAnimator.isStarted()) {
            mHideCount++;
            if (mHideCount >= 2)
                return;
            mHideBottomViewValueAnimator.setDuration(animTime);
            mHideBottomViewValueAnimator.setInterpolator(new LinearOutSlowInInterpolator());
            mHideBottomViewValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    view.setY((Float) animation.getAnimatedValue());
                }
            });
            mHideBottomViewValueAnimator.start();
            mShowCount = 0;
        }
    }

    public void showBottomNavigationView(BottomNavigationView view,long animTime){
        if(mShowBottomViewValueAnimator==null)
            mShowBottomViewValueAnimator=ValueAnimator.ofFloat
                    (view.getY(),view.getY()-view.getHeight());

        if(!mShowBottomViewValueAnimator.isStarted()){
            mShowCount++;
            if(mShowCount>=2)
                return;
            mShowBottomViewValueAnimator.setDuration(animTime);
            mShowBottomViewValueAnimator.setInterpolator(new LinearOutSlowInInterpolator());
            mShowBottomViewValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    view.setY((Float) animation.getAnimatedValue());
                }
            });
            mShowBottomViewValueAnimator.start();
            mHideCount=0;
        }
    }
}
