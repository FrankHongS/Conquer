package com.hon.conquer;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.hon.conquer.util.ToastUtil;

import timber.log.Timber;

/**
 * Created by Frank on 2018/2/1.
 * E-mail:frank_hon@foxmail.com
 */

public class BottomNavigationViewBehavior
        extends CoordinatorLayout.Behavior<BottomNavigationView>{

    private static final String TAG="BottomNavigationViewBehavior";
    
    private Rect mTempRect;

    public BottomNavigationViewBehavior() {
    }

    public BottomNavigationViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

//    @Override
//    public boolean layoutDependsOn(CoordinatorLayout parent, BottomNavigationView child, View dependency) {
//        return dependency instanceof FrameLayout;
//    }
//
//    @Override
//    public boolean onDependentViewChanged(CoordinatorLayout parent, BottomNavigationView child, View dependency) {
//        ToastUtil.showToast("onDependentViewChanged");
//        return super.onDependentViewChanged(parent, child, dependency);
//    }

//    @Override
//    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull BottomNavigationView child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
//        Timber.d("onStartNestedScroll");
//        return axes== ViewCompat.SCROLL_AXIS_VERTICAL||super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type);
//    }

//    @Override
//    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
//                               @NonNull BottomNavigationView child,
//                               @NonNull View target, int dxConsumed,
//                               int dyConsumed, int dxUnconsumed,
//                               int dyUnconsumed, int type) {
//        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
//        Timber.d("onNestedScroll");
//        ToastUtil.showToast("onNestedScroll");
//        if(dyConsumed>0){
//            ViewCompat.offsetTopAndBottom(child,child.getHeight());
//        }else{
//            ViewCompat.offsetTopAndBottom(child,-child.getHeight());
//        }
//    }

//    @Override
//    public boolean onNestedFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull BottomNavigationView child, @NonNull View target, float velocityX, float velocityY, boolean consumed) {
//        Log.d(TAG, "onNestedFling: ");
//        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
//    }

    float preX;
    float preY;
    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, 
                                         BottomNavigationView child,
                                         MotionEvent ev) {
        Log.d(TAG, "onInterceptTouchEvent: ");
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                preX=ev.getX();
                preY=ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if(Math.abs(ev.getY()-preY)>Math.abs(ev.getX()-preX)){
                    if(ev.getY()-preY<0&&!onTouchEvent(parent, child, ev))
                        child.setVisibility(View.GONE);
//                        ObjectAnimator.ofFloat(child,"Y",-child.getHeight(),0)
//                                      .setDuration(100)
//                                      .start();

//                    else
//                        child.setVisibility(View.VISIBLE);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(parent, child, ev);
    }

    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, BottomNavigationView child, MotionEvent ev) {
        return super.onTouchEvent(parent, child, ev);
    }
}
