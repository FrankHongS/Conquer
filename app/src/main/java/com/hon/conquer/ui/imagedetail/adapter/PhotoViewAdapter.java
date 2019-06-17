package com.hon.conquer.ui.imagedetail.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.hon.conquer.R;
import com.hon.photopreviewlayout.PhotoView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

/**
 * Created by Frank on 2018/9/16.
 * E-mail:frank_hon@foxmail.com
 */

public class PhotoViewAdapter extends PagerAdapter {

    private List<String> mPhotoUrlList;

    public PhotoViewAdapter(List<String> urlList) {
        this.mPhotoUrlList = urlList;
    }

    @Override
    public int getCount() {
        return mPhotoUrlList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.layout_image_preview_item, container, false);
        bindView(view, position);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    private void bindView(View view, int position) {
        PhotoView photoView = view.findViewById(R.id.pv_photo_preview);
        ProgressBar progressBar = view.findViewById(R.id.pb_photo);

        Glide.with(view.getContext())
                .load(mPhotoUrlList.get(position))
                .apply(
                        new RequestOptions()
                                .placeholder(R.mipmap.placeholder)
                                .error(R.mipmap.error_image)
                )
                .into(new DrawableImageViewTarget(photoView) {
                    @Override
                    public void onLoadStarted(Drawable placeholder) {
                        super.onLoadStarted(placeholder);
                        progressBar.setVisibility(View.VISIBLE);
                        progressBar.setProgress(1);
                    }

                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        super.onResourceReady(resource, transition);
                        progressBar.setProgress(100);
                        progressBar.setVisibility(View.GONE);
                        photoView.setImageDrawable(resource);
                    }
                });
    }
}
