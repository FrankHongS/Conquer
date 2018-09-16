package com.hon.conquer.ui.imagedetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hon.conquer.R;
import com.hon.conquer.ui.imagedetail.adapter.PhotoViewAdapter;
import com.hon.conquer.util.ToastUtil;
import com.hon.conquer.vo.event.ImageDetailEvent;
import com.hon.photopreviewlayout.ImageData;
import com.hon.photopreviewlayout.PhotoPreviewLayout;
import com.hon.photopreviewlayout.PhotoViewPagerAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by Frank on 2018/5/27.
 * E-mail:frank_hon@foxmail.com
 */

public class ImageDetailFragment extends Fragment{

    public static final String IMAGE_POSITION = "image_position";
    
    private ViewPager mViewPager;
    private TextView mIndicatorText;

    private PagerAdapter mPagerAdapter;

    private List<String> mImageUrlList;
    private int mPosition;

    private int mPhotoCount=0;

    public ImageDetailFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_image_detail,container,false);
        initView(view);
        return view;
    }

    private void initView(View view){
        mViewPager=view.findViewById(R.id.vp_photo);
        mIndicatorText=view.findViewById(R.id.tv_photo_indicator);

        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                mIndicatorText.setText((position+1)+"/"+mPhotoCount);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPosition=getActivity().getIntent().getIntExtra(IMAGE_POSITION,2);

        if(mImageUrlList!=null&&mImageUrlList.size()>0){
            mPagerAdapter=new PhotoViewAdapter(getActivity(),mImageUrlList);
            mViewPager.setAdapter(mPagerAdapter);
            mViewPager.setCurrentItem(mPosition);

            mPhotoCount=mImageUrlList.size();
            mIndicatorText.setText((mPosition+1)+"/"+mPhotoCount);
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onImageDetailEvent(ImageDetailEvent event){
        mImageUrlList=event.getImageUrl();
        Log.d("onImageDetailEvent", "onImageDetailEvent: "+mImageUrlList);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
