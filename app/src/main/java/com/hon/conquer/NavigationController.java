package com.hon.conquer;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hon.conquer.ui.MainActivity;
import com.hon.conquer.ui.message.MessageFragment;
import com.hon.conquer.ui.music.MusicFragment;
import com.hon.conquer.ui.news.NewsFragment;
import com.hon.conquer.ui.video.VideoFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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
    private List<Fragment> mFragments=new ArrayList<>();

    private Toolbar mToolbar;
    private FloatingActionButton mFab;

    public NavigationController(MainActivity mainActivity, int containerId, Toolbar toolbar,
                                FloatingActionButton fab, String firstFragment){
        this.mContainerId=containerId;
        this.mFragmentManager=mainActivity.getSupportFragmentManager();
        this.mToolbar=toolbar;
        this.mFab=fab;

        initFragments(firstFragment);
    }

    private void initFragments(String firstFragment) {

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

        navigationTo(firstFragment);
    }

    public void navigationTo(String target){
        BaseFragment currentFragment=null;

        if("news".equals(target)){
            mFragmentManager.beginTransaction()
                    .show(mNewsFragment)
                    .hide(mVideoFragment)
                    .hide(mMusicFragment)
                    .hide(mMessageFragment)
                    .commit();
            currentFragment=mNewsFragment;
        }else if("video".equals(target)){
            mFragmentManager.beginTransaction()
                    .show(mVideoFragment)
                    .hide(mNewsFragment)
                    .hide(mMusicFragment)
                    .hide(mMessageFragment)
                    .commit();
            currentFragment=mVideoFragment;
        }else if("music".equals(target)){
            mFragmentManager.beginTransaction()
                    .show(mMusicFragment)
                    .hide(mNewsFragment)
                    .hide(mVideoFragment)
                    .hide(mMessageFragment)
                    .commit();
            currentFragment=mMusicFragment;
        }else if("message".equals(target)){
            mFragmentManager.beginTransaction()
                    .show(mMessageFragment)
                    .hide(mNewsFragment)
                    .hide(mVideoFragment)
                    .hide(mMusicFragment)
                    .commit();
            currentFragment=mMessageFragment;
        }else{
            throw new RuntimeException("no such Fragment error");
        }

        if(currentFragment!=null){
            setCurrentFragment(currentFragment);
            currentFragment.setToolbarTitle(mToolbar);
            currentFragment.setFab(mFab);
        }
    }

    public List<Fragment> getFragments(){
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
