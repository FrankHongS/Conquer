package com.hon.conquer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Created by Frank on 2018/3/27.
 * E-mail:frank_hon@foxmail.com
 */

public abstract class BaseFragment extends Fragment{

    protected Toolbar mToolbar;
    private DrawerArrowDrawable mSlider;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSlider=new DrawerArrowDrawable(getActivity());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setToolbarTitle();
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public void bindToDrawerLayout(DrawerLayout drawerLayout){
        mToolbar.setNavigationIcon(mSlider);
        mToolbar.setNavigationOnClickListener(
                view->{
                    int drawerLockMode = drawerLayout.getDrawerLockMode(GravityCompat.START);
                    if (drawerLayout.isDrawerVisible(GravityCompat.START)
                            && (drawerLockMode != DrawerLayout.LOCK_MODE_LOCKED_OPEN)) {
                        drawerLayout.closeDrawer(GravityCompat.START);
                    } else if (drawerLockMode != DrawerLayout.LOCK_MODE_LOCKED_CLOSED) {
                        drawerLayout.openDrawer(GravityCompat.START);
                    }
                }
        );
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                setPosition(Math.min(1f, Math.max(0, slideOffset)));
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    private void setPosition(float position) {
        if (position == 1f) {
            mSlider.setVerticalMirror(true);
        } else if (position == 0f) {
            mSlider.setVerticalMirror(false);
        }
        mSlider.setProgress(position);
    }

    protected abstract void setToolbarTitle();
}
