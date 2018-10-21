package com.hon.conquer.ui.common;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;

import com.hon.conquer.ui.imagedetail.ImageDetailActivity;
import com.hon.conquer.ui.imagedetail.ImageDetailFragment;
import com.hon.conquer.vo.event.ImageDetailEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Frank on 2018/3/25.
 * E-mail:frank_hon@foxmail.com
 */

public class MyJSBridge {

    private String mAvatarUrl;
    private String mAuthor;
    private String mBio;

    private Context mContext;

    public MyJSBridge(Context context){
        this.mContext=context;
    }

    @JavascriptInterface
    public void setAvatarUrl(String avatarUrl){
        mAvatarUrl=avatarUrl;
    }

    @JavascriptInterface
    public void setAuthor(String author){
        mAuthor=author;
    }

    @JavascriptInterface
    public void setBio(String bio){
        mBio=bio;
    }

    @JavascriptInterface
    public void acquireImageUrl(String[] imageUrlArray){
        ArrayList<String> imageUrlList= new ArrayList<>(Arrays.asList(imageUrlArray));
        ImageDetailEvent event=new ImageDetailEvent();
        event.setImageUrl(imageUrlList);
        EventBus.getDefault().postSticky(event);
    }

    @JavascriptInterface
    public void startImageDetailActivity(String position){
        Intent intent=new Intent(mContext, ImageDetailActivity.class);
        intent.putExtra(ImageDetailFragment.IMAGE_POSITION,Integer.valueOf(position));
        mContext.startActivity(intent);
    }

    public String getAvatarUrl() {
        return mAvatarUrl;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getBio() {
        return mBio;
    }
}
