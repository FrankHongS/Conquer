package com.hon.conquer.base;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

/**
 * Created by Frank on 2018/3/27.
 * E-mail:frank_hon@foxmail.com
 */

public abstract class BaseFragment extends Fragment {

    protected FloatingActionButton fab;

    public abstract void setToolbarTitle(Toolbar toolbar);

    public void setFab(FloatingActionButton fab){
        this.fab=fab;
    }
}
