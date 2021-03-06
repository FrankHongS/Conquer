package com.hon.conquer.ui.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hon.conquer.BaseFragment;
import com.hon.conquer.R;

/**
 * Created by Frank on 2018/1/26.
 * E-mail:frank_hon@foxmail.com
 */

public class MessageFragment extends BaseFragment {

    private TextView mTextView;

    public MessageFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_message,container,false);
        mTextView=view.findViewById(R.id.tv_fragment);
        mToolbar=view.findViewById(R.id.toolbar);
        mTextView.setText(R.string.bottom_message);
        return view;
    }

    @Override
    protected void setToolbarTitle() {
        mToolbar.setTitle(R.string.bottom_message);
    }
}
