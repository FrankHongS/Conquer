package com.hon.conquer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Frank on 2018/3/27.
 * E-mail:frank_hon@foxmail.com
 */

public abstract class BaseFragment extends Fragment{

    protected FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public abstract void setToolbarTitle(Toolbar toolbar);

    public void setFab(FloatingActionButton fab){
        this.fab=fab;
    }
}
