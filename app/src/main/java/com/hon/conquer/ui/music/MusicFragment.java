package com.hon.conquer.ui.music;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hon.conquer.BaseFragment;
import com.hon.conquer.R;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

/**
 * Created by Frank on 2018/1/26.
 * E-mail:frank_hon@foxmail.com
 */

public class MusicFragment extends BaseFragment {

    private TextView mTextView;

    public MusicFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_music,container,false);
        mTextView=view.findViewById(R.id.tv_fragment);
        mTextView.setText(R.string.bottom_music);
        return view;
    }

    @Override
    public void setToolbarTitle(Toolbar toolbar) {
        toolbar.setTitle(R.string.bottom_music);
    }
}
