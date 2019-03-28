package com.hon.conquer.ui.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

/**
 * Created by Frank on 2018/2/2.
 * E-mail:frank_hon@foxmail.com
 */

public class FloatActionButtonBehavior extends FloatingActionButton.Behavior{
    public FloatActionButtonBehavior() {
    }

    public FloatActionButtonBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                                       @NonNull FloatingActionButton child,
                                       @NonNull View directTargetChild,
                                       @NonNull View target, int axes, int type) {
        return ViewCompat.SCROLL_AXIS_VERTICAL==axes||super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type);
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                               @NonNull FloatingActionButton child,
                               @NonNull View target, int dxConsumed,
                               int dyConsumed, int dxUnconsumed,
                               int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target,
                dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
        if(dyConsumed>0&&child.getVisibility()==View.VISIBLE){
            child.hide(new FloatingActionButton.OnVisibilityChangedListener() {
                @Override
                public void onHidden(FloatingActionButton fab) {
                    fab.setVisibility(View.INVISIBLE);
                }
            });
        }else if(dyConsumed<0&&child.getVisibility()!=View.VISIBLE){
            child.show();
        }
    }
}
