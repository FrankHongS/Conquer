package com.hon.conquer.ui.imagedetail;

import android.os.Bundle;

import com.hon.conquer.R;
import com.hon.conquer.vo.event.ImageDetailEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Frank on 2018/5/27.
 * E-mail:frank_hon@foxmail.com
 */

public class ImageDetailActivity extends AppCompatActivity {

    private ImageDetailFragment mImageDetailFragment;

    private ArrayList<String> mUrlList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        EventBus.getDefault().register(this);

        if (savedInstanceState != null) {
            mImageDetailFragment = (ImageDetailFragment) getSupportFragmentManager().getFragment(savedInstanceState,
                    ImageDetailFragment.class.getSimpleName());
            mUrlList = savedInstanceState.getStringArrayList(ImageDetailFragment.IMAGE_LIST);
        } else {
            mImageDetailFragment = new ImageDetailFragment();
        }
        if (mUrlList != null && !mUrlList.isEmpty()) {
            Bundle bundle = new Bundle();
            bundle.putStringArrayList(ImageDetailFragment.IMAGE_LIST, mUrlList);
            mImageDetailFragment.setArguments(bundle);
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, mImageDetailFragment)
                .commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mImageDetailFragment != null && mImageDetailFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState,
                    ImageDetailFragment.class.getSimpleName(), mImageDetailFragment);
        }

        if (mUrlList != null) {
            outState.putStringArrayList(ImageDetailFragment.IMAGE_LIST, mUrlList);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onImageDetailEvent(ImageDetailEvent event) {
        mUrlList = event.getImageUrl();
    }
}
