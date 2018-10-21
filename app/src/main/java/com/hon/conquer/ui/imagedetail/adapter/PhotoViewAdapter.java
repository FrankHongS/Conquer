package com.hon.conquer.ui.imagedetail.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.hon.conquer.R;
import com.hon.photopreviewlayout.PhotoView;

import java.util.List;

import timber.log.Timber;

/**
 * Created by Frank on 2018/9/16.
 * E-mail:frank_hon@foxmail.com
 */

public class PhotoViewAdapter extends PagerAdapter{

    private Context mContext;
    private List<String> mPhotoUrlList;

    public PhotoViewAdapter(Context context,List<String> urlList){
        this.mContext=context;
        this.mPhotoUrlList=urlList;
    }

    @Override
    public int getCount() {
        return mPhotoUrlList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.layout_image_preview_item,container,false);
        bindView(view,position);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    private void bindView(View view,int position) {
        Timber.d("bindView %s",mPhotoUrlList.get(position));
        PhotoView photoView=view.findViewById(R.id.pv_photo_preview);
        ProgressBar progressBar=view.findViewById(R.id.pb_photo);

        Glide.with(mContext)
                .load(mPhotoUrlList.get(position))
                .placeholder(R.mipmap.placeholder)
                .error(R.mipmap.error_image)
                .into(new GlideDrawableImageViewTarget(photoView){
                    @Override
                    public void onLoadStarted(Drawable placeholder) {
                        super.onLoadStarted(placeholder);
                        progressBar.setVisibility(View.VISIBLE);
                        progressBar.setProgress(1);
                    }

                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);
                        progressBar.setProgress(100);
                        progressBar.setVisibility(View.GONE);
                        photoView.setImageDrawable(resource);
                    }
                });
    }
}
