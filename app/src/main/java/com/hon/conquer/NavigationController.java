package com.hon.conquer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.hon.conquer.ui.message.MessageFragment;
import com.hon.conquer.ui.music.MusicFragment;
import com.hon.conquer.ui.news.NewsFragment;
import com.hon.conquer.ui.video.VideoFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank on 2018/1/26.
 * E-mail:frank_hon@foxmail.com
 */

public class NavigationController {

    private final int mContainerId;
    private final FragmentManager mFragmentManager;

    private NewsFragment mNewsFragment;
    private VideoFragment mVideoFragment;
    private MusicFragment mMusicFragment;
    private MessageFragment mMessageFragment;

    private BaseFragment mCurrentFragment;
    private List<BaseFragment> mFragments=new ArrayList<>();

    public NavigationController(MainActivity mainActivity){
        this.mContainerId=R.id.container;
        mFragmentManager=mainActivity.getSupportFragmentManager();
    }

    public void navigationTo(String target){
        if("news".equals(target)){
            mFragmentManager.beginTransaction()
                    .show(mNewsFragment)
                    .hide(mVideoFragment)
                    .hide(mMusicFragment)
                    .hide(mMessageFragment)
                    .commit();
            setCurrentFragment(mNewsFragment);
        }

        if("video".equals(target)){
            mFragmentManager.beginTransaction()
                    .show(mVideoFragment)
                    .hide(mNewsFragment)
                    .hide(mMusicFragment)
                    .hide(mMessageFragment)
                    .commit();
            setCurrentFragment(mVideoFragment);
        }
        if("music".equals(target)){
            mFragmentManager.beginTransaction()
                    .show(mMusicFragment)
                    .hide(mNewsFragment)
                    .hide(mVideoFragment)
                    .hide(mMessageFragment)
                    .commit();
            setCurrentFragment(mMusicFragment);
        }
        if("message".equals(target)){
            mFragmentManager.beginTransaction()
                    .show(mMessageFragment)
                    .hide(mNewsFragment)
                    .hide(mVideoFragment)
                    .hide(mMusicFragment)
                    .commit();
            setCurrentFragment(mMessageFragment);
        }
    }

    public void initFragments() {
        mNewsFragment=new NewsFragment();
        mVideoFragment=new VideoFragment();
        mMusicFragment=new MusicFragment();
        mMessageFragment=new MessageFragment();

        if(!mNewsFragment.isAdded()){
            mFragmentManager.beginTransaction()
                    .add(mContainerId,mNewsFragment,"NewsFragment")
                    .commit();
        }
        if(!mVideoFragment.isAdded()){
            mFragmentManager.beginTransaction()
                    .add(mContainerId,mVideoFragment,"VideoFragment")
                    .commit();
        }
        if(!mMusicFragment.isAdded()){
            mFragmentManager.beginTransaction()
                    .add(mContainerId,mMusicFragment,"MusicFragment")
                    .commit();
        }
        if(!mMessageFragment.isAdded()){
            mFragmentManager.beginTransaction()
                    .add(mContainerId,mMessageFragment,"MessageFragment")
                    .commit();
        }
    }

    public List<BaseFragment> getFragments(){
        mFragments.add(mNewsFragment);
        mFragments.add(mMusicFragment);
        mFragments.add(mVideoFragment);
        mFragments.add(mMessageFragment);

        return mFragments;
    }

    public BaseFragment getCurrentFragment() {
        return mCurrentFragment;
    }

    public void setCurrentFragment(BaseFragment currentFragment) {
        this.mCurrentFragment = currentFragment;
    }
}
